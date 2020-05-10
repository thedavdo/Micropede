/**
 * Changed the range that the beetle will move up for. Changed the deal with it
 * method. Added more comment to actual code.
 */

package com.micropede.main.entity.enemy;

import com.arcadeengine.ResourceUtil;
import com.micropede.main.Game;
import com.micropede.main.RoundHandler;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.EntityType;
import com.micropede.main.entity.player.Player;


import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The beetle constructs itself on the right side of the screen
 * then moves down the side to the bottom. Then it moves across
 * the bottom and comes up under the play then continue of the screen when it
 * reaches the top of the player zone.
 *
 * Original Author: Clayton, Nick
 *
 * Updated by: David Baker
 */

public class Beetle extends Entity {
	private static int speed = 2; //The generic speed of a Beetle

	private static int scoreValue;
	private static BufferedImage myImgs[];

	public static void loadRes() {

		myImgs = new BufferedImage[2];

		myImgs[0] = ResourceUtil.loadInternalImage("beetle1.png");
		myImgs[1] = ResourceUtil.loadInternalImage("beetle2.png");

	}

	/**
	 * Constructor for objects of class Bee
	 */
	public Beetle(RoundHandler roundHandler) {

		super(roundHandler, EntityType.OPPONENT);
		setLocation(Game.SCREEN_WIDTH - Game.GRID_SIZE, Game.SCREEN_HEIGHT - Game.PLAYER_ZONE_HEIGHT - Game.GRID_SIZE);
		setVelocity(-speed, 0);
		setSize(Game.GRID_SIZE, Game.GRID_SIZE);
		setHealth(1);
		scoreValue = 500;

	}

	//===== THE METHODS BELOW ARE PART OF THE Entity INTERFACE ====================

	/**
	 * Tells the Entity to move and interact with the Mushroom grid.
	 */
	@Override
	public void act() {

		Player ply = roundHandler.getPlayer();

		//Makes the bettle move down into the player zone when it enters
		//the screen
		if (getXPos() == Game.SCREEN_WIDTH - Game.GRID_SIZE) {
			setVelocity(0, speed);
		}
		//Makes the bettle move left when it is at the bottom of the player
		//zone
		if (getYPos() == Game.SCREEN_HEIGHT - Game.GRID_SIZE) {
			setVelocity(-speed, 0);
		}
		//Makes the bettle move up under the player when it is under it

		if (getXPos() >= ply.getXPos() - 10 && getXPos() <= ply.getXPos() + 10 && getYPos() > ply.getXPos() && getXPos() < Game.SCREEN_WIDTH - Game.GRID_SIZE) {
			setVelocity(0, -speed);
		}
		//Makes the bettle move when it is above the player zone

		if (getYPos() == Game.SCREEN_HEIGHT - Game.PLAYER_ZONE_HEIGHT * 2 - Game.GRID_SIZE) {
			setVelocity(-speed, 0);
		}
		//If the beetles reaches the end of the screen then it move along the
		// edge upwards.
		if (getXPos() == 0 && getYPos() != Game.SCREEN_HEIGHT - Game.PLAYER_ZONE_HEIGHT * 2 - Game.GRID_SIZE) {
			setVelocity(0, -speed);
		}
		//gets rid of the beetle when it if the screen
		if (getXPos() > Game.SCREEN_WIDTH || getXPos() < -Game.GRID_SIZE)
			setHealth(0);

		updateLoc();
	}

	private int currImage;
	private int imageLoop = 0;


	/**
	 * Draw the Entity onto the Graphics page.
	 *
	 * @param g - Games graphics object.
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
		this.myHP -= dmg;
	}
}
