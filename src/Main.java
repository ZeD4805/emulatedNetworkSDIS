import Node.*;

import java.io.IOException;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException {
        InetAddress address = InetAddress.getLocalHost();
        ServerNode serverNode = new ServerNode(11111);
        ClientNode clientNode = new ClientNode(address, 22222);
        Thread t_server = new Thread(serverNode);
        Thread t_client = new Thread(clientNode);

        t_server.start();
        t_client.start();
    }

}
