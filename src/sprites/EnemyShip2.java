package sprites;

import game.Gattaca;

import org.newdawn.slick.geom.Polygon;

public class EnemyShip2 extends Polygon{
	public double posX;
	public double posY;
	public double speed;
	
	public double finalPosY;
	public int moveMod;
	
	public static final int WIDTH = 81;
	public static final int HEIGHT = 116;
	
	public EnemyShip2(double x, double y, double nSpeed, double finalY) {
		posX = x;
		posY = y;
		speed = nSpeed;
		finalPosY = finalY;
		
		int rand = (int)(Math.random()*2);
		if (rand == 1) moveMod = 1;
		else moveMod = -1;
		
		super.addPoint(9,0);
		super.addPoint(1,23);
		super.addPoint(0,44);
		super.addPoint(12,75);
		super.addPoint(6,79);
		super.addPoint(6,91);
		super.addPoint(9,107);
		super.addPoint(14,115);
		super.addPoint(22,115);
		super.addPoint(33,102);
		super.addPoint(48,102);
		super.addPoint(60,115);
		super.addPoint(67,115);
		super.addPoint(73,106);
		super.addPoint(75,79);	
		super.addPoint(68,75);	
		super.addPoint(80,47);	
		super.addPoint(80,23);
		super.addPoint(71,0);	
		super.addPoint(59,0);	
		super.addPoint(48,24);	
		super.addPoint(33,24);	
		super.addPoint(21,0);	
		super.addPoint(9,0);	

//		super.addPoint(13,13);
//		super.addPoint(5,36);
//		super.addPoint(4,57);
//		super.addPoint(16,88);
//		super.addPoint(10,92);
//		super.addPoint(10,104);
//		super.addPoint(13,120);
//		super.addPoint(18,128);
//		super.addPoint(26,128);
//		super.addPoint(37,115);
//		super.addPoint(52,115);
//		super.addPoint(64,128);
//		super.addPoint(71,128);
//		super.addPoint(77,119);
//		super.addPoint(79,92);	
//		super.addPoint(72,88);	
//		super.addPoint(84,60);	
//		super.addPoint(84,36);
//		super.addPoint(75,13);	
//		super.addPoint(63,13);	
//		super.addPoint(52,37);	
//		super.addPoint(37,37);	
//		super.addPoint(25,13);	
//		super.addPoint(13,13);	

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
