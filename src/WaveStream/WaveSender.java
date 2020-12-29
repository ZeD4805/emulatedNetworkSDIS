package WaveStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class WaveSender implements Runnable {
    WaveSender(Socket socket, int period) throws IOException { //thread sleep for sending may be needed
        this.socket = socket;
        printWriter = new PrintWriter(socket.getOutputStream());
        this.period = period;
        current = 0;

        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        current = (current+1)%period;

        double res = Math.sin(current / (double)period);
        printWriter.print(res);
    }

    int period;
    int current;

    Socket socket;
    PrintWriter printWriter;
}
