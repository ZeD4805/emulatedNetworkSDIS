package FileTransfer;

import WaveStream.WaveSender;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server for FileTransferClient/BadFileTransferClient. Gets an integer and sends 256bytes that number of times.
 */
public class FileTransferServer {
    /** Constructor
     * @param port port
     * @throws IOException from stream interactions
     */
    public FileTransferServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);

        while(true){
            Socket acceptedSocket = serverSocket.accept();

            Thread t = new Thread(){
                public void run(){
                    DataOutputStream out;
                    DataInputStream in;
                    int i = 0;

                    try {

                        out = new DataOutputStream(acceptedSocket.getOutputStream());
                        in = new DataInputStream(acceptedSocket.getInputStream());

                        int remaining = in.readInt();
                        for (i = 0; !acceptedSocket.isClosed() && i < remaining; i++) {
                            out.write(new byte[256]);
                            //Thread.sleep(0, 100); //actually idk
                        }

                        acceptedSocket.close();
                    } catch (IOException e) {
                    }
                }
            };
            t.start();
        }
    }
}
