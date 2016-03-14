
/**
 * modifier class modeling an acceleration
 * @author martin
 */
public class Acceleration 
{
	/**
	 * parametric constructor
	 * @param acceleration to store
	 */
	public Acceleration (Vector acceleration)
	{
		mAcceleration = acceleration;
	}
	
	/**
	 * @return force vector stored
	 */
	public Vector getAcceleration()
	{
		return mAcceleration;
	}
	
	/**
	 * @param newAcceleration new acceleration to use
	 */
	public void setNewAcceleration (Vector newAcceleration)
	{
		mAcceleration = newAcceleration;
	}
	
	/**
	 * alters velocity depending on acceleration stored and dT passed
	 * @param velocity velocity vector to alter
	 * @param dT time interval
	 */
	public void apply (Vector velocity, double dT)
	{
		for (int cDim = 0; cDim < mAcceleration.getDimension(); ++cDim)
			velocity.move (cDim, mAcceleration.getCoordinate (cDim) * dT);
	}
	
	
	private Vector mAcceleration;
}
