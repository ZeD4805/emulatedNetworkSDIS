package WaveStream;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.internal.ChartBuilder;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.theme.Theme;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class WavePlaya implements Runnable{
    public WavePlaya(Socket socket, int graphLen, int graphUpdatePeriod, int dataUpdatePeriod) throws IOException {
        data = new double[2][];
        data[0] = new double[graphLen]; //x
        data[1] = new double[graphLen]; //y
        for (int i = 0; i < graphLen; i++) {
            data[0][i] = i;
            data[1][i] = 0;
        }

        this.graphUpdatePeriod = graphUpdatePeriod;
        this.dataUpdatePeriod = dataUpdatePeriod;
        catchup = false;

        this.socket = socket;
        if(socket.isClosed())
            socket.connect(socket.getLocalSocketAddress());

        Thread t = new Thread(this);
        t.start();
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        final XYChart chart =
                QuickChart.getChart(
                        "Sine wave double values reception", "x", "y", "received", data[0], data[1]);
        final SwingWrapper<XYChart> sw = new SwingWrapper<>(chart);
        sw.displayChart();

        Thread t = new Thread(){
            @SuppressWarnings("InfiniteLoopStatement")
            public void run(){
                try {
                    while (true){
                        while (!socket.isClosed()){
                            Thread.sleep(graphUpdatePeriod); //60 fps gamer style 8^)

                            chart.updateXYSeries("received", data[0], data[1], null);
                            sw.repaintChart();
                        }
                        socket.connect(socket.getLocalSocketAddress()); //if socket closed, reopen it
                    }

                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

            double newValue;
        while (!socket.isClosed()){
            try {
                if(in.available() == 0){
                    newValue = data[1][data[1].length - 1];
                    updateData(newValue);
                }
                else{
                    catchup = true;
                    while (in.available() != 0){ //if - consume one at a time //while - try to get as many asap
                        newValue = in.readDouble();
                        updateData(newValue);
                    }
                    catchup = false;
                }
                /*newValue = in.readDouble();
                updateData(newValue);*/

                Thread.sleep(dataUpdatePeriod); //for display thread
                //Thread.sleep(1000/60); //60 fps gamer style 8^)
                //chart.updateXYSeries("received", data[0], data[1], null);
                //sw.repaintChart();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateData(double newValue){
        if (data[1].length - 1 >= 0){
            System.arraycopy(data[1], 1, data[1], 0, data[1].length - 1);
        }
        data[1][data[1].length - 1] = newValue;
    }

    double[][] data;
    DataInputStream in;

    int graphUpdatePeriod;
    int dataUpdatePeriod;

    boolean catchup;

    Socket socket;
}