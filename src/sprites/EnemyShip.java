package sprites;

import org.newdawn.slick.geom.Polygon;

public class EnemyShip extends Polygon{

	public double velX;
	public double velY;
	public double posX;
	public double posY;
	public double angle;
	
	public static final int LENGTH = 5;
	public static final int WIDTH = 5;
	
	
	public EnemyShip(double x, double y, double theta, double speed) {
		posX = x;
		posY = y;
		velX = Math.cos(theta)*speed;
		velY = Math.sin(theta)*speed;
		
		double angle = theta;
		float a = (float) x;
		float b = (float) y;
		super.addPoint(a, b);
		a += Math.cos(angle) * LENGTH;
		b += Math.sin(angle) * LENGTH;
		super.addPoint(a, b);
		a += Math.cos(angle - Math.PI / 2) * WIDTH;
		b += Math.sin(angle - Math.PI / 2) * WIDTH;
		super.addPoint(a, b);
		a += Math.cos(angle - Math.PI) * LENGTH;
		b += Math.sin(angle - Math.PI) * LENGTH;
		super.addPoint(a, b);
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
}
