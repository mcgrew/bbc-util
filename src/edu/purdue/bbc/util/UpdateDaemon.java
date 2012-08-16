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

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class UpdateDaemon extends Thread {
  private Collection<DaemonListener> listeners;
  private DaemonRunnable runnable;

  private UpdateDaemon( DaemonRunnable runnable ){ 
    super( runnable );
    this.runnable = runnable;
    this.runnable.setLock( this );
    this.setPriority( MIN_PRIORITY );
    this.setDaemon( true );
    this.listeners = new LinkedList( );
    this.runnable.setListeners( this.listeners );
  }

  public static UpdateDaemon create( long interval ) {
    DaemonRunnable runnable = new DaemonRunnable( interval );
    return new UpdateDaemon( runnable );
  }

  public synchronized void update( DaemonListener listener ) {
    if ( !listeners.contains( listener )) 
      listeners.add( listener );
  }

  public synchronized void cancelUpdate( DaemonListener listener ) {
    listeners.remove( listener );
  }

  public void kill( ) {
    this.runnable.setKillBit( true );
  }

  private static class DaemonRunnable implements Runnable {
    private Object lock;
    private boolean kill = false;
    private long interval;
    private Collection<DaemonListener> listeners;

    public DaemonRunnable( long interval ) {
      this.interval = interval;
    }

    public void setListeners( Collection<DaemonListener> listeners ) {
      this.listeners = listeners;
    }

    public void setLock( Object lock ) {
      this.lock = lock;
    }

    public void setKillBit( boolean kill ) {
      this.kill = kill;
    }

    public void run( ) {
      while( true ) {
        try {
          Thread.sleep( this.interval );
        } catch ( InterruptedException e ) { 
          continue;
        }
        if ( this.kill )
          return;
        synchronized( this.lock ) {
          Iterator<DaemonListener> listenerIterator = listeners.iterator( );
          while( listenerIterator.hasNext( )) {
            listenerIterator.next( ).daemonUpdate( );
            listenerIterator.remove( );
          }
        }
      }
    }
  }
}
