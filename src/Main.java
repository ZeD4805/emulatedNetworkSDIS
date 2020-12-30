import WaveStream.WavePlaya;
import WaveStream.WaveSender;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        BadServerSocket badServerSocket = new BadServerSocket(11111);

        Socket client = new Socket();
        client.connect(badServerSocket.getLocalSocketAddress());

        BadSocket badSocket = badServerSocket.accept();


        WaveSender waveSender = new WaveSender(client, 30);//Integer.MAX_VALUE);
        WavePlaya wavePlaya = new WavePlaya(badSocket, 100);

        Thread sender = new Thread(waveSender);
        sender.start();
        Thread receiver = new Thread(wavePlaya);
        receiver.start();
    }

}
