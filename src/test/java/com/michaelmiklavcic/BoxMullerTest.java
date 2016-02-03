package com.michaelmiklavcic;

import org.junit.Assert;
import org.junit.Test;

import com.michaelmiklavcic.Distribution.BinType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BoxMullerTest {

    @Test
    public void generate_distribution() {
        double[] samples = BoxMuller.generate(10000, 5, 1);
        Distribution.draw(samples, 30, BinType.SQUARE_ROOT);
    }
    @Test
    public void generate_distribution_guava() {
        double sigma = 5;
        double mu = 1;
        double samples[] = Helper.pinch(BoxMuller.createSampleStream(sigma, mu), 10000);
        Distribution.draw(samples, 30, BinType.SQUARE_ROOT);
    }

    @Test
    public void testSample() {
        /*
        Let's make sure the generated samples have the expected first order statistics (i.e. mean and stddev)
         */
        double sigma = 5;
        double mu = 1;
        double samples[] = Helper.pinch(BoxMuller.createSampleStream(new Random(0), sigma, mu), 10000000);
        List<Double> firstOrders = computeFirstOrderStats(samples);
        Assert.assertEquals(mu, firstOrders.get(0), 1e-02);
        Assert.assertEquals(sigma, firstOrders.get(1), 1e-02);

    }
    public static List<Double> computeFirstOrderStats(double[] sample) {
        double sum = 0.0;
        double sumSquared = 0.0;
        for(int i = 0;i < sample.length;++i) {
            sum += sample[i];
            sumSquared += sample[i] * sample[i];
        }
        double mean = sum / sample.length;
        /*
        Just in case you're wondering, and because it's kinda neat, here's the derivation of variance:
        We know that variance of a random variable (denoted Var(X)) is the expected value of the variable adjusted for the mean squared
        aka E[(X - E[X])^2].  So,
        E[(X - E[X])^2] = E[ X^2 - 2XE[X] + E[X]^2 ]  -- by FOIL from algebra ;)
                        = E[X^2] - 2E[X]^2 + E[X]^2   -- because expected value is a linear operator and therefore distributes and allows for scalar arithmetic to be brought out
                        = E[X^2] - E[X]
        So, the variance is the mean of the squares of the values - the mean of the values.
        The standard deviation is the sqrt of variance.
         */
        double variance = sumSquared/sample.length - mean;
        double stddev = Math.sqrt(variance);
        return Arrays.asList(mean, stddev);
    }
}
