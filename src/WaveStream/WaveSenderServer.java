package WaveStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WaveSenderServer {
    @SuppressWarnings("InfiniteLoopStatement")
    WaveSenderServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);

        while(true){
            Socket acceptedSocket = serverSocket.accept();

            Thread t = new Thread(){
                public void run(){
                    try {
                        WaveSender waveSender = new WaveSender(acceptedSocket, 30, 250/30);
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
