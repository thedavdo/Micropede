package com.micropede.main.entity.environment;

import com.arcadeengine.ResourceUtil;
import com.micropede.main.Game;
import com.micropede.main.RoundHandler;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.EntityType;
import com.micropede.main.entity.enemy.Beetle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * Original Author: Brian Pierson
 *
 * Updated by: David Baker
 *
 * V1.2.18 Upgraded all aspects to the newest version of mushroom. Classes affected were the Game, Msuhroom, and HandlerKeyboard classes.
 * Took majority of class to do this.
 * <p/>
 * V1.2.19 No changes to the mushroom class today! Only changes were made to Game class.
 * (Spock: dont forget to change the keyboard handler so only one mushroom is spawned, not two)
 * <p/>
 * V1.2.20 Commented classes, and fleshed out framework of smart growth. Please update the game
 * class with the current growth coding, for both random and smart. There is also a small
 * change to the draw method as well, if that could be updated.
 * <p/>
 * == TO-DO ==
 * :(Minor) Make randomspawns able to put mushrooms in perviously occupied spaces
 * :(Minor) Figure out how to load images on a single image, not as a group of images (ex. Minecraft textures)
 */

public class Mushroom extends Entity {
	/**
	 * inverted: If "true" then the mushroom is in its inverted state.
	 */
	private boolean inverted;

	/**
	 * isFlower: If "true" then the mushroom is in its flower state.
	 */
	private boolean isFlower;
	private boolean dying = false;
	private boolean growing = false;
	private boolean doubled = false;
	//ga stands for growth frame
	private int myFrame;
	private int gf1;
	private int gf2;
	private int gf3;
	private int gf4;

	private static BufferedImage mushImg[] = new BufferedImage[10];

	private static int fullhealth = 4;
	Random randy = new Random();

	public static void loadRes() {

		mushImg[0] = ResourceUtil.loadInternalImage("mush1.png");
		mushImg[1] = ResourceUtil.loadInternalImage("mush2.png");
		mushImg[2] = ResourceUtil.loadInternalImage("mush3.png");
		mushImg[3] = ResourceUtil.loadInternalImage("mush4.png");

		mushImg[4] = ResourceUtil.loadInternalImage("mushInv1.png");
		mushImg[5] = ResourceUtil.loadInternalImage("mushInv2.png");
		mushImg[6] = ResourceUtil.loadInternalImage("mushInv3.png");
		mushImg[7] = ResourceUtil.loadInternalImage("mushInv4.png");

		mushImg[8] = ResourceUtil.loadInternalImage("flower.png");
		mushImg[9] = ResourceUtil.loadInternalImage("flowerInv.png");
	}

	/**
	 * Constructor: Creates a musroom at the place of the callers choosing.
	 * Set grow to true if you want the growing animation when you create the mushroom.
	 */
	public Mushroom(RoundHandler roundHandler, int x, int y, boolean grow) {

		super(roundHandler, EntityType.NEUTRAL);

		int xPlace = ((int) (((double) x / (double) Game.GRID_SIZE)));
		int yPlace = ((int) (((double) y / (double) Game.GRID_SIZE)));

		setLocation((xPlace * Game.GRID_SIZE), (yPlace * Game.GRID_SIZE));
		setSize(Game.GRID_SIZE - 9, Game.GRID_SIZE - 9);
		setHealth(fullhealth);

		if (!checkPosition())
			destroy();

	}

	/**
	 * Creates a mushroom at a random position
	 */
	public Mushroom(RoundHandler roundHandler) {

		this(roundHandler,(int) (Game.GRID_SIZE + (Math.random() * (Game.GRID_WIDTH - 2) * Game.GRID_SIZE)),
				(Game.SCREEN_HEIGHT - Game.PLAYER_ZONE_HEIGHT), false);

	}

	/**
	 * checkPosition: checks to see if the grid square of the mushroom is currently occupied by another mushroom
	 */
	public boolean checkPosition() {

		boolean clear = true;

		for (Entity e : roundHandler.getMushGrid()) {
			if(e.getRectangle().intersects(getRectangle()))
				clear = false;
		}
		return clear;
	}

	/**
	 * isInverted: returns the status of the mushroom
	 */
	public boolean isInverted() {
		return inverted;
	}

	/**
	 * invertShroom: This method will set the value of inverted
	 */
	public void invertShroom() {
		inverted = true;
	}

	/**
	 * makeFlower: This method calls upon the drawShroom method to redraw a mushroom as a flower.  Flowers cannot be
	 * destroyed by arrows or eaten by spiders.  Shooting them does not grant the player points.
	 */
	public void makeFlower() {
		isFlower = true;
	}

	/**
	 * destroy: Destroys the mushroom for no point value.
	 */
	protected void destroy() {

		setLocation(-1000, -1000);
		setHealth(0);
	}

	/**
	 * resetShroom: This method causes a mushroom to flash a white outline, grant points to the player if it is partially
	 * destroyed, and restore it to its normal state.
	 */
	public int refresh() {
		int pt = (fullhealth - getHealth());
		setHealth(fullhealth);
		return pt;
	}

	/**
	 * moveDown: This method calls upon the drawShroom method to redraw a mushroom or flower at a lower position on the
	 * screen.  If the mushroom or flower reaches the bottom of the screen without being destroyed, it deducts a
	 * certain number of points.
	 */
	public int moveDown() {
		setLocation(getXPos(), getYPos() + Game.GRID_SIZE);
		if (getYPos() > Game.SCREEN_HEIGHT) {
			destroy();
			return -5;
		}
		return 0;
	}


	/**
	 * die: Tells the mushroom to begin its death animation
	 */
	public void die(boolean setting) {
		if (setting) {
			dying = true;
		}
		else {
			dying = false;
		}

	}

	/**
	 * getDying: returns the status of the boolean "dying" for a mushroom
	 */

	public boolean getDying() {
		return dying;
	}

	/**
	 * grow: turns on growing on a mushroom. Should only need to be used by game class in growth method.
	 */
	public void grow(boolean setting) {
		if (setting) {
			growing = true;
		}
		else {
			growing = false;
		}
	}

	/**
	 * getGrowing: returns the status of the boolean "growing" for a mushroom
	 */

	public boolean getGrowing() {
		return growing;
	}

	/**
	 * doubled: controls the doubled boolean of a mushroom. Only needed for growth
	 */
	public void doubled(boolean setting) {
		doubled = setting;
//        if(setting)
//        {
//            doubled=true;
//        }
//        else
//        {
//            doubled=false;
//        }
	}

	/**
	 * getDoubled: returns the status of the boolean "doubled" for a mushroom
	 */
	public boolean getDoubled() {
		return doubled;
	}

	/**
	 * setGrowth: This method figures out what frames the mushrooms need to grow at
	 */
	public void setGrowth() {
		gf1 = myFrame + ((getXPos() / Game.GRID_SIZE));
		gf2 = gf1 + Game.GRID_WIDTH;
		gf3 = gf2 + Game.GRID_WIDTH;
		gf4 = gf3 + Game.GRID_WIDTH;
	}

	/**
	 * drawShroom: This method draws a mushroom in a certain state at a given location.  It can draw a mushroom in its normal
	 * state, its inverted state, or as a flower.  It can also draw the normal and inverted mushrooms in their
	 * various states of destruction, and a mushroom's "flashing" state during the resetShroom method.
	 */
	public void draw(Graphics g) {

		BufferedImage img = mushImg[0];

		Graphics2D g2 = (Graphics2D) g;

		if(getHealth() > 0) {

			int index = getHealth();

			if(isFlower) {
				if(inverted)
					img = mushImg[9];
				else
					img = mushImg[8];
			}
			else {
				if(inverted) {
					index += 4;
				}
				img = mushImg[index - 1];
			}
		}


		if (!inverted && !isFlower) {
			g.setColor(Color.RED);
		}
		if (inverted) {
			g.setColor(Color.CYAN);
		}
		if (isFlower) {
			g.setColor(Color.YELLOW);
		}

		int dx = (img.getWidth() - width) / 2;
		int dy = (img.getHeight() - height) / 2;

		g.drawImage(img, getXPos() - dx, getYPos() - dy, null);

		myFrame++;

//		if (!isDead() && !isFlower && !growing && !dying) {
//			int x = getXPos();
//			//int y = (getYPos()+Game.ROW_HEIGHT)-((Game.ROW_HEIGHT/fullhealth)*getHealth()); bottom up now
//			int y = getYPos();
//
//			//g.drawImage(img, x - 2, y - 2, null);
//			//g.fillRect(x, y, w, l);
//
//			//g.fillRect(getXPos(), getYPos(), 20, 20);
//		}
//
//		if (dying && !isFlower && !growing) {
//			int x = getXPos() + ((Game.GRID_SIZE - ((Game.GRID_SIZE / fullhealth) * getHealth())) / 2);
//			int y = getYPos() + ((Game.GRID_SIZE - ((Game.GRID_SIZE / fullhealth) * getHealth())) / 2);
//			int w = (Game.GRID_SIZE / fullhealth) * getHealth();
//			int l = (Game.GRID_SIZE / fullhealth) * getHealth();
//		//	g.drawRect(x, y, l, w);
//			//g.drawImage(img, x - 2, y - 2, null);
//			if (myFrame == gf1 || myFrame == gf2 || myFrame == gf3 || myFrame == gf4) {
//				setHealth(getHealth() - 1);
//			}
//			//framesSinceGrowth++;
//			if (isDead()) {
//				destroy();
//			}
//		}
//		if (growing && !isFlower && !dying) {
//			int x = getXPos() + ((Game.GRID_SIZE - ((Game.GRID_SIZE / fullhealth) * getHealth())) / 2);
//			int y = getYPos() + ((Game.GRID_SIZE - ((Game.GRID_SIZE / fullhealth) * getHealth())) / 2);
//
//
//		//	g.drawImage(img, x - 2, y - 2, null);
//		//	g.fillRect(x, y, l, w);
//
//			if (myFrame == gf1 || myFrame == gf2 || myFrame == gf3 || myFrame == gf4) {
//				setHealth(getHealth() + 1);
//			}
//
//			if (getHealth() == 4) {
//				growing = false;
//			}
//		}

	}

	@Override
	public void onCollide(Entity e) {

		if (e instanceof Explosion) {
			destroy();
		}
		if (e instanceof Beetle) {
			makeFlower();
		}

		//         if(e instanceof Earwig)
		//         {
		//                 invertShroom();
		//                 return 0;
		//         }
	}

	@Override
	public void act() {
		if (getYPos() > Game.SCREEN_HEIGHT)
			setHealth(0); //for the unusual case of a DDT going off a screen.
	}

	public void takeDamage(Entity ent, int dmg) {

		if(!isFlower) {
			this.myHP -= dmg;
		}
	}
}