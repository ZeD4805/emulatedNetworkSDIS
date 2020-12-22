import java.util.Random;

public class ProbabilityCalculator {
    private int scale;
    int threshold;

    private Random r;
    ProbabilityCalculator(int scale, int threshold){
        r = new Random();
        this.scale = scale;
        this.threshold = threshold;
    }

    boolean isTrue(){
        return ((r.nextInt(scale)) < threshold);
    }

    @Override
    public String toString() {
        return "ProbabilityCalculator{" +
                "scale=" + scale +
                ", threshold=" + threshold +
                '}';
    }
}
