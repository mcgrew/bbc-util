/*
 * Copyright (c) 2013. Purdue University
 *
 * This file is distributed under the following terms (MIT/X11 License):
 *
 *   Permission is hereby granted, free of charge, to any person
 *   obtaining a copy of this file and associated documentation
 *   files (the "Software"), to deal in the Software without
 *   restriction, including without limitation the rights to use,
 *   copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the
 *   Software is furnished to do so, subject to the following
 *   conditions:
 *
 *   The above copyright notice and this permission notice shall be
 *   included in all copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *   EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *   OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *   HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *   WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *   FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *   OTHER DEALINGS IN THE SOFTWARE.
 */

import edu.purdue.bbc.util.Statistics;
import edu.purdue.bbc.util.Range;

import jsc.correlation.SpearmanCorrelation;
import jsc.correlation.KendallCorrelation;
import jsc.correlation.LinearCorrelation;
import jsc.datastructures.PairedData;

import junit.framework.*;

public class StatisticsTest extends TestCase {
  private double[] slice1;
  private double[] slice2;

  public void testGetRank() {
    double[] slice = new double[1000];
    for (int i=0; i < 1000; i++) {
      slice[i] = 1000 - i;
    }
    double[] result = Statistics.getRank(slice);
    for (int i=1; i < 1000; i++) {
      assertTrue(result[i-1] > result[i]);
    }
    slice = new double[1000];
    for (int i=0; i < 1000; i++) {
       slice[i] = 999 - i;
    }
    slice[200] = 800;
    result = Statistics.getRank(slice);
    assertEquals(799.5, result[200]);
    assertEquals(799.5, result[199]);
  }

  public void setUp() {
    slice1 = new double[]{3.5, 10.0, 93.0, 12.0, 54.0};
    slice2 = new double[]{54.0, 93.0, 87.0, 3.5, 10.0, 12.0 };
  }

  public void testMedian() {
    double result = Statistics.median(slice1);
    double expect = 12.0;
    assertEquals(expect, result);

    expect = 33.0;
    result = Statistics.median(slice2);
    assertEquals(expect, result);
  }

  public void testMean() {
    double result = Statistics.mean(slice2);
    double expect = 43.25;
    assertEquals(expect, result);
  }

  public void testSum() {
    double result = Statistics.sum(slice2);
    double expect = 259.5;
    assertEquals(expect, result);
  }

  public void testMin() {
    double result = Statistics.min(slice2);
    double expect = 3.5;
    assertEquals(expect, result);
  }

  public void testFirstQuartile() {
    double result = Statistics.firstQuartile(slice2);
    double expect =  8.375;
    assertEquals(expect, result);
  }

  public void testThirdQuartile() {
    double result = Statistics.thirdQuartile(slice2);
    double expect =  88.5;
    assertEquals(expect, result);
  }

  public void testRegularRange() {
    Range range = Statistics.regularRange(slice2);

    double lower = range.getMin();
    double expect = -111.8125;
    assertEquals(expect, lower);

    double upper = range.getMax();
    expect = 208.6875;
    assertEquals(expect, upper);
  }

  public void testMinRegular() {
    double result = Statistics.minRegular(slice2);
    double expect = -111.8125;
    assertEquals(expect, result);
  }

  public void testMaxRegular() {
    double result = Statistics.maxRegular(slice2);
    double expect = 208.6875;
    assertEquals(expect, result);
  }

  public void testPearsonCorrelation() {
    for ( int i=0; i < 1E3; i++ ) {
      int jMax = (int)( Math.random( ) * 100 + 5);
      slice1 = new double[ jMax ];
      slice2 = new double[ jMax ];
      for ( int j=0; j < jMax; j++ ) {
        slice1[ j ] = Math.random( ) * 1E5;
        slice2[ j ] = Math.random( ) * 1E5;
      }
      PairedData jscData = new PairedData( slice1, slice2 );
      double jsc = 0;
      try {
        jsc = new LinearCorrelation( jscData ).getR( );
      } catch ( IllegalArgumentException e ) {
        e.printStackTrace( );
        continue;
      }
      double bbc = Statistics.getPearsonCorrelation( slice1, slice2 );
      assertEquals(jsc, bbc, 1E-10);
    }
  }

  public void testSpearmanCorrelation() {
    for ( int i=0; i < 1E4; i++ ) {
      int jMax = (int)( Math.random( ) * 100 + 5);
      slice1 = new double[ jMax ];
      slice2 = new double[ jMax ];
      for ( int j=0; j < jMax; j++ ) {
        slice1[ j ] = Math.random( ) * 1E5;
        slice2[ j ] = Math.random( ) * 1E5;
      }
      PairedData jscData = new PairedData( slice1, slice2 );
      double jsc = 0;
      try {
        jsc = new SpearmanCorrelation( jscData ).getR( );
      } catch ( IllegalArgumentException e ) {
        e.printStackTrace( );
        continue;
      }
      double bbc = Statistics.getSpearmanCorrelation( slice1, slice2);
      assertEquals(jsc, bbc, 1E-10);
    }
  }

  public void testKendallCorrelation() {
    for ( int i=0; i < 1E4; i++ ) {
      int jMax = (int)( Math.random( ) * 100 + 5);
      slice1 = new double[ jMax ];
      slice2 = new double[ jMax ];
      for ( int j=0; j < jMax; j++ ) {
        slice1[ j ] = Math.random( ) * 1E5;
        slice2[ j ] = Math.random( ) * 1E5;
      }
      PairedData jscData = new PairedData( slice1, slice2 );
      double jsc = 0;
      try {
        jsc = new KendallCorrelation( jscData ).getR( );
      } catch ( IllegalArgumentException e ) {
        e.printStackTrace( );
        continue;
      }
      double bbc = Statistics.getKendallCorrelation( slice1, slice2 );
      assertEquals(jsc, bbc, 1E-10);
    }
  }
}