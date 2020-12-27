package Node;

import java.io.*;
import java.net.*;

public class ClientNode implements Runnable{
    private InetAddress address;
    final int PORT;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientNode(InetAddress address, int port) {
        this.address = address;
        this.PORT = port;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            startConnection();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startConnection() throws IOException {
        clientSocket = new Socket(address, PORT);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public void sendMessage(String msg){
        out.println(msg);
    }
}


