package sprites;

import org.newdawn.slick.geom.Polygon;

public class Ship extends Polygon{
	
	public static final int SPEED = 1;
	
	public Ship(double x, double y) {
		super.addPoint(33, 7);
		super.addPoint(6, 61);
		super.addPoint(6, 91);
		super.addPoint(74, 91);
		super.addPoint(74, 61);
		super.addPoint(46, 7);
		super.addPoint(33, 7);
		setX((float) x);
		setY((float) y);		
	}					//(33, 7) to (6, 61) to (6,91) to (74,91) to (74, 61) to (46, 7) to (33,7)	
	

}
