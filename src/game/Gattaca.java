package game;
import java.awt.Font;
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
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

import sprites.EnemyShip;
import sprites.EnemyShip2;
import sprites.Laser;	
import sprites.Ship;
import sprites.Powerup;


public class Gattaca extends BasicGame {

	public boolean restart = true;
	public boolean endGame = false;
	public int finalScore = 0;
	
	public Image ship;
	public Image shipRed;
	public Image enemyShip;
	public Image enemyShip2;
	public Image explosion;
	public Image powerup;

	public Input input;
	
	public Ship player;
	public ArrayList<Laser> lasers = new ArrayList<Laser>();
	public ArrayList<Laser> enemyLasers = new ArrayList<Laser>();
	public ArrayList<EnemyShip> enemyships = new ArrayList<EnemyShip>();
	public ArrayList<EnemyShip2> enemyships2 = new ArrayList<EnemyShip2>();
	public ArrayList<Powerup> powerups = new ArrayList<Powerup>();
	
	//POWERUPS
	public boolean isInvincible = false;
	public boolean rapidFire = false;
	public static final int HEALTH_UP = 30;
	public static final int NORMAL_COOLDOWN = 20;
	public static final int RAPID_COOLDOWN = 5;

	public float health = 100;
	public int score = 0;
	public int frameCount;
	
	public static final double ENEMY_SPEED = 4;
	public static final double LASER_SPEED = -12;
	public static final double PLAYER_SPEED = 6;

	
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 700;
	//public static final double SPEED = 0.1;

	

	public Gattaca(String gamename) {
		super(gamename);
		player = new Ship(550,500);
		ship = null;
		input = null;
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		ship = new Image("/rsc/Ship.png");
		shipRed = new Image("/rsc/ShipRed.png");
		enemyShip = new Image("/rsc/EnemyShip.png");
		enemyShip2 = new Image("/rsc/EnemyShip2.png");
		powerup = new Image("/rsc/powerup.png");
		input = new Input(HEIGHT);
	}

	private int laserCoolCap = 20;
	private int laserCool = 0;
	private int invincibleCoolCap = 20;
	private int invincibleCool = 20;
	private float enemyShipSpawn=120;
	private float enemyShipSpawnCool=0;
	private float enemyShipSpawn2=120;
	private float enemyShipSpawnCool2=0;
	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		if(restart){
			lasers = new ArrayList<Laser>();
			enemyLasers = new ArrayList<Laser>();
			enemyships = new ArrayList<EnemyShip>();
			enemyships2 = new ArrayList<EnemyShip2>();
			powerups = new ArrayList<Powerup>();
			laserCoolCap = 20;
			invincibleCool = 20;
			frameCount = 0;
			health = 100;
			player = new Ship(550,500);
			score = 0;
			restart = false;
			hBar = new Rectangle(1188, 698-health, 10, health);
			enemyShipSpawn = 120;
			enemyShipSpawn2 = 120;
			isInvincible=false;
			rapidFire=false;
		}
		if(health <= 0){ 
			endGame = true;
			finalScore = score;
			if(input.isKeyDown(Input.KEY_R)){
				restart = true;
				endGame = false;
			}
			return;
		}
		frameCount++;
		enemyShipSpawnCool++;
		enemyShipSpawnCool2++;
		invincibleCool++;
		
		
		if(rapidFire)
			laserCoolCap = RAPID_COOLDOWN;
		else
			laserCoolCap = NORMAL_COOLDOWN;
		
		int scoreMod = score/1000;
		if (scoreMod>60) scoreMod=60;
		//generating Enemy Ships
		if (enemyShipSpawnCool>=enemyShipSpawn){ 
			enemyships.add(
					new EnemyShip((int)(Math.random()*(WIDTH-EnemyShip.WIDTH)), -EnemyShip.HEIGHT, ENEMY_SPEED, player.getX(),player.getY())
					);
			enemyShipSpawnCool=0;
			enemyShipSpawn = (float) (Math.random()*60+90)-scoreMod;
		}
		if (enemyShipSpawnCool2>=enemyShipSpawn2){ 
			enemyships2.add(
					new EnemyShip2((int)(Math.random()*(WIDTH-EnemyShip2.WIDTH)), -EnemyShip2.HEIGHT, ENEMY_SPEED/2, Math.random()*200+100)
					);		
			enemyShipSpawnCool2=0;
			enemyShipSpawn2 = (float) (Math.random()*60+90)-scoreMod;
		}
		
		//update stuff
		for(EnemyShip e : enemyships) e.update();
		for(EnemyShip2 e : enemyships2) e.update();
		for(Laser l : lasers) l.update();
		for(Laser l : enemyLasers) l.update();
		for(Powerup p: powerups) p.update();
		
		//generating powerups
		if(frameCount > 1000 && Math.random() < .001){
			int whichPow = (int)(Math.random()*3)+1;
			double x = Math.random()*(WIDTH-powerup.getWidth());
			double finalX = Math.random()*(WIDTH-powerup.getWidth());
			powerups.add(new Powerup(whichPow, x, -1*powerup.getHeight(), finalX, HEIGHT+powerup.getHeight()));
		}
		
		//powerups
		Iterator<Powerup> powerups_it = powerups.iterator();
		while(powerups_it.hasNext()){
			Powerup curr_powerup = powerups_it.next();
			if(curr_powerup.destruct(frameCount)){
				if (curr_powerup.isInvincible()){
					isInvincible = false;
					//System.out.println("Not Invincible");
				}
				else if (curr_powerup.isRapidFire()){
					rapidFire = false;
					//System.out.println("Not Rapid Fire");
				}
				powerups_it.remove();
			}
		}
				
		//Lasers
		if (input.isKeyDown(Input.KEY_SPACE) && laserCool > laserCoolCap){
			lasers.add(new Laser(player.getX()+ship.getWidth()/2, player.getY(), LASER_SPEED)); //launches vertically
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
			boolean lasGone = false; //if true, laser removed. 
			Laser laser = lasers_it.next();
			Iterator<EnemyShip> enemyships_it = enemyships.iterator();
			while(enemyships_it.hasNext()){
				EnemyShip e = enemyships_it.next();
				if(laser.intersects(e)){
					lasGone = true;
					enemyships_it.remove();
					score += 500;
					break; //because laser has been destroyed
				}
			}
			Iterator<EnemyShip2> enemyships_it2 = enemyships2.iterator();
			while(enemyships_it2.hasNext()){
				EnemyShip2 e = enemyships_it2.next();
				if(laser.intersects(e)){
					lasGone = true;
					enemyships_it2.remove();
					score += 300;
					break;
				}
			}
			if(lasGone || laser.getPosX() > WIDTH || laser.getPosY() > HEIGHT) lasers_it.remove();
		}
		
		Iterator<Laser> enemyLasersIt = enemyLasers.iterator();
		while(enemyLasersIt.hasNext()){
			Laser laser = enemyLasersIt.next();
			if (laser.intersects(player)){
				if (!isInvincible && invincibleCool>invincibleCoolCap){
					health-=20;
					hBar = new Rectangle(1188, 698-health, 10, health);
					invincibleCool = 0;
					 
				}
				enemyLasersIt.remove();
			}
			if(laser.getPosX() > WIDTH || laser.getPosY() > HEIGHT) enemyLasersIt.remove();
		}
		Iterator<EnemyShip> enemyShipsIt = enemyships.iterator();
		while (enemyShipsIt.hasNext()) {
			EnemyShip curShip = enemyShipsIt.next();
			if (curShip.intersects(player)){
				if (!isInvincible && invincibleCool>invincibleCoolCap){
					health-=50;
					hBar = new Rectangle(1188, 698-health, 10, health);
					invincibleCool = 0;
					 
				}
				enemyShipsIt.remove();
			}
		}
		
		//powerup detection
		powerups_it = powerups.iterator();
		while(powerups_it.hasNext()){
			Powerup curr_powerup = powerups_it.next();
			boolean flag = false;
			if(curr_powerup.intersects(player)){
				//add powerups
				curr_powerup.setInvisible(true);
				if(curr_powerup.isHealth()){
					health += HEALTH_UP;
					if(health > 100) health = 100;
					powerups_it.remove();
					flag = true;
					hBar = new Rectangle(1188, 698-health, 10, health);
				}
				else if (curr_powerup.isInvincible()){
					isInvincible = true;
					curr_powerup.setCanDestruct(frameCount);
					//System.out.println("Invincible");
				}
				else if (curr_powerup.isRapidFire()){
					rapidFire = true;
					curr_powerup.setCanDestruct(frameCount);
					//System.out.println("Rapid Fire");
				}

			}
			if(!flag && !curr_powerup.canDestruct() && (curr_powerup.getPosX() > WIDTH || curr_powerup.getPosY() > HEIGHT)) powerups_it.remove();
			
		}
		
		if (input.isKeyDown(Input.KEY_D) || input.isKeyDown(Input.KEY_RIGHT)) 
			player.setX((float) (player.getX()+PLAYER_SPEED));
		if (input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)) 
			player.setX((float) (player.getX()-PLAYER_SPEED));
		if (input.isKeyDown(Input.KEY_W) || input.isKeyDown(Input.KEY_UP))
			player.setY((float) (player.getY()-PLAYER_SPEED));
		if (input.isKeyDown(Input.KEY_S) || input.isKeyDown(Input.KEY_DOWN))
			player.setY((float) (player.getY()+PLAYER_SPEED));

		if (player.getX() + ship.getWidth() > WIDTH){
			player.setX(WIDTH - ship.getWidth());
		}
		else if (player.getX() < 0){
			player.setX(0);
		}
		
		if (player.getY() + ship.getHeight() > HEIGHT){
			player.setY(HEIGHT - ship.getHeight());
		}
		else if (player.getY() < HEIGHT*.6){
			player.setY((float) (HEIGHT*.6));
		}
		
		score++;
	}

	Rectangle hBarBG = new Rectangle(1186, 596, 14, 104);
	Rectangle hBar = new Rectangle(1188, 598, 10, 100);
	private float shift = 0;
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		Image spaceTile = new Image("/rsc/SpaceTile.png");
		for (int i=0; i<WIDTH; i+= spaceTile.getWidth()){
			for (int j=-spaceTile.getHeight(); j<HEIGHT; j+= spaceTile.getHeight()){
				spaceTile.draw(i, j+shift);
			}
		}
		shift+=.4;
		if (shift >= spaceTile.getHeight()) shift = 0;
		
		g.setColor(Color.gray);
		g.fill(hBarBG);
		
		g.setColor(Color.red);
		g.fill(hBar);
		
		if (isInvincible || invincibleCool>invincibleCoolCap) ship.draw((float) player.getX(), (float) player.getY());
		else shipRed.draw((float) player.getX(), (float) player.getY());
		//g.fill(player);
		
		g.setColor(Color.red);
		for (Laser l : lasers){
			g.fill(l);
		}
		g.setColor(Color.green);
		for (Laser l : enemyLasers){
			g.fill(l);
		}
		
		for (EnemyShip e : enemyships) {
			enemyShip.draw((float) e.getPosX(), (float) e.getPosY());
			//g.fill(e);
		}
		
		for (EnemyShip2 e : enemyships2) 
			enemyShip2.draw((float) e.getPosX(), (float) e.getPosY()); 
		
		for(Powerup p: powerups){
			if(!p.isInvisible()){
				powerup.draw((float) p.getPosX(), (float)(p.getPosY()));
			}
		}
		
		
		g.setColor(Color.white);
		g.drawString("Score: "+score, 1060, 10);
		if(isInvincible){
			g.drawString("Invincibility", 1060, 50);
		}
		if(rapidFire){
			g.drawString("Rapid Fire", 1060, 100);
		}
		
		if(endGame){
			Font font = new Font("Lucida Console", Font.BOLD, 60);
			TrueTypeFont ttf = new TrueTypeFont(font, true);
			int width1 = ttf.getWidth("GAME OVER");
			ttf.drawString(WIDTH/2-width1/2, HEIGHT/2-90, "GAME OVER", Color.white);
			
			font = new Font("Lucida Console", Font.BOLD, 32);
			ttf = new TrueTypeFont(font, true);
			int width2 = ttf.getWidth("Score: "+finalScore);
			int width3 = ttf.getWidth("Press R to Restart");

			ttf.drawString(WIDTH/2-width2/2, HEIGHT/2, "Score: "+finalScore, Color.white);
			ttf.drawString(WIDTH/2-width3/2, HEIGHT/2+50, "Press R to Restart", Color.white);
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
