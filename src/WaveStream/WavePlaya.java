package WaveStream;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;

/** Creates a simple real-time chart */
public class WavePlaya implements Runnable{

    public WavePlaya(Socket socket, int graphLen){
        data = new double[2][];
        data[0] = new double[graphLen]; //x
        data[1] = new double[graphLen]; //y
        for (int i = 0; i < graphLen; i++) {
            data[0][i] = i;
            data[1][i] = 0;
        }

        this.socket = socket;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        final XYChart chart =
                QuickChart.getChart(
                        "Sine wave double values reception", "x", "y", "received", data[0], data[1]);

        final SwingWrapper<XYChart> sw = new SwingWrapper<>(chart);
        sw.displayChart();

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
                    while (in.available() != 0){
                        newValue = in.readDouble();
                        updateData(newValue);
                    }
                }

                Thread.sleep(1000/30); //60 fps gamer style 8^)
                chart.updateXYSeries("received", data[0], data[1], null);
                sw.repaintChart();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateData(double newValue){
        if (data[0].length - 1 >= 0){
            System.arraycopy(data[1], 1, data[1], 0, data[1].length - 1);
        }
        data[1][data[1].length - 1] = newValue;
    }


    double[][] data;
    DataInputStream in;

    Socket socket;
}