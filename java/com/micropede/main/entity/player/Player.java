package com.micropede.main.entity.player;

import com.arcadeengine.KeyBinding;
import com.arcadeengine.KeyBindingHandler;
import com.arcadeengine.ResourceUtil;
import com.arcadeengine.gui.DebugLine;
import com.micropede.main.Game;
import com.micropede.main.RoundHandler;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.EntityType;
import com.micropede.main.entity.environment.Mushroom;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

/**
 * The player of the game
 *
 * Original Authors: Kevin Zhang, Nikita Patil, Connor Boye and Noah Czereszko
 *
 * Updated by: David Baker
 *
 * @version 2/1/2013
 *          <p/>
 *          2-22-13 Implemented Robot to move Mouse. (Also changed Cursor in other
 *          files.) - Spock
 */
public class Player extends Entity {

	private static Cursor def;
	private static boolean useKeys = true;

	private static boolean detectBounds = true;
	private static boolean ignoreDmg = false;
	private static BufferedImage shipImage;

	public static void loadRes() {

		shipImage = ResourceUtil.loadInternalImage("player.png");
		//shipImage = ResourceUtil.loadInternalImage("com.micropede.gui.res", "microTitle.png");
	}

	private Game game;
	private KeyBindingHandler kbHandler;
	private Robot robot;

	private final int totalVel = 6;//Game.GRID_SIZE - 2;
	private int maxArrows = 1;

	private double xVelCap = 0;
	private double yVelCap = 0;

	private double xVel = 0;
	private double yVel = 0;

	private int moveAccel = 3200;
	private int stopAccel = 3200;

	private int maxHealth = 100;
	private long breakTime;

	private DebugLine playerDebug = new DebugLine("PlayerDebug") {

		@Override
		public String[] getLines() {

			return new String[]{
					addBreak(),
					"Player X: " +  Math.round(xPos * 1000d) / 1000d,
					"Player Y: " +  Math.round(yPos * 1000d) / 1000d,
					"Player xVel: " +  Math.round(xVel * 1000d) / 1000d,
					"Player yVel: " +  Math.round(yVel * 1000d) / 1000d,
					addBreak(),
					"IMG Radian: " + Math.round(radian * 1000d) / 1000d,
					"TAR Radian: " + Math.round(refRad * 1000d) / 1000d,
//					"sin Radian: " + Math.round(sinR * 1000d) / 1000d,
//					"cos Radian: " + Math.round(cosR * 1000d) / 1000d,
					addBreak(),
			};
		}
	};

	private KeyBinding playerBinds = new KeyBinding("PlayerBinds") {

		@Override
		public void onPress(KeyEvent e, String key) {


			if(key.equals("F5")) {
				detectBounds = !detectBounds;
			//	System.out.println(detectBounds);
			}

			if(key.equals("F6")) {
				ignoreDmg = !ignoreDmg;
			}

			if(key.equals("F8")) {
				useKeys = !useKeys;

				if(def == null)
					def = game.getCursor();

				if(!useKeys) {
					BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
					Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
					game.setCursor(blankCursor);
				}
				else
					game.setCursor(def);
				game.updateUI();
			}
		}

		@Override
		public void onRelease(KeyEvent e, String key) {

		}
	};

	/**
	 * Player Constructor Allows to make several players when needed
	 *
	 * @param xInitial: initial x Coord
	 * @param yInitial: initial y Coord
	 */
	public Player(RoundHandler roundHandler, int xInitial, int yInitial)  {

		super(roundHandler, EntityType.PLAYER);

		this.game = roundHandler.getGame();
		game.getGuiHandler().addDebugLine(playerDebug);
		kbHandler = game.getKeyBoardHandler();
		kbHandler.addBindings(playerBinds);
		setSize(20, 20);
		setLocation(xInitial, yInitial);
		setHealth(maxHealth);

		breakTime = 0;

		try {
			robot = new Robot();
		}
		catch (AWTException e) {
		}

		roundHandler.addEntity(this);
	}

	public void updateMoveDir() {

		double xDir = 0, yDir = 0;


		if(useKeys) {
			if (kbHandler.isKeyHeld("Right"))
				xDir += 1;
			if (kbHandler.isKeyHeld("Up"))
				yDir -= 1;
			if (kbHandler.isKeyHeld("Left"))
				xDir -= 1;
			if (kbHandler.isKeyHeld("Down"))
				yDir += 1;
		}
		else {

			Point scrLoc = game.getLocationOnScreen();

			double scrXMid = ((double) scrLoc.x) + ((double) Game.SCREEN_WIDTH) / 2d;
			double scrYMid = ((double) scrLoc.y) + ((double) Game.SCREEN_HEIGHT) / 2d;

			Point ms = game.getMousePosition();

			if(ms != null) {
				xDir = ((double) ms.x) -  ((double) Game.SCREEN_WIDTH) / 2d;
				yDir = ((double) ms.y) - ((double) Game.SCREEN_HEIGHT) / 2d;
			}

			robot.mouseMove((int) scrXMid,(int)  scrYMid);

//			if (Math.abs(yDir) <= 3)
//				yDir = 0;
//			if (Math.abs(xDir) <= 3)
//				xDir = 0;

//			double xMid = (xPos + (width / 2d));
//			double yMid = (yPos + (height / 2d));
//
//			Point ms = game.getMousePosition();
//
//			if(ms != null) {
//				xDir = ((double) ms.x) - xMid;
//				yDir = ((double) ms.y) - yMid;
//			}
//
//			if (Math.abs(yDir) <= 3)
//				yDir = 0;
//			if (Math.abs(xDir) <= 3)
//				xDir = 0;
		}


		double radian = -1000;

		double sR = Math.abs(xDir) / xDir * (Math.asin(yDir / Math.sqrt(xDir * xDir + yDir * yDir)) + Math.PI / 2);
		double cR = Math.abs(yDir) / yDir * (Math.acos(xDir / Math.sqrt(xDir * xDir + yDir * yDir)) - Math.PI / 2);

		if(cR == 0 && Math.abs(yDir) / yDir > 0)
			cR = Math.PI;

		if(xDir != 0)
			radian = sR;
		else if(yDir != 0)
			radian = cR;
		if(radian < 0d)
			radian += Math.PI * 2d;

		if(xDir == yDir && xDir == 0)
			radian = -1000;

		if (radian != -1000) {
			radian -= Math.PI /2;
			xVelCap = ((double) totalVel) * Math.cos(radian);
			yVelCap = ((double) totalVel) * Math.sin(radian);
		}
		else {
			xVelCap = 0;
			yVelCap = 0;
		}
	}

	@Override
	public void act() {
		//Find the center of the game window.

		this.updateMoveDir();

		if (kbHandler.isKeyHeld("Space")) {

			int numArrows = 0;

			for (Entity entity : roundHandler.getEntityList()) {
				if (entity instanceof Arrow)
					numArrows++;
			}

			if(numArrows < maxArrows) {
				roundHandler.addEntity(new Arrow(roundHandler, ((int) xPos) + width / 2, ((int) yPos) - 5/*, radian*/));
			}
		}

		int xCount = moveAccel;
		if(xVelCap == 0)
			xCount = stopAccel;

		for (int loop = 0; loop < xCount; loop++) {
			if (xVel < xVelCap) xVel += .001;
			if (xVel > xVelCap) xVel -= .001;
		}

		int yCount = moveAccel;
		if(yVelCap == 0)
			yCount = stopAccel;

		for (int loop = 0; loop < yCount; loop++) {
			if (yVel < yVelCap) yVel += .001;
			if (yVel > yVelCap) yVel -= .001;
		}

		if (Math.abs(yVel) <= .002)
			yVel = 0;
		if (Math.abs(xVel) <= .002)
			xVel = 0;


		double xv = 0;
		double yv = 0;
		double loopMax = 0;

		if(Math.abs(xVel) > Math.abs(yVel)) {
			xv = (Math.abs(xVel)) / xVel;
			yv = yVel / Math.abs(xVel);
			loopMax = Math.abs(xVel);
		}
		else {
			xv = xVel / Math.abs(yVel);
			yv = (Math.abs(yVel)) / yVel;
			loopMax = Math.abs(yVel);
		}

		for (int i = 0; i < loopMax; i++) {
			xPos += xv;
			yPos += yv;

			if(detectBounds) {
				playerArea();
				checkMush();
			}
		}
	}

	/**
	 * Makes sure that the player is in side the player area.
	 */
	private void playerArea() {

		if ((this.getXPos() + this.getRectangle().width + 1 > Game.SCREEN_WIDTH)) {
			this.setLocation(Game.SCREEN_WIDTH - this.getRectangle().width, this.getYPos());
			xVel = xVel / 2;
		}

		if (this.getXPos() - 1 < 0) {
			this.setLocation(1, this.getYPos());
			xVel = xVel / 2;
		}

//		if ((this.getXPos() > Game.SCREEN_WIDTH)) {
//			this.setLocation(0 - this.getRectangle().width, this.getYPos());
//		}

		if ((this.getYPos() - 1 < Game.SCREEN_HEIGHT - Game.PLAYER_ZONE_HEIGHT)) {
			this.setLocation(this.getXPos(), Game.SCREEN_HEIGHT - Game.PLAYER_ZONE_HEIGHT + 1);
			yVel = yVel / 2;
		}

		if ((this.getYPos() + this.getRectangle().height + 1 > Game.SCREEN_HEIGHT)) {
			this.setLocation(this.getXPos(), Game.SCREEN_HEIGHT - this.getRectangle().height);
			yVel = yVel / 2;
		}
	}

	/**
	 * Poorly checks to see if the player is intersecting with any of the
	 * mushrooms.
	 */
	private void checkMush() {
		for (Mushroom mush : roundHandler.getMushGrid()) {
			if (this.getRectangle().intersects(mush.getRectangle())) {
				// The rectangle created by the overlap of the player and mushroom
				Rectangle intersect = this.getRectangle().intersection(mush.getRectangle());

				// If the intersection height is taller than the width, then fix the x coord of the player.
				if (intersect.height >= intersect.width) {
					if (this.getRectangle().x > mush.getRectangle().x)
						this.setLocation(this.getXPos() + intersect.width, this.getYPos());
					else
						this.setLocation(this.getXPos() - intersect.width, this.getYPos());
					xVel = xVel / 2;
				}

				// If the intersection height is shorter than the width, then fix the y coord of the player.
				if (intersect.height <= intersect.width) {
					if (this.getRectangle().y > mush.getRectangle().y)
						this.setLocation(this.getXPos(), this.getYPos() + intersect.height);
					else
						this.setLocation(this.getXPos(), this.getYPos() - intersect.height);

					yVel = yVel / 2;
				}
			}
		}
	}

	private double radian = 0;
	private double refRad = 0;

	private double cosR = 0;
	private double sinR = 0;

	private void findAccRotation() {

		sinR = Math.abs(xVel) / xVel * (Math.asin(yVel / Math.sqrt(xVel * xVel + yVel * yVel)) + Math.PI / 2);
		cosR = Math.abs(yVel) / yVel * (Math.acos(xVel / Math.sqrt(xVel * xVel + yVel * yVel)) - Math.PI / 2);

		if(cosR == 0 && Math.abs(yVel) / yVel > 0)
			cosR = Math.PI;

		if(xVel != 0)
			refRad = sinR;
		else if(yVel != 0)
			refRad = cosR;

		if(kbHandler.isKeyHeld("Space"))
			refRad = 0;

		if(refRad < 0d)
			refRad += Math.PI * 2d;

		if(Math.abs((radian + Math.PI * 2d) - refRad) < Math.abs(radian - refRad))
			radian += Math.PI * 2d;

		if(Math.abs((radian - Math.PI * 2d) - refRad) < Math.abs(radian - refRad))
			radian -= Math.PI * 2d;

		for(int loop = 0; loop < 300; loop++) {
			if(radian < refRad)
				radian += 0.001d;

			if(radian > refRad)
				radian -= 0.001d;
		}
	}

	private int currImage = 1;
	private int imageLoop = 0;

	/**
	 * Method draw Draws the player on the screen
	 */
	@Override
	public void draw(Graphics g) {

		if(!(xVelCap == yVelCap && xVelCap == 0) || kbHandler.isKeyHeld("Space"))
			findAccRotation();

		Graphics2D g2 = (Graphics2D) g;

		BufferedImage rotated = ResourceUtil.rotateImage(shipImage, radian);

		int x = getXPos() + (width - rotated.getWidth()) / 2;
		int y = getYPos() + (height - rotated.getHeight()) / 2;

		float r = (((float) myHP / (float) maxHealth));

		g2.setColor(Color.GREEN);
		g2.fillRect(20, 100, (int) (100f * r), 25);

		if(isProtected() || (isShieldBreaking() && currImage == 1)) {
			RescaleOp dOp = new RescaleOp(new float[] { 1-r, 0.2f, r, 1f }, new float[4], null);
			BufferedImage shieldImage = new BufferedImage(rotated.getWidth(), rotated.getHeight(), BufferedImage.TYPE_INT_ARGB);
			shieldImage.getGraphics().drawImage(dOp.filter(rotated, null), 0, 0, null);

			rotated = shieldImage;

			g2.setColor(new Color(1f - r, 0.2f, r, 1f));
			g2.fillRect(20, 100, (int) (100f * r), 25);
		}

		if(isShieldBreaking()) {
			if(imageLoop % 8 == 0) {
				if(currImage == 1)
					currImage = 0;
				else
					currImage = 1;
			}
			if(imageLoop == 12 * 8)
				imageLoop = 0;
			imageLoop++;
		}

		g2.drawImage(rotated, x, y, null);
	}

	@Override
	public void onCollide(Entity e) {

	}

	public boolean isProtected() {
		return System.currentTimeMillis() - breakTime > 3000;
	}

	public boolean isShieldBreaking() {
		return System.currentTimeMillis() - breakTime < 1000;
	}

	public void takeDamage(Entity ent, int dmg) {

		if(!ignoreDmg) {
			if(isProtected())
				breakTime = System.currentTimeMillis();
			else if(!isShieldBreaking())
				this.myHP -= dmg;
		}
	}
}
