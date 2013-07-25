
package edu.purdue.bbc.util;

import java.util.ArrayList;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class MonitorableRange extends Range {
	private ArrayList <ChangeListener> changeListeners = new ArrayList<ChangeListener>( );
	
	public MonitorableRange( ) {
		super( );
	}

	public MonitorableRange( int min, int max ) {
		super( min, max );
	}

	public MonitorableRange( float min, float max ) {
		super( min, max );
	}

	public MonitorableRange( double min, double max ) {
		super( min, max );
	}

	public MonitorableRange( Range range ) {
		super( range.getMin( ), range.getMax( ));
	}

	public void addChangeListener( ChangeListener c ) {
		changeListeners.add( c );
	}

	public ChangeListener [] getChangeListeners( ) {
		return changeListeners.toArray( 
			new ChangeListener[ changeListeners.size( )]);
	}
	
	public boolean removeChangeListener( ChangeListener c ) {
		return changeListeners.remove( c );
	}

	private void fireChangeListeners( ) {
		for( ChangeListener c : changeListeners ) {
			c.stateChanged( new ChangeEvent( this ));
		}
	}

	public void setMin( double min ) {
		super.setMin( min );
		this.fireChangeListeners( );

	}

	public void setMax( double max ) {
		super.setMax( max );
		this.fireChangeListeners( );
	}

	public void setRange( double min, double max ) {
		super.setRange( min, max );
		this.fireChangeListeners( );
	}
}
