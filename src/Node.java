import java.io.*;
import java.net.*;

public class Node implements Runnable{
    private InetAddress address;
    private int port;
    private Socket socket;


    Node(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        while(true)
            System.out.println("Pa");
    }

    public void startConnection() {
        
    }
}


