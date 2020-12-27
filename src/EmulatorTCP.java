import Node.*;

import java.io.*;
import java.util.concurrent.TimeoutException;

public class EmulatorTCP implements Runnable{
    private final ServerNode serverNode;
    private ClientNode clientNode;
    PipedInputStream pIn;
    BufferedReader in;
    PipedOutputStream pOut;
    PrintWriter out;

    ProbabilityCalculator probabilityCalculator;

    int acknowledgementNumber = 0;
    boolean SYN, ACK, FIN;

    FileWriter fw;

    EmulatorTCP(int port) throws IOException {
        serverNode = new ServerNode(port);
        probabilityCalculator = new ProbabilityCalculator((int) 1e9,
                                            1000,
                                            1000000,
                                            1000,
                                            100);

        //initialize piped streams
        pOut = new PipedOutputStream();
        out = new PrintWriter(pOut);

        pIn = new PipedInputStream();
        in = new BufferedReader(new InputStreamReader(pIn));

        //initialize logging
        fw = new FileWriter("log.txt");
        fw.write(probabilityCalculator.toString());

        //start "run" thread
        Thread t = new Thread(this);
        t.start();
    }

    PipedInputStream getPipedInputStream(){
        return pIn;
    }

    PipedOutputStream getPipedOutputStream(){
        return pOut;
    }

    public void startConnection() throws IOException{
        serverNode.startConnection();
        String synPacket = serverNode.readLine();
        fw.write("Received: " + synPacket + "\n");

        SYN = true;
        ACK = true;
        FIN = false;
        sendMessage("");

    }

    @Override
    public void run() {
        while (true){
            try {
            startConnection();
            while (!serverNode.isClosed()){
                String received = serverNode.readLine();
                if(received == null)
                    continue;
                Packet pReceived = new Packet(received);
                fw.write("Received: " + pReceived.toString() + "\n");

                if(true){ //TODO use ProbabilityCalculator to simulate packet loss
                    out.write(pReceived.data);

                    sendACK();

                    resetFlags();
                    sendMessage(pReceived.data);
                }

                /*if(pReceived.args.size() > 0){
                    String packetType = pReceived.args.get(0);
                    if("close".equals(packetType)){
                        close();
                        System.out.println("Server closed");
                    }
                    else if("delay".equals(packetType) && pReceived.args.size() > 1){
                        int delayAmount = Integer.parseInt(pReceived.args.get(1), 10);
                        Thread.sleep(delayAmount);
                        //add to inputStream
                        out.println(pReceived.data);
                    }
                }*/
                System.out.println(pReceived.toString());
            }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

    }

    public void resetFlags(){
        SYN = false;
        ACK = false;
        FIN = false;
    }

    public void sendACK() throws IOException {
        SYN = false;
        ACK = true;
        FIN = false;
        sendMessage("");
    }

    public void sendMessage(String msg) throws IOException {
        String prefix = "<" + serverNode.getSourcePort() + "/" +
                        serverNode.getDestinationPort() + "/" +
                        acknowledgementNumber + "/" +
                        ((SYN ? 100 : 0) + (ACK ? 10 : 0) + (FIN ? 1 : 0)) + "/"+
                        probabilityCalculator.getDelay();

        msg = prefix + ">" + msg;

        serverNode.sendMessage(msg);
        fw.write("Sent: " + msg + "\n");
    }

    public void close() throws IOException {
        serverNode.close();
        fw.close();
    }
}
