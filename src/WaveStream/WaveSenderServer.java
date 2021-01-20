package WaveStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server for receiving WavePlayer requests, on each accept the ServerSocket does, it creates a thread with a WaveSender
 * to work on the request.
 */
public class WaveSenderServer {
    /**
     * Constructor
     * @param port port
     * @param wavePeriod wave period (30 points for a wave create a wave with period 30)
     * @param sendPeriod float sending interval, a value is sent every sendPeriod milliseconds
     * @throws IOException from Server.accept call
     */
    @SuppressWarnings("InfiniteLoopStatement")
    public WaveSenderServer(int port, int wavePeriod, int sendPeriod) throws IOException {
        this.serverSocket = new ServerSocket(port);

        while(true){
            Socket acceptedSocket = serverSocket.accept();

            Thread t = new Thread(){
                public void run(){
                    try {
                        WaveSender waveSender = new WaveSender(acceptedSocket, wavePeriod, sendPeriod);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
    }
    ServerSocket serverSocket;
}
