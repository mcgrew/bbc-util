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

package edu.purdue.bbc.util.attributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class AttributesFilterList<T extends Attributes>
                                  extends ArrayList<AttributesFilter<T>> 
                                  implements AttributesFilter<T> {
	public static final int AND = 0;
	public static final int OR = 1;
	private int operation = AND;

	/**
	 * Creates a new CriteriaFilter
	 */
	public AttributesFilterList( ) { 
		super( );
	}

	/**
	 * Creates a new AttributesFilterList with the initial number of Criterion slots.
	 * 
	 * @see ArrayList#constructor( int )
	 * @param operation The operation to be performed by this list. Should be one of
	 *	AttributesFilterList.AND or AttributesFilterList.OR.
	 */
	public AttributesFilterList( int operation ) {
		super( );
		this.operation = operation;
	}

	/**
	 * Creates a new AttributesFilterList with the initial number of Criterion slots.
	 * 
	 * @see ArrayList#constructor( int )
	 * @param operation The operation to be performed by this list. Should be one of
	 *	AttributesFilterList.AND or AttributesFilterList.OR.
	 * @param initialSize The initial size of the backing List
	 */
	public AttributesFilterList( int operation, int initialSize ) {
		super( initialSize );
		this.operation = operation;
	}

	/**
	 * Creates a new AttributesFilterList with the passed in AttributesFilters
	 * 
	 * @see ArrayList#constructor( Collection )
	 * @param criteria The initial set of Criteria to use.
	 */
	public AttributesFilterList( Collection<AttributesFilter<T>> criteria ) {
		super( criteria );
	}

	/**
	 * Sets the operation for this filter list. If set to AND, only Attributes
	 * which pass through ALL filters contained within will be returned. If set
	 * to OR, Attributes which pass through ANY of the contained filters will
	 * be returned.
	 * 
	 * @param 
	 * @return 
	 */
	public void setOperation( int operation ) {
		this.operation = operation;
	}

	/**
	 * Gets the value of the operation being used by this list.
	 * 
	 * @return The operation value.
	 */
	public int getOperation( ) {
		return this.operation;
	}

	/**
	 * Filters the passed in Attribute objects based on this filter's criteria.
	 * 
	 * @see AttributesFilter#filter( Collection )
	 * @param attributes The set of Attributes to be filtered.
	 * @return The new set containing only objects which passed through the 
	 *	filtering process.
	 */
	public Collection<T> filter( Collection<T> attributes ) {

		Collection<T> returnValue;
		if ( operation == OR ) {
			returnValue = new HashSet( );
			for( AttributesFilter a : this ) {
				returnValue.addAll( a.filter( attributes ));
			}
		} else {
			returnValue = new ArrayList( attributes );
			for ( AttributesFilter a : this ) {
				returnValue = a.filter( returnValue );
			}
		}
		return returnValue;
	}

	/**
	 * Returns a string representation of this set of AttributeFilters
	 * 
	 * @return A string representation of this object.
	 */
	public String toString( ) {
		String returnValue = "(";
		for ( AttributesFilter f : this ) {
			if ( returnValue.length( ) > 1 ) {
				if ( this.operation == OR )
					returnValue += " or ";
				else
					returnValue += " and ";
			}
			returnValue += f.toString( );
		}
		return returnValue + ")";
	}

}



