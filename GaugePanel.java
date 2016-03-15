import javax.swing.*;
import java.awt.*;


public class GaugePanel extends JPanel 
{
	
	public final double HORIZONTAL_FREE = 0.05, VERTICAL_FREE = 0.05;
	
	public GaugePanel (double min, double max)
	{
		mMin = min;
		mMax = max;
		mVal = min;
	}
	
	public double getIncrementSize (int noIncrements)
	{
		return ((mMax - mMin) / noIncrements);
	}
	
	public void increase (double amount)
	{
		if (mVal + amount > mMax)
			throw new IllegalArgumentException();
		mVal += amount;
	}
	
	public void decrease (double amount)
	{
		if (mVal - amount < mMin)
			throw new IllegalArgumentException();
		mVal -= amount;
	}
	
	public void paintComponent (Graphics g)
	{
		super.paintComponent (g);
		Graphics2D g2d = (Graphics2D) g;
		
		int x = (int) (getWidth() * VERTICAL_FREE);
		int wid = (int) (getWidth() * (1 - 2 * VERTICAL_FREE));
		int y = (int) (getHeight() * HORIZONTAL_FREE);
		int hig = (int) (getHeight() * (1 - 2 * HORIZONTAL_FREE));
		int bar = (int) (y + hig - hig * (mVal - mMin) / (mMax - mMin));
		
		g2d.drawRect (x, y, wid, hig);
		g2d.drawLine (x, bar, x + wid, bar);
	}
	
	
	
	private double mMin, mMax, mVal;
}
