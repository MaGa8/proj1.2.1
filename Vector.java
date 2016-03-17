
import java.util.*;

/**
 * class storing a set of double coordinates
 * in order to identify a position
 * values are rounded to the closest digit according to 
 * static field DIGITS_ACCURACY
 * @author martin
 */
public class Vector
{
	/**
	 * exception thrown on bad index
	 * @author martin
	 */
	public static class IllegalIndexException extends IllegalArgumentException
	{
		public IllegalIndexException() {}
		
		public IllegalIndexException (String message) { super (message); }
	}
	
	/**
	 * exception thrown on interaction of points
	 * having different dimensions
	 * @author martin
	 */
	public static class BadDimensionException extends IllegalArgumentException
	{
		public BadDimensionException() {}
		
		public BadDimensionException (String message) { super (message); }
	}
	
	/**
	 * @param num number to round
	 * @return num rounded to closest DIGITS_ACCURACY th digit
	 */
	public static double round (double num)
	{
		double power = Math.pow (10, DIGITS_ACCURACY);
		return (Math.round (power * num) / power);
	}
	
	/**
	 * @param n1 first number
	 * @param n2 second number
	 * @return true if n1 and n2 are considered equal given a margin of error
	 * smaller than the accuracy used
	 */
	public static boolean compareEpsilon (double n1, double n2)
	{
		double eps = 1.0 / Math.pow (10, DIGITS_ACCURACY);
		return (n1 >= n2 - eps && n1 <= n2 + eps);
	}
	
	public static int DIGITS_ACCURACY = 3;
	
	/**
	 * @param dim dimension point is in
	 * initializes position to origin
	 */
	public Vector (int dim)
	{
		mStore = new ArrayList<>();
		for (int cDim = 0; cDim < dim; ++cDim)
			mStore.add (new Double (0.0));
	}
	
	/**
	 * @param pos set of coordinates to use for initialization
	 * Note: pos is not cloned
	 */
	public Vector (ArrayList<Double> pos)
	{
		mStore = pos;
	}
	
	/**
	 * deep clones object
	 * @return clone
	 */
	public Vector clone()
	{
		ArrayList<Double> coords = new ArrayList<>();
		for (int cDim = 0; cDim < getDimension(); ++cDim)
			coords.add (new Double (mStore.get (cDim)));
		return new Vector (coords);
	}
	
	/**
	 * @return vector in opposite direction
	 */
	public Vector getOppositeVector()
	{
		ArrayList<Double> negValues = new ArrayList<> ();
		for (int cDim = 0; cDim < getDimension(); ++cDim)
			negValues.add (-getCoordinate (cDim));
		return new Vector (negValues);
	}
	
	/**
	 * @return internal array list used to store position
	 */
	public ArrayList<Double> getCoordinates() { return mStore; }
	
	/**
	 * @param index index of coordinate to retrieve
	 * @return coordinate at index
	 * Precondition: 
	 */
	public double getCoordinate (int index) { return mStore.get (index); }
	
	/**
	 * @return dimension of point (number of coordinates)
	 */
	public int getDimension() { return mStore.size(); }
	
	/**
	 * computes magnitude
	 * @return magnitude of this vector
	 */
	public double getMagnitude()
	{
		double mag = 0.0;
		for (int cDim = 0; cDim < getDimension(); ++cDim)
			mag += Math.pow (getCoordinate (cDim), 2);
		return Math.sqrt (mag);
	}
	
	/**
	 * @param v vector to compare to this vector
	 * @return true if v and this have the same orientation
	 * i.e. if they have the same ratio over all coordinates
	 * and this ratio is always positive
	 */
	public boolean isSameOrientation (Vector v)
	{
		double scalar = this.getCoordinate (0) / v.getCoordinate (0);
		
		for (int cDim = 1; cDim < getDimension() - 1; ++cDim)
		{
			//negative ratio
			if (scalar < 0)
				return false;
			double newScalar = this.getCoordinate (cDim) / v.getCoordinate (cDim);
			//different ratio
			if ((v.getCoordinate (cDim) != 0.0 && !compareEpsilon (scalar, newScalar)) || 
				(v.getCoordinate (cDim) == 0.0 && this.getCoordinate (cDim) != 0.0))
				return false;
		}
		return true;
	}
	
	/**
	 * @param v vector to compare to this vector
	 * @return true if v and this have the opposite orientation
	 * i.e. if they have the opposite ratio over all coordinates
	 * and this ratio is always negative
	 */
	public boolean isOppositeOrientation (Vector v)
	{
		double scalar = -this.getCoordinate (0) / v.getCoordinate (0);
		
		for (int cDim = 1; cDim < getDimension() - 1; ++cDim)
		{
			//negative ratio
			if (scalar < 0)
				return false;
			double newScalar = -this.getCoordinate (cDim) / v.getCoordinate (cDim);
			//different ratio
			if ((v.getCoordinate (cDim) != 0.0 && !compareEpsilon (scalar, newScalar)) ||
				(v.getCoordinate (cDim) == 0.0 && this.getCoordinate (cDim) != 0.0))
				return false;
		}
		return true;
	}
	
	/**
	 * @param v vector to compare to
	 * @return true if v and this vector have same coordinates
	 */
	public boolean equals (Vector v)
	{
		for (int cDim = 0; cDim < getDimension() - 1; ++cDim)
		{
			if (!compareEpsilon (this.getCoordinate (cDim), v.getCoordinate (cDim)))
				return false;
		}
		return true;
	}
	
	/**
	 * @param add position to add to current one
	 */
	public void move (Vector add)
	{
		if (this.getDimension() != add.getDimension())
			throw new BadDimensionException ("point of dimension " + add.getDimension() + "cannot be added to point of dimension " + this.getDimension());
		for (int cDim = 0; cDim < getDimension(); ++cDim)
			this.move (cDim, add.getCoordinate (cDim));
	}
	
	/**
	 * @param index index of coordinate to move
	 * @param value value to move by
	 */
	public void move (int index, double value)
	{
		if (index < 0 || index >= getDimension())
			throw new IllegalIndexException ("inex " + index + " is " + (index < 0 ? " less than zero " : " too large"));
		mStore.set (index, round (mStore.get (index) + value));
	}
	
	/**
	 * @param scalar scalar to apply
	 * scales the vector by given scalar
	 */
	public void scale (double scalar)
	{
		for (int cDim = 0; cDim < getDimension(); ++cDim)
			move (cDim, getCoordinate (cDim) * (scalar - 1));
	}
	
	private ArrayList<Double> mStore;
}
