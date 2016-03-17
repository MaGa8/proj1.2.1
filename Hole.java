
/**
 * class modelling a hole1
 * @author martin
 */
public class Hole 
{
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
	 * @param stop ball to stop
	 */
	public void putInWhole (Ball stop)
	{
		Vector stopForce = stop.obtainForceManager().getVelocity();
		stopForce.scale (-1 * stop.getMass() / stop.obtainForceManager().getTimeInterval());
		stop.obtainForceManager().applyForce (new Force (stopForce));
	}
	
	private Vector mCenter;
	private double mTargetVelocity;
	private double mRadius;
}
