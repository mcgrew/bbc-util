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

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class will create a background thread which will process update events
 * in the background at minimum priority by default.
 */
public class UpdateDaemon extends Thread {
  protected Object lock;
  protected boolean kill = false;
  protected long interval;
  protected List<DaemonListener> listeners;


  /**
   * Creates a new UpdateDaemon this method is protected, so create a new
   * daemon with the create() method.
   * 
   * @param interval The time to wait between update attempts (in milliseconds)
   */
  public UpdateDaemon( long interval ){
    super( );
    this.setPriority( MIN_PRIORITY );
    this.setDaemon( true );
    this.listeners = new LinkedList( );
  }

  /**
   * Creates a new UpdateDaemon and returns it. This instance should then be 
   * started by calling it's start() method.
   * 
   * @param interval The interval to check for new updates, in milliseconds.
   * @return a new UpdateDaemon.
   */
  @Deprecated
  public static UpdateDaemon create( long interval ) {
    return new UpdateDaemon( interval );
  }

  /**
   * Calling this method with an object implementing DaemonListener will cause
   * it's daemonUpdate method to be called at the next interval.
   * 
   * @param listener The object to be processed.
   */
  public void update( DaemonListener listener ) {
    if ( listener != null && !listeners.contains( listener )) 
      listeners.add( listener );
  }

  /**
   * Attempts to cancel the next update. If the event has not yet fired, the
   * update will be cancelled. There is no guarantee that cancellation will
   * succeed.
   * 
   * @param listener The listener to cancel the update for.
   * @return true if the cancellation was successful. False if the update has
   *   already started and thus unable to be cancelled.
   */
  public boolean cancelUpdate( DaemonListener listener ) {
    return listeners.remove( listener );
  }

  /**
   * Kills the thread. At the next update, the thread will not execute and
   * instead exit.
   */
  public void kill( ) {
    this.kill = true;
  }

  public void run( ) {
    while( true ) {
      ProcessUtils.sleep( this.interval );
      if ( this.kill )
        return;
      while( listeners.size( ) > 0) {
        try {
          DaemonListener listener = listeners.remove( 0 );
          listener.update( );
        } catch( NoSuchElementException e ) {
        } catch ( Exception e ) {
          e.printStackTrace();
        }
      }
    }
  }
}
