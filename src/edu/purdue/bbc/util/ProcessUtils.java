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

package edu.purdue.bbc.util;

import java.io.PrintStream;
import java.lang.management.ManagementFactory;

public class ProcessUtils {

  private ProcessUtils( ){ }

  /**
   * Prints the current stack trace to System.out, omitting this method.
   */
  public static void printStackTrace( ) {
  	printStackTrace( System.out );
  }

  /**
   * Prints the current stack trace to the specified OutputStream, omitting 
   * this method.
   * 
   * @param out The PrintStream to print the stack trace to.
   */
  public static void printStackTrace( PrintStream out ) {
  	StackTraceElement[ ] stacktrace = Thread.currentThread( ).getStackTrace( );
  	out.println( "Current stack trace:" );
  	for ( int i=3; i < stacktrace.length; i++ ) {
  		StackTraceElement caller = stacktrace[ i ];
  		out.printf( "\t%s.%s(%s:%d)%n",
  		                   caller.getClassName( ),
  		                   caller.getMethodName( ),
  											 caller.getFileName( ),
  		                   caller.getLineNumber( ));
  	}
  }

  /**
   * Gets the stack trace of the current thread, omitting this method.
   * 
   * @return The current stack trace.
   */
  public static StackTraceElement[ ] getStackTrace( ) {
  	StackTraceElement[ ] stacktrace = Thread.currentThread( ).getStackTrace( );
  	StackTraceElement[ ] returnValue = 
  		new StackTraceElement[ stacktrace.length - 3 ];
  	for ( int i=3; i < stacktrace.length; i++ ) {
  		returnValue[ i-3 ] = stacktrace[ i ];
  	}
  	return returnValue;
  }

  /**
   * Gets the StackTraceElement containing the caller of the current method.
   * 
   * @return The StackTraceElement containing the caller of the current method.
   */
  public static String getCallerDescription( ) {
  	StackTraceElement caller = getCaller( );
  	return String.format( "%s.%s(%s:%d)", 
  	                      caller.getClassName( ),
  												caller.getMethodName( ),
  												caller.getFileName( ),
  												caller.getLineNumber( ));
  }

  /**
   * Gets the StackTraceElement containing the caller of the current method.
   * 
   * @return The StackTraceElement containing the caller of the current method.
   */
  public static StackTraceElement getCaller( ) {
  	return Thread.currentThread( ).getStackTrace( )[ 4 ];
  }


  /**
   * Gets the process id of the current running process. May not be supported
   * on all platforms.
   * 
   * @throws UnsupportedOperationException if this method is unable to
   *	determine the process id.
   * @return The current process id.
   */
  public static int getPid( ) {
  	int pid = -1;
  	try {
  		String pidString = ManagementFactory.getRuntimeMXBean( ).getName( );
  		pidString = pidString.substring( 0, pidString.indexOf( "@" ));
  		pid = Integer.parseInt( pidString );
  	} catch ( Exception e ) {
  		throw new UnsupportedOperationException(
  			"ProcessUtils.getPid() is not supported on your platform." );
  	}
  	return pid;
  }

  /**
   * Returns the current number of running threads in the application.
   * 
   * @return The number of threads.
   */
  public static int getThreadCount( ) {
    return 
      ManagementFactory.getThreadMXBean().getThreadCount();        
  }
}
