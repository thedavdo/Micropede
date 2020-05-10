//2-27-13 Created Mosquito Class and its methods
package com.micropede.main.entity.enemy;

import com.micropede.main.RoundHandler;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.EntityType;
import com.micropede.main.entity.environment.Explosion;

import java.awt.*;


/**
 * Original Author: Kevin Zhang
 *
 * Updated by: David Baker
 */
public class Mosquito extends Entity {
	private static int speed = 3; //The generic speed of a Mosquito

	public Mosquito(RoundHandler roundHandler) {

		this(roundHandler, 40, 40);
	}

	public Mosquito(RoundHandler roundHandler, int a, int b) {

		super(roundHandler, EntityType.OPPONENT);
		setLocation(a, b);
		setVelocity(speed, speed);
	}

	public void act() {

	}

	public void draw(Graphics g) {
		g.setColor(Color.white);//A mosquito is white in color
	}

	public void onCollide(Entity e) {

		if (e instanceof Explosion) {
			setHealth(0);
		}
	}

	public void takeDamage(Entity ent, int dmg) {
		this.myHP -= dmg;
	}
}
