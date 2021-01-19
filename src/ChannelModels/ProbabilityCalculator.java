package ChannelModels;

import java.util.Random;

public class ProbabilityCalculator {
    private final int scale;
    int spontaneousCloseProb;
    int packetLossProb;
    int delayMean;
    int delayStandardDeviation;

    private final Random r;
    public ProbabilityCalculator(int scale, int spontaneousCloseProb, int packetLossProb, int delayMean, int delayStandardDeviation){
        r = new Random();
        this.scale = scale;
        this.spontaneousCloseProb = spontaneousCloseProb;
        this.packetLossProb = packetLossProb;
        this.delayMean = delayMean;
        this.delayStandardDeviation = delayStandardDeviation;
    }

    int getNextInt(){
        return r.nextInt();
    }

    public boolean spontaneousClose(){
        return ((r.nextInt(scale)) < spontaneousCloseProb);
    }

    public boolean packetLoss(){
        return ((r.nextInt(scale)) < packetLossProb);
    }

    public int getDelay(){
        return delayMean + (int)(r.nextGaussian() * delayStandardDeviation);
    }
}
