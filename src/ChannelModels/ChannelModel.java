package ChannelModels;

import java.util.Random;

public abstract class ChannelModel {
    protected Random random;

    ChannelModel(){
        random = new Random();
    }

    public boolean spontaneousClose(){
        return false;
    }

    public boolean packetLoss(){
        return false;
    }

    public int getDelayMs(){
        return 0;
    }

    public int getDelayNs(){
        return 0;
    }
}
