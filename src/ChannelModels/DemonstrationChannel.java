package ChannelModels;

/**
 * Generic Gaussian distribution channel.
 */
public class DemonstrationChannel extends ChannelModel {

    /**
     * Constructor that defines the properties of the channel
     *
     * If number from 0 to scale is inferior to spontaneousCloseProb, we get a spontaneous close.
     * If number from 0 to scale is inferior to packetLoss, we get a delay.
     *
     * @param scale integer that defines the scale for the probability
     * @param spontaneousCloseProb limit for number spontaneous socket closing
     * @param packetLossProb limit for number delay
     * @param delayMean mean for delay
     * @param delayStandardDeviation standard deviation for delay
     */
    public DemonstrationChannel(int scale, int spontaneousCloseProb, int packetLossProb, int delayMean, int delayStandardDeviation){
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
        int delay = delayMean + (int)(random.nextGaussian() * delayStandardDeviation);
        return Math.max(delay, 0);
    }

    int scale;
    int spontaneousCloseProb;
    int packetLossProb;
    int delayMean;
    int delayStandardDeviation;
}
