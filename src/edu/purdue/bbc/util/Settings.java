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

	public Settings( ) {
		this((Properties)null, null );
	}

	public Settings( String settingsFile ) {
		this( (Properties)null, settingsFile );
	}

	public Settings( Properties defaultSettings ) {
		this( defaultSettings, null );
	}

	public Settings( Map<String,String> defaultSettings ) {
		this( defaultSettings, null );
	}

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

	public Object setProperty( String property, String value ) {
		Object returnValue = super.setProperty( property, value );
		if ( "locale".equals( property ))
			if ( this.language == null )
				language = new Language( value );
			else
				language.setLocale( value );
		this.save( );
		return returnValue;
	}

	public static Settings getSettings( ) {
		return settings;
	}

	public static Language getLanguage( ) {
		if ( language == null )
			language = new Language( );
		return language;
	}

	public Properties getDefaults( ){
		return this.defaults;
	}

	public boolean getBoolean( String property ) {
		return Boolean.parseBoolean( get( property ));
	}

	public int getInt( String property ) {
		return Integer.parseInt( get( property ));
	}

	public double getDouble( String property ) {
		return Double.parseDouble( get( property ));
	}

	public String get( String property ) {
		return getProperty( property );
	}

	public void setBoolean( String property, boolean value ) {
		setProperty( property, Boolean.toString( value ));
	}

	public void setInt( String property, int value ) throws NumberFormatException {
		setProperty( property, Integer.toString( value ));
	}

	public void setDouble( String property, double value ) throws NumberFormatException {
		setProperty( property, Double.toString( value ));
	}

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
