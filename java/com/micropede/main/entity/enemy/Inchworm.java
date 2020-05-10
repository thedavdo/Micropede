package com.micropede.main.entity.enemy;

import com.arcadeengine.ResourceUtil;
import com.micropede.main.Game;
import com.micropede.main.RoundHandler;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.EntityType;
import com.micropede.main.entity.environment.Explosion;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Use this as a model for the other Opponents.
 * Spock 1-30-13
 *
 * The act method should now cause the Inchworm to move across the screen and then disappear.
 * Jacob Heiland Comment: Inchworm now can be placed anywhere on the screen and will move back and forth
 * within a set range.
 *
 * @author Jacob Heiland
 * At this point, Inchworm still needs an actual image, it needs animation, and it needs to be able to
 * spawn at random locations.
 *
 *
 * Original Author: Jacob Heiland
 *
 * Updated by: David Baker
 */
public class Inchworm extends Entity {


	private static BufferedImage myImage;

	public static void loadRes() {

		myImage = ResourceUtil.loadInternalImage("player.png");
	}

	private final int velocity = 5;

	private Random randy = new Random();

	public Inchworm(RoundHandler roundHandler) {

		super(roundHandler, EntityType.OPPONENT);
		//Initialize parameters
		int row = randy.nextInt(6) + 15;
		setSize(Game.GRID_SIZE * 2, Game.GRID_SIZE);
		if (randy.nextBoolean()) {   //move right
			setLocation(Game.GRID_SIZE * -1, Game.GRID_SIZE * row);
			setVelocity(velocity, 0);
		}
		else {   //move left
			setLocation(Game.SCREEN_WIDTH * Game.GRID_SIZE, Game.GRID_SIZE * row);
			setVelocity(-velocity, 0);
		}

		setSize(Game.GRID_SIZE * 2, Game.GRID_SIZE);
		setHealth(1);
	}

	/**
	 * Tells the Entity to move and interact with the Mushroom grid.
	 */
	@Override
	public void act() {
		updateLoc();
		if (getXPos() > Game.SCREEN_WIDTH + 2 * Game.GRID_SIZE || getXPos() < -2 * Game.GRID_SIZE) {
			setHealth(0);
		}
	}

	/**
	 * Draw the Entity onto the Graphics page.
	 *
	 * @param g - Game's graphics object.
	 */
	@Override
	public void draw(Graphics g) {
		//Temporary - just fills a blue rectangle.
		g.setColor(Color.BLUE);
		g.fillRect((int) xPos, (int) yPos, width, height);

		g.drawImage(myImage, (int) xPos, (int) yPos, null);
	}

	/**
	 * The method is called by the Game when it is determined that another Entity
	 * overlaps this Entity.  This Entity 'deals with' the interaction.
	 *
	 * @param e is intersecting entity
	 * @return the points to be added to the game score as a result of this interaction.
	 * <p/>
	 * The return value can also be used a message to the game, for example, if an
	 * inchworm is hit by an arrow, it will return 713, which is a code to have the
	 * Game switch into 'slowMode'.
	 */
	@Override
	public void onCollide(Entity e) {
		if (e instanceof Explosion) {
			setHealth(0);// worm dies
		}
	}

	public void takeDamage(Entity ent, int dmg) {
		this.myHP -= dmg;
	}
}
