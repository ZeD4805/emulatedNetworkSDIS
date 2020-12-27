import Node.*;

import java.io.IOException;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        InetAddress address = InetAddress.getLocalHost();
        //ServerNode serverNode = new ServerNode(11111);

        Packet c = new Packet("<11111/11111/69/0/111/delay/1000>HeyGuys!");
        System.out.println(c);
        Packet a = new Packet("<delay/19199>dadosaqui");
        Packet b = new Packet("dadosOnly");
        Packet closed = new Packet("<close>");

        EmulatorTCP server = new EmulatorTCP(11111);
        ClientNode clientNode = new ClientNode(address, 11111);
        Thread t_server = new Thread(server);
        Thread t_client = new Thread(clientNode);

        t_server.start();
        t_client.start();
        Thread.sleep(3000);
        clientNode.sendMessage("Hello there");
        clientNode.sendMessage("<delay/1000>General Kenobi");
        clientNode.sendMessage("<close>");

    }

}
