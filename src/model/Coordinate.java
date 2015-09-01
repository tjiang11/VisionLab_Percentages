package model;

/**
 * This class is a basic storage object for an x-y coordinate.
 * 
 * @author Ryan Ly
 * @version 1.01, 2010-02-06
 */
public class Coordinate {

	/** The x value of this coordinate */
	public final int x;

	/** The y value of this coordinate */
	public final int y;

	/**
	 * Creates a new instance of <code>Coordinate</code> with initial values for
	 * x and y
	 * 
	 * @param x
	 *            the initial x value
	 * @param y
	 *            the initial y value
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Determines whether the passed Object is equal to this
	 * <code>Coordinate</code>
	 * 
	 * @param o
	 *            the Object to be compared to this <code>Coordinate</code>
	 * @return true is the x and y values of the passed Object are the same as
	 *         those of this <code>Coordinate</code>
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Coordinate))
			return false;
		Coordinate c = (Coordinate) o;
		return (x == c.x) && (y == c.y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 17 * x + y;
	}

	/**
	 * Returns a string representation of this <code>Coordinate</code>
	 * 
	 * @return a string representation of this <code>Coordinate</code> that
	 *         looks like: (x,y)
	 */
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
