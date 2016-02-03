package com.michaelmiklavcic;

import static java.lang.String.format;

public class Distribution {

    public enum BinType {
        CEILING,
        SQUARE_ROOT,
        STURGES,
        RICE,
        DOANE,
        SCOTT,
        FREEDMAN_DIACONIS
    }

    public static void draw(double[] samples, int maxHeight, BinType type) {
        HistogramSettings settings = getHistogramSettings(samples, type);
        println(format("bins: %s, min: %s, max: %s", settings.getNumBins(), settings.getMin(), settings.getMax()));
        int[] histogram = new int[settings.getNumBins()];
        for (double sample : samples) {
            int bin = getBin(sample, settings.getNumBins(), settings.getMin(), settings.getMax());
            histogram[bin - 1]++;
        }
        draw(histogram, maxHeight);
        printBinCounts(histogram);
    }

    private static HistogramSettings getHistogramSettings(double[] samples, BinType type) {
        double min = 0, max = 0;
        boolean first = true;
        for (double s : samples) {
            if (first) {
                min = s;
                max = s;
                first = !first;
            } else {
                if (s < min)
                    min = s;
                if (s > max)
                    max = s;
            }
        }
        HistogramSettings settings = new HistogramSettings();
        settings.setMin(min);
        settings.setMax(max);
        switch (type) {
            case SQUARE_ROOT:
                settings.setNumBins((int) Math.round(Math.sqrt(samples.length)));
                break;
            default:
                throw new RuntimeException("Unsupported bin type: " + type);
        }
        return settings;
    }

    private static int getBin(double sample, int numBins, double min, double max) {
        double binWidth = (max - min) / numBins;
        return (int) Math.min(((sample - min) / binWidth) + 1, numBins);
    }

    private static void draw(int[] histogram, int maxHeight) {
        int min = 0, max = 0;
        boolean first = true;
        for (int c : histogram) {
            if (first) {
                min = c;
                max = c;
                first = !first;
            } else {
                if (c < min)
                    min = c;
                if (c > max)
                    max = c;
            }
        }
        double scaleFactor = (max > maxHeight) ? ((double) max / maxHeight) : 1;
        println("scale: " + scaleFactor);
        int height = (max > maxHeight) ? maxHeight : max;
        for (int i = height; i >= 0; i--) {
            printCountLabel(height, i);
            for (int j = 0; j < histogram.length; j++) {
                if (histogram[j] / scaleFactor >= i) {
                    print("x");
                } else {
                    print(" ");
                }
            }
            println("");
        }
    }

    private static void printCountLabel(int height, int i) {
        int padWidth = (int) Math.pow(height, 1 / 10) + 1;
        print(fixedWidthNum(padWidth, i));
    }

    private static String fixedWidthNum(int padWidth, int i) {
        return padLeft("" + i, padWidth) + " ";
    }

    private static void print(String arg) {
        System.out.print(arg);
    }

    private static void println(String arg) {
        System.out.println(arg);
    }

    private static void printBinCounts(int[] histogram) {
        for (int i = 0; i < histogram.length; i++) {
            System.out.println("bin: " + i + ", count: " + histogram[i]);
        }
    }

    private static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

}
