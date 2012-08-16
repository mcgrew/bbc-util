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

import java.util.Properties;
import java.util.Locale;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

/**
 * This class is intended to allow your application to support multiple
 * language options. By creating an java Properties xml file containing
 * the keys and their translation. The file should be named by using the 
 * langauge abbreviation followed by an underscore and the country code, for
 * example, en_US.xml.
 *
 * If the file is intended to be a generic file for all countries using the
 * specified language, you may omit the country code, such as en.xml. The most
 * specific file will be chosen for the specified locale. If you specify en_GB
 * as the locale, Language will first look in the resources folder for a file
 * called en_GB.xml. If this file is not present, Language will try to load the
 * generic English file named en.xml. If this file is also not found, Language
 * will log a debug message using log4j and continue without a language file.
 *
 * File formats for the language file should be as follows:
 *
 * For example the file en_US.xml:
 * <pre>
 *  &lt;?xml version="1.0" encoding="UTF-8" standalone="no"?&gt;
 *  &lt;!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"&gt;
 *  &lt;properties&gt;
 *  	&lt;entry key="File"&gt;File&lt;/entry&gt;
 *  	&lt;entry key="Help"&gt;Help&lt;/entry&gt;
 *  	&lt;entry key="Open"&gt;Open&lt;/entry&gt;
 *  	&lt;entry key="OK"&gt;OK&lt;/entry&gt;
 *  	&lt;entry key="Cancel"&gt;Cancel&lt;/entry&gt;
 *  	&lt;entry key="Save"&gt;Save&lt;/entry&gt;
 *  &lt;/properties&gt;
 * </pre>
 * 
 * Would perhaps be accompanied by a file called es.xml:
 * <pre>
 *  &lt;?xml version="1.0" encoding="UTF-8" standalone="no"?&gt;
 *  &lt;!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd"&gt;
 *  &lt;properties&gt;
 *  	&lt;entry key="File"&gt;Archivo&lt;/entry&gt;
 *  	&lt;entry key="Help"&gt;Ayuda&lt;/entry&gt;
 *  	&lt;entry key="Open"&gt;Abrir&lt;/entry&gt;
 *  	&lt;entry key="OK"&gt;Confirmar&lt;/entry&gt;
 *  	&lt;entry key="Cancel"&gt;Cancelar&lt;/entry&gt;
 *  	&lt;entry key="Save"&gt;Guardar&lt;/entry&gt;
 *  &lt;/properties&gt;
 * </pre>
 * 
 * In this case the call get( "Open" ) would return "Open" if the locale is set 
 * to en_US, and "Abrir" if the locale is set to es (such as with es_MX). If the
 * word is not found in the language file (or no language file is present for 
 * the current locale), A debug message is generated and the String which was 
 * passed in will be returned.
 * 
 */
public class Language {
  private Locale locale = Locale.getDefault( );
  private Properties strings = new Properties( );
  private String resourceLocation;

  /**
   * Creates a new Language resource with a resourceLocation of
   * "/resources/i18n/" and the default Locale.
   */
  public Language( ) {
  	this.resourceLocation = "/resources/i18n/";
  	// Detect the locale by checking the Settings. If that fails, use 
  	// the default locale.
  	Settings settings = Settings.getSettings( );
  	if ( settings == null || settings.getProperty( "locale" ) == null )
  		setLocale( Locale.getDefault( ));
  	else
  		setLocale( settings.getProperty( "locale" ));
  }

  /**
   * Creates a new Language resource with a resourceLocation of
   * "/resources/i18n/" and the specified Locale.
   *
   * @param locale The locale to use for translations.
   */
  public Language( String locale ) {
  	this( locale, "/resources/i18n/" );
  }

  /**
   * Creates a new Language resource with a resourceLocation of
   * "/resources/i18n/" and the specified Locale.
   *
   * @param locale The Locale to use for translations.
   */
  public Language( Locale locale ) {
  	this( locale, "/resources/i18n/" );
  }

  /**
   * Creates a new Language resource with the given locale and
   * resource location.
   * 
   * @param locale The locale to use for translations.
   * @param resourceLocation The location of the language resource files.
   */
  public Language( String locale, String resourceLocation ) {
  	setLocale( locale );
  	if ( resourceLocation.endsWith( "/" )) {
  		this.resourceLocation = resourceLocation;
  	} else {
  		this.resourceLocation = resourceLocation + "/";
  	}
  }

  /**
   * Creates a new Language resource with the given locale and
   * resource location.
   * 
   * @param locale The Locale to use for translations.
   * @param resourceLocation The location of the language resource files.
   */
  public Language( Locale locale, String resourceLocation ) {
  	setLocale( locale );
  	if ( resourceLocation.endsWith( "/" )) {
  		this.resourceLocation = resourceLocation;
  	} else {
  		this.resourceLocation = resourceLocation + "/";
  	}
  }

  /**
   * Sets the locale to the newly specified locale.
   * 
   * @param locale The new locale to use.
   */
  public void setLocale( String locale ) {
  		String [] args = locale.split( "_", 2 );
  		if ( args.length == 1 )
  			setLocale( new Locale( args[0] ));
  		else
  			setLocale( new Locale( args[0], args[1] ));
  }

  /**
   * Sets the locale to the newly specified Locale.
   * 
   * @param locale The new locale to use.
   */
  public void setLocale( Locale locale ) {
  	this.locale = locale;
  	// try language + country first.
  	InputStream resource = getClass( ).getResourceAsStream( 
  		"/resources/i18n/" + locale.getLanguage( ) + "_" +locale.getCountry( ) + ".xml" );
  	if ( resource == null ) {
  		// try just language.
  		resource = getClass( ).getResourceAsStream( 
  			"/resources/i18n/" + locale.getLanguage( ) + ".xml" );
  	}
  	if ( resource /*still*/ == null ) {
  		Logger.getLogger( getClass( )).debug( 
  			get( "Unable to read language file for " ) + 
  			locale.toString( ) + ". " );
  	} else {
  		try {
  			strings.loadFromXML( resource ); 
  		} catch ( IOException e ) {
  			Logger.getLogger( getClass( )).debug( 
  				get( "Unable to read language file for " ) + 
  				locale.toString( ) + ". " );
  		}
  	}
  }

  /**
   * Gets the currently used Locale for this language resource.
   * 
   * @return The current Locale.
   */
  public Locale getLocale( ) {
  	return locale;
  }

  /**
   * Returns the proper translation of the specified string according
   * to the current locale. If the string is not found in the language
   * file, a debug message is issued (log4j) and the passed in String
   * is returned.
   * 
   * @param s The string to be translated.
   * @return The translated string.
   */
  public String get( String s ) {
  	if ( strings == null )
  		return s;

  	String returnValue = strings.getProperty( s );
  	if ( returnValue != null ) {
  		return returnValue;
  	}

  	if ( true )
  		Logger.getLogger( getClass( )).debug( 
  			String.format( "'%s' not in language file (%s)", 
  				s, ProcessUtils.getCallerDescription( )));
  	return s;
  }
}

