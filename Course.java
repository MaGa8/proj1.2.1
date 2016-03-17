

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

public class Course extends JComponent{

	private int height, width;
	private ArrayList<Obstacle> obstacles=new ArrayList();
        private int ballX,ballY;
	private int holeX, holeY;
        private String name;
	private static long serialVersionUID = -1558523441458683306L;
        
        public Course(int height,int width){
            this.height=height;
            this.width=width;
            Obstacle e1 = new Obstacle(0, 0, height + 10, 10);
            Obstacle e2 = new Obstacle(0, width, height+10, 10);
            Obstacle e3 = new Obstacle(height,0,10,width+10);
            Obstacle e4 = new Obstacle(0,0,10,width+10);
            obstacles.add(e1);
            obstacles.add(e2);
            obstacles.add(e3);
            obstacles.add(e4);
        }
	
	public void setBall(int x, int y){
            ballX=x;
            ballY=y;
        }
	
        public void setHole(int x,int y){
            holeX=x;
            holeY=y;
        }

        public int[] getHole(){
            int[] hole = new int[2];
            hole[0]=holeX;
            hole[1]=holeY;
            return hole;
        }
        
        public int[] getBall(){
            int[] ball = new int[2];
            ball[0]=ballX;
            ball[1]=ballY;
            return ball;
        }
        public ArrayList obstacles(){
            return obstacles;
        }
        public void addOb(Obstacle a){
            obstacles.add(a);
        }
        @Override
        public int getWidth(){return width;}
        public int getHeigth(){return height;}
	public void setName(String a){name=a;}
	public String getName(){return name;}
}
