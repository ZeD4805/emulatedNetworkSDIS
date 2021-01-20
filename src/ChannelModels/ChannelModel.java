package ChannelModels;

import java.util.Random;

/**
 * Class that defines connection channel properties and dictates when a close/delay happens
 */
public abstract class ChannelModel {
    protected Random random;

    /**
     * Constructor for ChannelModel
     * Initializes Random
     */
    ChannelModel(){
        random = new Random();
    }

    /**
     * @return If a spontaneous close happens
     */
    public boolean spontaneousClose(){
        return false;
    }

    /**
     * @return  If a delay due to packet loss
     */
    public boolean packetLoss(){
        return false;
    }

    /**
     * @return Get the millisecond part of the delay (for Thread.sleep call)
     */
    public int getDelayMs(){
        return 0;
    }

    /**
     * @return Get the nanosecond part of the delay (for Thread.sleep call)
     */
    public int getDelayNs(){
        return 0;
    }
}
