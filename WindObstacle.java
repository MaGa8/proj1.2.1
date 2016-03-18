
import java.awt.geom.Rectangle2D;

/**
 * class modeling wind exerted within rectangle
 * @author martin
 */
public class WindObstacle 
{
	/**
	 * @param size size of wind's area
	 * @param v velocity of wind
	 */
	public WindObstacle (Rectangle2D size, Velocity v)
	{
		mSize = size;
		mVeloc = v;
	}
	
	/**
	 * @return internal velocity stored as vector
	 */
	public Vector getVelocity() { return mVeloc.getVelocity(); }
	
	/**
	 * @return internal rectangle object stored to hold size
	 */
	public Rectangle2D getObstacleSize() { return mSize; }
	
	/**
	 * @param check ball to check
	 * @return true if ball should be exposed to wind due to its position
	 */
	public boolean isExposed (Ball check)
	{
		Vector pos = check.getPosition();
		if (pos.getCoordinate (0) >= mSize.getMinX() && pos.getCoordinate (0) <= mSize.getMaxX())
			return false;
		if (pos.getCoordinate (1) >= mSize.getMinY() && pos.getCoordinate (1) <= mSize.getMinY())
			return false;
		return true;
	}
	
	/**
	 * @param exposed ball to expose to wind
	 * ball will only be modified if it is within the size
	 */
	public void expose (Ball exposed)
	{
		if (isExposed (exposed))
		{
			Vector force = mVeloc.getVelocity().clone();
			force.scale (PhysicsConstants.WIND_COEFFICIENT * exposed.getSurface());
			exposed.obtainForceManager().applyForce (new Force (force));
		}
	}
	
	private Velocity mVeloc;
	private Rectangle2D mSize;
}
