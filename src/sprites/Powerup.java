package sprites;

import org.newdawn.slick.geom.Polygon;

public class Powerup extends Polygon{

	public boolean rapidFire; //1
	public boolean health; //2
	public boolean invincible; //3

	public boolean canDestruct;





	public double posX;
	public double posY;


	public double theta;

	public boolean invisible; //powerup sprite is invisible

	public int startFrame;
	public static final int frameLength = 300;

	public static final int SPEED = 5;

	public Powerup(int num, double posX, double posY, double finalX, double finalY){
		super.addPoint(0, 0);
		super.addPoint(0, 50);
		super.addPoint(50, 50);
		super.addPoint(50, 0);
		
		rapidFire = false;
		health = false;
		invincible = false;


		invisible = false;
		canDestruct = false;
		this.startFrame = 0;
		this.posX = posX;
		this.posY = posY;
		setX((float) posX);
		setY((float) posY);

		if(num == 1) rapidFire = true;
		if(num == 2) health = true;
		if(num == 3) invincible = true;


		double deltaX = finalX - posX;
		double deltaY = finalY - posY;

		if(deltaX == 0){
			if(posY > finalY) theta = 3*Math.PI/2;
			else theta = Math.PI/2;
		}
		else{
			if(deltaX > 0) theta = Math.atan(deltaY/deltaX);
			else if(deltaX < 0) theta = Math.atan(deltaY/deltaX) + Math.PI;
		}

	}

	public void setInvisible(boolean b) {
		invisible = true;
	}

	public void update(){
		posX += SPEED*Math.cos(theta);
		posY += SPEED*Math.sin(theta);
		setX((float) posX);
		setY((float) posY);
	}

	public boolean destruct(int frame) {
		if(canDestruct){ 
			if(frame-startFrame >= frameLength){
				return true;
			}
		}
		return false;
	}

	public void setStartFrame(int frame){
		this.startFrame = frame;
	}


	public boolean isRapidFire() {
		return rapidFire;
	}

	public void setRapidFire(boolean rapidFire) {
		this.rapidFire = rapidFire;
	}

	public boolean isHealth() {
		return health;
	}

	public void setHealth(boolean health) {
		this.health = health;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public boolean isInvisible(){
		return invisible;
	}


	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
		setX((float) posX);
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
		setY((float) posY);
	}

	public void setCanDestruct(int frame) {
		this.canDestruct = true;
		startFrame = frame;
	}

	public boolean canDestruct(){
		return canDestruct;
	}



}
