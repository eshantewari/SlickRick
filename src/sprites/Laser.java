package sprites;

import org.newdawn.slick.geom.Polygon;

public class Laser extends Polygon {
	public double posX;
	public double posY;

	public static final int LENGTH = 20;
	public static final int WIDTH = 5;
	public double ySpeed;

	public Laser(double x, double y, int newSpeed) {
		posX = x;
		posY = 0;
		ySpeed = newSpeed;
		
		float a = (float) x;
		float b = (float) y;
		super.addPoint(a, b);
		super.addPoint(a+WIDTH, b);
		super.addPoint(a+WIDTH, b+LENGTH);
		super.addPoint(a, b+LENGTH);
	}

	public void update() {
		posY += ySpeed;
		setY((int)posY);
	}
	
	public double getPosX() {
		return posX;
	}
	
	public double getPosY() {
		return posY;
	}
}