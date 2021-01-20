package ChannelModels;

/**
 * Cauchy channel model
 */
public class CauchyChannel extends ChannelModel {

    /**
     * Constructor for Cauchy Channel
     *
     * @param spontaneousCloseProb probability to drop connection at each packet
     * @param cauchyScale scale factor of cauchy distribution
     * @param cauchyNoticeableT mean/threshold of distribution values
     */
    public CauchyChannel(double spontaneousCloseProb, double cauchyScale, double cauchyNoticeableT){
        this.spontaneousCloseProb = spontaneousCloseProb;
        this.cauchyScale = cauchyScale;
        this.cauchyNoticeableT = cauchyNoticeableT;

        lastDelay = 0;
    }

    @Override
    public boolean spontaneousClose() {
        return random.nextDouble() < spontaneousCloseProb;
    }

    @Override
    public boolean packetLoss() {
        double x = random.nextFloat();

        lastDelay = cauchyScale * Math.tan(Math.PI * x - Math.PI/2) - cauchyNoticeableT; //inverse commulative Cauchy to map a cauchy distribution to [0, 1.0]

        return lastDelay > 0;
    }

    @Override
    public int getDelayMs() {
        return ((int)lastDelay);
    }

    @Override
    public int getDelayNs() {
        return ((int)(lastDelay * 1000))%1000; //1.322... -> 1322 -> 322
    }

    double lastDelay;

    double spontaneousCloseProb;

    double cauchyScale;
    double cauchyNoticeableT;
}
