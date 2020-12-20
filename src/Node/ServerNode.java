package Node;

import java.net.*;
import java.io.*;

public class ServerNode implements Runnable{

    final int PORT;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ServerNode(int port) {
        this.PORT = port;
    }

    @Override
    public void run() {
        try {
            System.out.println("Pa de Server");
            startConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Pa de Server 2");
    }

    public void startConnection() throws IOException {
        serverSocket = new ServerSocket(PORT);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String greeting = in.readLine();
        if ("hello server".equals(greeting)) {
            out.println("hello client");
        }
        else {
            out.println("unrecognised greeting");
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
