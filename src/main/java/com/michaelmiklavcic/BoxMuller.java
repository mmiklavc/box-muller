package com.michaelmiklavcic;

import java.util.Random;

public class BoxMuller {

    private static double z0, z1;
    private static boolean generate = false;

    public static double[] generate(int numSamples, double mu, double sigma) {
        double[] samples = new double[numSamples];

        for (int i = 0; i < numSamples; i++) {
            samples[i] = generate(mu, sigma);
        }

        return samples;
    }

    public static double generate(double mu, double sigma) {
        final double epsilon = Double.MIN_VALUE;
        final double twoPi = 2.0 * 3.14159265358979323846;

        generate = !generate;
        if (!generate) {
            return z1 * sigma + mu;
        }

        double u1 = 0, u2 = 0;
        do {
            u1 = new Random().nextDouble();
            u2 = new Random().nextDouble();
        } while (u1 <= epsilon);

        z0 = Math.sqrt(-2.0 * Math.log(u1)) * Math.cos(twoPi * u2);
        z1 = Math.sqrt(-2.0 * Math.log(u1)) * Math.sin(twoPi * u2);
        return z0 * sigma + mu;
    }

}
