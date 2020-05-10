package com.micropede.main.entity.enemy;

import com.arcadeengine.ResourceUtil;
import com.micropede.main.Game;
import com.micropede.main.RoundHandler;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.EntityType;
import com.micropede.main.entity.environment.Mushroom;

import java.awt.*;
import java.util.Random;

/**
 * Original Author: David Baker
 */
public class Bee extends Entity {

	private static Image myImage;

	public static void loadRes() {
		myImage = ResourceUtil.loadInternalImage("Bee.png");
	}


	private double VELOCITY = 4;
	private final int mushChance = 200;

	private Random rand = new Random();

	private boolean isAwake = false;

	private int mushCount = 0;

	public Bee(RoundHandler roundHandler) {

		this(roundHandler, 400 + (int) (Math.random() * 10000d));
	}

	public Bee(RoundHandler roundHandler, final int delay) {

		super(roundHandler, EntityType.OPPONENT);

		mushCount = rand.nextInt(6) + 3;

		int x = (rand.nextInt(Game.GRID_WIDTH - 1)) * Game.GRID_SIZE;

		width = Game.GRID_SIZE;
		height = Game.GRID_SIZE;

		setLocation(x, -Game.GRID_SIZE);
		setVelocity(0, 0);
		myHP = 1;

		Thread th = new Thread() {
			public void run() {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				setVelocity(0,(int) VELOCITY);
				isAwake = true;
			}
		};

		th.start();
	}

	@Override
	public void act() {

		if(isAwake) {

			if(getYPos() > Game.SCREEN_HEIGHT) {
				setHealth(0);
			}

			if(mushCount > 0 && getYPos() > 50) {
				if(rand.nextInt(1000) < mushChance) {
					Mushroom mush = new Mushroom(roundHandler, this.getXPos(), this.getYPos(), false);
					if (!mush.isDead()) {
						roundHandler.addEntity(mush);
						mushCount--;
					}
				}
			}
			else {
				VELOCITY *= 1.07d;
				yVel = VELOCITY;
			}

			updateLoc();
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(myImage, getXPos(), getYPos(), null);
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
