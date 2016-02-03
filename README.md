# box-muller
Box-Muller implementation - algorithm taken from https://en.wikipedia.org/wiki/Box%E2%80%93Muller_transform

Calling 'generate' repeatedly will return a normally distributed set of numbers from a uniformly distributed set of numbers.

Demonstration found in [com.michaelmiklavcic.BoxMullerTest](src/test/java/com/michaelmiklavcic/BoxMullerTest.java). 'BoxMuller' provides a generate method for creating the samples. 'Distribution' provides a draw method for creating a histogram visualization.

```
Args:
  numSamples - How many samples to generate
  mu - mean
  sigma - standard deviation
BoxMuller.generate(10000, 5, 1);

Args:
  samples - Unsorted data samples to produce the histogram from
  maxHeight - Maximum height of the histogram
  type - Method for calculating number of bins. Currently only SQUARE_ROOT is supported.
Distribution.draw(samples, 30, BinType.SQUARE_ROOT);
```
