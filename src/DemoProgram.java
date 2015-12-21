import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class DemoProgram extends BasicGame {

	public Image ship;
	public double shipX;
	public double shipY;
	public Input input;
	public int velX;
	public int velY;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final double SPEED = 0.25;

	public DemoProgram(String gamename) {
		super(gamename);
		shipX = 375;
		shipY = 275;
		ship = null;
		input = null;
		velX = 0;
		velY = 0;
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		ship = new Image("/rsc/spaceship.png");
		input = new Input(HEIGHT);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {

		if (input.isKeyDown(input.KEY_D))
			velX++;
		if (input.isKeyDown(input.KEY_A))
			velX--;
		if (input.isKeyDown(input.KEY_W))
			velY--;
		if (input.isKeyDown(input.KEY_S))
			velY++;
		
		shipX += velX * SPEED;
		shipY += velY * SPEED;

		if (shipX > WIDTH)
			shipX = -ship.getWidth();
		else if (shipX < -ship.getWidth())
			shipX = WIDTH;

		if (shipY > HEIGHT)
			shipY = -ship.getHeight();
		else if (shipY < -ship.getHeight())
			shipY = HEIGHT;

		float angle = (float) (Math.atan2(input.getMouseY() - shipY - ship.getHeight() / 2,
				input.getMouseX() - shipX - ship.getWidth() / 2) * 180 / Math.PI + 45);
		ship.setRotation(angle);

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		ship.draw((int) shipX, (int) shipY);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new DemoProgram("Simple Slick Game"));
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(DemoProgram.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}