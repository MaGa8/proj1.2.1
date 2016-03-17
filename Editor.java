

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
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
public class Editor extends JFrame implements ActionListener{
    JTextField fieldX;
    JTextField fieldY;
    EditCoursePanel  course;
    int[] holeLocation = new int[2];
    ButtonGroup group;
    JRadioButton hole;
    JRadioButton ball;
    JRadioButton rect;
    JRadioButton trian;
    static boolean first = true;
    
    public Editor(){
        JPanel control = setupControl();
        this.setSize(800,700);
        this.setLocationRelativeTo(null);
        //this.setLayout(new GridLayout(1,2));
        course = new EditCoursePanel(0,0,new ArrayList(),new ArrayList(),this);
        //this.add(course);
        this.add(control,BorderLayout.EAST);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setResizable(false);
        this.setVisible(true);
        if (first){
            ObjectOutputStream outputStream=null;
            try {
                ArrayList<Course> courses = new ArrayList<Course>();
                System.out.println("yt");
                outputStream = new ObjectOutputStream(new FileOutputStream("courses.dat"));
                outputStream.writeObject((ArrayList<Course>)courses);
            } catch (IOException ex) {
                System.out.println("[Laad] IO Error11: " + ex.getMessage());
            }
            finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e2) {
                System.out.println("[Update] Error12: " + e2.getMessage());
            }
        }
        }
    }
    
    public JPanel setupControl(){

        JPanel control = new JPanel();
        JLabel sizeField = new JLabel("Size Field:");
        fieldX = new JTextField("300",5);
        fieldY = new JTextField("300",5);
        JButton button = new JButton("Generate Field");
        JButton save = new JButton("Save Course");
        save.setActionCommand("save");
        button.addActionListener(this);
        save.addActionListener(this);
        JLabel type = new JLabel("Type Oject:");
        hole = new JRadioButton("Hole");
        ball = new JRadioButton("Ball");
        rect = new JRadioButton("Rectangle");
        trian = new JRadioButton("Triangle");
        group = new ButtonGroup();
        group.add(hole);
        group.add(ball);
        group.add(rect);
        group.add(trian);
        
        control.setLayout(new GridLayout(9,1));
        control.add(sizeField);
        control.add(fieldX);
        control.add(fieldY);
        control.add(button);
        control.add(type);
        control.add(hole);
        control.add(ball);
        control.add(rect);
        //control.add(trian);
        control.add(save);
        
        control.setBounds(50,50,200,200);
        return control;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("save")){
            Course toSave = course.getCourse();
            String nameInput = JOptionPane.showInputDialog("Course Name?");
            toSave.setName(nameInput);
            ObjectInputStream inputStream=null;
            ObjectOutputStream outputStream=null;
            try {
                inputStream = new ObjectInputStream(new FileInputStream("courses.dat"));
                System.out.println("g");
                ArrayList<Course> courses = (ArrayList<Course>) inputStream.readObject();
                courses.add(toSave);
                outputStream = new ObjectOutputStream(new FileOutputStream("courses.dat"));
                outputStream.writeObject((ArrayList<Course>)courses);
            } catch (IOException ex) {
                System.out.println("[Laad] IO Error: " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println("error");
            }finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();
                }
            } catch (IOException e2) {
                System.out.println("[Update] Error: " + e2.getMessage());
            }
        }

            
        }else
        System.out.println("gg");
        String text1 = fieldX.getText();
        int a = Integer.parseInt(text1);
        String text2 = fieldY.getText();
        int b = Integer.parseInt(text2);
        Container contain = getContentPane();
        contain.removeAll();
        course = new EditCoursePanel(a,b,new ArrayList(),new ArrayList(),this);
        this.add(course);
        JPanel control = setupControl();
        this.add(control,BorderLayout.EAST);
        contain.validate();
        contain.repaint();
        this.repaint();
    }
    
    public boolean ballSelec(){
        return ball.isSelected();
    }
    public boolean holeSelec(){
        return hole.isSelected();
    }
    public boolean rectSelec(){
        return rect.isSelected();
    }
    public boolean trianSelec(){
        return trian.isSelected();
    }
}

