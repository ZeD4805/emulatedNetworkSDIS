package BadSockets;

import java.io.IOException;
import java.net.ServerSocket;

public class BadServerSocket extends ServerSocket {
    public BadServerSocket(int port) throws IOException {
        super(port);
    }

    @Override
    public BadSocket accept() throws IOException {
        BadSocket b = new BadSocket();
        this.implAccept(b);

        return b;
    }
}
