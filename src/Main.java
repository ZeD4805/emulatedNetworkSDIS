import Node.*;

import java.io.IOException;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        InetAddress address = InetAddress.getLocalHost();
        //ServerNode serverNode = new ServerNode(11111);

        /*ProbabilityCalculator pb = new ProbabilityCalculator((int)1e9, 0, 0, 1000, 100);
        int delay = pb.getDelay();

        Packet c = new Packet("<11111/11111/69/0/111/delay/1000>HeyGuys!");
        System.out.println(c);
        Packet a = new Packet("<delay/19199>dadosaqui");
        Packet b = new Packet("dadosOnly");
        Packet closed = new Packet("<close>");*/



        EmulatorTCP server = new EmulatorTCP(11111);
        ClientNode clientNode = new ClientNode(address, 11111);
        Thread t_server = new Thread(server);
        Thread t_client = new Thread(clientNode);

        t_server.start();
        t_client.start();
        Thread.sleep(3000);

        ProbabilityCalculator pb = new ProbabilityCalculator((int)1e9, 0, 0, 1000, 100);
        int delay = pb.getDelay();

        clientNode.sendMessage("<11111/11111/69/111/" + delay + ">Hello there");
        delay = pb.getDelay();
        clientNode.sendMessage("<11111/11111/69/111/" + delay + ">General Kenobi");
        delay = pb.getDelay();
        clientNode.sendMessage("<11111/11111/69/111/" + delay + ">You're a bold one");

        Thread.sleep(3000);
        clientNode.stopConnection();
        server.close();
    }

}
