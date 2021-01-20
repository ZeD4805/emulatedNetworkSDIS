import BadSockets.BadServerSocket;
import BadSockets.BadSocket;
import ChannelModels.CauchyChannel;
import ChannelModels.ChannelModel;
import ChannelModels.DemonstrationChannel;
import FileTransfer.BadFileTransferClient;
import FileTransfer.FileTransferServer;
import WaveStream.WavePlayer;
import WaveStream.WaveSender;
import WaveStream.WaveSenderServer;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for BadSocket/BadSocketServer capabilities showcase
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //serverPlayer();

        //clientPlayer();

        //fileTransferMain();

        //transferTimeTests();


        wavePlayerRealistic();

        //serverPlayer();
    }

    static void waveSenderServerMain() throws IOException {
        int port = 11121;

        Thread waveServerThread = new Thread(){
            public void run(){
                try {
                    WaveSenderServer waveSenderServer = new WaveSenderServer(port, 30, 250/30);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        waveServerThread.start();

        BadSocket badClientSocket = new BadSocket();
        badClientSocket.connect(new InetSocketAddress(InetAddress.getByName("localhost"), port));

        WavePlayer wavePlaya = new WavePlayer(badClientSocket, "Sine wave double values reception", 300, 1000/30, 1000/60);


        BadSocket badClientSocket2 = new BadSocket();
        badClientSocket2.connect(new InetSocketAddress(InetAddress.getByName("localhost"), port));

        WavePlayer wavePlaya2 = new WavePlayer(badClientSocket2, "Sine wave double values reception", 300, 1000/30, 1000/60);
    }

    static void serverPlayer() throws IOException {
        BadServerSocket badServerSocket = new BadServerSocket(11121);

        Socket client = new Socket();
        client.connect(badServerSocket.getLocalSocketAddress());

        BadSocket badSocket = badServerSocket.accept();


        WaveSender waveSender = new WaveSender(client, 30, 250/30);
        WavePlayer wavePlaya = new WavePlayer(badSocket, "Sine wave values reception", 300, 1000/30, 1000/60);

        Thread sender = new Thread(waveSender);
        sender.start();
        Thread receiver = new Thread(wavePlaya);
        receiver.start();
    }

    static void clientPlayer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(11121);

        ChannelModel channelModel = new DemonstrationChannel((int)1e3, 0, 1, 400, 200);
        BadSocket badClientSocket = new BadSocket(channelModel);
        badClientSocket.connect(serverSocket.getLocalSocketAddress());

        Socket socket = serverSocket.accept();


        WaveSender waveSender = new WaveSender(socket, 30, 250/30);
        WavePlayer wavePlaya = new WavePlayer(badClientSocket, "Sine wave double values reception", 300, 1000/30, 1000/60);

        BadSocket badClientSocket2 = new BadSocket(channelModel);
        badClientSocket2.connect(serverSocket.getLocalSocketAddress());

        Socket socket2 = serverSocket.accept();

        WaveSender waveSender2 = new WaveSender(socket2, 60, 250/60);
        WavePlayer wavePlaya2 = new WavePlayer(badClientSocket2, "Sine wave double values reception", 300, 1000/30, 1000/60);
    }

    static void fileTransferMain() {
        int port = 11121;

        Thread ftpServerThread = new Thread() {
            public void run() {
                try {
                    FileTransferServer fileTransferServer = new FileTransferServer(port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        ftpServerThread.start();

        CauchyChannel channel = new CauchyChannel(0.05, 0.5, 1);
        Thread ftpClientThread = new Thread() {
            public void run() {
                try {
                    BadFileTransferClient fileTransferClient = new BadFileTransferClient(InetAddress.getByName("localhost"), port, channel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        ftpClientThread.start();
    }

    static void transferTimeTests() throws InterruptedException {
        int port = 11121;

        Thread ftpServerThread = new Thread() {
            public void run() {
                try {
                    FileTransferServer fileTransferServer = new FileTransferServer(port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        ftpServerThread.start();

        ArrayList<Long> performancePointsBadSocket = new ArrayList<Long>();

        ArrayList<Double> scaleFactors = new ArrayList<Double>();


        for (int i = 0; i < 10; i++) {
            ArrayList<Long> badTimeDiffs = new ArrayList<Long>();
            CauchyChannel channel = new CauchyChannel(0.001, 0.5 + i, 1);
            for (int j = 0; j < 50; j++) {
                long start = System.nanoTime();
                Thread ftpClientThread = new Thread() {
                    public void run() {
                        try {
                            BadFileTransferClient badFileTransferClient = new BadFileTransferClient(InetAddress.getByName("localhost"), port, channel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ftpClientThread.start();
                ftpClientThread.join();
                long end = System.nanoTime();
                badTimeDiffs.add(end-start);
            }

            badTimeDiffs.remove((Object)Collections.min(badTimeDiffs));
            badTimeDiffs.remove((Object)Collections.max(badTimeDiffs));
            long badAverage = 0;
            for (long l : badTimeDiffs){
                badAverage += l/(badTimeDiffs.size() * 1000L);
            }
            performancePointsBadSocket.add(badAverage);
        }

        System.out.println("Bad socket timings: " + performancePointsBadSocket);
    }

    static void wavePlayerRealistic() throws IOException {
        ServerSocket serverSocket = new ServerSocket(11121);

        CauchyChannel channel = new CauchyChannel(0, 5, 0);
        //BadSocket badClientSocket = new BadSocket(channel);
        Socket badClientSocket = new Socket();
        badClientSocket.connect(serverSocket.getLocalSocketAddress());

        Socket socket = serverSocket.accept();


        WaveSender waveSender = new WaveSender(socket, 30, 250/30);
        WavePlayer wavePlaya = new WavePlayer(badClientSocket, "Ideal channel", 300, 1000/30, 1000/60);
    }
}
