package com.micropede.gui;


import com.arcadeengine.AnimPanel;
import com.arcadeengine.ResourceUtil;
import com.arcadeengine.gui.Gui;
import com.arcadeengine.gui.GuiButton;
import com.arcadeengine.gui.GuiComponent;
import com.arcadeengine.gui.TransitionType;
import com.micropede.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Original Author: David Baker
 */
public class GuiGameOver extends Gui {
	private Game game = (Game) panel;

	private ArrayList<GuiComponent> buttons = new ArrayList<>();

	private GuiButton restart = new GuiButton(game, 200, 210, 200, 20, "Restart");
	private GuiButton mainmenu = new GuiButton(game, 200, 270, 200, 20, "Quit to Main Menu");
	private GuiButton exit = new GuiButton(game, 200, 300, 200, 20, "Exit");

	private Gui inGame;

	private static BufferedImage title = ResourceUtil.loadInternalImage("gui/gameOver.png");



	public GuiGameOver(AnimPanel animPanel, Gui inGame) {

		super(animPanel);

		this.inGame = inGame;

		this.setBGColor(new Color(0,0,0,0));

		buttons.add(restart);
		buttons.add(mainmenu);
		buttons.add(exit);

		componentArrays.add(buttons);
	}

	public void drawGui(Graphics g) {

		inGame.drawGui(g);

		g.setColor(new Color(123, 0, 10, 161));
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);

		int tx = (Game.SCREEN_WIDTH - title.getWidth()) / 2;

		g.drawImage(title, tx, 100, game);

		drawComponents(g, buttons, 10, 10);
	}

	public void updateGui() {

		updateComponents();
	}

	public void actionPerformed(GuiComponent btn, int msBtn) {

		if (btn.equals(restart))
			game.getGuiHandler().switchGui(new GuiInGame(game), TransitionType.fade);
		else if (btn.equals(mainmenu))
			game.getGuiHandler().switchGui(new GuiToMainMenu(game), TransitionType.fade);
		else if (btn.equals(exit))
			game.getGuiHandler().switchGui(new GuiQuit(game), TransitionType.fade);
	}
}
