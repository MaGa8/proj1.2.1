
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.Timer;
import javax.swing.event.*;

/**
 * class applying forces to an object given its position and mass
 * moves object's position regularly according to time interval
 * @author martin
 */
public class ForceManager 
{
	public static final double DEFAULT_FRICTION_COEFFICIENT = 0.0;
	
	public class MoveListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			mAcc.apply (mVeloc.getVelocity(), mUpdateInterval);
			mVeloc.apply (mPos, mUpdateInterval);
			applyForce (getFriction());
		}
	}
	
	/**
	 * @param pos position reference to update
	 */
	public ForceManager (Vector pos, double mass)
	{
		mAcc = new Acceleration (new Vector (pos.getDimension()));
		mVeloc = new Velocity (new Vector (pos.getDimension()));
		mPos = pos;
		mMass = mass;
		mFrictionCoefficient = DEFAULT_FRICTION_COEFFICIENT;
		mUpdateInterval = 1;
		mTimer = new Timer (mUpdateInterval, new MoveListener());
		mTimer.setRepeats (true);
	}
	
	/**
	 * @return friction proportional by current friction coefficient to
	 * the force moving the object
	 */
	public Force getFriction()
	{
		Vector fric = mAcc.getAcceleration().getOppositeVector();
		fric.scale (mMass * mFrictionCoefficient);
		return new Force (fric);
	}
	
	/**
	 * @return true if velocity is zero
	 */
	public boolean isStill()
	{
		return (mVeloc.getVelocity().equals (new Vector (mPos.getDimension())));
	}
	
	/**
	 * @return true if acceleration is not zero
	 */
	public boolean isAccelerating()
	{
		return (!mAcc.getAcceleration().equals (new Vector (mPos.getDimension())));
	}
	
	/**
	 * @param newUpdateInterval set update interval to be applied once a force is applied
	 * Precondition the object is not moving
	 */
	public void setUpdateInterval (int newUpdateInterval)
	{
		if (mTimer != null)
			throw new IllegalStateException();
		mUpdateInterval = newUpdateInterval;
	}
	
	/**
	 * @param forces set of forces to apply
	 * changes the acceleration of the object and starts
	 * process of regular movement
	 */
	public void applyForces (Collection<Force> forces)
	{
		Vector resultingForce = new Vector (mPos.getDimension());
		for (Force f : forces)
			resultingForce.move (f.getForce());
		applyForce (new Force (resultingForce));
	}
	
	/**
	 * updates acceleration and starts or stops timer if necessary
	 */
	public void applyForce (Force f)
	{
		f.apply (mAcc.getAcceleration(), mMass);
		resetTimer();
	}
	
	/**
	 * @param newFrictionCoefficient new friction coefficient
	 * sets newFrictionCoefficient
	 */
	public void setFriction (double newFrictionCoefficient)
	{
		mFrictionCoefficient = newFrictionCoefficient;
	}
	
	
	private void resetTimer()
	{
		if (mTimer.isRunning() && isStill())
			mTimer.stop();
		else if (!mTimer.isRunning() && isAccelerating())
			mTimer.start();
		
	}
	
	public Acceleration mAcc;
	public Velocity mVeloc;
	public Vector mPos;
	public double mMass, mFrictionCoefficient;
	int mUpdateInterval;
	
	public Timer mTimer;
}
