package sprites;

import org.newdawn.slick.geom.Polygon;

public class Ship extends Polygon{

	public double velX;
	public double velY;
	public double posX;
	public double posY;
	
	public static final int SPEED = 1;
	
	public Ship(double x, double y) {
		posX = x;
		posY = y;
		velX = 0;
		velY = 0;
		
		super.addPoint(33, 7);
		super.addPoint(6, 61);
		super.addPoint(6, 91);
		super.addPoint(74, 91);
		super.addPoint(74, 61);
		super.addPoint(46, 7);
		super.addPoint(33, 7);
	}					//(33, 7) to (6, 61) to (6,91) to (74,91) to (74, 61) to (46, 7) to (33,7)

	public void update() {
		posX += velX;
		posY += velY;
		setX((float) posX);
		setY((float) posY);
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
	
	public void resetVelX(){
		velX = 0;
	}
	
	public void resetVelY(){
		velY = 0;
	}

}
