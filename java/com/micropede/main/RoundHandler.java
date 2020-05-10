package com.micropede.main;

import com.arcadeengine.gui.Gui;
import com.arcadeengine.gui.TransitionType;
import com.micropede.gui.GuiGameOver;
import com.micropede.gui.GuiInGame;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.EntitySpawnInfo;
import com.micropede.main.entity.EntityType;
import com.micropede.main.entity.enemy.*;
import com.micropede.main.entity.environment.DDT;
import com.micropede.main.entity.environment.Mushroom;
import com.micropede.main.entity.player.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * Original Author: David Baker
 */
public class RoundHandler {

	public static void loadRes() { }

	private Game game;

	private final int bonusInterval = 3;
	private final int ddtNumStart = 4;
	private final int mushNumStart = 56;

	private Player player;
	private int currScore;
	private int currRound;
	private int currLives;

	private ArrayList<Entity> entityAdd;
	private ArrayList<Entity> entityList;

	private ArrayList<EntitySpawnInfo> roundAdditions;

	private Random rand = new Random();

	public RoundHandler(Game gm) {
		this.game = gm;

		entityAdd = new ArrayList<>();
		entityList = new ArrayList<>();
		roundAdditions = new ArrayList<>();

		initRoundAdditions();

		currScore = 0;
		currRound = -1;
		currLives = 3;
	}

	private void initRoundAdditions() {

		roundAdditions.add(new EntitySpawnInfo<>(Millipede.class, 1, 0));
		roundAdditions.add(new EntitySpawnInfo<>(Spider.class, 1, 0.25, .25));
		roundAdditions.add(new EntitySpawnInfo<>(Beetle.class, 2, 0.125, .25));
		roundAdditions.add(new EntitySpawnInfo<>(Inchworm.class, 4, 0.0625, .003));
	}

	// --- Accessor Methods ---

	public Game getGame() {
		return game;
	}

	public ArrayList<Mushroom> getMushGrid() {

		ArrayList<Mushroom> mush = new ArrayList<>();

		for (Entity entity : entityList) {
			if(entity instanceof Mushroom) {
				mush.add((Mushroom) entity);
			}
		}

		return mush;
	}

	public ArrayList<Entity> getEntityList() {
		return entityList;
	}

	public Player getPlayer() {
		return player;
	}

	public void addEntity(Entity opp) {
		if(!opp.isDead())
			entityAdd.add(opp);
	}

	public void forceAddEntity(Entity opp) {
		if(!opp.isDead())
			entityList.add(opp);
	}

	public void clearEntities() {

		for (int i = 0; i < entityList.size(); i++) {

			Entity e = entityList.get(i);

			if(!(e instanceof Mushroom) && !(e instanceof Player)) {
				entityList.remove(e);
				i--;
			}
		}
	}


	public int getScore() {
		return currScore;
	}

	public int getRound() {
		return currRound;
	}

	public int getLives() {
		return currLives;
	}

	public boolean isRoundOver() {

		boolean over = true;

		for (Entity entity : entityList) {
			if((entity.getEntityType() == EntityType.OPPONENT))
				over = false;
		}

		return over;
	}

	public void addScore(int score) {
		currScore += score;
	}


	public void update() {

		this.updateSpawns();
		this.updateEntities();

		if(isRoundOver()) {
			startNewRound();
		}

		if(player.isDead()) {
			if(currLives > 0) {
				restartRound();
				currLives--;
			}
			else {
				Gui gui =  game.getGuiHandler().getCurrentGui();
				if(gui instanceof GuiInGame)
					game.getGuiHandler().switchGui(new GuiGameOver(game, gui), TransitionType.fade);
			}
		}
	}

	private void updateEntities() {

		if(currRound != -1) {
			if(entityAdd.size() != 0) {
				entityList.addAll(entityAdd);
				entityAdd.clear();
			}

			ArrayList<Entity> ents = getEntityList();

			for (Entity ent : ents) {
				ent.act();
			}

			for (Entity ent1 : ents) {
				for (Entity ent2 : ents) {
					if(!ent1.isDead() && !ent2.isDead() && ent1 != ent2) {
						if (ent1.checkOverlap(ent2)) {
							ent1.onCollide(ent2);
							ent2.onCollide(ent1);
						}
					}
				}
			}

			for (int i = 0; i < ents.size(); i++) {
				Entity ent = ents.get(i);
				if(ent.isDead()) {
					ents.remove(i);
					i--;
				}
			}
		}
	}

	private void updateSpawns() {

		if(currRound % bonusInterval != 0)
			for (EntitySpawnInfo roundAddition : roundAdditions) {
				entityAdd.addAll(roundAddition.getMidRoundAdditions(this));
			}
	}


	public void startNewGame() {

		currScore = 0;
		currRound = -1;
		currLives = 3;

		entityList.clear();
		startNewRound();
	}

	public void skipRound() {

		this.entityAdd.clear();
		clearEntities();
		startNewRound();
	}

	private void startNewRound() {

		//First Round
		if(currRound == -1) {
			currRound = 1;

			setupFirstGame();
			startNormalRound();
		}
		//All other rounds
		else {
			currRound++;

			for(Mushroom mushroom : getMushGrid())
				mushroom.moveDown();

			for(int loop = 0; loop < 5; loop++) {
				if (rand.nextInt(100) < 10)
					forceAddEntity(new DDT(this, Game.GRID_SIZE + (rand.nextInt(Game.GRID_WIDTH - 2) * Game.GRID_SIZE), 0));

				else
					forceAddEntity(new Mushroom(this, Game.GRID_SIZE + (rand.nextInt(Game.GRID_WIDTH - 2) * Game.GRID_SIZE), 0, false));
			}

			if(currRound % bonusInterval == 0)
				startBonusRound();
			else
				startNormalRound();
		}

		System.out.println("Starting Round: " + currRound);
	}

	private void restartRound() {

		clearEntities();
		player = new Player(this, 300, 650);

		if(currRound % bonusInterval == 0)
			startBonusRound();
		else
			startNormalRound();

		System.out.println("Restarting Round: " + currRound);
	}

	private void setupFirstGame() {

		player = new Player(this, 300, 650);

		for(int loop = 0; loop < ddtNumStart; loop++)
			forceAddEntity(new DDT(this));


		for (int i = 0; i < mushNumStart; i++) {

			Mushroom newMush = new Mushroom(this, Game.GRID_SIZE * (rand.nextInt(Game.GRID_WIDTH - 2) + 1), Game.GRID_SIZE * (rand.nextInt(Game.GRID_HEIGHT - 2) + 1), false);

			if(!newMush.isDead())
				forceAddEntity(newMush);
			else
				i--;
		}

	}

	private void startNormalRound() {

		for (EntitySpawnInfo roundAddition : roundAdditions) {
			entityAdd.addAll(roundAddition.getStartRoundAdditions(this));
		}
	}

	private void startBonusRound() {

		for (int i = 0; i < 12; i++)
			this.addEntity(new Bee(this, (i * 100) + 400 + rand.nextInt(6000)));


	}
}
