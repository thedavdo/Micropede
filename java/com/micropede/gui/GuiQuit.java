package com.micropede.gui;


import com.arcadeengine.AnimPanel;
import com.arcadeengine.gui.Gui;
import com.arcadeengine.gui.GuiButton;
import com.arcadeengine.gui.GuiComponent;
import com.arcadeengine.gui.TransitionType;
import com.micropede.main.Game;


import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Original Author: David Baker
 */
public class GuiQuit extends Gui {
	private Game game = (Game) panel;

	private ArrayList<GuiComponent> buttons = new ArrayList<>();

	private GuiButton quit = new GuiButton(game, 200, 240, 200, 20, "Quit");
	private GuiButton back = new GuiButton(game, 200, 270, 200, 20, "Back");

	public GuiQuit(AnimPanel panel) {

		super(panel);

		this.setBGColor(new Color(0,0,0,0));

		Random rand = new Random();

		String quitTitles[] =
				{
						"'I Don't Want To Close...'",
						"Get me Outta Here!!!",
						"Fine, Leave Me.",
						"Click Me!",
						"Go Get Some Air.",
						"I've Been Here Too Long!",
						"Say 'Hi' to Your Mother.",
				};

		String backTitles[] =
				{
						"NO, NO NO NO!",
						"Must Have More!",
						"UNDO UNDO UNDO!",
						"No! CLICK ME!",
						"Play More Micropede.",
						"Error. Don't Want to Close.",
						"NOAH WANT MOAR!",
						"FEED THE BEASTLYPEDE",
						"AAAAAHHH!"
				};

		quit.setLabel(quitTitles[rand.nextInt(quitTitles.length)]);
		back.setLabel(backTitles[rand.nextInt(backTitles.length)]);

		buttons.add(quit);
		buttons.add(back);

		componentArrays.add(buttons);
	}

	public void drawGui(Graphics g) {
		g.setColor(new Color(230, 68, 68, 100));
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);

		//g.drawImage(this.title, 0, 100, game);

		drawComponents(g, buttons, 10, 10);
	}

	@Override
	public void updateGui() {

		updateComponents();
	}

	@Override
	public void actionPerformed(GuiComponent comp, int msBtn) {

		if (comp.equals(quit))
			System.exit(0);
		else if (comp.equals(back))
			game.getGuiHandler().previousGui(TransitionType.fade);

	}
}
