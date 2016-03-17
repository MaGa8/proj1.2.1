
/**
 * class behaving as a lock
 * handing out exactly one key object
 * to the caller who thereafter becomes the lock's owner.
 * the locks owner may lock or unlock the lock using the key
 * @author martin
 */
public class Lock 
{
	/**
	 * default exception for lock class
	 * @author martin
	 */
	public static class LockException extends IllegalStateException
	{
		public LockException() {}
		
		public LockException (String s) { super (s); }
	}
	
	public class Key {}
	
	/**
	 * instantiates an open lock
	 * not owned by anyone
	 */
	public Lock()
	{
		mOpener = null;
		mOpen = true;
	}
	
	/**
	 * @return the key object thus making the caller the
	 * owner of this lock
	 */
	public Key getOwnership()
	{
		if (hasOwner())
			throw new LockException ("Lock already has owner, cannot assume ownership");
		mOpener = new Key();
		return mOpener;
	}
	
	/**
	 * @return true if lock has owner
	 */
	public boolean hasOwner() { return (mOpener == null); }
	
	/**
	 * @param opener a given key
	 * @return true if opener is the key to open this lock
	 */
	public boolean canOpen (Key opener) { return mOpener == opener; }
	
	/**
	 * @return true if lock is open
	 */
	public boolean isOpen() { return mOpen; }
	
	/**
	 * @param openFlag true to unlock, false to lock
	 * @param k key to open lock
	 * sets open state of lock to open flag
	 */
	public void setOpen (boolean openFlag, Key k)
	{
		if (!canOpen (k))
			throw new LockException ("invalid key, cannot change state of lock");
		mOpen = openFlag;
	}
	
	private Key mOpener;
	private boolean mOpen;
}
