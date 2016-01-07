package sprites;

import org.newdawn.slick.geom.Polygon;

public class Ship extends Polygon{

	public double velX;
	public double velY;
	public double posX;
	public double posY;
	
	public static final int LENGTH = 5;
	public static final int WIDTH = 5;
	public static final double SPEED = .1;
	
	public Ship(double x, double y) {
		posX = x;
		posY = y;
		velX = 0;
		velY = 0;
	}

	public void update() {
		posX += velX;
		posY += velY;
	}
	
	public double getPosX() {
		return posX;
	}
	
	public double getPosY() {
		return posY;
	}
	
	public void setVelX(boolean incOrDec){
		if(incOrDec) velX += SPEED;
		else velX -= SPEED;
	}
	
	public void resetVelX(){
		velX = 0;
	}
	public void resetVelY(){
		velY = 0;
	}
	
	public void setVelY(boolean incOrDec){
		if(incOrDec) velY += SPEED;
		else velY -= SPEED;
	}
	
	public void setPosX(double posX) {
		this.posX = posX;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}
	

}
