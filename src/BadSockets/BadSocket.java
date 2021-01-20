package BadSockets;

import ChannelModels.ChannelModel;
import ChannelModels.DemonstrationChannel;

import java.io.*;
import java.net.Socket;

/**
 * Class that behaves as a Socket while injecting delays and closing connections according to channel properties
 * Channel properties can be defined on creation of the Socket
 */
public class BadSocket extends Socket implements Runnable {
    /**
     * Generic constructor of BadSocket.
     * Uses DemonstrationChannel with parameters:
     * scale                    1e3
     * spontaneousCloseProb     0
     * packetLossProb           0
     * delayMean                400
     * delayStandardDeviation   50
     */
    public BadSocket() {
        super();
        int scale = (int) 1e3;
        pc = new DemonstrationChannel(scale, 0, 10, 400, 50);
    }

    /**
     * Generic channel definition constructor of BadSocket
     * @param channel defines channel model
     */
    public BadSocket(ChannelModel channel){
        pc = channel;
    }

    /**
     * Getter for socket input stream.
     * Starts a thread for the badSocket data flow interference.
     * Through piped streams allows for interfering with inbound data before sending it to the application.
     *
     * @return input stream
     * @throws IOException from creation of piped streams
     */
    @Override
    public InputStream getInputStream() throws IOException {
        pOut = new PipedOutputStream();
        reader = new BufferedInputStream(super.getInputStream());
        out = new PipedInputStream(pOut);
        Thread t = new Thread(this);
        t.setPriority(Thread.NORM_PRIORITY + 1);
        t.start();
        return out;
    }


    /**
     * Run function for running the badSocket.
     *
     * Each time there is something to read, calculates if it can spontaneously close or produce a delay.
     */
    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        byte[] byteBuf = new byte[2048];
        int count;

        while (!super.isClosed()) {
            try {
                if ((count = reader.read(byteBuf)) != -1) { //while
                    if(pc.spontaneousClose()) {
                        pOut.close();
                        reader.close();
                        out.close();
                        this.close();
                        return;
                    }

                    if(pc.packetLoss())
                        Thread.sleep(pc.getDelayMs(), pc.getDelayNs());
                    pOut.write(byteBuf, 0, count);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    BufferedInputStream reader;
    PipedOutputStream pOut;
    InputStream out;

    ChannelModel pc;
}
