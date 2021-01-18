package ChannelModels;

public class DemonstrationChannel extends ChannelModel {

    DemonstrationChannel(int scale, int spontaneousCloseProb, int packetLossProb, int delayMean, int delayStandardDeviation){
        super();

        this.scale = scale;
        this.spontaneousCloseProb = spontaneousCloseProb;
        this.packetLossProb = packetLossProb;
        this.delayMean = delayMean;
        this.delayStandardDeviation = delayStandardDeviation;
    }

    @Override
    public boolean spontaneousClose() {
        return ((random.nextInt(scale)) < spontaneousCloseProb);
    }

    @Override
    public boolean packetLoss() {
        return ((random.nextInt(scale)) < packetLossProb);
    }

    @Override
    public int getDelayMs() {
        return delayMean + (int)(random.nextGaussian() * delayStandardDeviation);
    }

    int scale;
    int spontaneousCloseProb;
    int packetLossProb;
    int delayMean;
    int delayStandardDeviation;
}
