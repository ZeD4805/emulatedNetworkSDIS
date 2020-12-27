import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
    e.g.
        Packet a = new Packet("<delay/19199>dadosaqui");
        Packet b = new Packet("dadosOnly");
        Packet closed = new Packet("<close>");
 */

public class Packet {
    Packet(String str){
        args = new ArrayList<>();

        int index = str.indexOf('>');
        if(index != -1){
            String sub = str.substring(1, index);
            Collections.addAll(args, sub.split("/"));
            if (args.size() >= 5){
                source_port = Integer.parseInt(args.get(0));
                destination_port = Integer.parseInt(args.get(1));
                sequence_number = Integer.parseInt(args.get(2));
                acknowledgement_number = Integer.parseInt(args.get(3));
                int flags = Integer.parseInt(args.get(4));

                if (flags/100 == 1){
                    SYN = true;
                    flags -= 100;
                }
                if (flags/10 == 1){
                    ACK = true;
                    flags -= 10;
                }
                if (flags == 1){
                    FIN = true;
                }
                for(int i=0 ; i<4; i++)
                    args.remove(0);
            }

        }

        data = str.substring(index + 1);

    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("<" + source_port + "/" + destination_port + "/" + sequence_number + "/" + acknowledgement_number + "/" + ((SYN ? 100 : 0) + (ACK ? 10 : 0) + (FIN ? 1 : 0)));
        for (String str:args){
            result.append("/").append(str);
        }
        result.append(">").append(data);
        return result.toString();
    }

    int source_port;
    int destination_port;
    int sequence_number;
    int acknowledgement_number;
    boolean SYN = false, ACK=false, FIN=false;
    ArrayList<String> args; //can also contain flags like SYN ACK & FIN
    String data;
}
