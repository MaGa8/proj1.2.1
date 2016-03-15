
/**
 * modifier class modeling a force
 * modifies acceleration vector when applied to it
 * @author martin
 */
public class Force 
{
	/**
	 * parametric constructor
	 * @param force force to store
	 */
	public Force (Vector force)
	{
		mForce = force;
	}
	
	/**
	 * @return force vector stored
	 */
	public Vector getForce()
	{
		return mForce;
	}
	
	/**
	 * @param newForce new force vector to use
	 */
	public void setNewForce (Vector newForce)
	{
		mForce = newForce;
	}
	
	/**
	 * alters acceleration depending on force stored
	 * @param acceleration acceleration to alter
	 * @param mass mass exposed to acceleration
	 */
	public void apply (Vector acceleration, double mass)
	{
		for (int cDim = 0; cDim < acceleration.getDimension(); ++cDim)
			acceleration.move (cDim, mForce.getCoordinate (cDim) / mass);
	}
	
	private Vector mForce;
}
