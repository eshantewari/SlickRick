package sprites;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import game.Gattaca;

public class EnemyShip extends Polygon{

	public double posX;
	public double posY;
	public double speed;
	
	private boolean onFinalStretch;
	private double finalTheta;

	//will be the point to which the ship is headed
	public double finalPosX; 
	public double finalPosY;

	public static final int HEIGHT = 90;
	public static final int WIDTH = 70;

	public EnemyShip(double x, double y, double nSpeed, double finalX, double finalY) {
		posX = x;
		posY = y;
		speed = nSpeed;
		
		onFinalStretch = false;
		finalTheta = 0;

		finalPosX = finalX;
		finalPosY = finalY;
		
		super.addPoint(21,20);		
		super.addPoint(10,20);
		super.addPoint(4,48);
		super.addPoint(4,60);
		super.addPoint(29,85);
		super.addPoint(40,85);
		super.addPoint(65,60);
		super.addPoint(65,48);
		super.addPoint(59, 20);
		super.addPoint(48, 20);
		super.addPoint(48, 4);
		super.addPoint(21,4);
		super.addPoint(21,20);		
	}

	public void update() {
		if(!(posX >= finalPosX-5 && posX <= finalPosX+5 && posY >= finalPosY-5 && posY <= finalPosY+5) && !onFinalStretch){
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
			//System.out.println(theta);
			posX += speed*Math.cos(theta);
			posY += speed*Math.sin(theta);
		}
		else{
			if(finalTheta == 0){
				finalTheta = Math.random()*Math.PI*2;
				onFinalStretch = true;
			}
			posX += speed*Math.cos(finalTheta);
			posY += speed*Math.sin(finalTheta);
			
		}
		setX((float)posX);
		setY((float)posY);
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
	
	public Laser shootLaser() {
		return new Laser(this.getPosX()+WIDTH/2, this.getPosY()+HEIGHT, Gattaca.LASER_SPEED*-1);
	}


}