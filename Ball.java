
/**
 * class encapsulating a location and acceleration
 * @author martin
 */
public class Ball 
{
	/**
	 * @param initPos initial position
	 */
	public Ball (Vector initPos)
	{
		mPosition = initPos;
		mVelocity = new Velocity (new Vector (initPos.getDimension()));
		mAcceleration = new Acceleration (new Vector (initPos.getDimension()));
	}
	
	/**
	 * moves the ball by one tick in time
	 * according to acceleration stored
	 */
	public void move()
	{
		double hardCodeTimeInterval = 1;
		mAcceleration.apply (mVelocity.getVelocity(), hardCodeTimeInterval);
		mPosition.move (mVelocity.getVelocity());
	}
	
	/**
	 * @param force force applied
	 * alters acceleration according to force
	 */
	public void applyForce (Force force)
	{
		force.apply (mAcceleration.getAcceleration(), mMass);
	}
	
	
	private Vector mPosition; 
	private Velocity mVelocity;
	private Acceleration mAcceleration;
	private double mMass = 1;
}
