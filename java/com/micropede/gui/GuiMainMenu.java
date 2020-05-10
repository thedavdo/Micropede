package com.micropede.gui;


import com.arcadeengine.AnimPanel;
import com.arcadeengine.ResourceUtil;
import com.arcadeengine.gui.Gui;
import com.arcadeengine.gui.GuiComponent;
import com.arcadeengine.gui.GuiImgButton;
import com.arcadeengine.gui.TransitionType;
import com.micropede.main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Original Author: David Baker
 */
public class GuiMainMenu extends Gui {
	private Game game = (Game) panel;

	private ArrayList<GuiComponent> buttons = new ArrayList();

//	private GuiButton start = new GuiButton(game, 200, 210, 200, 20, "Start New Game!");
//	private GuiButton options = new GuiButton(game, 200, 240, 200, 20, "Options");
//	private GuiButton exit = new GuiButton(game, 200, 270, 200, 20, "Exit");




	private static BufferedImage title = ResourceUtil.loadInternalImage("gui/microTitle.png");


	private static BufferedImage startImg = ResourceUtil.loadInternalImage("gui/startButton.png");
	private static BufferedImage optionImg = ResourceUtil.loadInternalImage("gui/optionsButton.png");
	private static BufferedImage exitImg = ResourceUtil.loadInternalImage("gui/exitButton.png");

	private GuiImgButton start = new GuiImgButton(game, startImg,    (Game.SCREEN_WIDTH - startImg.getWidth()) / 2,  210, "start");
	private GuiImgButton options = new GuiImgButton(game, optionImg, (Game.SCREEN_WIDTH - optionImg.getWidth()) / 2, 210 + 44 + 10, "options");
	private GuiImgButton exit = new GuiImgButton(game, exitImg,      (Game.SCREEN_WIDTH - exitImg.getWidth()) / 2,   210 + 44 + 44 + 10 + 10, "exit");


	public GuiMainMenu(AnimPanel animPanel) {

		super(animPanel);

		buttons.add(start);
		buttons.add(options);
		buttons.add(exit);

		this.setBGColor(new Color(0,0,0,0));

		this.componentArrays.add(buttons);
	}

	public void drawGui(Graphics g) {

		g.setColor(new Color(123, 68, 68, 100));
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);

		int tx = (Game.SCREEN_WIDTH - title.getWidth()) / 2;

		g.drawImage(title, tx, 100, game);


		drawComponents(g, buttons, 10, 10);
	}

	public void updateGui() {

		updateComponents(buttons);
	}

	public void actionPerformed(GuiComponent btn, int msBtn) {

		if (btn.equals(start))
			game.getGuiHandler().switchGui(new GuiInGame(game), TransitionType.fade);
		else if (btn.equals(options))
			game.getGuiHandler().switchGui(new GuiOptions(game), TransitionType.fade);
		else if (btn.equals(exit))
			game.getGuiHandler().switchGui(new GuiQuit(game), TransitionType.fade);
	}
}
