/**
 * modifier class modeling a velocity
 * modifies acceleration vector when applied to it
 * @author martin
 */
public class Velocity 
{
	/**
	 * parametric constructor
	 * @param velocity velocity to store
	 */
	public Velocity (Vector velocity)
	{
		mVelocity = velocity;
	}
	
	/**
	 * @return force vector stored
	 */
	public Vector getVelocity()
	{
		return mVelocity;
	}
	
	/**
	 * @param newvelocity new velocity vector to use
	 */
	public void setNewvelocity (Vector newvelocity)
	{
		mVelocity = newvelocity;
	}
	
	/**
	 * alters acceleration depending on velocity stored
	 * @param acceleration acceleration to alter
	 * @param mass mass exposed to acceleration
	 */
	public void apply (Vector acceleration, double mass)
	{
		for (int cDim = 0; cDim < acceleration.getDimension(); ++cDim)
			acceleration.move (cDim, mVelocity.getCoordinate (cDim) / mass);
	}
	
	private Vector mVelocity;
}
