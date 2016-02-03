package com.michaelmiklavcic;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by cstella on 2/3/16.
 */
public class Uniform {
    public static List<Double> generateSamplePair(Random r, double epsilon) {
        double u1 = sample(r, epsilon);
        double u2 = sample(r, epsilon);
        return Arrays.asList(u1, u2);
    }
    public static Double sample(Random r, double epsilon) {
        double u1 = 0;
        do {
            u1 = r.nextDouble();
        } while (u1 <= epsilon);
        return u1;
    }
    public static class SampleStream implements Iterator<Double> {
        Random r;
        double epsilon;
        public SampleStream(Random r, double epsilon) {
            this.r = r;
            this.epsilon = epsilon;
        }

        public SampleStream() {
            this(new Random(), Double.MIN_VALUE);
        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return true;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Double next() {
            return Uniform.sample(r, epsilon);
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Can't get piss out of the pool, baby.");
        }
    }
}
