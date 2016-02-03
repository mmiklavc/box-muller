package com.michaelmiklavcic;

import org.junit.Test;

import com.michaelmiklavcic.Distribution.BinType;

public class BoxMullerTest {

    @Test
    public void generate_distribution() {
        double[] samples = BoxMuller.generate(10000, 5, 1);
        Distribution.draw(samples, 30, BinType.SQUARE_ROOT);
    }

}
