package WaveStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class WaveSender implements Runnable {
    public WaveSender(Socket socket, int period) throws IOException { //thread sleep for sending may be needed
        this.socket = socket;
        printWriter = new DataOutputStream(socket.getOutputStream());
        this.period = period;
        current = 0;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        while (!socket.isClosed()){
            current = (current+1)%period;
            try {
                Thread.sleep(500/30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double res = Math.sin((current / (double)period) * Math.PI * 2);
            try {
                printWriter.writeDouble(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    int period;
    int current;

    Socket socket;
    DataOutputStream printWriter;
}
