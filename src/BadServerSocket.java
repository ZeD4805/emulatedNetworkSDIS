import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;

public class BadServerSocket extends ServerSocket {
    BadServerSocket(int port) throws IOException {
        super(port);
    }

    @Override
    public Socket accept() throws IOException {
        BadSocket b = new BadSocket();
        this.implAccept(b);

        return b;
    }
}
