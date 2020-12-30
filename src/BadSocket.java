import java.io.*;
import java.net.Socket;

public class BadSocket extends Socket implements Runnable {
    BadSocket() throws IOException {
        super();
        reader = new BufferedInputStream(super.getInputStream());
    }

    BadSocket(Socket s) throws IOException {
        super(s.getInetAddress(), s.getPort(), s.getLocalAddress(), s.getLocalPort());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new PipedInputStream(pOut);
    }

    @Override
    public void run() {
        byte[] byteBuf = new byte[2048];
        int count;

        while (!super.isClosed()) {
            try {
                if ((count = reader.read(byteBuf)) != -1) {
                    //TODO insert delay maybe
                    pOut.write(byteBuf, 0, count);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    BufferedInputStream reader;
    PipedOutputStream pOut;
}
