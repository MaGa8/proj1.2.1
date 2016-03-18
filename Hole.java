import java.util.ArrayList;


/**
 * class modelling a hole
 * a hole having a center, radius and target velocity
 * provides utility for interaction
 * @author martin
 */
public class Hole 
{
	public static final double ATTRACTION = 1;
	
	
	/**
	 * parametric constructor
	 * @param center center of the circular hole
	 * @param radius radius of the hole
	 * sets target velocity to 0
	 * sets attraction radius to radius
	 */
	public Hole (Vector center, double radius)
	{
		mCenter = center;
		mRadius = radius;
		mAttractionRadius = radius;
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
	 * @return attraction radius of the hole
	 */
	public double getAttractionRadius() { return mAttractionRadius; }
	
	/**
	 * @param check ball to check
	 * @return true if ball is within perimeter and magnitude of velocity is below target
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
	 * @param check ball to check
	 * @return true if ball is witin attraction perimeter and outside of the hole's perimeter
	 */
	public boolean isAttracted (Ball check)
	{
		Vector distToCenter = check.getPosition().clone();
		distToCenter.move (mCenter.getOppositeVector());
		if (distToCenter.getMagnitude() <= mAttractionRadius && distToCenter.getMagnitude() > mRadius)
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
	 * @param attractionRadius new attraction radius to use
	 */
	public void setAttractionRadius (double attractionRadius)
	{
		mAttractionRadius = attractionRadius;
	}
	
	/**
	 * @param attract ball to attract
	 * will apply a fixed force on ball oriented towards center
	 * if isAttracted is true
	 */
	public void attractBall (Ball attract)
	{
		if (isAttracted (attract))
		{
			ArrayList<Double> distance = new ArrayList<>();
			for (int cDim = 0; cDim < attract.getPosition().getDimension(); ++cDim)
				distance.add (mCenter.getCoordinate (cDim) - attract.getPosition().getCoordinate(cDim));
			Vector attraction = new Vector (distance);
			attraction.scale (ATTRACTION * attract.getMass() / attraction.getMagnitude());
			attract.obtainForceManager().applyForce (new Force (attraction));
		}
	}
	
	/**
	 * @param stop ball to stop
	 * stops the ball is isInHole is true
	 */
	public void putInHole (Ball stop)
	{
		if (isInHole (stop))
		{
			Vector stopForce = stop.obtainForceManager().getVelocity();
			stopForce.scale (-1 * stop.getMass() / stop.obtainForceManager().getTimeInterval());
			stop.obtainForceManager().applyForce (new Force (stopForce));
		}
	}
	
	private Vector mCenter;
	private double mTargetVelocity;
	private double mRadius, mAttractionRadius;
}
