package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import sprites.EnemyShip;
import sprites.EnemyShip2;
import sprites.Explosion;
import sprites.Laser;
import sprites.Powerup;
import sprites.Ship;

public class Gattaca extends BasicGame {

	public Image ship;
	public Image enemy;
	public Image explosion;
	public Image powerup;

	public Input input;

	public Ship player;
	public ArrayList<Laser> lasers = new ArrayList<Laser>();
	public ArrayList<EnemyShip> enemyships = new ArrayList<EnemyShip>();
	public ArrayList<EnemyShip2> enemyships2 = new ArrayList<EnemyShip2>();
	public ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	public ArrayList<Powerup> powerups = new ArrayList<Powerup>();
	public ArrayList<Laser> enemyLasers = new ArrayList<Laser>();

	public int health;
	public int frameCount;
	public int level;
	
	//POWERUPS
	public boolean isInvincible = false;
	public boolean rapidFire = false;
	public static final int HEALTH_UP = 10;

	//SPEEDS
	public static final double ENEMY_SPEED = 4;
	public static final double PLAYER_SPEED = 5;
	public static final double LASER_SPEED =-12;

	public static final int WIDTH = 1200;
	public static final int HEIGHT = 700;

	private int laserCoolCap = 10;
	private int laserCool = 0;
	
	public int spawnRate = 120;


	public Gattaca(String gamename) {
		super(gamename);
		player = new Ship(375,275);
		ship = null;
		input = null;
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		ship = new Image("/rsc/ship.png");
		enemy = new Image("/rsc/enemy.png");
		explosion = new Image("/rsc/explosion.png");
		powerup = new Image("rsc/kanye.png");
		input = new Input(HEIGHT);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		frameCount++;
		//generating Enemy Ships
		if (frameCount%spawnRate==0){
			enemyships.add(
					new EnemyShip((int)(Math.random()*WIDTH), 50, ENEMY_SPEED, player.getPosX(),player.getPosY())
					);
			enemyships2.add(
					new EnemyShip2((int)(Math.random()*WIDTH), -50, ENEMY_SPEED/2, Math.random()*200+100)
					);
		}
		
		if(frameCount % 1200 == 0 && spawnRate >= 30){
			spawnRate -= 20;
		}

		//generating powerups
		if(Math.random() < .001 && frameCount > 1){
			int powerup = (int)(Math.random()*3)+1;
			double x = Math.random()*(WIDTH);
			double y = Math.random()*HEIGHT/3;
			powerups.add(new Powerup(powerup, x, y, player.getPosX(), player.getPosY()));
		}
		

		//update stuff
		player.update();

		for(EnemyShip e : enemyships) e.update();
		for(EnemyShip2 e : enemyships2) e.update();
		for(Laser l : lasers) l.update();
		for(Laser l : enemyLasers) l.update();
		for(Powerup p: powerups) p.update();

		Iterator<Explosion> explosions_it = explosions.iterator();
		while(explosions_it.hasNext()){
			if(explosions_it.next().destruct(frameCount))
				explosions_it.remove();
		}

		Iterator<Powerup> powerups_it = powerups.iterator();
		while(powerups_it.hasNext()){
			Powerup curr_powerup = powerups_it.next();
			if(curr_powerup.destruct(frameCount)){
				if (curr_powerup.isInvincible()){
					isInvincible = false;
				}
				else if (curr_powerup.isRapidFire()){
					rapidFire = false;
				}
				powerups_it.remove();
			}
		}


		//Lasers
		if (input.isKeyDown(Input.KEY_SPACE) && laserCool > laserCoolCap){
			lasers.add(new Laser(player.getPosX()+ship.getWidth()/2, player.getPosY(), LASER_SPEED)); //launches vertically
			System.out.println();
			laserCool = 0;
		}
		laserCool++;
		
		//Enemy lasers
		Iterator<EnemyShip> enemyShipItLasers = enemyships.iterator();
		EnemyShip curEnemy;
		while (enemyShipItLasers.hasNext()){
			curEnemy=enemyShipItLasers.next();
			if (Math.random()<.01){
				enemyLasers.add(curEnemy.shootLaser());
			}
		}
		
		Iterator<EnemyShip2> enemyShipItLasers2 = enemyships2.iterator();
		EnemyShip2 curEnemy2;
		while (enemyShipItLasers2.hasNext()){
			curEnemy2=enemyShipItLasers2.next();
			if (Math.random()<.01){
				enemyLasers.add(curEnemy2.shootLaser());
			}
		}


		//collision detection, removing extra sprites
		Iterator<Laser> lasers_it = lasers.iterator();
		while(lasers_it.hasNext()){
			Laser laser = lasers_it.next();
			Iterator<EnemyShip> enemyships_it = enemyships.iterator();
			while(enemyships_it.hasNext()){
				EnemyShip e = enemyships_it.next();
				if(laser.intersects(e)){
					Explosion curr_explosion = new Explosion(e.getPosX()-explosion.getWidth()/2, e.getPosY(), frameCount);
					explosions.add(curr_explosion);
					lasers_it.remove();
					enemyships_it.remove();
					break; //because laser has been destroyed
				}
				if(e.getPosX() > WIDTH || e.getPosY() > HEIGHT) enemyships_it.remove();
			}
			Iterator<EnemyShip2> enemyships_it2 = enemyships2.iterator();
			while(enemyships_it2.hasNext()){
				EnemyShip2 e = enemyships_it2.next();
				if(laser.intersects(e)){
					Explosion curr_explosion = new Explosion(e.getPosX()-explosion.getWidth()/2, e.getPosY(), frameCount);
					explosions.add(curr_explosion);
					lasers_it.remove();
					enemyships_it2.remove();
					break;
				}
			}
			if(laser.getPosX() > WIDTH || laser.getPosY() > HEIGHT) lasers_it.remove();
		}

		Iterator<Laser> enemyLasersIt = enemyLasers.iterator();
		while(enemyLasersIt.hasNext()){
			Laser laser = enemyLasersIt.next();
			if (laser.intersects(player)){
				health--;
				enemyLasersIt.remove();
			}
			if(laser.getPosX() > WIDTH || laser.getPosY() > HEIGHT) enemyLasersIt.remove();
		}
		
		//kamikaze detection
		Iterator<EnemyShip> enemyships_it = enemyships.iterator();
		while(enemyships_it.hasNext()){
			EnemyShip e = enemyships_it.next();
			if(e.intersects(player)){
				Explosion curr_explosion = new Explosion(e.getPosX()-explosion.getWidth()/2, e.getPosY(), frameCount);
				explosions.add(curr_explosion);
				enemyships_it.remove();
				health--;
			}
		}
		
		//powerup detection
		powerups_it = powerups.iterator();
		while(powerups_it.hasNext()){
			Powerup curr_powerup = powerups_it.next();
			if(curr_powerup.intersects(player)){
				//add powerups
				if(curr_powerup.isHealth()){
					health += HEALTH_UP;
				}
				else if (curr_powerup.isInvincible()){
					isInvincible = true;
					curr_powerup.setStartFrame(frameCount);
				}
				else if (curr_powerup.isRapidFire()){
					rapidFire = true;
					curr_powerup.setStartFrame(frameCount);
				}
				curr_powerup.setInvisible(true);
			}
			if(curr_powerup.getPosX() > WIDTH || curr_powerup.getPosY() > HEIGHT) powerups_it.remove();
			
		}


		if (input.isKeyDown(input.KEY_D)) 
			player.setPosX(player.getPosX()+PLAYER_SPEED);
		if (input.isKeyDown(input.KEY_A)) 
			player.setPosX(player.getPosX()-PLAYER_SPEED);
		if (input.isKeyDown(input.KEY_W))
			player.setPosY(player.getPosY()-PLAYER_SPEED);
		if (input.isKeyDown(input.KEY_S))
			player.setPosY(player.getPosY()+PLAYER_SPEED);


		if (player.getPosX() + ship.getWidth() > WIDTH){
			player.setPosX(WIDTH - ship.getWidth());
			player.resetVelX();
		}

		else if (player.getPosX() < 0){
			player.setPosX(0);
			player.resetVelX();
		}
		if (player.getPosY() + ship.getHeight() > HEIGHT){
			player.setPosY(HEIGHT - ship.getHeight());
			player.resetVelY();
		}

		else if (player.getPosY() < HEIGHT*.6){
			player.setPosY(HEIGHT*.6);
			player.resetVelY();
		}

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		ship.draw((float) player.getPosX(), (float) player.getPosY());


		g.setColor(Color.red);
		for (Laser l : lasers)
			g.fill(l);
		
		g.setColor(Color.green);
		for (Laser l : enemyLasers){
			g.fill(l);
		}

		for (EnemyShip e : enemyships)
			enemy.draw((float) e.getPosX(), (float) e.getPosY());
		for (EnemyShip2 e : enemyships2) 
			enemy.draw((float) e.getPosX(), (float) e.getPosY()); 

		for (Explosion e: explosions)
			explosion.draw((float) e.getPosX(), (float) e.getPosY());
		
		for(Powerup p: powerups){
			if(!p.isInvisible()){
				powerup.draw((float) p.getPosX(), (float)(p.getPosY()));

			}
		}
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Gattaca("Gattaca"));
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
			appgc.setTargetFrameRate(60);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Gattaca.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}