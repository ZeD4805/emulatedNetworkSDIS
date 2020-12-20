import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        Node clientNode = new Node(address, 11111);
        clientNode.run();
        //Node serverNode = new Node(address, 22222);



    }

}
