import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        BadServerSocket badServerSocket = new BadServerSocket(11111);

        BadSocket waveSenda = new BadSocket();
        waveSenda.connect(badServerSocket.getLocalSocketAddress());

        badServerSocket.accept();
    }

}
