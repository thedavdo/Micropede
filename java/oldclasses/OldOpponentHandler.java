/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oldclasses;

/**
 * This class handles the spawning of opponents and initializing of rounds.
 *
 * @author Spock 2-25-13
 */
public class OldOpponentHandler {

//	private Game game;
//
//	public static final int BONUS_ROUND_FREQ = 3;
//	public Random randy = new Random();
//
//	private int numBeesToSpawnThisRound;
//	private int maxBeesAtOnce;
//	private int numDragonfliesToSpawnThisRound;
//	private int framesToFirstDragonfly;
//	private int numEarwigsToSpawnThisRound;
//	private int framesToFirstEarwig;
//	private int numInchwormsToSpawnThisRound;
//	private int framesToFirstInchworm;
//	private int numMosquitosToSpawnThisRound;
//	private int framesToFirstMosquito;
//	private int maxSpidersAtOnce;
//	private int chanceOfAddingSpider;  //out of 10000
//	private int maxInchwormsAtOnce;
//	private int chanceOfAddingInchworm;  //out of 10000
//	private int maxBeetlesAtOnce;
//	private int chanceOfAddingBeetle = 5000;  //out of 10000
//	private int chanceOfGrowth;  //out of 1000000
//
//	public OldOpponentHandler(Game game) {
//
//		this.game = game;
//	}
//
//
//	public boolean isRoundOver() {
//
//		boolean over = false;
//
//		boolean millipedeInArena = false;
//		int oppCount = 0;
//		// Loops through the opps to see how many Millipedes Exist.
//		for (Entity e : game.opp) {
//			if (e instanceof Millipede) {
//				millipedeInArena = true;
//			}
//			else if (!(e instanceof Arrow)) //anything but Arrows
//			{
//				oppCount++;
//			}
//		}
//		if (!millipedeInArena) {
//			resetSpawnInfo();
//		}
//
//		over = (!millipedeInArena && oppCount == 0);
//
//		if(over)
//			game.round++;
//
//		return over;
//	}
//
//
//	public void startRound() {
//
//		System.out.println("Starting round #" + game.round);
//		resetSpawnInfo();
//
//		//**** Drop all mushrooms a row ***
//		for (Mushroom m : game.shrooms)
//			m.moveDown();
//		//Add some new ones in the top row.
//		for (int q = 0; q < 5; q++) {
//			if (randy.nextInt(100) < 10) {
//                game.shrooms.add(new DDT(game.shrooms));
//			}
//			else
//				game.shrooms.add(new Mushroom(Game.GRID_SIZE + (randy.nextInt(Game.GRID_WIDTH - 2) * Game.GRID_SIZE), 0, game.shrooms, false));
//		}
//
//		if (game.round % BONUS_ROUND_FREQ == 0) //Bonus or regular round
//			startBonusRound();
//		else
//			startRegularRound();
//	}
//
//
//	private void startRegularRound() {
//		//create new Millipede
//		game.opp.clear();
//
//		game.opp.add(new Millipede(game, 2 + (game.round / 4), 12));
//
//		//initialize other spawn rates for this round.
//		if (game.round % 3 == 1 || game.round < 3) //Bees
//		{
//			numBeesToSpawnThisRound = game.round * 3;
//		}
//		else if (game.round % 3 == 2) //Dragonflies
//		{
//			numDragonfliesToSpawnThisRound = game.round * 3;
//			framesToFirstDragonfly = randy.nextInt(300) + 100;
//		}
//		else //Mosquitos
//		{
//
//		}
//
//		maxSpidersAtOnce = game.round / 5 + 1 < 4 ? game.round / 5 + 1 : 3;  //max 3!
//
//		maxInchwormsAtOnce = 5;
//
//		chanceOfAddingSpider = 10; //max 50% of frames.
//
//		chanceOfAddingInchworm = 30; //max 50% of frames.
//
//		maxBeetlesAtOnce = game.round / 5 + 1 < 4 ? game.round / 5 + 1 : 3;  //max 3!
//		chanceOfAddingBeetle = game.round > 1 ? game.round * 100 : 0; //only after first round.
//
//		numInchwormsToSpawnThisRound = game.round > 5 ? 1 : 0;
//		framesToFirstInchworm = randy.nextInt(1200) + 300;
//		numEarwigsToSpawnThisRound = game.round > 5 ? 1 : 0;
//		framesToFirstEarwig = randy.nextInt(1200) + 300;
//
//		chanceOfGrowth = 10 * game.round;
//	}
//
//
//	private void startBonusRound() {
//		game.opp.clear();
//
//		if (game.round % (2 * BONUS_ROUND_FREQ) == BONUS_ROUND_FREQ) { //Bees
//			maxBeesAtOnce = 30;
//			for (int i = 0; i < maxBeesAtOnce; i++) {
//				game.opp.add(new Bee(game, 600 * i));
//			}
//		}
//		else {  //Dragonflies
//			numDragonfliesToSpawnThisRound = 30 + game.round;
//			framesToFirstDragonfly = randy.nextInt(300) + 100;
//		}
//	}
//
//	public void spawnOpps() {
//		int numSpiders = 0, numBeetles = 0, numBees = 0, numInch = 0;
//		//Count Types of Entities
//		for (Entity e : game.opp) {
//			if (e instanceof Spider) numSpiders++;
//			if (e instanceof Beetle) numBeetles++;
//			if (e instanceof Bee) numBees++;
//			if (e instanceof Inchworm) numInch++;
//		}
//
//		//Consider adding Spiders or Beetles
//		if (numSpiders < maxSpidersAtOnce && randy.nextInt(10000) < chanceOfAddingSpider)
//			game.opp.add(new Spider());
//		if (numBeetles < maxBeetlesAtOnce && randy.nextInt(10000) < chanceOfAddingBeetle)
//			game.opp.add(new Beetle());
////		if (numInch < maxInchwormsAtOnce && randy.nextInt(10000) < chanceOfAddingInchworm)
////			game.opp.add(new Inchworm());
//
////        //Consider adding Inchworm or Earwig
////        if(framesToFirstInchworm > 0)
////            framesToFirstInchworm--;
////        else if (numInchwormsToSpawnThisRound > 0)
////        {
////            game.opp.add(new Inchworm());
////        }
//
//
//		if (numBeesToSpawnThisRound > 0 && numBees < maxBeesAtOnce) {
//			game.opp.add(new Bee(game, 400 + randy.nextInt(4000)));
//			numBeesToSpawnThisRound--;
//		}
//
//	}
//
//	/**
//	 * Resets all of the rates and values for spawning.
//	 * Useful when starting a new round.
//	 */
//	private void resetSpawnInfo() {
//
//		numBeesToSpawnThisRound = 0;
//		maxBeesAtOnce = 1;
//		numDragonfliesToSpawnThisRound = 0;
//		framesToFirstDragonfly = 0;
//		numEarwigsToSpawnThisRound = 0;
//		framesToFirstEarwig = 0;
//		numInchwormsToSpawnThisRound = 0;
//		framesToFirstInchworm = 0;
//		numMosquitosToSpawnThisRound = 0;
//		framesToFirstMosquito = 0;
//		maxSpidersAtOnce = 0;
//		chanceOfAddingSpider = 0;  //out of 10000
//		maxBeetlesAtOnce = 0;
//		chanceOfAddingBeetle = 0;  //out of 10000
//		chanceOfGrowth = 0;  //out of 1000000
//	}


}
