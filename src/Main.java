import WaveStream.WavePlaya;
import WaveStream.WaveSender;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        //serverPlayer();
        clientPlayer();
    }

    static void serverPlayer() throws IOException {
        BadServerSocket badServerSocket = new BadServerSocket(11111);

        Socket client = new Socket();
        client.connect(badServerSocket.getLocalSocketAddress());

        BadSocket badSocket = badServerSocket.accept();


        WaveSender waveSender = new WaveSender(client, 30, 250/30);//Integer.MAX_VALUE);
        WavePlaya wavePlaya = new WavePlaya(badSocket, 300, 1000/30, 1000/60);

        Thread sender = new Thread(waveSender);
        sender.start();
        Thread receiver = new Thread(wavePlaya);
        receiver.start();
    }

    static void clientPlayer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(11111);

        BadSocket badClientSocket = new BadSocket();
        badClientSocket.connect(serverSocket.getLocalSocketAddress());

        Socket socket = serverSocket.accept();


        WaveSender waveSender = new WaveSender(socket, 30, 250/30);
        WavePlaya wavePlaya = new WavePlaya(badClientSocket, 300, 1000/30, 1000/60);

        BadSocket badClientSocket2 = new BadSocket();
        badClientSocket2.connect(serverSocket.getLocalSocketAddress());

        Socket socket2 = serverSocket.accept();

        WaveSender waveSender2 = new WaveSender(socket2, 30, 250/30);
        WavePlaya wavePlaya2 = new WavePlaya(badClientSocket2, 300, 1000/30, 1000/60);
    }
}
