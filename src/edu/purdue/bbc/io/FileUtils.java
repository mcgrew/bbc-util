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

package edu.purdue.bbc.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A class for File operations.
 */
public class FileUtils {
	
	private FileUtils( ) { }

	/**
	 * Copies a file from one location to another.
	 * 
	 * @param original The source file.
	 * @param copy The destination file.
	 * @throws IOException
	 */
	public static void copyFile( File original, File copy ) 
	                                throws IOException {
		FileInputStream in = new FileInputStream(  original );
		copy.getParentFile( ).mkdirs( );
		FileOutputStream out = new FileOutputStream( copy );
		byte[] buffer = new byte[ 8192 ];
		int length;
		while(( length = in.read( buffer )) > 0 ) {
			out.write( buffer, 0, length );
		}
		in.close( );
		out.close( );
	}

	/**
	 * Copies a file from one location to another.
	 * 
	 * @param original The source file.
	 * @param copy The destination file.
	 * @throws IOException
	 */
	public static void copyFile( String original, String copy ) 
	                                throws IOException {
		copyFile( new File( original ), new File( copy ));
	}
}


