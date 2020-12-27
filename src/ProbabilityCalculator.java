import java.util.Random;

public class ProbabilityCalculator {
    private int scale;
    int spontaneousCloseProb;
    int packetLossProb;
    int delayMean;
    int delayStandardDeviation;

    private Random r;
    ProbabilityCalculator(int scale, int spontaneousCloseProb, int packetLossProb, int delayMean, int delayStandardDeviation){
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

    boolean spontaneousClose(){
        return ((r.nextInt(scale)) < spontaneousCloseProb);
    }

    boolean packetLoss(){
        return ((r.nextInt(scale)) < packetLossProb);
    }

    int getDelay(){
        return delayMean + (int)(r.nextGaussian() * delayStandardDeviation);
    }

    @Override
    public String toString() {
        return "ProbabilityCalculator{" +
                "scale=" + scale +
                ", spontaneousCloseProb=" + spontaneousCloseProb +
                ", packetLossProb=" + packetLossProb +
                ", delayMean=" + delayMean +
                ", delayStandardDeviation=" + delayStandardDeviation +
                '}';
    }
}
