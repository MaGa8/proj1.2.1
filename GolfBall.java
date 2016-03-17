
import java.util.Vector;

public class GolfBall {

	private Vector direction;
	private int posX, posY;
	private int radius;
	
	public GolfBall(int posX, int posY, int radius)	{
		this.posX = posX;
		this.posY= posY;
		this.radius = radius;
	}
	
	public GolfBall()	{}
	



	public int getX()	{
		return posX;
	}
	
	public int getRadius()	{
		return radius;
	}
	
	public int getY()	{
		return posY;
	}
	
	
	
}
