
import java.util.*;

/**
 * class storing a set of coordinates
 * in order to identify a position
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
	 * @param dim dimension point is in
	 * initializes position to origin
	 */
	public Vector (int dim)
	{
		mPos = new ArrayList<>();
		for (int cDim = 0; cDim < dim; ++cDim)
			mPos.add (new Double (0.0));
	}
	
	/**
	 * @param pos set of coordinates to use for initialization
	 * Note: pos is not cloned
	 */
	public Vector (ArrayList<Double> pos)
	{
		mPos = pos;
	}
	
	/**
	 * deep clones object
	 * @return clone
	 */
	public Vector clone()
	{
		ArrayList<Double> coords = new ArrayList<>();
		for (int cDim = 0; cDim < getDimension(); ++cDim)
			coords.add (new Double (mPos.get (cDim)));
		return new Vector (coords);
	}
	
	/**
	 * @return internal array list used to store position
	 */
	public ArrayList<Double> getCoordinates() { return mPos; }
	
	/**
	 * @param index index of coordinate to retrieve
	 * @return coordinate at index
	 * Precondition: 
	 */
	public double getCoordinate (int index) { return mPos.get (index); }
	
	/**
	 * @return dimension of point (number of coordinates)
	 */
	public int getDimension() { return mPos.size(); }
	
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
		mPos.set (index, mPos.get (index) + value);
	}
	
	
	private ArrayList<Double> mPos;
}
