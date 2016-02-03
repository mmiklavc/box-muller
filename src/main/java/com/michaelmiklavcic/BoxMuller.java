package com.michaelmiklavcic;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BoxMuller {
    public static final double PI =3.14159265358979323846;
    public static final double TWO_PI = 2*PI;
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
        return generate(mu, sigma, Double.MIN_VALUE);
    }
    public static double generate(double mu, double sigma, double epsilon) {

        generate = !generate;
        if (!generate) {
            return z1 * sigma + mu;
        }
        List<Double> us = Uniform.generateSamplePair(new Random(), epsilon);
        double u1 = us.get(0);
        double u2 = us.get(1);
        return transform(mu, sigma, u1, u2);
    }

    public synchronized static double transform(double mu, double sigma, double u1, double u2) {
        z0 = Math.sqrt(-2.0 * Math.log(u1)) * Math.cos(TWO_PI* u2);
        z1 = Math.sqrt(-2.0 * Math.log(u1)) * Math.sin(TWO_PI* u2);
        return z0 * sigma + mu;
    }

    public static Iterable<Double> createSampleStream(final Iterator<Double> uniformSampleStream, double sigma, double mu) {
        return createSampleStream(new Iterable<Double>() {
            @Override
            public Iterator<Double> iterator() {
                return uniformSampleStream;
            }
        }, sigma, mu);
    }
    public static Iterable<Double> createSampleStream(Iterable<Double> uniformSampleStream, double sigma, double mu) {
        Iterable<List<Double>> uniformPairs = Iterables.partition(uniformSampleStream, 2);
        return Iterables.transform(uniformPairs, new Transformer(sigma, mu));
    }

    public static Iterable<Double> createSampleStream( Random r, double epsilon, double sigma, double mu) {
       return createSampleStream(new Uniform.SampleStream(r, epsilon), sigma, mu);
    }
    public static Iterable<Double> createSampleStream( double epsilon, double sigma, double mu) {
        return createSampleStream(new Uniform.SampleStream(new Random(), epsilon), sigma, mu);
    }
    public static Iterable<Double> createSampleStream( Random r, double sigma, double mu) {
        return createSampleStream(new Uniform.SampleStream(r, Double.MIN_VALUE), sigma, mu);
    }
    public static Iterable<Double> createSampleStream(  double sigma, double mu) {
        return createSampleStream(new Uniform.SampleStream(), sigma, mu);
    }

    public static class Transformer implements Function<List<Double>, Double> {
        double sigma;
        double mu;

        public Transformer(double sigma, double mu) {
            this.sigma = sigma;
            this.mu = mu;
        }

        @Override
        public Double apply(List<Double> doubles) {
            return transform(mu, sigma, doubles.get(0), doubles.get(1));
        }
    }

}
