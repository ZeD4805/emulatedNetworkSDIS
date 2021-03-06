package FileTransfer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
/**
 * File transfer Client with regular Socket. If connection drops, before all 100 256Byte packets are exchanged,
 * the client tries to reconnect and ask for the remaining ones (shouldn't happen in localhost).
 */
public class FileTransferClient {
    /**
     * Constructor
     *
     * @param address Inet address
     * @param port port
     * @throws IOException from data stream interactions
     */
    public FileTransferClient(InetAddress address, int port) throws IOException {
        int receiveNum = 100;

        Socket socket = null;
        while (receiveNum > 0){

            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(address, port));

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                //Thread.sleep(30);
                System.out.println("Request " + receiveNum);

                out.writeInt(receiveNum);

                while (!socket.isClosed() && receiveNum > 0){
                    in.read(new byte[256]);
                    receiveNum--;
                }
            }catch (IOException e){ //socket closed
                //e.printStackTrace();
            }
        }

        System.out.println("Finished file transfer");

        socket.close();
    }
}
