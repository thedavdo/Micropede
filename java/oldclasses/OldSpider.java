package oldclasses;

//2/22/13 cleaned up the Spider class
//2/21/13 changed size, changed act method so spider can travel vertically
//2/19/13 edited constructor
//2/18/13 edited act class
//2/13/13 edited act class
//2/8/13 added mushroom to deal with it method, edited act method
//2/6/13 edited act method
//2/5/13 wrote part of onCollide method, editied a little of the act class
//2/4/13 made spider move
//imported color classl, initialized parameters to spider constructor, drew a red box to represent spider, looked at PongBall class to make the act class, 

import com.arcadeengine.ResourceUtil;
import com.micropede.main.Game;
import com.micropede.main.RoundHandler;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.EntityType;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Write a description of class Spider here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class OldSpider extends Entity {
	private static int speed = 2; //The generic speed of a Spider
	public double originalVel;//stores the Spider's original x velocity
	Random random = new Random();

	private static BufferedImage myImgs[];

	public static void loadRes() {

		myImgs = new BufferedImage[2];

		myImgs[0] = ResourceUtil.loadInternalImage("spider.png");
		myImgs[1] = ResourceUtil.flipImage(myImgs[0], true, false);
	}

	private int currImage;
	private int imageLoop = 0;

	/**
	 * Default constructor (no parameters)
	 * This constructor chooses a logical place to start a Spider on its own.
	 */
	public OldSpider(RoundHandler roundHandler) {

		super(roundHandler, EntityType.OPPONENT);

		currImage = random.nextInt(2);
		setSize(myImgs[0].getWidth(), myImgs[0].getHeight());//set location, velocity, size and health.

		int x = 0;
		int y = (Game.SCREEN_HEIGHT - Game.PLAYER_ZONE_HEIGHT) + random.nextInt(Game.PLAYER_ZONE_HEIGHT - getRectangle().height - 1);

		//choose a side of the board (randomly) 50% chance for either side
		if (random.nextInt(2) == 0) {
			x = Game.SCREEN_WIDTH - 1;
		}

		setLocation(x, y);
		setVelocity(random.nextInt(2) + 1, 2);//choose a velocity (semi-randomly)

		originalVel = getXVel();//the original x velocity is stored

		setHealth(roundHandler.getRound() + 1);
	}

	/**
	 * Tells the Opponent to move
	 */
	@Override
	public void act() {
		updateLoc();

	}

	/**
	 * Draw the Opponents sprite
	 *
	 * @param g - Games graphics obj
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(myImgs[currImage], getXPos(), getYPos(), null);

		if(imageLoop % 10 == 0) {
			if(currImage == 1)
				currImage = 0;
			else
				currImage = 1;
		}

		if(imageLoop == 100)
			imageLoop = 0;

		imageLoop++;
	}

	@Override
	public void onCollide(Entity e) {

		if(e.getEntityType() == EntityType.PLAYER)
			e.takeDamage(this, 1);
	}

	public void takeDamage(Entity ent, int dmg) {

		myHP -= dmg;

		this.yPos -= height / 2d;

		if(isDead())
			roundHandler.addScore(600);
	}
}