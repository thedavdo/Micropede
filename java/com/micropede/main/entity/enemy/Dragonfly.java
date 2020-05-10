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
 * A special Opponent that only spawns during Bonus Rounds.
 * ===================
 * CHANGELOG
 * ===================
 * v1.0.1: Cleaned up some commented code.
 * v1.0: Fixed the diagonal movement. Not yet refined.
 * alpha 0.1: Created the class, copied similar code from the Bee class.
 *
 * @version v1.0.1 2/20/2013
 *
 *
 * Original Authors: Jonah Austin, Gary Drake
 *
 * Updated by: David Baker
 */
public class Dragonfly extends Entity {
	/**
	 * This class needs constants defined for velocities and scoring.
	 */
	private static final int SWAY_DISTANCE = Game.GRID_SIZE * 4;
	private static final int Y_VELOCITY = 2;

	private static Image myImage;
	private static ImageObserver observer = null;

	private static int scoreValue;

	private int centralXlocation;
	private int velmultiplier = 10;

	private Random rob = new Random();

//    public Dragonfly(int x, int y)
//    {
//        //initialize base parameters.
//        int xPlace =  ((int)((x/Game.COLUMN_WIDTH)));
//        int yPlace = ((int)((y/Game.ROW_HEIGHT)));
//        
//        setLocation((xPlace*Game.COLUMN_WIDTH),(yPlace*Game.ROW_HEIGHT));
//        setVelocity(3,2);
//        setSize(Game.COLUMN_WIDTH,Game.ROW_HEIGHT);
//        setHealth(1);
//        scoreValue = 200;
//    }

	public Dragonfly(RoundHandler roundHandler) {
		super(roundHandler, EntityType.OPPONENT);

		centralXlocation = (rob.nextInt(Game.GRID_WIDTH - 1)) * Game.GRID_SIZE;
		setLocation(centralXlocation, 0); //top of screen, random horiz. pos.
		setVelocity(0, Y_VELOCITY);
		setSize(Game.GRID_SIZE, Game.GRID_SIZE);
		setHealth(1);
		scoreValue = 200;
	}

	public static void loadResources() {

		myImage = ResourceUtil.loadInternalImage("Dragonfly.png");
	}


	//===== THE METHODS BELOW ARE PART OF THE Entity INTERFACE ====================

	@Override
	public void act() {
		int newXPos = (int) (centralXlocation + SWAY_DISTANCE * Math.sin(getYPos() / 10.0));
		updateLoc();
		setLocation(newXPos, getYPos());
		if (getYPos() > Game.SCREEN_HEIGHT) //if it's off the screen..
		{
			setHealth(0); //kill itself.
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(getRectangle().x, getRectangle().y, getRectangle().width, getRectangle().height);

		g.drawImage(myImage, getRectangle().x, getRectangle().y, observer);
	}

	@Override
	public void onCollide(Entity e) {

	}

	public void takeDamage(Entity ent, int dmg) {
		this.myHP -= dmg;
	}
}
