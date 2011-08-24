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

import java.awt.Toolkit;
import java.awt.Dimension;
import java.util.Properties;
import java.util.Locale;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Logger;

public class Settings extends Properties {
	private static Settings settings = new Settings( );
	private static Language language;

	public Settings( ) {
		super( );
		this.defaults = new DefaultSettings( );
		this.load( );
	}

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

	public void save( ) {
		Logger logger = Logger.getLogger( getClass( ));
		try {
			logger.debug( "Saving settings..." );
			File settingsFile = new File( this.getProperty( "settingsFile" ));
			if( !settingsFile.getParentFile( ).exists( ) && !settingsFile.getParentFile( ).mkdirs( )) {
				logger.error( String.format(
				  "Unable to create directory '%s' for saving program settings.",
					settingsFile.getParent( )));
			}
			this.storeToXML( new BufferedOutputStream( 
				new FileOutputStream( settingsFile )), null );
		} catch ( IOException e ) {
			logger.error( String.format( 
				"Unable to save program settings. File '%s' is not writeable", 
				this.getProperty( "settingsFile" )), e );
		}
	}
	
	public void load( ) {
		Logger logger = Logger.getLogger( getClass( ));
		try {
			logger.debug( "Loading settings..." );
			this.loadFromXML( new BufferedInputStream( 
				new FileInputStream( new File( this.getProperty( "settingsFile" )))));
		} catch ( IOException e ) {
			logger.debug( String.format(
			  "Unable to read program settings. File %s is not readable",
				this.getProperty( "settingsFile" )));
		}
		if ( this.getProperty( "locale" ) != null )
			if ( language != null )
				language.setLocale( get( "locale" ));
			else
				language = new Language( get( "locale" ));
	}
	
	private class DefaultSettings extends Properties {

		public DefaultSettings( ) {
			super( );
			this.setProperty( "windowWidth", "1024" );
			this.setProperty( "windowHeight", "768" );
			this.setProperty( "homeDir", System.getProperty( "user.home" ));
			this.setProperty( "settingsFile", this.getProperty( "homeDir" ) + "/.jsysnet/settings.xml" );
			Dimension desktopSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
			this.setProperty( "desktopWidth", Integer.toString( desktopSize.width ));
			this.setProperty( "desktopHeight", Integer.toString( desktopSize.height ));
			this.setProperty( "windowXPosition", Integer.toString(( desktopSize.width - 1024 ) / 2 ));
			this.setProperty( "windowYPosition", Integer.toString(( desktopSize.height - 768 ) / 2 ));
			this.setProperty( "detailWindowWidth", "850" );
			this.setProperty( "detailWindowHeight", "350" );
			this.setProperty( "detailWindowXPosition", "100" );
			this.setProperty( "detailWindowYPosition", "40" );
			this.setProperty( "debug", "false" );
			this.setProperty( "verbose", "false" );
		}

	}


}
