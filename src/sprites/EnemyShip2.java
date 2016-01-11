package sprites;

import game.Gattaca;

import org.newdawn.slick.geom.Polygon;

public class EnemyShip2 extends Polygon{
	public double posX;
	public double posY;
	public double speed;
	
	public double finalPosY;
	public int moveMod = 1;
	
	public static final int HEIGHT = 90;
	public static final int WIDTH = 70;

	public EnemyShip2(double x, double y, double nSpeed, double finalY) {
		posX = x;
		posY = y;
		speed = nSpeed;
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
		double dist = Math.abs(finalPosY-posY);
		if (dist>speed){
			posY = posY+speed;
		}
		else if (dist <= posY-finalPosY){
			posY = finalPosY;
		}
		else {
			posX += speed*moveMod;
			if (moveMod == 1 && posX+WIDTH>Gattaca.WIDTH) moveMod = -1;
			if (moveMod == -1 && posX<0) moveMod = 1;
		}
		setX((float) posX);
		setY((float) posY);
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}
	
	public Laser shootLaser() {
		return new Laser(this.getPosX()+WIDTH/2, this.getPosY()+HEIGHT, Gattaca.LASER_SPEED*-1);
	}
	
}
