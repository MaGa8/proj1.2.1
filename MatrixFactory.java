/**
 * class storing setup objects for matrix
 * producing finished matrix objects
 * @author martin
 * @param <T>
 */
public class MatrixFactory<T extends Number>
{
	/**
	 * @param noRows number of rows
	 * @param noCols number of columns
	 * @return new matrix object of size noRows x noCols
	 */
	public Matrix<T> produce (int noRows, int noCols)
	{
		return new Matrix<T> (noRows, noCols, mInit, mAdd, mMulti, mComp);
	}
	
	/**
	 * @param init initializer object to use to create matrices
	 */
	public void setInitializer (Matrix.ElementInitializer<T> init)
	{
		mInit = init;
	}
	
	/**
	 * @param add addition object to use to add matrices
	 */
	public void setAdditor (Matrix.ElementAdditor<T> add)
	{
		mAdd = add;
	}
	
	/**
	 * @param multi multiplication object to use to multiply matrices
	 */
	public void setMultiplicator (Matrix.ElementMultiplicator<T> multi)
	{
		mMulti = multi;
	}
	
	/**
	 * @param comp comparator object to use to compare matrix entries
	 */
	public void setComparator (Matrix.ElementComparator<T> comp)
	{
		mComp = comp;
	}
	
	
	private Matrix.ElementInitializer<T> mInit;
	private Matrix.ElementAdditor<T> mAdd;
	private Matrix.ElementMultiplicator<T> mMulti;
	private Matrix.ElementComparator<T> mComp;
}
