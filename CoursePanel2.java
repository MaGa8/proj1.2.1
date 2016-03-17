

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


import javax.swing.Timer;

public class CoursePanel2 extends JPanel {

	private Vector startPos;
	
	private double x,y,width,height;
	
	protected int PosX = 0;
	protected int PosY = 0;
        protected int holeX;
        protected int holeY;
        private Collision collision;
        private Hole hole;

	private int ballRadius = 5;
	private ArrayList<Ball> balls;
	private ArrayList<Obstacle> obstacles;
        
	private int tarX, tarY;
	private int playerTurn = 0;
	
	TimeClass tc = new TimeClass();
    Timer timer = new Timer(5, tc);
	
	private boolean won;
	
	public CoursePanel2(double width, double height,Hole hole, ArrayList<Ball> inBalls, ArrayList inObstacles)	{
		this.width = width;
		this.height = height;
		this.balls = inBalls;
                this.obstacles= inObstacles;
                this.holeX=(int)hole.getCenter().getCoordinate(0);
                this.holeY=(int)hole.getCenter().getCoordinate(1);
                this.hole=hole;
		won = false;
		timer.start();
		
		tarX = 0;
		tarY = 0;
		
		class MousePressListener implements MouseListener	{

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				double X = e.getX();
				double Y = e.getY();
				
				Ball temp = null;
				int radius = 5;
				
				if(balls.size() != 0 && balls.get(playerTurn) != null)	{
					
					temp = balls.get(playerTurn);
					
					
					double x2 = X - temp.getPosition().getCoordinate(0);
					double y2 = Y - temp.getPosition().getCoordinate(1);
					
					//System.out.println("Vector:\nX " + ((int) x2) + "\tY: " + ((int) y2));
					
					ArrayList<Double> newVec = new ArrayList();
					newVec.add(x2);
					newVec.add(y2);
					
					Vector newVec2 = new Vector(newVec);
					
					Force f = new Force(newVec2);
					
					ArrayList<Force> fList = new ArrayList();
					fList.add(f);
					
					
					temp.applyForce(fList);
					
					//temp.applyForce(new Force(newVec2.getOppositeVector()));
					repaint();
					
                                }
			
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		addMouseListener(new MousePressListener());
		
		
		
		
	}
	
	class TimeClass implements ActionListener {
        public TimeClass() {

        }
        public void actionPerformed(ActionEvent tc){
            //System.out.println("Time");
        	
        	Ball temp = balls.get(playerTurn);
			
			
			double x2 = temp.getPosition().getCoordinate(0);
			double y2 = temp.getPosition().getCoordinate(1);
			
			//System.out.println("Ball-Position:\nX " + ((int) x2) + "\tY: " + ((int) y2));
                //collision = new Collision(obstacles,temp);
			
			double x3 = temp.getPosition().getCoordinate(0);
			double y3 = temp.getPosition().getCoordinate(1);
			//System.out.println("\nTemp-New Position:\nX " + ((int) x3) + "\tY: " + ((int) y3));
	
			balls.set(playerTurn, temp);
			collision = new Collision(obstacles,temp);
			collision.checkCollision(balls.get (playerTurn));
        	repaint();
        }
    }
	
	
	public void setTarget(int x, int y)	{
		tarX = x;
		tarY = y;
	}
	
	public void paintComponent(Graphics g)	{
		super.paintComponent(g);
		Graphics g2 = (Graphics2D) g;
		int width2 = (int) width;
		int height2 = (int) height;
		
		g2.setColor(Color.GREEN);
		g2.fillRect(10, 10, width2 - 10, height2 - 10);
		
		Ball temp = null;
		int radius = 0;
		if(balls.size() != 0)	{
			for(int i = 0; i < balls.size(); i++)	{
				temp = balls.get(i);
				if(temp != null)	{
					radius = 5;
					double x2 = balls.get(0).getPosition().getCoordinate(0);
					double y2 = balls.get(0).getPosition().getCoordinate(1);
					
					int x = (int) x2;
					int y = (int) y2;
					
					//System.out.println("\nPainting-Method:\nX: " + x + "\tY: " + y);
					
					g2.setColor(Color.white);
					if(hole.isInHole(balls.get(playerTurn)) != true)	{
						g2.setColor(Color.white);
						if(x - radius > 10 && x + radius < width && y - radius > 10 && y + radius < height)	{
							g2.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
							g2.setColor(Color.BLACK);
							g2.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
						}
					}
				}
				
			}
		}
		if(obstacles.size() != 0)	{
		for(int i = 0; i < obstacles.size(); i++)	{
                   Obstacle temp2 = (Obstacle) obstacles.get(i);
                   g2.setColor(Color.DARK_GRAY);
                   g2.fillRect((int)temp2.getX(),(int) temp2.getY(), temp2.getWidth(), temp2.getHeight());
                }
                }
		//Wall one
                /*
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, width2 + 10, 10);
		
		//Wall one
		g2.fillRect(0, 10, 10, height2 - 10);
		g2.fillRect(0, height2, width2 + 10, 10);
		g2.fillRect(width2, 10, 10, height2 - 10);
		*/
		g2.setColor(Color.BLACK);
		g2.fillOval(holeX, holeY, 3 * (int)hole.getRadius(), 3 * (int)hole.getRadius());
		
	}
	
}
