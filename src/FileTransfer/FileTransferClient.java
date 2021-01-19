package FileTransfer;

import BadSockets.BadSocket;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class FileTransferClient {
    public FileTransferClient(InetAddress address, int port) throws IOException {
        int receiveNum = 1000;

        Socket socket = null;

        while (receiveNum > 0){

            try {
                socket = new BadSocket((int)1e9, (int)1e7 * 3, 100, 10, 1);

                socket.connect(new InetSocketAddress(address, port));

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                Thread.sleep(30);

                out.writeInt(receiveNum);
                System.out.println("Still need " + receiveNum);

                while (!socket.isClosed() && receiveNum > 0){
                    in.read(new byte[256]);
                    receiveNum--;
                }
            }catch (IOException | InterruptedException e){ //socket closed
                //e.printStackTrace();
            }
        }

        System.out.println("Finished file transfer");

        socket.close();

    }
}
