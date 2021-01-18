package ChannelModels;

public class RealisticCauchyChannel extends ChannelModel {

    public RealisticCauchyChannel(double spontaneousCloseProb, float cauchyMean, float cauchyScale, float cauchyNoticeableT){
        this.spontaneousCloseProb = spontaneousCloseProb;
        this.cauchyMean = cauchyMean;
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

        lastDelay = cauchyScale * Math.tan(Math.PI * x - Math.PI/2) + cauchyNoticeableT; //inverse commulative Cauchy to map a cauchy distribution to [0, 1.0]

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

    double cauchyMean;
    double cauchyScale;
    double cauchyNoticeableT;
}
