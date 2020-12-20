package Node;

import java.net.*;
import java.io.*;

public class ServerNode implements Runnable{

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ServerNode(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            System.out.println("Pa de Server");
            //server loop
            while (true){
                startConnection();
                while (!serverSocket.isClosed()){
                    String received = in.readLine();
                    System.out.println("Server received: " + received);
                    if("close".equals(received)){
                        close();
                        System.out.println("Server closed");
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Pa de Server 2");
    }

    public void startConnection() throws IOException {
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void close() throws  IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
