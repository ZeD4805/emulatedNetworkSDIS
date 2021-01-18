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
            socket = new BadSocket();

            socket.connect(new InetSocketAddress(address, port));

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String send = "" + receiveNum;
            out.write(send.getBytes());
            System.out.println(receiveNum);

            try {
                while (receiveNum > 0){
                    in.read(new byte[256]);
                    receiveNum--;
                }
            }catch (IOException e){ //socket closed
            }
        }

        System.out.println("Finished file transfer");

        socket.close();

    }
}
