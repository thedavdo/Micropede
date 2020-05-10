package com.micropede.main.entity.environment;

import com.arcadeengine.ResourceUtil;
import com.micropede.main.Game;
import com.micropede.main.RoundHandler;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.EntityType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Original Author: Unknown
 *
 * Updated by: David Baker
 */
public class DDT extends Mushroom {

	private static BufferedImage myImage;

	public static void loadRes() {

		myImage = ResourceUtil.loadInternalImage("ddt.png");
	}


	/**
	 * Constructor for objects of class DDT
	 */
	public DDT(RoundHandler roundHandler) {

		this(roundHandler, new Random().nextInt(Game.SCREEN_WIDTH - Game.GRID_SIZE), new Random().nextInt(Game.SCREEN_HEIGHT - Game.PLAYER_ZONE_HEIGHT));
	}

	/**
	 * 'Top Row' Constructor - only puts DDT into top row.
	 *
	 */

	public DDT(RoundHandler roundHandler, int x, int y) {

		super(roundHandler, x, y, false);

		setSize((2 * Game.GRID_SIZE) - 9, Game.GRID_SIZE - 9);

		setHealth(1);

		if (!checkPosition())
			destroy();
	}

	@Override
	public void onCollide(Entity e) {

		if(e.getEntityType().equals(EntityType.PLAYER_WEAPON)) {
			setHealth(0);
			roundHandler.addEntity(new Explosion(roundHandler, (int) xPos, (int) yPos));
		}
	}

	/**
	 * Draw the Entity onto the Graphics page.
	 *
	 * @param g - Game's graphics object.
	 */
	@Override
	public void draw(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		int dx = (int) ((myImage.getWidth() - width) / 2d);
		int dy = (int) ((myImage.getHeight() - height) / 2d);


		g.drawImage(myImage, getXPos() - dx, getYPos() - dy, null);

	}

}