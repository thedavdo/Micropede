package com.micropede.main.entity;


import com.micropede.main.RoundHandler;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 *
 * Original Author: David Baker
 *
 */
public class EntitySpawnInfo<E> {

	private final Class<E> entClass;

	private final int roundStartSpawning;
	private final double roundScale;

	private final double spawnChance;

	public EntitySpawnInfo(Class<E> entClass, int roundStartSpawning, double roundScale) {

		this(entClass, roundStartSpawning, roundScale, 0);
	}

	public EntitySpawnInfo(Class<E> entClass, int roundStartSpawning, double roundScale, double spawnChance) {

		this.entClass = entClass;

		this.spawnChance = spawnChance / 60d;

		this.roundStartSpawning = roundStartSpawning;
		this.roundScale = roundScale;
	}

	public ArrayList<E> getMidRoundAdditions(RoundHandler roundHandler) {

		ArrayList<E> ents = new ArrayList<>();

		if(roundHandler.getRound() >= roundStartSpawning && spawnChance != 0) {

			int round = roundHandler.getRound();
			int entCount = 0;
			for (Entity entity : roundHandler.getEntityList()) {
				if (entity.getClass().getName().equals(entClass.getName()))
					entCount++;
			}

			double scale = ((double) round) * roundScale;

			if (roundScale == 0)
				scale = 1;

			if (entCount < scale) {
				for (int i = 0; i < scale - entCount; i++) {
					if (Math.random() <= spawnChance) {
						E ent = getNewInstance(roundHandler);
						ents.add(ent);
					}
				}
			}
		}

		return ents;
	}

	public ArrayList<E> getStartRoundAdditions(RoundHandler roundHandler) {

		ArrayList<E> newAdd = new ArrayList<>();

		if(roundHandler.getRound() >= roundStartSpawning) {
			if (spawnChance == 0) {
				int round = roundHandler.getRound();

				double scale = round * roundScale;
				if (roundScale == 0)
					scale = 1;

				for (int i = 0; i < scale; i++) {
					newAdd.add(getNewInstance(roundHandler));
				}
			}
		}

		return newAdd;
	}

	private E getNewInstance(RoundHandler roundHandler) {

		try {
			Class[] argsClass = { RoundHandler.class };
			Object[] args = { roundHandler };

			Constructor cnstr = entClass.getConstructor(argsClass);
			E ent = (E) cnstr.newInstance(args);

			return ent;
		}
		catch (Exception e) {
			System.out.println(e);
		}


		return null;
	}
}
