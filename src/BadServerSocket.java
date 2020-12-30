import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BadServerSocket extends ServerSocket {
    BadServerSocket() throws IOException {
        super();

    }

    @Override
    public Socket accept() throws IOException {
        return new BadSocket(super.accept());
    }
}
