import java.util.ArrayList;


public class VectorTest 
{
	
	public static void main (String[] args)
	{
		System.out.println ("testing");
		VectorTest test = new VectorTest();
		test.testEqual();
		test.testSame();
		test.testOpposite();
	}
	
	public VectorTest()
	{
		ArrayList<Double> initPos = new ArrayList<>();
		mVector = new Vector (3);
	}
	
	public void setVector (int x1, int x2, int x3)
	{
		mVector.move (0, mVector.getCoordinate (0) - x1);
		mVector.move (1, mVector.getCoordinate (1) - x2);
		mVector.move (2, mVector.getCoordinate (2) - x3);
	}
	
	public void testOpposite()
	{
		System.out.print ("direction opposite "); 
		System.out.println (mVector.isOppositeOrientation (mVector.getOppositeVector()));
	}
	
	public void testEqual()
	{
		System.out.print ("vector equal ");
		System.out.println (mVector.equals (mVector.clone()));
	}
	
	public void testSame()
	{
		Vector secnd = mVector.clone();
		secnd.move (mVector);
		System.out.print ("direction equal ");
		System.out.println (mVector.isSameOrientation (secnd));
	}
	
	
	private Vector mVector;
}
