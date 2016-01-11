package sprites;

import org.newdawn.slick.geom.Polygon;

public class Explosion{

	public double posX;
	public double posY;
	public int startFrame;
	
	public static final int LENGTH = 5;
	public static final int WIDTH = 5;
	public static final double SPEED = .1;
	
	public static final int frameLength = 60;
	
	public Explosion(double x, double y, int startFrame) {
		posX = x;
		posY = y;
	}

	public boolean destruct(int frame) {
		if(frame-startFrame >= frameLength){
			return true;
		}
		else return false;
	}
	
	public double getPosX() {
		return posX;
	}
	
	public double getPosY() {
		return posY;
	}
	
	public void setPosX(double posX) {
		this.posX = posX;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}
	

}
