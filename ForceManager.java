
import java.util.*;

public class ForceManager 
{
	
	/**
	 * @param pos position reference to update
	 */
	public ForceManager (Vector pos, double mass)
	{
		mAcc = new Acceleration (new Vector (pos.getDimension()));
		mVeloc = new Velocity (new Vector (pos.getDimension()));
		mPos = pos;
		mMass = mass;
	}
	
	public void applyForces (Collection<Force> forces)
	{
		Vector resultingForce = new Vector (mPos.getDimension());
		for (Force f : forces)
			resultingForce.move (f.getForce());
		applyForce (new Force (resultingForce));
	}
	
	/**
	 * updates acceleration, velocity and position
	 */
	public void applyForce (Force f)
	{
		double hardcodeTimeInterval = 1;
		f.apply (mAcc.getAcceleration(), mMass);
		mAcc.apply (mVeloc.getVelocity(), hardcodeTimeInterval);
		mVeloc.apply (mPos, hardcodeTimeInterval);
	}
	
	public Acceleration mAcc;
	public Velocity mVeloc;
	public Vector mPos;
	public double mMass;
}
