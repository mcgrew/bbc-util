/*

Copyright: 2010 Purdue University

This file is distributed under the following terms (MIT/X11 License):

  Permission is hereby granted, free of charge, to any person
  obtaining a copy of this file and associated documentation
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

package edu.purdue.bbc.util;

import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Locale;
import java.util.Map;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public class Settings extends Properties {
  private static Settings settings;
  private static Language language;
  private String settingsFilename;

  /**
   * Creates a new Settings object.
   */
  public Settings( ) {
  	this((Properties)null, null );
  }

  /**
   * Creates a new Settings object, reading settings from the given file.
   * 
   * @param settingsFile The file to read previously saved settings from.
   */
  public Settings( String settingsFile ) {
  	this( (Properties)null, settingsFile );
  }

  /**
   * Creates a new Settings object with the passed in default settings.
   * 
   * @param defaultSettings A Properties object to be used for default values.
   */
  public Settings( Properties defaultSettings ) {
  	this( defaultSettings, null );
  }

  /**
   * Creates a new Sttings object with the passed in default settings.
   * 
   * @param defaultSettings A Map containing settings to be used for default 
   *	values.
   */
  public Settings( Map<String,String> defaultSettings ) {
  	this( defaultSettings, null );
  }

  /**
   * Creates a new Settings object with the passed in default settings and
   * default values.
   * 
   * @param defaultSettings A Properties object to be used for default values.
   * @param settingsFile The file to read previously saved settings from.
   */
  public Settings( Properties defaultSettings, String settingsFile ) {
  	super( );
  	this.defaults = new DefaultSettings( );

  	if ( defaultSettings != null ) {
  		Enumeration props = defaultSettings.propertyNames( );
  		while( props.hasMoreElements( )) {
  			String prop = props.nextElement( ).toString( );
  			this.defaults.setProperty( prop, defaultSettings.getProperty( prop ));
  		}
  	}
  	this.settingsFilename = settingsFile;
  	if ( this.settingsFilename != null ) {
  		this.load( );
  	}
  	if ( settings == null ) {
  		settings = this;
  	}
  }

  /**
   * Creates a new Settings object with the passed in default settings and
   * default values.
   * 
   * @param defaultSettings a Map containing settings to be used for default 
   *	values.
   * @param settingsFile The file to read previously saved settings from.
   */
  public Settings( Map<String,String> defaultSettings, String settingsFile ) {
  	super( );
  	this.defaults = new DefaultSettings( );
  	if ( defaultSettings != null ) {
  		for( Map.Entry<String,String> entry : defaultSettings.entrySet( )) {
  			this.defaults.setProperty( entry.getKey( ), entry.getValue( ));
  		}
  	}
  	this.settingsFilename = settingsFile;
  	if ( this.settingsFilename != null ) {
  		this.load( );
  	}
  	if ( settings == null ) {
  		settings = this;
  	}
  }

  /**
   * Sets the specified property to the specified value.
   * 
   * @param property The name of property to be set.
   * @param value The value to set the property to.
   * @return The previous value of the specified property, or null if there
   *	is not one.
   */
  @Override
  public Object setProperty( String property, String value ) {
  	Object returnValue = super.setProperty( property, value );
  	if ( "locale".equals( property ))
  		if ( language == null )
  			language = new Language( value );
  		else
  			language.setLocale( value );
  	this.save( );
  	return returnValue;
  }

  /**
   * Gets the main settings object, usually the first one created by the
   * application.
   * 
   * @return The main settings object.
   */
  public static Settings getSettings( ) {
  	return settings;
  }

  /**
   * Gets the Language which associated with the locale specified in
   * the settings. Creates a new empty Language if one is not present.
   * 
   * @return The current Language.
   */
  public static Language getLanguage( ) {
  	if ( language == null )
  		language = new Language( );
  	return language;
  }

  /**
   * Gets the default settings specified at the creation of this Settings
   * object, if any.
   * 
   * @return The default settings.
   */
  public Properties getDefaults( ){
  	return this.defaults;
  }

  /**
   * Returns the value of the property as a boolean.
   * 
   * @param property The name of the property to retrieve.
   * @return A boolean containing the value of this property.
   */
  public boolean getBoolean( String property ) {
  	return Boolean.parseBoolean( this.get( property ));
  }

  /**
   * Returns the value of the property as a boolean, or the default value
   * specified is this property is not set.
   * 
   * @param property The name of the property to retrieve.
   * @param defaultValue The default value to return if the property is not set.
   * @return A boolean containing the value of this property.
   */
  public boolean getBoolean( String property, boolean defaultValue ) {
  	String returnValue = this.get( property );
  	if ( returnValue == null )
  		return defaultValue;
  	else
  		return Boolean.parseBoolean( returnValue );
  }

  /**
   * Returns the value of the property as an int.
   * 
   * @param property the name of the property to retrieve.
   * @return An int containing the value of this property.
   * @throws NumberFormatException if this property does not contain a valid
   *	integer.
   */
  public int getInt( String property ) {
  	return Integer.parseInt( this.get( property ));
  }

  /**
   * Returns the value of the property as an int, or the default value
   * specified if this property is not set.
   * 
   * @param property The name of the property to retrieve.
   * @param defaultValue The default value to return if the property is not set.
   * @return An int containing the value of this property.
   * @throws NumberFormatException if this property does not contain a valid
   *	integer.
   */
  public int getInt( String property, int defaultValue ) {
  	String returnValue = this.get( property );
  	if ( returnValue == null )
  		return defaultValue;
  	else
  		return Integer.parseInt( returnValue );
  }

  /**
   * Returns the value of the property as a double.
   * 
   * @param property The name of the property to retrieve.
   * @return A double containing the value of this property.
   * @throws NumberFormatException if this property does not contain a valid
   *	double.
   */
  public double getDouble( String property ) {
  	return Double.parseDouble( this.get( property ));
  }

  /**
   * Returns the value of the property as a double, or the default value
   * specified if this property is not set.
   * 
   * @param property The name of the property to retrieve.
   * @param defaultValue The default value to return if the property is not set.
   * @return A double containing the value of this property.
   * @throws NumberFormatException if this property does not contain a valid
   *	double.
   */
  public double getDouble( String property, double defaultValue ) {
  	String returnValue = this.get( property );
  	if ( returnValue == null )
  		return defaultValue;
  	else
  		return Double.parseDouble( returnValue );
  }

  /**
   * Returns the value of the property.
   * 
   * @param property The name of the property to retrieve.
   * @return The value of the specified property.
   */
  public String get( String property ) {
  	return this.getProperty( property );
  }

  /**
   * Returns the value of the property, or the default value specified if
   * the property is not set.
   * 
   * @param property The name of the property to retrieve.
   * @param defaultValue The default value to return if the property is not set.
   * @return A String containing the value of this property.
   */
  public String get( String property, String defaultValue ) {
  	return this.getProperty( property, defaultValue );
  }

  /**
   * Sets the value of the specified property, returning it's old value (if any).
   * 
   * @param property The name of the property to be set.
   * @param value The new value for the property.
   * @return The old value for the property, or null if it was not previously 
   *	present.
   */
  public Object set( String property, String value ) {
  	return this.setProperty( property, value );
  }

  /**
   * Sets the value of the specified property to the specified boolean value.
   * 
   * @param property The name of the property to be set.
   * @param value The value to set the property to.
   * @return The previous value of this property, if any, false otherwise.
   */
  public boolean setBoolean( String property, boolean value ) {
  	Object returnValue = this.setProperty( property, Boolean.toString( value ));
  	if ( returnValue == null )
  		return false;
  	else
  		return Boolean.parseBoolean( returnValue.toString( ));
  }

  /**
   * Sets the value of the specified property to the specified int value.
   * 
   * @param property The name of the property to be set.
   * @param value The value to set this property to.
   * @return The previous value of this property, if any, -1 otherwise.
   * @throws NumberFormatException if the previous value of this property is not
   *	null and does not contain a valid integer.
   */
  public int setInt( String property, int value ) throws NumberFormatException {
  	Object returnValue = this.setProperty( property, Integer.toString( value ));
  	if ( returnValue == null )
  		return -1;
  	else
  		return Integer.parseInt( returnValue.toString( ));
  }

  /**
   * Sets the value of the specifeid property to the specified double value.
   * 
   * @param property The name of the property to be set.
   * @param value The value to set this property to.
   * @return The previous value of this property, if any, Double.NaN othersise.
   * @throws NumberFormatException if the previous value of this property is not
   *	null and does not contain a valid double.
   */
  public double setDouble( String property, double value ) throws NumberFormatException {
  	Object returnValue = this.setProperty( property, Double.toString( value ));
  	if ( returnValue == null )
  		return Double.NaN;
  	else
  		return Double.parseDouble( returnValue.toString( ));
  }

  /**
   * Saves the settings to the output file specified in the constructor.
   * 
   * @return true if saving of the file was successful.
   */
  public boolean save( ) {

  	Logger logger = Logger.getLogger( getClass( ));
  	if ( this.settingsFilename == null ) {
  		return false;
  	}
  	try {
  		logger.debug( "Saving settings..." );
  		File settingsFile = new File( this.settingsFilename );
  		if( !settingsFile.getParentFile( ).exists( ) && !settingsFile.getParentFile( ).mkdirs( )) {
  			logger.error( String.format(
  			  "Unable to create directory '%s' for saving program settings.",
  				settingsFile.getParent( )));
  			return false;
  		}
  		this.storeToXML( new BufferedOutputStream( 
  			new FileOutputStream( settingsFile )), null );
  	} catch ( IOException e ) {
  		logger.error( String.format( 
  			"Unable to save program settings. File '%s' is not writeable", 
  			this.getProperty( "settingsFile" )), e );
  		return false;
  	}
  	return true;
  }
  
  /**
   * Loads the settings from the file specified in the constructor.
   * 
   * @return true if loading of the settings was successful.
   */
  public boolean load( ) {
  	Logger logger = Logger.getLogger( getClass( ));
  	if ( this.settingsFilename == null ) {
  		return false;
  	}
  	try {
  		logger.debug( "Loading settings..." );
  		this.loadFromXML( new BufferedInputStream( 
  			new FileInputStream( new File( this.settingsFilename ))));
  	} catch ( IOException e ) {
  		logger.debug( String.format(
  		  "Unable to read program settings. File %s is not readable",
  			this.getProperty( "settingsFile" )));
  		return false;
  	}
  	if ( this.getProperty( "locale" ) != null )
  		if ( language != null )
  			language.setLocale( get( "locale" ));
  		else
  			language = new Language( get( "locale" ));
  	return true;
  }
  
  private class DefaultSettings extends Properties {

  	public DefaultSettings( ) {
  		super( );
  		this.setProperty( "home", System.getProperty( "user.home" ));
  		this.setProperty( "debug", "false" );
  		this.setProperty( "verbose", "false" );
  	}

  }
}
