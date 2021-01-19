package BadSockets;



import ChannelModels.ProbabilityCalculator;

import java.io.*;
import java.net.Socket;

public class BadSocket extends Socket implements Runnable {
    public BadSocket() {
        super();
        int scale = (int) 1e3;
        pc = new ProbabilityCalculator(scale, 0, 10, 400, 50);
    }

    public BadSocket(int scale, int spontaneousCloseProb, int packetLossProb, int delayMean, int delayStandardDeviation){
        super();
        pc = new ProbabilityCalculator(scale, spontaneousCloseProb, packetLossProb, delayMean, delayStandardDeviation);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        pOut = new PipedOutputStream();
        reader = new BufferedInputStream(super.getInputStream());
        out = new PipedInputStream(pOut);
        Thread t = new Thread(this);
        t.start();
        return out;
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        byte[] byteBuf = new byte[2048];
        int count;

        while (!super.isClosed()) {
            try {
                if(pc.spontaneousClose()) {
                    pOut.close();
                    reader.close();
                    out.close();
                    this.close();
                    return;
                }
                else if ((count = reader.read(byteBuf)) != -1) { //while
                    if(pc.packetLoss())
                        Thread.sleep(pc.getDelay());
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

    ProbabilityCalculator pc;
}
