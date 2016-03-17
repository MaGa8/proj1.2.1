

import java.io.Serializable;
import java.util.*;

/**
 *	offset position: upper left corner
 */
public class Obstacle implements Serializable{
    
    
    
    private Vector lowerLeft;
    private Vector spanX;
    private Vector spanY;
    private double width;
    private double height;
    ArrayList<Double> lowerLeftA = new ArrayList();
    ArrayList<Double> span1 = new ArrayList();
    ArrayList<Double> span2 = new ArrayList();

    
    public int getWidth(){return (int)width;}
    public int getHeight(){return (int) height;}
    public double getX(){return (double)lowerLeft.getCoordinate(0);}
    public double getY(){return (double)lowerLeft.getCoordinate(1);}
    
    public Obstacle(double xCord, double yCord, double width, double height) {
  

        lowerLeftA.add(xCord);
        lowerLeftA.add(yCord);
        this.width = width;
        this.height = height;
        this.span1.add(1.0); 
        this.span1.add(0.0);
        this.span2.add(0.0);
        this.span2.add(1.0);

        lowerLeft = new Vector(lowerLeftA);
        spanX = new Vector(span1);
        spanY = new Vector(span2);

    }

    //Getters
    public Vector getLowerLeft() {
        return lowerLeft;
    }
    public Vector getspanX() {
        return spanX;
    }

    public Vector getspanY() {
        return spanY;
    }

    public ArrayList getLowerLeftA(){
        return lowerLeftA;
    }

    public ArrayList getSpan1(){
        return span1;
    }

    public ArrayList getSpan2(){
        return span2;
    }


    //Setters
    public void setspanX(Vector spanX) {
        this.spanX = spanX;
    }

    public void setspanY(Vector spanY) {
        this.spanY = spanY;
    }

    public void setLowerLeft(Vector lowerLeft) {
        this.lowerLeft = lowerLeft;
    }

    public void setlowerLeftA(ArrayList<Double> lowerLeftA){
        this.lowerLeftA = lowerLeftA;
    }

    public void setSpan1(ArrayList<Double> span1){
        this.span1 = span1;
    }

    public void setSpan2(ArrayList<Double> span2){
        this.span2 = span2;
    }
}