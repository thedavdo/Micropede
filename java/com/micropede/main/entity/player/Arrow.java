package com.micropede.main.entity.player;

import com.micropede.main.Game;
import com.micropede.main.RoundHandler;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.EntityType;

import java.awt.*;

/**
 * Arrow fired by the player to kill opponents by the player
 *
 * 2/22/13- Updated to use the SPEED constant. Commented more stuff. The arrow arrows now starts in the
 * middle of the player, not the left or right side. Cleaned up the onCollide me
 *
 * Original Authors: Nick Green and Jonathon Weaver
 *
 * Updated by: David Baker
 *
 * @version 2/22/2013
 */
public class Arrow extends Entity {


	/**
	 * Arrow Constructor
	 * Allows to make may arrows if needed
	 *
	 * @param x the x variable for the arrow to start from
	 * @param y the y variable for the arrow to start from
	 */
	public Arrow(RoundHandler roundHandler, int x, int y) {

		this(roundHandler, x, y, Math.PI * 3 / 2);
	}

	public Arrow(RoundHandler roundHandler, int x, int y, double radian) {

		super(roundHandler, EntityType.PLAYER_WEAPON);

		this.width = 12;
		this.height = 12;
		setLocation(x -( width / 2), y);//middle of player
		setVelocity((int) (20d * Math.cos(radian)), (int) (20d * Math.sin(radian)));//speed
		myHP = 1;
	}

	/**
	 * Method act
	 * The arrow moves straight up when shot
	 * ((This method is part of the EntityInterface))
	 */
	public void act() {
		//arrow flies straight up, and resets itself when it hits an object or flies off screen.

		updateLoc();//move one rectangle up according to velocity

		if (getYPos() < 1 || getYPos() > Game.SCREEN_HEIGHT)//if it's off the screen
		{
			myHP = 0;
		}

		if (getXPos() < 1 || getXPos() > Game.SCREEN_WIDTH)//if it's off the screen
		{
			myHP = 0;
		}
	}

	/**
	 * Draw the arrow onto the Graphics page.
	 * Three lines make up the arrow
	 *
	 * @param g - Game's graphics object.
	 */
	public void draw(Graphics g) {

		g.setColor(Color.WHITE);//white

		//left side
		g.drawLine((int) xPos,(int) (yPos + 6), (int) (xPos + (((double) width)/ 2d)), (int) yPos);
		//base/middle line
		g.drawLine((int) (xPos + (((double) width)/ 2d)), (int) yPos, (int) (xPos + (((double) width)/ 2d)), (int) yPos + height);
		//right side
		g.drawLine((int) xPos + width,(int) (yPos + 6), (int) (xPos + (((double) width)/ 2d)), (int) yPos);
	}



	/**
	 * Gets rid of arrow if runs into an entity by setting it off the screen.
	 * Then the act method gets rid of the arrow.
	 *
	 * @param e the entity to interact with
	 * @return 0
	 */
	public void onCollide(Entity e) {

		if(e.getEntityType() != EntityType.PLAYER) {
			e.takeDamage(this, 1);
			this.setHealth(0);
		}

	}

	public void takeDamage(Entity ent, int dmg) {

	}
}