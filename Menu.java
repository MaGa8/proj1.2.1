/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Asus
 */
public class Menu extends JFrame implements ActionListener {

	public static final double BALL_RADIUS = 7;
	
	
    Editor truck;
    ButtonGroup type;
    ButtonGroup alg;
    JRadioButton parc;
    JRadioButton pent;
    JRadioButton greedy;
    JRadioButton other;
    JRadioButton random;
    JRadioButton genetic;
    JLabel lastTotalValue;
    JRadioButton efficiencyGreedy;
    JTextField one;
    JTextField two;
    JTextField xt;
    JTextField yt;
    JTextField zt;
    
    public Menu() throws IOException{
        JPanel buttonPanel = new JPanel();
        ImagePanel imagePanel = new ImagePanel();
        buttonPanel.setLayout(new GridLayout(3,1));
        JButton b1 = new JButton("start game");
        b1.addActionListener(this);
        JButton b2 = new JButton("course editor");
        b2.addActionListener(this);
        JButton b3 = new JButton("exit");
        b3.addActionListener(this);
        b1.setActionCommand("start");
        b2.setActionCommand("editor");
        b3.setActionCommand("exit");
        
        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.add(b3);
        this.add(buttonPanel,BorderLayout.WEST);        
        this.add(imagePanel,BorderLayout.CENTER);

        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(350,330);
        this.setVisible(true);
    }
    


    
    
    public static void main(String[] args) throws IOException {
        Menu menu = new Menu();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("editor")){
            System.out.println("aah");
            Editor editor = new Editor();
        }if(e.getActionCommand().equals("start")){
            ObjectInputStream inputStream;
            ArrayList<Course> toPlay=null;
            try {
                inputStream = new ObjectInputStream(new FileInputStream("courses.dat"));
                toPlay = (ArrayList<Course>) inputStream.readObject();
            } catch (IOException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
            String courseName = JOptionPane.showInputDialog("Course Name?");
            Course play=null;
            for (int i=0;i<toPlay.size();i++){
                if (courseName.equals(toPlay.get(i).getName())){
                    play = toPlay.get(i);
                }
            }
            
            ArrayList<Double> v = new ArrayList();
            v.add((double)play.getBall()[0]);
            v.add((double)play.getBall()[1]);
            Ball b = new Ball(new Vector(v), BALL_RADIUS, 0.0003);
            ArrayList<Ball> t = new ArrayList();
            t.add(b);
            ArrayList<Double> m = new ArrayList();
            m.add((double)play.getHole()[0]);
            m.add((double)play.getHole()[1]);
            Hole hole = new Hole(new Vector(m),7);
            CoursePanel2 p = new CoursePanel2(play.getHeigth(),play.getWidth(),hole,
                    t,play.obstacles());
            JFrame playFrame;
            playFrame = new JFrame(toPlay.get(0).getName());
            playFrame.setSize(700,700);
            playFrame.add(p);
            playFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            playFrame.setVisible(true);

            
        }
        
    }
    
}
class ImagePanel extends JPanel{

    private BufferedImage image;

    public ImagePanel() {
       try {                
          image = ImageIO.read(new File("golfcourse2.jpg"));
       } catch (IOException ex) {
            // handle exception...
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }

}