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

import java.awt.geom.Point2D;

/**
 * A Class for extending Point2D to include Polar Coordinates. Although this class has public members x, y, r, and
 * theta, it is not recommended to modify them directly, this may have unpredictable results. Instead use the
 * setLocation( ) and move( ) methods to manipulate the location of the point.
 */
public class PolarPoint2D extends Point2D.Double {

	public double r;
	public double theta;
	public final static boolean POLAR = true;
	public final static boolean RECTANGULAR = false;
	private Point2D origin;

	/**
	 * Creates a PolarPoint2D using polar notation with an origin at (0,0).
	 * 
	 * @param r The r coordinate of the point in polar notation.
	 * @param theta The angle of the point from the origin (in radians).
	 * @param polar true if this point is being created with polar coordinates.
	 */
	public PolarPoint2D( double r, double theta, boolean polar ) {
		this.setOrigin( 0, 0 );
		if ( polar ) {
			this.setLocation( r, theta, POLAR );
		} else {
			this.setLocation( r, theta );
		}
	}

	/**
	 * Creates a PolarPoint2D using polar notation with respect to the specified
	 * origin point.
	 * 
	 * @param r The r coordinate of the point in polar notation.
	 * @param theta The angle of the point from the origin (in radians)
	 * @param origin The origin point for this PolarPoint2D
	 */
	public PolarPoint2D( double r, double theta, Point2D origin ) {
		this.setOrigin( origin );
		this.setLocation( r, theta, POLAR );
	}

	/**
	 * Creates a PolarPoint2D using polar notation with respect to the specified
	 * origin point.
	 * 
	 * @param r The r coordinate of the point in polar notation.
	 * @param theta The angle of the point from the origin (in radians)
	 * @param originX The x coordinate of the origin point for this PolarPoint2D
	 * @param originY The y coordinate of the origin point for this PolarPoint2D
	 */
	public PolarPoint2D( double r, double theta, double originX, double originY ) {
		this.setOrigin( originX, originY );
		this.setLocation( r, theta, POLAR );
	}

	/**
	 * Creates a PolarPoint2D using x/y notation.
	 * 
	 * @param x The x coordinate of the new point.
	 * @param y The y coordinate of the new point.
	 */
	public PolarPoint2D( double x, double y ) {
		this.setOrigin( 0, 0 );
		this.setLocation( x, y );
	}

	/**
	 * Creates a new PolarPoint2D at location (0,0)
	 */
	public PolarPoint2D( ) {
		this.x = 0;
		this.y = 0;
		this.r = 0;
		this.theta = 0;
		this.origin = new Point2D.Double( 0, 0 );
	}

	/**
	 * Creates a new PolarPoint2D with the same location as the specified Point2d.
	 * 
	 * @param p The Point2D to copy the location of.
	 */
	public PolarPoint2D( Point2D p ) {
		this( p.getX( ), p.getY( ));
	}

	/**
	 * Sets the location of this PolarPoint2D to the specified coordinates.
	 * 
	 * @param x The new x coordinate of this PolarPoint2D.
	 * @param y The new y coordinate of this PolarPoint2D.
	 */
	public void setLocation( double x, double y ) {
		this.x = x;
		this.y = y;
		double relativeX = x - this.origin.getX( );
		double relativeY = y - this.origin.getY( );
		this.r = Math.abs( Math.hypot( relativeX, relativeY ));
		this.theta = Math.atan2( relativeY, relativeX );
	}

	/**
	 * Sets the location of this PolarPoint2D to the same coordinates as the specified Point2D object. 
	 * 
	 * @param p The Point2D to copy the location of.
	 */
	public void setLocation( Point2D p ) {
		this.setLocation( p.getX( ), p.getY( ));
	}

	/**
	 * Sets the location of this PolarPoint2D to the specified polar coordinates relative to the
	 * origin point.
	 * 
	 * @param r The r coordinate of the new location.
	 * @param theta The angle theta of the new location.
	 * @param polar true if these coordinates are in polar notation.
	 */
	public void setLocation( double r, double theta, boolean polar ) {
		if ( polar ) {
			if ( r < 0 ) {
				r = Math.abs( r );
				theta = ( theta < 0 ) ? theta + Math.PI : theta - Math.PI;
			}
			this.r = r;
			this.theta = theta;
			this.x = r * Math.cos( theta ) + origin.getX( );
			this.y = r * Math.sin( theta ) + origin.getY( );
		} else {
			this.setLocation( r, theta );
		}
	}

	/**
	 * Returns the current origin point of this PolarPoint2D.
	 * 
	 * @return The current origin point.
	 */
	public Point2D getOrigin( ) {
		return this.origin;
	}

	/**
	 * Leaves the point in it's current location, but changes the location of the origin,
	 * recalculating r and theta.
	 * 
	 * @param origin the new Point2D to be used as the origin.
	 */
	public void setOrigin( Point2D origin ) {
		this.origin = origin;
		this.setLocation( this.x, this.y );
	}

	/**
	 * Leaves the point in it's current location, but changes the location of the origin,
	 * recalculating r and theta.
	 * 
	 * @param originX the x coordinate for the new point to be used as the origin.
	 * @param originY the y coordinage for the new point to be used as the origin.
	 */
	public void setOrigin( double originX, double originY ) {
		this.setOrigin( new Point2D.Double( originX, originY ));
	}

	/**
	 * Creates a new object of the same class and with the same contents as this object.
	 * 
	 * @return a clone of this instance.
	 */
	public Object clone( ) {
		return ( Object )new PolarPoint2D( this.r, this.theta, this.origin );
	}

	/**
	 * Moves this point by the specified distances. 
	 * 
	 * @param x The distance to move the PolarPoint2D on the x axis.
	 * @param y The distance to move the PolarPoint2D on the y axis.
	 */
	public void move( double x, double y ) {
		this.setLocation( this.x + x, this.y + y );
	}

	public void move( Point2D p ) {
		this.setLocation( this.x + p.getX( ), this.y + p.getY( ));
	}

	/**
	 * Moves this point by the specified distances.
	 * 
	 * @param r The distance to move the PolarPoint2D from the origin.
	 * @param theta The amount to change the theta angle of the PolarPoint2D.
	 * @param polar true if these coordinates are in polar notation.
	 */
	public void move( double r, double theta, boolean polar ) {
		if ( polar ) {
			if ( r < 0 ) {
				r = Math.abs( r );
				theta = ( theta < 0 ) ? theta + Math.PI : theta - Math.PI;
			}
			double x = r * Math.cos( theta );
			double y = r * Math.sin( theta );
			this.move( x, y );
		} else {
			this.move( r, theta );
		}
	}

	/**
	 * Returns the r coordinate of this PolarPoint2D in double precision.
	 * 
	 * @return The r coordinate of this PolarPoint2D.
	 */
	public double getR( ) {
		return this.r;	
	}

	/**
	 * Returns the theta angle of this PolarPoint2D in double precision.
	 * 
	 * @return The theta angle of this PolarPoint2d.
	 */
	public double getTheta( ) {
		return this.theta;
	}

	/**
	 * Scales the r coordinate for this point, moving the point exactly this
	 * amount from the origin. For example, passing a value of 2 will move
	 * The PolarPoint2D such that it is exactly twice as far from the origin
	 * with the same theta value.
	 * 
	 * @param value The amount to scale r by.
	 */
	public void scale( double value ) {
		this.setLocation( this.r * value, this.theta );
	}

	/**
	 * Scales the x and y coordinates of this point in relation to the point (0,0).
	 * 
	 * @param xValue The amount to scale the x coordinate by.
	 * @param yValue The amount to scale the y coordinate by.
	 */
	public void scale( double xValue, double yValue ) {
		this.setLocation( this.x * xValue, this.y * yValue );
	}

	/**
	 * Scales the x coordinate of this point in relation to the point (0,0).
	 * 
	 * @param value The amount to scale the x coordinate by.
	 */
	public void scaleX( double value ) {
		this.setLocation( this.x * value, this.y );
	}

	/**
	 * Scales the xycoordinate of this point in relation to the point (0,0).
	 * 
	 * @param value The amount to scale the y coordinate by.
	 */
	public void scaleY( double value ) {
		this.setLocation( this.x, this.y * value );
	}
}
