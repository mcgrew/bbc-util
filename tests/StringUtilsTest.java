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

import edu.purdue.bbc.util.StringUtils;

import junit.framework.*;

public class StringUtilsTest extends TestCase {
  private String[] numbers;

  public void setUp() {
    numbers = new String[]{
      "-0.001332938",
      "4.388183E4",
      "-0.001171869",
      "-8.497317E-4",
      "548515478",
      "-634.53451E4",
      "51.163315145E-4",
      "45543.2563452",
      "5.576246e-004",
      "4554325e9",
      "+5189324E+5",
      "+5234512",

      "4543.45234.45234",
      "4535.41t5",
      "dsfnaso",
      "51234ee8"
    };

  }

  public void testIsNumeric() {
    for (String i : numbers) {
      try { 
        Double.parseDouble(i);
        assertTrue("Failed on " + i + ", this is a number", 
                   StringUtils.isNumeric(i));
      } catch (NumberFormatException e) {
        assertTrue("Failed on " + i + ", this is not a number", 
                   !StringUtils.isNumeric(i));
      }
    }
  }
}
