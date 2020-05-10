package com.micropede.main.entity.environment;

import com.micropede.main.Game;
import com.micropede.main.RoundHandler;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.EntityType;

import java.awt.*;
import java.util.Random;

/**
 * Original Author: Unknown
 *
 * Updated by: David Baker
 *
 * I only changed the draw method to be a bit more awesome, the act method
 * is also changed to cause the draw method to work. Random must be imported,
 * must be created, and the instance variable "col" must be made
 */
public class Explosion extends Entity {

	private Random randy = new Random();
	/**
	 * Constructs an Explosion centered at the grid coordinates (w,z)
	 *
	 * @param w
	 * @param z
	 */
	public Explosion(RoundHandler roundHandler, int w, int z) {

		super(roundHandler, EntityType.PLAYER_WEAPON);

		setSize(6 * Game.GRID_SIZE, 3 * Game.GRID_SIZE);
		int xPlace = (w / Game.GRID_SIZE) - 2;
		int yPlace = (z / Game.GRID_SIZE) - 1;
		setLocation((xPlace * Game.GRID_SIZE), (yPlace * Game.GRID_SIZE));
		setHealth(30);
	}


	@Override
	public void act() {
		myHP--;
		col = randy.nextInt(3) + 1;
	}

	private int col = 1;

	@Override
	public void draw(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		if (col == 1)
			g2.setColor(Color.BLUE);
		if (col == 2)
			g2.setColor(Color.YELLOW);
		if (col == 3)
			g2.setColor(Color.RED);

		g2.fill(getRectangle());
		g2.setColor(Color.RED);
		g2.drawString("         EXPLOSION!!!", (int) xPos, (int) yPos + 30);
	}

	@Override
	public void onCollide(Entity e) {

		if(e.getEntityType() != EntityType.PLAYER) {
			e.takeDamage(this, myHP);
		}
	}

	public void takeDamage(Entity ent, int dmg) {

	}
}
