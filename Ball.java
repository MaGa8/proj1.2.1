
/**
 * class encapsulating a location and acceleration
 * @author martin
 */
public class Ball 
{
	public final static double FRICTION_COEFFICIENT = 0.075;
	
	
	/**
	 * @param initPos initial position
	 */
	public Ball (Vector initPos, double radius, double density)
	{
		mPosition = initPos;
		mVelocity = new Velocity (new Vector (initPos.getDimension()));
		mAcceleration = new Acceleration (new Vector (initPos.getDimension()));
		mRadius = radius;
		mMass = 4 / 3 * Math.pow(mRadius, 3) * Math.PI * density;
	}
	
	/**
	 * @return internal position vector
	 */
	public Vector getPosition() { return mPosition; }
	
	/**
	 * @return ball's mass
	 */
	public double getMass() { return mMass; }
	
	/**
	 * @return radius of the ball
	 */
	public double getRadius() { return mRadius; }
	
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
	private double mMass, mRadius;
}
