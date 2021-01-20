package WaveStream;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Receives float values from a WaveSender and displays them on a graph of xChart.
 * Graph length (point number), graph update period and data update period can all be set on the constructor.
 */
public class WavePlayer implements Runnable{
    /**
     * Constructor for WavePlayer
     * @param socket socket to use
     * @param graphLen number of points in graph
     * @param graphTitle title of graph
     * @param graphUpdatePeriod period in milliseconds after which to update the graph (example 1000/30 for 30 updates per second)
     * @param dataUpdatePeriod period in milliseconds after which to update the data
     * @throws IOException Socket.connect may throw exception
     */
    public WavePlayer(Socket socket, String graphTitle, int graphLen, int graphUpdatePeriod, int dataUpdatePeriod) throws IOException {
        data = new double[2][];
        data[0] = new double[graphLen]; //x
        data[1] = new double[graphLen]; //y (sin values)
        for (int i = 0; i < graphLen; i++) { //graph initialization
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

        this.graphTitle = graphTitle;//"Sine wave double values reception";
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        final XYChart chart =
                QuickChart.getChart(
                        graphTitle, "x", "y", "received", data[0], data[1]);
        final SwingWrapper<XYChart> sw = new SwingWrapper<>(chart);
        sw.displayChart();

        //extra graph update thread
        Thread t = new Thread(){
            @SuppressWarnings("InfiniteLoopStatement")
            public void run(){
                try {
                    while (true){
                        while (!socket.isClosed()){
                            Thread.sleep(graphUpdatePeriod);

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
        while (!socket.isClosed()){ //data update cycle
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

                Thread.sleep(dataUpdatePeriod); //for display thread

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

    String graphTitle;

    int graphUpdatePeriod;
    int dataUpdatePeriod;

    boolean catchup;

    Socket socket;
}