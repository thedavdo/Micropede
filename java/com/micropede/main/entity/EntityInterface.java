package com.micropede.main.entity;

import java.awt.*;

/**
 *
 * Original Authors: Connor Boyle, Noah Czeresko, and David Baker
 *
 * Updated by: David Baker
 */
public interface EntityInterface {

	void act();

	void draw(Graphics g);

	void onCollide(Entity ent);

	void takeDamage(Entity ent, int dmg);
}