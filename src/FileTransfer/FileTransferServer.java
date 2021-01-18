package FileTransfer;

import WaveStream.WaveSender;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileTransferServer {
    public FileTransferServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);

        while(true){
            Socket acceptedSocket = serverSocket.accept();

            DataOutputStream out;
            DataInputStream in;
            try {

                out = new DataOutputStream(acceptedSocket.getOutputStream());
                in = new DataInputStream(acceptedSocket.getInputStream());

                String receivedStr = new String(in.readAllBytes());
                int remaining = Integer.parseInt(receivedStr);
                System.out.println(" " + remaining);

                for (int i = 0; i < remaining; i++) {
                    out.write(new byte[256]);
                    Thread.sleep(1); //actually idk
                    System.out.println("Sending packet " + i);
                }

                while (acceptedSocket.isConnected());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            /*Thread t = new Thread(){
                public void run(){
                    DataOutputStream out;
                    DataInputStream in;
                    try {

                        out = new DataOutputStream(acceptedSocket.getOutputStream());
                        in = new DataInputStream(acceptedSocket.getInputStream());

                        String receivedStr = new String(in.readAllBytes());
                        int remaining = Integer.parseInt(receivedStr);
                        System.out.println(" " + remaining);

                        for (int i = 0; i < remaining; i++) {
                            out.write(new byte[256]);
                            Thread.sleep(1); //actually idk
                            System.out.println("Sending packet " + i);
                        }

                        while (acceptedSocket.isConnected());
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();*/
        }
    }
}
