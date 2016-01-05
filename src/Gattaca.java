
import java.util.ArrayList;
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

import sprites.Laser;

public class Gattaca extends BasicGame {

	public Image ship;
	public Input input;
	public double shipX;
	public double shipY;
	public int vel;
	public float angle;
	public double theta;
	public ArrayList<Laser> lasers = new ArrayList<Laser>();

	public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	public static final double SPEED = 0.1;

	public Gattaca(String gamename) {
		super(gamename);
		shipX = 375;
		shipY = 275;
		ship = null;
		input = null;
		vel = 0;
		angle = 0;
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		ship = new Image("/rsc/Ship.png");
		input = new Input(HEIGHT);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {

		for (Laser l : lasers)
			l.update();

		if (input.isKeyDown(input.KEY_D)) 
			shipX += 3;
		if (input.isKeyDown(input.KEY_A)) 
			shipX -= 3;
		if (input.isKeyDown(input.KEY_W))
			shipY -= 3;
		if (input.isKeyDown(input.KEY_S))
			shipY += 3;

		if (shipX + ship.getWidth() > WIDTH)
			shipX = WIDTH - ship.getWidth();
		else if (shipX < 0)
			shipX = 0;

		if (shipY + ship.getHeight() > HEIGHT)
			shipY = HEIGHT - ship.getHeight();
		else if (shipY < 500)
			shipY = 500;

		if (input.isMousePressed(input.MOUSE_LEFT_BUTTON))
			lasers.add(new Laser(shipX, shipY, angle));

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		ship.draw((float) shipX, (float) shipY);
		g.setColor(Color.red);
		for (Laser l : lasers)
			g.draw(l);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new DemoProgram("Battle of Hoth"));
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
			appgc.setTargetFrameRate(60);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(DemoProgram.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}