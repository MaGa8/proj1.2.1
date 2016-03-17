import java.util.ArrayList;


/**
 * class modelling a hole1
 * @author martin
 */
public class Hole 
{
	public static final double ATTRACTION = 1;
	
	
	/**
	 * parametric constructor
	 * @param center center of the circular hole
	 * @param radius radius of the hole
	 */
	public Hole (Vector center, double radius)
	{
		mCenter = center;
		mRadius = radius;
		mTargetVelocity = 0.0;
	}
	
	/**
	 * @return center position of the hole
	 */
	public Vector getCenter() { return mCenter; }
	
	/**
	 * @return radius of the hole
	 */
	public double getRadius() { return mRadius; }
	
	/**
	 * @param check ball to check
	 * @return true if ball is within perimeter and 
	 */
	public boolean isInHole (Ball check)
	{
		Vector distToCenter = check.getPosition().clone();
		distToCenter.move (mCenter.getOppositeVector());
		if (distToCenter.getMagnitude() <= mRadius && 
			check.obtainForceManager().getVelocity().getMagnitude() <= mTargetVelocity)
			return true;
		return false;
	}
	
	/**
	 * @param target new magnitude of target velocity
	 * if ball is on hole and target velocity > velocity
	 * ball is said to be in hole
	 */
	public void setTargetVelocity (double target)
	{
		mTargetVelocity = target;
	}
	
	/**
	 * @param attract ball to attract
	 * will apply a fixed force on ball oriented towards center
	 */
	public void attractBall (Ball attract)
	{
		ArrayList<Double> distance = new ArrayList<>();
		for (int cDim = 0; cDim < attract.getPosition().getDimension(); ++cDim)
			distance.add (mCenter.getCoordinate (cDim) - attract.getPosition().getCoordinate(cDim));
		Vector attraction = new Vector (distance);
		Vector ballVelocity = attract.obtainForceManager().getVelocity();
		attraction.scale (ATTRACTION * ballVelocity.getMagnitude() / attraction.getMagnitude());
		attract.obtainForceManager().applyForce (new Force (attraction));
	}
	
	/**
	 * @param stop ball to stop
	 */
	public void putInHole (Ball stop)
	{
		Vector stopForce = stop.obtainForceManager().getVelocity();
		stopForce.scale (-1 * stop.getMass() / stop.obtainForceManager().getTimeInterval());
		stop.obtainForceManager().applyForce (new Force (stopForce));
	}
	
	private Vector mCenter;
	private double mTargetVelocity;
	private double mRadius;
}
