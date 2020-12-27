import Node.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.TimeoutException;

public class EmulatorTCP implements Runnable{
    private ServerNode serverNode;
    private ClientNode clientNode;
    private OutputStream out;
    private InputStream in;
    ProbabilityCalculator probabilityCalculator;

    EmulatorTCP(int port) throws IOException {
        serverNode = new ServerNode(port);
        probabilityCalculator = new ProbabilityCalculator((int) 1e9, 1000);
    }

    public void startConnection() throws IOException, TimeoutException {
        if(!probabilityCalculator.isTrue())
            serverNode.startConnection();
        else{ //no reply
            System.out.println("Start connection timed out " + probabilityCalculator.toString());
            throw new TimeoutException(); //maybe?
        }
    }

    @Override
    public void run() {
        try {
            while (true){
                startConnection();
                while (!serverNode.isClosed()){
                    String received = serverNode.readLine();
                    Packet pReceived = new Packet(received);

                    if(pReceived.args.size() > 0){
                        String packetType = pReceived.args.get(0);
                        if("close".equals(packetType)){
                            close();
                            System.out.println("Server closed");
                        }
                        else if("delay".equals(packetType) && pReceived.args.size() > 1){
                            int delayAmount = Integer.parseInt(pReceived.args.get(1), 10);
                            Thread.sleep(delayAmount);
                            //add to inputStream
                           // Charset utf8 = StandardCharsets.UTF_8;
                            //out.write(utf8.encode(pReceived.data).array());
                        }
                    }

                    System.out.println(pReceived.toString());
                }
            }
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void syn_ack(){

    }

    public void sendMessage(String msg) {
        serverNode.sendMessage(msg);
    }

    public void close() throws IOException {
        serverNode.close();
    }
}
