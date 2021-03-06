
import java.util.*;

/**
 * class encapsulating a location and acceleration
 * @author martin
 */
public class Ball 
{
	public static class BallSetup
	{
		public Vector initPos;
		public double radius;
		public double density;
	}
	
	/**
	 * @param initPos initial position
	 */
	public Ball (Vector initPos, double radius, double density)
	{
		mPosition = initPos;
		mRadius = radius;
		mMass = 4 / 3 * Math.pow(mRadius, 3) * Math.PI * density;
		mManageForce = new ForceManager (mPosition, mMass);
		mForceManagerKey = mManageForce.getOwnership();
		mManageForce.setOpen (false, mForceManagerKey);
	}
	
	/**
	 * @param setup setup object
	 * initializes ball using the setup object
	 */
	public Ball (BallSetup setup)
	{
		this (setup.initPos, setup.radius, setup.density);
	}
	
	/**
	 * @return force manager object applied to this ball
	 */
	public ForceManager obtainForceManager() { return mManageForce; }
	
	/**
	 * @return internal position vector
	 */
	public Vector getPosition() { return mPosition; }
	
	/**
	 * @return ball's mass
	 */
	public double getMass() { return mMass; }
	
	/**
	 * @return ball's surface
	 */
	public double getSurface() 
	{
		return (4 * Math.PI * Math.pow (getRadius(), 2));
	}
	
	/**
	 * @return radius of the ball
	 */
	public double getRadius() { return mRadius; }
	
	/**
	 * moves the ball by one tick in time
	 * according to acceleration stored
	 */
	public void applyForce (Collection<Force> forces)
	{
		mManageForce.applyForces (forces);
	}
	
	
	private Vector mPosition; 
	private ForceManager mManageForce;
	private Lock.Key mForceManagerKey;
	private double mMass, mRadius;
}
