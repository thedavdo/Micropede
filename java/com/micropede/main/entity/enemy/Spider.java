package com.micropede.main.entity.enemy;

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
public class Spider extends Entity {

	private static BufferedImage myImgs[];

	public static void loadRes() {

		myImgs = new BufferedImage[2];

		myImgs[0] = ResourceUtil.loadInternalImage("spider.png");
		myImgs[1] = ResourceUtil.flipImage(myImgs[0], true, false);
	}

	private Random rand = new Random();

	private int currImage;
	private int imageLoop = 0;

	private boolean negX;

	private long nextTime;

	public Spider(RoundHandler roundHandler) {
		super(roundHandler, EntityType.OPPONENT);

		currImage = rand.nextInt(2);

		setSize(myImgs[0].getWidth(), myImgs[0].getHeight());
		setHealth(1);

		xPos = 0 - width;
		yPos = Game.SCREEN_HEIGHT - Game.PLAYER_ZONE_HEIGHT + rand.nextInt(Game.PLAYER_ZONE_HEIGHT - height);

		negX = rand.nextBoolean();

		if(negX)
			xPos = Game.SCREEN_WIDTH;

		newVel();
	}

	public void newVel() {
		xVel = rand.nextInt(3);
		yVel = rand.nextInt(5) - 2;

		while(yVel == 0)
			yVel = (rand.nextInt(5) - 2);

		if(negX)
			xVel *= -1;

		nextTime = System.currentTimeMillis() + rand.nextInt(400) + 400;
	}

	@Override
	public void draw(Graphics g) {

		g.drawImage(myImgs[currImage], getXPos(), getYPos(), null);

		double tVel = (Math.sqrt(xVel * xVel + yVel * yVel));


		if(imageLoop % (int) (5 * Math.sqrt(8) / tVel) == 0) {
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
	public void act() {

		updateLoc();


		if(negX && (xPos + width) < 0 || (!negX && xPos > Game.SCREEN_WIDTH))
			setHealth(0);

		if(System.currentTimeMillis() > nextTime)
			newVel();

		if((this.yPos + height + 1 > Game.SCREEN_HEIGHT) || this.yPos <= 0) {
			this.yPos = Game.SCREEN_HEIGHT - height;
			newVel();
		}
	}

	@Override
	public void onCollide(Entity e) {

		if(e.getEntityType() == EntityType.PLAYER)
			e.takeDamage(this, 1);
	}

	@Override
	public void takeDamage(Entity ent, int dmg) {

		myHP -= dmg;

		if(isDead())
			roundHandler.addScore(600);
	}
}
