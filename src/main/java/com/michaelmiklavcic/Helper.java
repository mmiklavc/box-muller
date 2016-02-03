package com.michaelmiklavcic;

/**
 * Created by cstella on 2/3/16.
 */
public class Helper {
    public static double[] pinch(Iterable<Double> sample, int limit) {
        double[] samples = new double[limit];
        int i = 0;
        for(Double d : sample) {
            samples[i++] = d;
            if(i >= limit) {
                break;
            }
        }
        return samples;
    }
}
