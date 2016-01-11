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
import sprites.Laser;
import sprites.Ship;

public class Gattaca extends BasicGame {

	public Image ship;
	public Image enemy;

	public Input input;

	public Ship player;
	public ArrayList<Laser> lasers = new ArrayList<Laser>();
	public ArrayList<EnemyShip> enemyships = new ArrayList<EnemyShip>();

	public int health;
	public int frameCount;
	public static final double ENEMY_SPEED = 3;
	public static final double PLAYER_SPEED = 3;

	public static final int WIDTH = 1200;
	public static final int HEIGHT = 700;
	public static final double SPEED = 0.1;
	
	private int laserCoolCap = 30;
	private int laserCool = 0;

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
		input = new Input(HEIGHT);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		frameCount++;
		//generating Enemy Ships
		if (frameCount%120==0){ //spawn every 60 frames (temp)
			enemyships.add(
					new EnemyShip((int)(Math.random()*WIDTH), 50, ENEMY_SPEED, player.getPosX(),player.getPosY())
					);
		}

		//update shit
		player.update();

		for(EnemyShip e : enemyships) e.update();
		for(Laser l : lasers) l.update();

		//Lasers
		if (input.isKeyDown(Input.KEY_SPACE) && laserCool > laserCoolCap){
			lasers.add(new Laser(player.getPosX()+ship.getWidth()/2, player.getPosY(), -7)); //launches vertically
			System.out.println();
			laserCool = 0;
		}
		laserCool++;

		Iterator<Laser> lasers_it = lasers.iterator();


		//collision detection, removing extra sprites
		while(lasers_it.hasNext()){
			Laser laser = lasers_it.next();
			Iterator<EnemyShip> enemyships_it = enemyships.iterator();
			while(enemyships_it.hasNext()){
				EnemyShip e = enemyships_it.next();
				if(laser.intersects(e)){
					lasers_it.remove();
					enemyships_it.remove();;
					break; //because laser has been destroyed
				}
				if(e.getPosX() > WIDTH || e.getPosY() > HEIGHT) enemyships_it.remove();
			}
			if(laser.getPosX() > WIDTH || laser.getPosY() > HEIGHT) lasers_it.remove();
			if(laser.intersects(player)){
				health--;
				lasers_it.remove();;
			}
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
		//what needs to happen: the ship is scaled to match its dimensions in the Ship sprite class
		ship.draw((float) player.getPosX(), (float) player.getPosY());


		g.setColor(Color.red);
		for (Laser l : lasers)
			g.draw(l);


		for (EnemyShip e : enemyships)
			enemy.draw((float) e.getPosX(), (float) e.getPosY()); //change this to enemy_ship.draw
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Gattaca("Battle of Hoth"));
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
			appgc.setTargetFrameRate(60);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Gattaca.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}