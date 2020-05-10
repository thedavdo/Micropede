package com.micropede.main.entity.enemy;

import com.arcadeengine.ResourceUtil;
import com.micropede.main.Game;
import com.micropede.main.RoundHandler;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.EntityType;
import com.micropede.main.entity.environment.Mushroom;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Original Author: Unknown
 *
 * Updated by: David Baker
 */
public class Segment extends Entity {

	private static BufferedImage segImg[][] = new BufferedImage[2][2];
	private static BufferedImage headImg[][] = new BufferedImage[4][2];

	public static void loadRes() {

		BufferedImage head = ResourceUtil.loadInternalImage("segHead.png");

		headImg[0][0] = head;
		headImg[0][1] = ResourceUtil.flipImage(headImg[0][0], false, true);

		headImg[1][0] = ResourceUtil.rotateImage(headImg[0][0], Math.PI * (1d / 2d));
		headImg[1][1] = ResourceUtil.flipImage(headImg[1][0], true, false);

		headImg[2][0] = ResourceUtil.rotateImage(headImg[0][0], Math.PI);
		headImg[2][1] = ResourceUtil.flipImage(headImg[2][0], false, true);

		headImg[3][0] = ResourceUtil.rotateImage(headImg[0][0], Math.PI * 3d / 2d);
		headImg[3][1] = ResourceUtil.flipImage(headImg[3][0], true, false);

		BufferedImage seg = ResourceUtil.loadInternalImage("segment.png");

		segImg[0][0] = seg;
		segImg[0][1] = ResourceUtil.flipImage(segImg[0][0], false, true);

		segImg[1][0] = ResourceUtil.rotateImage(segImg[0][0], Math.PI / 2);
		segImg[1][1] = ResourceUtil.flipImage(segImg[1][0], true, false);

	}

	private Random rand = new Random();

	private Segment parent = null;

	private final int vel = 4;
	private double xVel = 0;
	private double yVel = 0;
	private final int followPause = (int) Math.round(23d / (double) vel);

	private int dir = 2;

	private boolean inPlayerArea = false;

	private int minHeight = 0;

	private boolean isHead = false;

	private ArrayList<Movement> moves;

	private class Movement {

		private int x;
		private int y;
		private int dir;

		public Movement(int x, int y, int dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getDir() {
			return dir;
		}
	}

	public Segment(RoundHandler roundHandler, int x, int y) {

		super(roundHandler, EntityType.OPPONENT);

		this.moves = new ArrayList<>();

		this.setLocation(x, y);
		this.setSize(segImg[0][0].getWidth(), segImg[0][0].getHeight());
		isHead = true;
	}

	public Segment(RoundHandler roundHandler, Segment parent) {

		super(roundHandler, EntityType.OPPONENT);

		this.moves = new ArrayList<>();

		this.parent = parent;
		this.setSize(segImg[0][0].getWidth(), segImg[0][0].getHeight());
		this.setLocation(parent.getXPos() + segImg[0][0].getWidth(), parent.getYPos());
	}

	public ArrayList<Movement> getMoves() {
		return moves;
	}

	public void consumeMove() {
		moves.remove(0);
	}

	private boolean canMove(int d) {

		ArrayList<Mushroom> grid = roundHandler.getMushGrid();

		boolean canMove = true;

		double xd = Math.cos(Math.PI * (((double) d) / 2d));
		double yd = Math.sin(Math.PI * (((double) d) / 2d));

		double x = getXPos() + xd;
		double y = getYPos() + yd;

		Rectangle r = new Rectangle((int) x,(int) y, getRectangle().width, getRectangle().height);

		for (Mushroom mush : grid) {
			Rectangle inter = r.intersection(mush.getRectangle());
			if(!inter.isEmpty()) {
				canMove = false;
			}
		}

		if(x < 0 && d == 2)
			canMove = false;
		if(x + r.getWidth() > Game.SCREEN_WIDTH && d == 0)
			canMove = false;

		if(y + r.getHeight() > Game.SCREEN_HEIGHT /*&& d == 1*/)
			canMove = false;
		if(y < minHeight && d == 3)
			canMove = false;

		return canMove;
	}

	@Override
	public void act() {


		if(getYPos() > Game.SCREEN_HEIGHT - Game.PLAYER_ZONE_HEIGHT) {
			minHeight = Game.SCREEN_HEIGHT - Game.PLAYER_ZONE_HEIGHT;
			inPlayerArea = true;
		}

		if(parent != null) {
			if (parent.isDead()) {

				parent = null;
				this.isHead = true;

				int newDir = rand.nextInt(4);

				while (!canMove(newDir) && newDir != dir)
					newDir = rand.nextInt(4);

				dir = newDir;
			}
		}

		if(isHead) {
			for (int i = 0; i < vel; i++) {
				move();
			}
			moves.add(new Movement((int) xPos,(int)  yPos, dir));
		}
		else {
			if(parent != null) {
				if(parent.getMoves().size() > followPause) {
					Movement move = parent.getMoves().get(0);

					this.xPos = move.getX();
					this.yPos = move.getY();
					this.dir = move.getDir();

					parent.consumeMove();

					this.moves.add(move);
				}
			}
		}
	}

	private void move() {

		int newDir = dir;

		if(((int) yPos) % Game.GRID_SIZE == 0) {
			if((newDir == 1 || newDir == 3)) {
				int r = rand.nextInt(2);
				if(r == 1) {
					if(canMove( 0))
						newDir = 0;
					else if(canMove(2))
						newDir = 2;
				}
				else if(canMove(2))
					newDir = 2;
				else if(canMove(0))
					newDir = 0;
//				else if(canMove(1))
//					newDir = 1;
//				else if(canMove(3))
//					newDir = 3;
			}
		}

		if(!canMove(newDir)) {

			if ((newDir == 0 || newDir == 2)) {
				if(canMove(1))
					newDir = 1;
				else
					newDir = 3;
			}
			else if(canMove(1))
				newDir = 1;
			else if(canMove(2))
				newDir = 2;
			else if(canMove(0))
				newDir = 0;
			else if(canMove(3))
				newDir = 3;
		}

		dir = newDir;

		xVel = Math.cos(Math.PI * (((double) dir) / 2d));
		yVel = Math.sin(Math.PI * (((double) dir) / 2d));

		xPos += xVel;
		yPos += yVel;
	}

	private int currImage = 0;
	private int imageLoop = 0;

	@Override
	public void draw(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		if(isHead)
			g2.drawImage(headImg[dir][currImage], getXPos(), getYPos(), null);
		else
			g2.drawImage(segImg[((dir + 1) % 2 == 0) ? 1 : 0][currImage], getXPos(), getYPos(), null);

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

		if(isDead() && ent.getEntityType() == EntityType.PLAYER_WEAPON) {
			int score = 200;
			if(isHead)
				score += 100;
			roundHandler.addScore(score);

			roundHandler.addEntity(new Mushroom(roundHandler, (int) xPos, (int) yPos, false));
		}
	}
}
