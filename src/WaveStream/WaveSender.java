package WaveStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Sends sin wave to WavePlayeer
 */
public class WaveSender implements Runnable {

    /**
     * Constructor
     *
     * @param socket socket
     * @param wavePeriod wave period (30 points for a wave create a wave with period 30)
     * @param sendPeriod float sending interval, a value is sent every sendPeriod milliseconds
     * @throws IOException from Socket.getOutputStream
     */
    public WaveSender(Socket socket, int wavePeriod, int sendPeriod) throws IOException { //thread sleep for sending may be needed
        this.socket = socket;
        printWriter = new DataOutputStream(socket.getOutputStream());
        this.wavePeriod = wavePeriod;
        current = 0;
        this.sendPeriod = sendPeriod;

        Thread t = new Thread(this);
        t.start();
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        while (!socket.isClosed()){
            current = (current+1)% wavePeriod;
            try {
                Thread.sleep(sendPeriod);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double res = Math.sin((current / (double) wavePeriod) * Math.PI * 2);
            try {
                printWriter.writeDouble(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    int wavePeriod;
    int current;
    int sendPeriod;

    Socket socket;
    DataOutputStream printWriter;
}
