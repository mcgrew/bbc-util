/*

Copyright: 2010 Bindley Bioscience Center, Purdue University

License: X11 license.

  Permission is hereby granted, free of charge, to any person
  obtaining a copy of this software and associated documentation
  files (the "Software"), to deal in the Software without
  restriction, including without limitation the rights to use,
  copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the
  Software is furnished to do so, subject to the following
  conditions:

  The above copyright notice and this permission notice shall be
  included in all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
  OTHER DEALINGS IN THE SOFTWARE.

*/

package edu.purdue.bbc.util.attributes;

import java.util.Map;

/**
 * Interface for a class containing arbitrary attributes.
 */
public class StringAttributes extends BasicAttributes<String> {

  /**
   * Creates a new StringAttributes object.
   */
  public StringAttributes( ) { 
  	super( );
  }

  /**
   * Creates a new Stringattributes object with the specified case sensitivity.
   * When case sensitivity is set to false, all attribute names are forced to
   * lower case.
   * 
   * @param caseSensitive false forces attribute names to	lower case. 
   */
  public StringAttributes( boolean caseSensitive ) {
  	super( caseSensitive );
  }

  /**
   * Gets an attribute for this object.
   * 
   * @param attribute The attribute to retrieve.
   * @return The value of the requested attribute, or null if it does not exist.
   */
  public boolean getBooleanAttribute( String attribute ) {
  	return Boolean.parseBoolean( this.attributes.get( attribute ));
  }

  /**
   * Gets an attribute for this object.
   * 
   * @param attribute The attribute to retrieve.
   * @param defaultValue The value to return if this attribute is not set.
   * @return The value of the requested attribute, or null if it does not exist.
   */
  public boolean getBooleanAttribute( String attribute, boolean defaultValue ) {
  	return this.hasAttribute( attribute ) ? 
  		Boolean.parseBoolean( this.attributes.get( attribute )) : defaultValue;
  }


  /**
   * Gets an attribute for this object.
   * 
   * @param attribute The attribute to retrieve.
   * @return The value of the requested attribute, or null if it does not exist.
   */
  public int getIntAttribute( String attribute ) {
  	return Integer.parseInt( this.attributes.get( attribute ));
  }

  /**
   * Gets an attribute for this object.
   * 
   * @param attribute The attribute to retrieve.
   * @param defaultValue The value to return if this attribute is not set.
   * @return The value of the requested attribute, or null if it does not exist.
   */
  public int getIntAttribute( String attribute, int defaultValue ) {
  	return this.hasAttribute( attribute ) ? 
  		Integer.parseInt( this.attributes.get( attribute )) : defaultValue;
  }

  /**
   * Gets an attribute for this object.
   * 
   * @param attribute The attribute to retrieve.
   * @return The value of the requested attribute, or null if it does not exist.
   */
  public double getDoubleAttribute( String attribute ) {
  	return Double.parseDouble( this.attributes.get( attribute ));
  }

  /**
   * Gets an attribute for this object.
   * 
   * @param attribute The attribute to retrieve.
   * @param defaultValue The value to return if this attribute is not set.
   * @return The value of the requested attribute, or null if it does not exist.
   */
  public double getDoubleAttribute( String attribute, double defaultValue ) {
  	return this.hasAttribute( attribute ) ? 
  		Double.parseDouble( this.attributes.get( attribute )) : defaultValue;
  }

  /**
   * Sets an attribute for this object.
   * 
   * @param attribute The attribute to set.
   * @param value The new value for the specified attribute.
   */
  public boolean setBooleanAttribute( String attribute, boolean value ) {
  	return Boolean.parseBoolean( 
  		this.setAttribute( attribute, Boolean.toString( value )));
  }

  /**
   * Sets an attribute for this object.
   * 
   * @param attribute The attribute to set.
   * @param value The new value for the specified attribute.
   */
  public int setIntAttribute( String attribute, int value ) {
  	try {
  		return Integer.parseInt( 
  			this.setAttribute( attribute, Integer.toString( value )));
  	} catch ( NumberFormatException e ) {
  		return 0;
  	}
  }

  /**
   * Sets an attribute for this object.
   * 
   * @param attribute The attribute to set.
   * @param value The new value for the specified attribute.
   */
  public double setDoubleAttribute( String attribute, double value ) {
  	try {
  		return Double.parseDouble( 
  			this.setAttribute( attribute, Double.toString( value )));
  	} catch ( NumberFormatException e ) {
  		return 0.0;
  	}
  }

}

