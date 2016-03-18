

import java.util.ArrayList;

/**
 * Created by Alexander on 16.03.2016.
 */
public class Collision{

    ArrayList<Obstacle> arrayOfObjects;
    Ball gameBall;

    public enum Orientation {HORIZONTAL, VERTICAL}

    public Collision(ArrayList<Obstacle> arrayOfObjects, Ball gameBall){
        this.arrayOfObjects = arrayOfObjects;
        this.gameBall = gameBall;
    }

    public boolean checkCollision(Ball gameBall) {
     
        Vector ballCheck = gameBall.getPosition();
        double xCordBall = ballCheck.getCoordinate(0);
        double yCordBall = ballCheck.getCoordinate(1);
        if (!gameBall.obtainForceManager().isStill()) {
        
            for(int i=0; i<arrayOfObjects.size(); i++){
                Obstacle toCheck=arrayOfObjects.get(i);
                Vector checkVector = toCheck.getLowerLeft();
                double xCordObstacle = checkVector.getCoordinate(0);
                double yCordObstacle = checkVector.getCoordinate(1);

                if (xCordBall + gameBall.getRadius() >= xCordObstacle && 
                	xCordBall - gameBall.getRadius() <= (xCordObstacle + toCheck.getWidth()) &&
                	yCordBall + gameBall.getRadius() >= yCordObstacle && 
                	yCordBall - gameBall.getRadius() <= (yCordObstacle + toCheck.getHeight())) {
                    findSide(xCordBall, yCordBall, gameBall, toCheck, checkVector);
                    return true;
                } 
            }
        }
        return false;
    }

    public void findSide(double xCordBall, double yCordBall, Ball gameBall, Obstacle toCheck, Vector checkVector) {
        double disLeftX = xCordBall - checkVector.getCoordinate(0);
        double disRightX = checkVector.getCoordinate(0) + toCheck.getWidth() - xCordBall;
        double disLowerY = yCordBall - checkVector.getCoordinate(1);
        double disUpperY = checkVector.getCoordinate(1) + toCheck.getHeight() - yCordBall;
        Vector spanX=toCheck.getspanX();
        Vector spanY=toCheck.getspanY();

        double min = Math.min (disLeftX, Math.min (disRightX, Math.min (disLowerY, disUpperY)));
        //if ball is closest to vertical line
        if (min == disLeftX || min == disRightX)
        	updateAngle (gameBall, Orientation.VERTICAL);
        else
        	updateAngle (gameBall, Orientation.HORIZONTAL);
        
        /*
        if (disLeftX < disRightX && disLeftX < disLowerY && disLeftX < disUpperY) {
            findAngle(spanY, gameBall);
            //GetVelocity
        } else if (disRightX < disUpperY && disRightX < disLowerY) {
            //rightSide
            findAngle(spanY, gameBall);
        } else if (disUpperY < disLowerY) {
            findAngle(spanX, gameBall);
            //UpperSide
        } else {
            findAngle(spanX, gameBall);
            //Lower Side
        }
        */
    }

    /*
    public void findAngle(Vector fromObstacle, Ball gameBall) {
        System.out.println("3");
        Vector direction = gameBall.obtainForceManager().getVelocity();
        double xDirection = direction.getCoordinate(0);
        double yDirection = direction.getCoordinate(1);
        
        
        double xObstacle = fromObstacle.getCoordinate(0);
        double yObstacle = fromObstacle.getCoordinate(1);
        double scalar = xDirection * xObstacle + yDirection * yObstacle;
        double length = Math.sqrt(xDirection * xDirection + yDirection * yDirection);
        double quotient = scalar / length;
        double angle = Math.toDegrees(Math.acos(quotient));
        
        if (angle < 90) {
            updateAngle(gameBall, angle, fromObstacle);

        } else if (angle > 90) {
            updateAngle(gameBall, 180 - angle, fromObstacle);

        } else {
            updateAngle(gameBall, angle, fromObstacle);
        }
    }*/

    public void updateAngle(Ball gameBall, Orientation collisionLineOrientation) 
    {
    	ForceManager fManage = gameBall.obtainForceManager();
    	Vector counterForce = new Vector (2);
    	
    	if (collisionLineOrientation == Orientation.HORIZONTAL)
    	{
    		double yVal = fManage.getVelocity().getCoordinate (1);
    		counterForce.move (1, -2 * yVal);
    	}
    	else
    	{
    		double xVal = fManage.getVelocity().getCoordinate (0);
    		counterForce.move (0, -2 * xVal);
    	}
    	
    	counterForce.scale (gameBall.getMass() / fManage.getTimeInterval());
		fManage.applyForce (new Force (counterForce));
    	
    	
    	/*
        System.out.println("4");
        //The 0- takes care of flippin, may need to be removed later
        Vector direction = gameBall.obtainForceManager().getVelocity();
        double xDirection = direction.getCoordinate(0);
        double yDirection = direction.getCoordinate(1);
        double turningAngle = 180 - 2 * angle;

        if(hitSide.getCoordinate(0)==1){
	        if (angle == 90) {
	            xDirection = xDirection*2;
	            yDirection = 0 - yDirection*2;
        	} 
	        else {
	            xDirection = xDirection * Math.cos(Math.toRadians(turningAngle)) - yDirection * Math.sin(Math.toRadians(turningAngle)) - xDirection;
	            yDirection = 0 - xDirection * Math.sin(Math.toRadians(turningAngle)) + yDirection * Math.cos(Math.toRadians(turningAngle)) - yDirection;
        	}
        }
        else{
        	if (angle == 90) {
	            xDirection = 0 - xDirection*2;
	            yDirection = yDirection*2;
	
	        } 
        	else {
	            xDirection = 0- xDirection * Math.cos(Math.toRadians(turningAngle)) - yDirection * Math.sin(Math.toRadians(turningAngle)) - xDirection;
	            yDirection = xDirection * Math.sin(Math.toRadians(turningAngle)) + yDirection * Math.cos(Math.toRadians(turningAngle)) - yDirection;
        	
        	}
        }
        xDirection = xDirection*gameBall.getMass()/gameBall.obtainForceManager().getTimeInterval();
        yDirection = yDirection*gameBall.getMass()/gameBall.obtainForceManager().getTimeInterval();
        ArrayList<Double> nDirection = new ArrayList();
        nDirection.add(xDirection);
        nDirection.add(yDirection);
        Vector newDirection = new Vector(nDirection);
        Force f = new Force(newDirection);
        ArrayList<Force> forceList = new ArrayList();
        forceList.add(f);
        gameBall.applyForce(forceList);
        return true;
        */
    }
}


