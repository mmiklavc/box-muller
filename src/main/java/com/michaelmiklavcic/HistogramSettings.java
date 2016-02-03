package com.michaelmiklavcic;

public class HistogramSettings {

    private double min;
    private double max;
    private int numBins;

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setNumBins(int numBins) {
        this.numBins = numBins;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public int getNumBins() {
        return numBins;
    }

}
