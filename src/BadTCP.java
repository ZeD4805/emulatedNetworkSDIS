import java.io.*;

public class BadTCP {
    BadTCP(int port) throws IOException {
        emulatorTCP = new EmulatorTCP(port);

        pIn = new PipedInputStream(emulatorTCP.getPipedOutputStream());
        pOut = new PipedOutputStream(emulatorTCP.getPipedInputStream());
    }
    public InputStream getInputStream() {
        return pIn;
    }

    public OutputStream getOutputStream(){
         return pOut;
    }

    public void accept(){ //TODO

    }

    public void close() throws IOException {
        emulatorTCP.close();
    }

    EmulatorTCP emulatorTCP;
    PipedInputStream pIn;
    PipedOutputStream pOut;
}
