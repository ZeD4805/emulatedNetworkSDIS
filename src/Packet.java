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
        }

        data = str.substring(index + 1);
    }

    @Override
    public String toString() {
        return "Packet{" +
                "args=" + args +
                ", data='" + data + '\'' +
                '}';
    }

    ArrayList<String> args;
    String data;
}
