package sprites;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class EnemyShip extends Polygon{

	public double posX;
	public double posY;
	public double speed;

	//will be the point to which the ship is headed
	public double finalPosX; 
	public double finalPosY;

	public static final int LENGTH = 5;
	public static final int WIDTH = 5;

	public EnemyShip(double x, double y, double nSpeed, double finalX, double finalY) {
		posX = x;
		posY = y;
		speed = nSpeed;

		finalPosX = finalX;
		finalPosY = finalY;
	}

	public void update() {
		if(!(posX == finalPosX && posY == finalPosY)){
			double theta = 0;
			double deltaX = finalPosX - posX;
			double deltaY = finalPosY - posY;
			if(deltaX == 0){
				if(posY > finalPosY) theta = 3*Math.PI/2;
				else theta = Math.PI/2;
			}
			else{
				if(deltaX > 0) theta = Math.atan(deltaY/deltaX);
				else if(deltaX < 0) theta = Math.atan(deltaY/deltaX) + Math.PI;
			}
			System.out.println(theta);
			posX += speed*Math.cos(theta);
			posY += speed*Math.sin(theta);
		}

	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}
	
	public void updateFinalPos(double newX, double newY){
		finalPosX = newX;
		finalPosY = newY;
	}


}
