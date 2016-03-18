
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * class drawing wind
 * @author martin
 */
public class WindDrawer 
{
	public static final double WIND_DRAWING_COEFFICIENT = 1.0;
	public static final int NUMBER_ARROWS = 5;
	
	/**
	 * parametric constructor
	 * @param size size of wind area
	 * @param wind wind vector
	 */
	public WindDrawer (Rectangle2D size, Vector wind)
	{
		mSize = size;
		mWind = wind;
	}
	
	/**
	 * @param g graphics object
	 * paints wind
	 */
	public void paint (Graphics g)
	{
		if (!mWind.equals (new Vector (2)))
		{
			Color grey = new Color (128, 128, 128, 90);
			g.setColor (grey);
			g.fillRect ((int)mSize.getMinX(), (int)mSize.getMinY(), (int)mSize.getWidth(), (int)mSize.getHeight());
		}
	}
	
	
	
	public Rectangle2D mSize;
	public Vector mWind;
}
