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

            Thread t = new Thread(){
                public void run(){
                    DataOutputStream out;
                    DataInputStream in;
                    try {

                        out = new DataOutputStream(acceptedSocket.getOutputStream());
                        in = new DataInputStream(acceptedSocket.getInputStream());

                        int remaining = in.readInt();
                        System.out.println("Sending remaining " + remaining);

                        for (int i = 0; !acceptedSocket.isClosed() && i < remaining; i++) {
                            out.write(new byte[256]);
                            Thread.sleep(1); //actually idk
                        }

                        acceptedSocket.close();
                    } catch (IOException | InterruptedException e) {
                    }
                }
            };
            t.start();
        }
    }
}
