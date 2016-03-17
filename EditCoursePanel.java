

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;

public class EditCoursePanel extends JPanel {

	private Vector startPos;
	
        Editor parent;
        
	protected int x,y,width,height;
	
	protected int PosX = 0;
	protected int PosY = 0;

	private int ballRadius = 5;
	private ArrayList balls;
	private ArrayList obstacles;
        
	private int tarX, tarY;
        
        Course course;
        
        private int linePressCnt=0;
        private int firstX;
        private int firstY;
        
        public Course getCourse(){return course;}
	
	public EditCoursePanel(int width, int height, ArrayList inBalls, ArrayList inObstacles,Editor inParent)	{
		this.width = width;
		this.height = height;
		this.balls = inBalls;
		this.parent = inParent;
		this.obstacles = inObstacles;
                
		startPos = new Vector(20, 20);
		
		tarX = 0;
		tarY = 0;
                
                course = new Course(width,height);
		
		class MousePressListener implements MouseListener	{

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int X = e.getX();
				int Y = e.getY();
                                System.out.println("X: " + X + "\tY: " + Y);
				if(parent.holeSelec()){
                                   linePressCnt=0;
                                   course.setHole(X,Y);
                                   repaint();                                  
                                }if(parent.ballSelec()){
                                    linePressCnt=0;
                                    
                                    GolfBall ball = new GolfBall(X, Y, 5);
                                    if(balls.size()!=0){
                                        balls.removeAll(balls);
                                    }
                                    course.setBall(X,Y);
                                    balls.add(ball);
                                    repaint();
                                }if(parent.rectSelec()){
                                   if(linePressCnt%2==0){
                                       firstX=X;
                                       firstY=Y;
                                       linePressCnt++;
                                   } else{
                                       Obstacle line;
                                       if(X>firstX&&Y>firstY){
                                       line = new Obstacle(firstX,firstY,X-firstX,Y-firstY);
                                       }else 
                                       if(X<firstX&&Y>firstY){
                                       line = new Obstacle(X,firstY,firstX-X,Y-firstY);
                                       }else 
                                       if(X>firstX&&Y<firstY){
                                       line = new Obstacle(firstX,Y,X-firstX,firstY-Y);
                                       }else {
                                       line = new Obstacle(X,Y,firstX-X,firstY-Y);
                                       } 
                                       obstacles.add(line);
                                       course.addOb(line);
                                       linePressCnt=0;
                                       repaint();
                                   }
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
	
	
	
	
	public void setTarget(int x, int y)	{
		tarX = x;
		tarY = y;
	}
	
	public void paintComponent(Graphics g)	{
		super.paintComponent(g);
		Graphics g2 = (Graphics2D) g;
		
		g2.setColor(Color.GREEN);
		g2.fillRect(10, 10, width - 10, height - 10);
		
		//GolfBall temp = new GolfBall();
		int radius = 0;
		if(balls.size() != 0)	{
			for(int i = 0; i < balls.size(); i++)	{
				GolfBall temp = (GolfBall) balls.get(i);
				if(temp != null)	{
					radius = temp.getRadius();
					int x = temp.getX();
					int y = temp.getY();
					
					g2.setColor(Color.white);
					if(x - temp.getRadius() > 10 && x + temp.getRadius() < width && y - temp.getRadius() > 10 && y + temp.getRadius() < height)	{
						g2.fillOval(x - ballRadius, y - ballRadius, 2 * ballRadius, 2 * ballRadius);
						g2.setColor(Color.BLACK);
						g2.drawOval(x - ballRadius, y - ballRadius, 2 * ballRadius, 2 * ballRadius);
					}
					
				}
				
			}
		}
                if(obstacles.size() != 0)	{
		for(int i = 0; i < obstacles.size(); i++)	{
                   Obstacle temp = (Obstacle) obstacles.get(i);
                   g2.setColor(Color.DARK_GRAY);
                   g2.fillRect((int)temp.getX(),(int) temp.getY(), temp.getWidth(), temp.getHeight());
                }
                }
		//Wall one
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, width + 10, 10);
		
		//Wall one
		g2.fillRect(0, 10, 10, height - 10);
		g2.fillRect(0, height, width + 10, 10);
		g2.fillRect(width, 10, 10, height - 10);
		
		g2.setColor(Color.BLACK);
		g2.fillOval(course.getHole()[0], course.getHole()[1], 3 * ballRadius, 3 * ballRadius);
		
	}
	
}

