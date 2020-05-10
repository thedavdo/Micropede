package com.micropede.main.entity;


import com.micropede.main.RoundHandler;

import java.awt.*;

/**
 *
 * Original Authors: Connor Boyle, Noah Czeresko, and David Baker
 *
 * Updated by: David Baker
 */
public abstract class Entity implements EntityInterface {

	protected RoundHandler roundHandler;

	protected EntityType myType;

	protected int width;
	protected int height;

	protected double xPos;
	protected double yPos;

	protected double xVel;
	protected double yVel;

	protected int myHP;

	public Entity(RoundHandler roundHandler, EntityType type) {
		this.roundHandler = roundHandler;
		this.myType = type;
	}

	/**
	 * Access the location and the dimensions of the Entity
	 *
	 * @return The  Rectangle object specifying the space occupied by this entity.
	 */
	public Rectangle getRectangle() {
		return new Rectangle((int) xPos,(int) yPos, width, height);
	}

	public int getXPos() {
		return (int) xPos;
	}

	public int getYPos() {
		return (int) yPos;
	}

	/**
	 * Access the velocity of the entity
	 *
	 * @return The x velocity of the Entity
	 */
	public double getXVel() {
		return xVel;
	}

	/**
	 * Access the velocity of the entity
	 *
	 * @return The y velocity of the Entity
	 */
	public double getYVel() {
		return yVel;
	}

	/**
	 * Get the health status of the Entity
	 *
	 * @return the myHP of the Entity
	 */
	public int getHealth() {
		return myHP;
	}

	public EntityType getEntityType() {
		return myType;
	}

	/**
	 * Checks to see if the Entity is dead.
	 *
	 * @return True if not alive.
	 */
	public boolean isDead() {
		if (myHP <= 0)
			return true;

		return false;
	}

	/**
	 * Replaces the current X,Y coordinates with the specified location.
	 *
	 * @param x The new X Coordinate.
	 * @param y The new Y Coordinate.
	 */
	protected void setLocation(int x, int y) {
		xPos = x;
		yPos = y;
	}

	protected void setLocation(double x, double y) {
		xPos = x;
		yPos = y;
	}

	/**
	 * Replaces the current X,Y velocities with the specified velocities.
	 *
	 * @param x The new X velocity.
	 * @param y The new Y velocity.
	 */
	protected void setVelocity(double x, double y) {
		xVel = x;
		yVel = y;
	}

	/**
	 * Replaces the current X,Y velocities with the specified velocities.
	 *
	 * @param x The new X velocity.
	 * @param y The new Y velocity.
	 */
	protected void setSize(int x, int y) {
		width = x;
		height = y;
	}

	public void setHealth(int hp) {
		myHP = hp;
	}

	/**
	 * Checks to see if an Entity comes in contact with another.
	 *
	 * @param ent The Entity to check for overlap/collision.
	 * @return True if overlap/collision occured.
	 */
	public boolean checkOverlap(Entity ent) {
		return this.getRectangle().intersects(ent.getRectangle());
	}

	protected void updateLoc() {

		xPos += xVel;
		yPos += yVel;
	}
}


