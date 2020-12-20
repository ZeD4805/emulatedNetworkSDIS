import Node.*;

import java.io.IOException;
import java.util.Random;

public class EmulatorTCP {
    private ServerNode serverNode;
    private ClientNode clientNode;
    ProbabilityCalculator probabilityCalculator;

    EmulatorTCP(int port) throws IOException {
        serverNode = new ServerNode(port);
        probabilityCalculator = new ProbabilityCalculator((int) 1e9, 1000);
    }

    public void startConnection() throws IOException {
        if(probabilityCalculator.isTrue())
            serverNode.startConnection();
        else{

        }
    }

    public void close(){

    }
}
