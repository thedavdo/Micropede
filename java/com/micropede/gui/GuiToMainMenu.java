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
public class GuiToMainMenu extends Gui {
	private Game game = (Game) panel;

	private ArrayList<GuiComponent> buttons = new ArrayList<>();

	private GuiButton mainmenu = new GuiButton(game, 200, 240, 200, 20, "Yes Please!");
	private GuiButton back = new GuiButton(game, 200, 270, 200, 20, "Take Me Away From Here!");

	private static BufferedImage title = ResourceUtil.loadInternalImage("gui/microTitle.png");

	public GuiToMainMenu(AnimPanel animPanel) {

		super(animPanel);

		buttons.add(mainmenu);
		buttons.add(back);

		componentArrays.add(buttons);
	}

	public void drawGui(Graphics g) {
		g.setColor(new Color(230, 68, 68, 100));
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);

		int tx = (Game.SCREEN_WIDTH - title.getWidth()) / 2;
		g.drawImage(title, tx, 100, game);

		this.setBGColor(new Color(0,0,0,0));

		drawComponents(g, buttons, 10, 10);
	}

	@Override
	public void updateGui() {

		updateComponents();
	}

	@Override
	public void actionPerformed(GuiComponent comp, int msBtn) {

		if (comp.equals(mainmenu)) {
			game.getGuiHandler().switchGui(new GuiMainMenu(game), TransitionType.fade);
		}
		else if (comp.equals(back))
			game.getGuiHandler().previousGui(TransitionType.fade);
	}
}
