package com.micropede.main.entity.enemy;

import com.arcadeengine.ResourceUtil;
import com.micropede.main.Game;
import com.micropede.main.RoundHandler;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.EntityType;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Random;

/**
 * Everything is finished except the following:
 * Image graphics
 * Act method to send Earwig across the screen from a random side.
 * Tell Mushroom class to make a deal with it method that turns mushrooms run over
 * by an Earwig into flowers or inverted.
 * Make a random generator to make sure which side the Earwig originates from is random.
 * Engel 2-22-13
 *
 * Original Author: Engel
 *
 * Updated by: David Baker
 */
public class Earwig extends Entity {
	private static final int VELOCITY = 5;
	private static final int SCORE_VALUE = 500;
	private static Random randy = new Random();
	private static Image myImage;
	private static ImageObserver observer = null;

	//CONSTRUCTORS
	public Earwig(RoundHandler roundHandler) {

		super(roundHandler, EntityType.OPPONENT);
		//Initialize parameters
		int xStart;
		if (randy.nextBoolean()) {
			xStart = -2;
			setVelocity(VELOCITY, 0);
		}
		else {
			xStart = Game.GRID_WIDTH + 2;
			setVelocity(-VELOCITY, 0);
		}
		int yPlace = ((int) (((randy.nextInt(10) + 10) / Game.GRID_SIZE)));
		setLocation((xStart * Game.GRID_SIZE), (yPlace * Game.GRID_SIZE));
		setSize(Game.GRID_SIZE * 2, Game.GRID_SIZE);
		setHealth(1);
	}

	public static void loadResources() {

		myImage = ResourceUtil.loadInternalImage("Earwig.png");
	}

	/**
	 * Tells the Entity to move and interact with the Mushroom grid.
	 */
	@Override
	public void act() {
		updateLoc();
		if (getXPos() > Game.SCREEN_WIDTH + 2 * Game.GRID_SIZE
				|| getXPos() < -2 * Game.GRID_SIZE) {
			setHealth(0); //off screen
		}
	}

	/**
	 * Draw the Earwig onto the Graphics page.
	 *
	 * @param g - Game's graphics object.
	 */
	@Override
	public void draw(Graphics g) {
		//Temporary - just fills a orange rectangle.
		g.setColor(Color.ORANGE);
		g.fillRect(getRectangle().x, getRectangle().y, getRectangle().width, getRectangle().height);

		g.drawImage(myImage, getRectangle().x, getRectangle().y, observer);
	}

	/**
	 * This method is called by the game when an Earwig overlaps with something else.
	 * The only objects in the game an Earwig has to call a method for upon overlap are
	 * arrows and DDTs. The Mushroom's onCollide method should contain a command for the
	 * Mushroom overlapped by an Earwig to turn into a flower.
	 */
	@Override
	public void onCollide(Entity e) {


	}

	public void takeDamage(Entity ent, int dmg) {
		this.myHP -= dmg;
	}
}