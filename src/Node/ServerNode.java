package Node;

import java.net.*;
import java.io.*;

public class ServerNode{

    ServerSocket serverSocket;
    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;

    public ServerNode(int port) throws IOException {
        serverSocket = new ServerSocket(port);
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

    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    public String readLine() throws IOException {
        return in.readLine();
    }

    public void sendMessage(String msg){
        out.println(msg);
    }

    public int getSourcePort(){
        return serverSocket.getLocalPort();
    }

    public int getDestinationPort(){
        return clientSocket.getPort();
    }
}
