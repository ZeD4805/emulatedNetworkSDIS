package FileTransfer;

import BadSockets.BadSocket;
import ChannelModels.CauchyChannel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * File transfer Client with BadSocket. If connection drops, before all 100 256Byte packets are exchanged,
 * the client tries to reconnect and ask for the remaining ones.
 */
public class BadFileTransferClient {
    /**
     * Constructor
     * @param address Inet address
     * @param port port
     * @param channel channel model
     * @throws IOException
     */
    public BadFileTransferClient(InetAddress address, int port, CauchyChannel channel) throws IOException {
        int receiveNum = 100;

        Socket socket = null;
        while (receiveNum > 0){

            try {
                socket = new BadSocket(channel);

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
