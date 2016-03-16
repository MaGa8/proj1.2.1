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
	 * alters position depending on velocity stored
	 * @param pos position to alter
	 * @param mass mass exposed to acceleration
	 */
	public void apply (Vector position, double dT)
	{
		for (int cDim = 0; cDim < mVelocity.getDimension(); ++cDim)
			position.move (cDim, mVelocity.getCoordinate (cDim) * dT);
	}
	
	private Vector mVelocity;
}
