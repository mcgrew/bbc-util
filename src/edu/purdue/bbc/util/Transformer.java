package edu.purdue.bbc.util;

/**
 * A drop-in replacement for the Apache commons Transformer interface
 */
public interface Transformer<I,O> {

  /**
   * Transforms one object to another
   * @param i The input of the transform
   * @return The output of the transform
   */
  public O transform(I i);
}

