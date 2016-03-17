import java.io.PrintStream;

/**
 * Class modeling a generic matrix
 * @author martin
 * @param <T> type of entries of matrix
 */
public class Matrix <T extends Number> implements Cloneable
{
	/**
	 * Exception thrown when index beyond the bounds of the matrix is used to access the matrix
	 * @author martin
	 */
	public static class MatrixOutOfBoundsException extends ArrayIndexOutOfBoundsException
	{
		private static final long serialVersionUID = 1L;

		public MatrixOutOfBoundsException() {super(); }
	
		public MatrixOutOfBoundsException (String message) {super (message); }
	}
	
	@SuppressWarnings("serial")
	/**
	 * Exception throws when dimension of a matrix does not conform with preconditions
	 * @author martin
	 */
	public static class MatrixDimensionMismatchException extends IllegalArgumentException
	{
		public MatrixDimensionMismatchException() {super(); }
		
		public MatrixDimensionMismatchException (String message) {super (message); } 
	}
	
	/**
	 * class to obtain initial element of type T
	 * @author martin
	 * @param <T> type
	 */
	public static interface ElementInitializer<T>
	{
		public T getElement();
	}
	
	/**
	 * class to add elements of type T
	 * @author martin
	 * @param <T> type
	 */
	public static interface ElementAdditor<T>
	{
		public T addElements (T add1, T add2);
	}
	
	/**
	 * class to multiply elements of type T
	 * @author martin
	 * @param <T> type
	 */
	public static interface ElementMultiplicator<T>
	{
		public T multiplyelements (T multi1, T multi2);
	}
	
	/**
	 * class to compare elements of type T
	 * @author martin
	 * @param <T> type
	 */
	public static interface ElementComparator<T>
	{
		public boolean equals (T a1, T a2);
	}
	
	public static final int PRECISION = 5;
	
	/** Do nothing constructor which needs to be overridden by subclasses
	 * @param rows rows of matrix
	 * @param cols columns of matrix
	 */
	public Matrix (int rows, int cols, ElementInitializer<T> init, ElementAdditor<T> add, ElementMultiplicator<T> multi, ElementComparator<T> comp)
	{
		mInit = init;
		mAdd = add;
		mMulti = multi;
		mComp = comp;
		mStoreArray = new Object[rows][cols];
		for (int cRow = 0; cRow < rows; ++cRow)
		{
			for (int cCol = 0; cCol < cols; ++cCol)
				mStoreArray[cRow][cCol] = mInit.getElement(); 
		}
	}
	
	//accessor methods
	/**
	 * Multiplies this with multiplier
	 * @param multiplier multiplier matrix
	 * @param result matrix to store result in
	 */
	public Matrix <T> multiply (Matrix<T> multiplier, Matrix<T> result)
	{
		for (int cRow = 0; cRow < this.getRows(); ++cRow)
		{
			for (int cCol = 0; cCol < multiplier.getColumns(); ++cCol)
			{
				T resultCell = vectorProduct (this.getRow(cRow), multiplier.getColumn(cCol));
				result.setCell(cRow, cCol, resultCell);
			}
		}
		return result;
	}
	
	/**
	 * performs deep copy
	 */
	public Matrix<T> clone()
	{
		Matrix<T> newMat = new Matrix<T> (getRows(), getColumns(), mInit, mAdd, mMulti, mComp);
		this.copyValues (this, 0, 0, 0, 0, this.getRows(), this.getColumns());
		return newMat;
	}
	
	/**
	 * @param scalar number the scalar matrix should scale by
	 * @param scalarMat object of appropriate size initialized with 0s in which to store the scalar matrix
	 * @return
	 */
	public Matrix<T> getScalarMatrix (T scalar, Matrix<T> scalarMat)
	{
		for (int cEntry = 0; cEntry < scalarMat.getRows(); ++cEntry)
			scalarMat.setCell(cEntry, cEntry, mMulti.multiplyelements (scalar, mInit.getElement()));
		return scalarMat;
	}
	
	/**
	 * @param row row index
	 * @return array containing elements of row
	 */
	public T[] getRow (int row)
	{
		if (row >= getRows())
			throw new MatrixOutOfBoundsException ("Could not get row with index " + row);
		return (T[]) mStoreArray[row];
	}
	
	/**
	 * @param col column index
	 * @return array containing elements of column
	 */
	public T[] getColumn (int col)
	{
		Object[] ret = new Object[getRows()];
		for (int cRow = 0; cRow < getRows(); ++cRow)
			ret[cRow] = getCell (cRow, col);
		return (T[]) ret;
	}
	
	/**
	 * @param v1 vector
	 * @param v2 vector
	 * @return vector product of v1 and v2
	 * @throws MatrixDimensionMismatchException
	 */
	public T vectorProduct (T[] v1, T[] v2) throws MatrixDimensionMismatchException
	{
		if (v1.length != v2.length)
			throw new MatrixDimensionMismatchException ("vectors do not have same dimensions");
		T product = mInit.getElement();
		for (int cDim = 0; cDim < v1.length; ++cDim)
			product = mAdd.addElements (product, mMulti.multiplyelements (v1[cDim], v2[cDim]));
		return product;
	}
	
	/** @param row row index
	 * 	@param col column index
	 * 	@return cell at specified index
	 * 	@throws MatrixOutOfBoundsException
	 */
	public T getCell (int row, int col) throws MatrixOutOfBoundsException
	{
		if (row < getRows() && col < getColumns())
			return (T) mStoreArray[row][col];
		else
			throw new MatrixOutOfBoundsException (row + "|" + col + " out of bounds in " + getRows() + "x" + getColumns() + " matrix");
	}
	
	/**
	 * @return matrix factory initialized to helper classes contained
	 * in this matrix
	 */
	public MatrixFactory<T> getFactory()
	{
		MatrixFactory<T> fax = new MatrixFactory<>();
		fax.setInitializer (mInit);
		fax.setAdditor (mAdd);
		fax.setMultiplicator (mMulti);
		fax.setComparator (mComp);
		return fax;
	}
	
	public String toString()
	{
		String s =  getRows() + "x" + getColumns() + " matrix\n[\t";
		for (int cRow = 0; cRow < getRows(); ++cRow)
		{
			for (int cCol = 0; cCol < getColumns(); ++cCol)
				s += getCell (cRow, cCol) + " ";
			if (cRow < getRows() - 1)
				s += "\n\t";
			else
				s += "]";
		}
		return s;
	}

	/**
	 * @param rowIndex index of row to count
	 * @return Number of set cells in rowIndex
	 */
	public int countRow (int rowIndex)
	{
		int cnt = 0;
		for (int cCol = 0; cCol < getColumns(); ++cCol)
		{
			if (!getCell(rowIndex, cCol).equals (mInit.getElement()))
				++cnt;
		}
		return cnt;
	}
	
	/**
	 * @param index of row to count
	 * @param startIndex  index to start counting at
	 * @param stopIndex index to stop counting at
	 * @return number of set cells in row in given interval
	 */
	public int countRowPart (int rowIndex, int startIndex, int stopIndex)
	{
		int cnt = 0;
		for (int cCol = startIndex; cCol <= stopIndex; ++cCol)
		{
			if (!getCell(rowIndex, cCol).equals (mInit.getElement()))
				++cnt;
		}
		return cnt;
	}
	
	/**
	 * @param colIndex index of column to count
	 * @return Number of set cells in colIndex
	 */
	public int countCol (int colIndex)
	{
		int cnt = 0;
		for (int cRow = 0; cRow < getRows(); ++cRow)
		{
			if (!getCell (cRow, colIndex).equals (mInit.getElement()))
				++cnt;
		}
		return cnt;
	}
	
	/**
	 * @param colIndex index of column to count
	 * @param startIndex  index to start counting at
	 * @param stopIndex index to stop counting at
	 * @return Number of set cells in colIndex
	 */
	public int countColPart (int colIndex, int startIndex, int stopIndex)
	{
		int cnt = 0;
		for (int cRow = startIndex; cRow <= stopIndex; ++cRow)
		{
			if (!getCell (cRow, colIndex).equals (mInit.getElement()))
				++cnt;
		}
		return cnt;
	}	

	/** @return number of rows **/
	public int getRows() 
	{
		return mStoreArray.length;
	}
	
	/** @return number of columns **/
	public int getColumns() 
	{
		return mStoreArray[0].length;
	}
	
	/** @return true if internal array is not null **/
	public boolean checkValidArray() 
	{
		return (mStoreArray != null);
	}
	
	/**
	 * @param compare matrix to compare this to
	 * @return true if both matrices have the same dimensions and entries for each cell
	 */
	public boolean equals(Matrix<T> compare) 
	{
		if ((this.getRows() != compare.getRows()) ||
			(this.getColumns() != compare.getColumns())) 
			return false;
		for (int cRow = 0; cRow < compare.getRows(); cRow++) 
		{
			for (int cCol = 0; cCol < compare.getColumns(); cCol++) 
			{
				if (!mComp.equals (this.getCell (cRow, cCol), compare.getCell (cRow, cCol))) 
					return false;
			}
		}
		return true;
	}
	
	/**
	 * @param compare matrix to compare dimension to
	 * @return true if dimensions are the same
	 */
	public boolean dimensionEquals (Matrix<?> compare)
	{
		return (this.getRows() == compare.getRows() &&
				this.getColumns() == compare.getColumns());
	}
	
	/**
	 * @param row The index of the row that should be checked
	 * @return True if row is filled
	 */
	public boolean isRowFilled (int row)
	{
		for (int cCol = 0; cCol < getColumns(); cCol++)
		{
			if (getCell (row, cCol).equals (mInit.getElement())) 
				return false;
		}
		return true;
	}
	
	/**
	 * @param row The index of the column that should be checked
	 * @return True if column is filled
	 */
	public boolean isColFilled(int col)
	{
		for (int cRow = 0; cRow < getRows(); ++cRow) 
		{
			if (getCell (cRow, col).equals (mInit.getElement())) 
				return false;
		}
		return true;
	}
	
	/**
	 * @param row index of row to check
	 * @return True if every element in the row is 0
	 */
	public boolean isRowEmpty (int row)
	{
		for (int cCol = 0; cCol < getColumns(); cCol++)
		{
			if (!getCell (row, cCol).equals (mInit.getElement())) 
				return false;
		}
		return true;
	}
	
	/**
	 * @param column col index of column to check
	 * @return True if every element in the column is 0
	 */
	public boolean isColEmpty (int col)
	{
		for (int cRow = 0; cRow < getRows(); ++cRow) 
		{
			if (!getCell (cRow, col).equals (mInit.getElement())) 
				return false;
		}
		return true;
	}
	
	//mutator methods
	/**
	 * Writes value to the given cell
	 * @param row row of the cell to write to
	 * @param col column of the cell to write to
	 * @param value value to write
	 */
	public void setCell (int row, int col, T value) 
	{
		if (row < 0 || row >= getRows() || col < 0 || col >= getColumns())
			throw new MatrixOutOfBoundsException ("Index " + row + " " + col + "out of bounds in matrix of size " + getRows() + " " + getColumns());
		mStoreArray[row][col] = value;
	}
	
	/**Copies values from source to this matrix starting from startRow|startCol
	 * Precondition: source contains enough entries to fill every cell of this matrix while copying
	 * @param source matrix containing values to be copied
	 * @param startRow row in this to start copying at
	 * @param startCol column in this to start copying at
	 * @param srcStartRow row in source to start copying at
	 * @param srcStartCol column in source to start copying at
	 * @param copyRows number of rows to copy
	 * @param copyCols number of columns to copy
	 */
	public void copyValues (Matrix<T> source, int startRow, int startCol, int srcStartRow, int srcStartCol, int copyRows, int copyCols)
	{
		for (int cRow = 0; cRow < copyRows; ++cRow)
		{
			for (int cCol = 0; cCol < copyCols; ++cCol)
				this.setCell(cRow + startRow, cCol + startCol, source.getCell (cRow + srcStartRow, cCol + srcStartCol));
		}
	}
	
	
	/**
	 * @param index of row the line above will be moved to. Precondition: Row > 0
	 * This method copies the row from above to one row below
	 */
	public void moveRow(int row) 
	{
		for (int cCol = 0; cCol < getColumns(); cCol++)
				setCell (row, cCol, getCell (row - 1, cCol));
	}
	
	//debugging methods
	/**
	 * Prints the entries of the matrix using writer
	 * @param out stream to write to
	 */
	public void print (PrintStream out) 
	{
		for (int cRows = 0; cRows < getRows(); cRows++) {
			for (int cCols = 0; cCols < getColumns(); cCols++)
				out.print(" " + getCell(cRows, cCols) + " ");
			out.println("");
		}
	}
	
	//private methods
	
	// Data Members
	private Object[][] mStoreArray;
	private ElementInitializer<T> mInit;
	private ElementAdditor<T> mAdd;
	private ElementMultiplicator<T> mMulti;
	private ElementComparator<T> mComp;
}
