package com.micropede.main.entity.environment;

import com.micropede.main.Game;
import com.micropede.main.RoundHandler;

import java.util.ArrayList;

/**
 * Original Author: Brian Pierson
 */
public class MushroomGrowth {

	private RoundHandler roundHandler;

	public MushroomGrowth(RoundHandler roundHandler) {

		this.roundHandler = roundHandler;

	}

	public void randomMushroomGrowth(ArrayList<Mushroom> shrooms) {
		ArrayList<Mushroom> newShrooms = new ArrayList<>(); //for growth
		//3 parts
		//Step 1) Generate the new array
		//step 2) compare the two arrays
		//step 3) fix the diifrences

		//Step 1) Generate the new array
		for (int loop = 0; loop < 100; loop++) {
			Mushroom mush = new Mushroom(roundHandler);
			newShrooms.add(mush);
		}

		//step 2) compare the two arrays
		for (int z = 0; z < newShrooms.size(); z++) {
			newShrooms.get(z).grow(true);
		}
		for (int y = 0; y < shrooms.size(); y++) {
			shrooms.get(y).doubled(false);
		}
		for (int z = 0; z < newShrooms.size(); z++) {
			for (int y = 0; y < shrooms.size(); y++) {
				if (newShrooms.get(z).checkOverlap(shrooms.get(y))) {
					newShrooms.get(z).grow(false);
					shrooms.get(y).doubled(true);
				}
			}
		}

		//step 3 code
		//set old mushrooms to die
		//it identifies which mushrooms to add and kill by the variables used in step 2
		for (int y = 0; y < shrooms.size(); y++) {
			if (!shrooms.get(y).getDoubled()) {
				shrooms.get(y).die(true);
				shrooms.get(y).setGrowth();
			}
			shrooms.get(y).doubled(true);
		}
		//add and set new mushrooms to grow
		for (int z = 0; z < newShrooms.size(); z++) {
			if (newShrooms.get(z).getGrowing()) {
				Mushroom mush = new Mushroom(roundHandler, newShrooms.get(z).getXPos(), newShrooms.get(z).getYPos(), true);
				mush.setGrowth();
				shrooms.add(mush);

			}
		}
	}

	//I will split this method into smaller methods once i reach a opportune point to do so!
	// Also, this is no where near opperational. I am not responsible for any crashes you may get
	// from running this methind. You have been warned.-Brian
	public void smartMushroomGrowth(ArrayList<Mushroom> shrooms) {
		//3 parts
		//Step 1) Generate the new array
		//step 2) compare the two arrays
		//step 3) fix the diifrences

		//step 1)
		ArrayList<Mushroom> newShrooms = new ArrayList<Mushroom>(); //array list that is generated from the old layout

		for (int x = 0; x < Game.GRID_WIDTH; x++) // for every grid square in the old array list... (x of the grid square)
		{
			for (int y = 0; y < Game.GRID_HEIGHT; y++) // for every grid square in the old array list... (y of the grid square)
			{
				int near = 0; //number of mushrooms directly around the grid square
				boolean farinv = false; //is there a inverted mushroom two square away
				boolean nearinv = false; //is there a inverted mushroom directly touching the square
				boolean occ = false; //is this grid sqaure occupied

				//checks to see if the current gridsquare is occupied
				for (int z = 0; z < shrooms.size(); z++) {
					if (shrooms.get(z).getXPos() == (x * Game.GRID_SIZE) && shrooms.get(z).getYPos() == (y * Game.GRID_SIZE)) {
						occ = true;
					}
				}

				//counts the surrounding mushrooms
				for (int cx = 0; cx < 5; cx++) {
					for (int cy = 0; cy < 5; cx++) {
						int checkx = (cx - 2) * Game.GRID_SIZE;
						int checky = (cy - 2) * Game.GRID_SIZE;
						boolean check = false; //if true, the gridsquare has been checked
						if (cx == 0 && cy == 0 || cx == 0 && cy == 5 || cx == 5 && cy == 5 || cx == 5 && cy == 0) //instances for corner squares
						{
							//do nothing
							//the far corner squares arent important
							check = true;
						}

						if (!check) {
							if (cx == 0 || cx == 5 || cy == 0 || cy == 5)//if far away
							//check for inverted mushrooms
							{
								for (int z = 0; z < shrooms.size(); z++) {
									if (shrooms.get(z).getXPos() == (checkx) && shrooms.get(z).getYPos() == (checky)) {
										if (shrooms.get(z).isInverted()) {
											check = true;
											farinv = true;
										}
									}
								}
							}
						}

						//note to self: still need to add identifier if statment
						if (!check) {
							//check for a mushroom directly on the spot
							for (int z = 0; z < shrooms.size(); z++) {
								if (shrooms.get(z).getXPos() == (checkx) && shrooms.get(z).getYPos() == (checky)) {
									if (shrooms.get(z).isInverted()) //if the mushroom is an inverted mushroom
									{
										check = true;
										farinv = true;
									}
									else // else, add one to the nearby count
									{
										near++;
									}
								}
							}
						}
					}
				}

				// add mushrooms
			}
		}

		//Step 2 code
		//these two variables are the ones used to identify which mushrooms are and are not doubled
		for (int z = 0; z < newShrooms.size(); z++) {
			newShrooms.get(z).grow(true);
		}
		for (int y = 0; y < shrooms.size(); y++) {
			shrooms.get(y).doubled(false);
		}
		//compare the arraylist, and if there are similarities, reset the variables
		for (int z = 0; z < newShrooms.size(); z++) {
			for (int y = 0; y < shrooms.size(); y++) {
				if (newShrooms.get(z).checkOverlap(shrooms.get(y))) {
					newShrooms.get(z).grow(false);
					shrooms.get(y).doubled(true);
				}
			}
		}

		//step 3 code
		//set old mushrooms to die
		//it identifies which mushrooms to add and kill by the variables used in step 2
		for (int y = 0; y < shrooms.size(); y++) {
			if (!shrooms.get(y).getDoubled()) {
				shrooms.get(y).die(true);
				shrooms.get(y).setGrowth();
			}
			shrooms.get(y).doubled(true);
		}
		//add and set new mushrooms to grow
		for (int z = 0; z < newShrooms.size(); z++) {
			if (newShrooms.get(z).getGrowing()) {
				Mushroom mush = new Mushroom(roundHandler, newShrooms.get(z).getXPos(), newShrooms.get(z).getYPos(), true);
				mush.setGrowth();
				shrooms.add(mush);

			}
		}
	}


}
