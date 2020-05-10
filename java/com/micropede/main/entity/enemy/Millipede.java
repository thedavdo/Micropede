package com.micropede.main.entity.enemy;

import com.micropede.main.Game;
import com.micropede.main.RoundHandler;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.EntityType;

import java.awt.*;
import java.util.ArrayList;

/**
 * Original Author: David Baker
 */
public class Millipede extends Entity {

	private ArrayList<Segment> segments;

	public Millipede(RoundHandler roundHandler) {

		this(roundHandler, 2 + (roundHandler.getRound() / 4), 12);
	}

	public Millipede(RoundHandler roundHandler, int strength, int numSeg) {

		super(roundHandler, EntityType.OPPONENT);
		segments = new ArrayList<>();

		Segment parent = null;

		for (int i = 0; i < numSeg; i++) {

			Segment seg;

			if(parent == null)
				seg = new Segment(roundHandler, Game.SCREEN_WIDTH - 1, 0);
			else
				seg = new Segment(roundHandler, parent);

			seg.setHealth(strength);
			segments.add(seg);

			roundHandler.addEntity(seg);
			parent = seg;
		}
	}

	@Override
	public void act() {

		for (int i = 0; i < segments.size(); i++) {
			Segment seg = segments.get(i);
			if(seg.isDead()) {
				segments.remove(i);
				i--;
			}
		}
	}

	@Override
	public void draw(Graphics g) { }

	@Override
	public boolean isDead() {
		return (segments.size() == 0);
	}

	@Override
	public void onCollide(Entity e) { }

	public void takeDamage(Entity ent, int dmg) { }
}

