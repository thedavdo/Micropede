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
public class GuiOptions extends Gui {

	private Game game = (Game) panel;

	private ArrayList<GuiComponent> buttons = new ArrayList<>();

	private GuiButton dev = new GuiButton(game, 200, 240, 200, 20, "Dev Toggle");
	private GuiButton back = new GuiButton(game, 200, 270, 200, 20, "Go Back");

	private static BufferedImage title = ResourceUtil.loadInternalImage("gui/optionsTitle.png");

	public GuiOptions(AnimPanel animPanel) {

		super(animPanel);

		buttons.add(dev);
		buttons.add(back);

		this.setBGColor(new Color(0,0,0,0));

		componentArrays.add(buttons);
	}

	public void drawGui(Graphics g) {
		g.setColor(new Color(68, 123, 68, 100));
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);

		int tx = (Game.SCREEN_WIDTH - title.getWidth()) / 2;
		g.drawImage(title, tx, 100, game);

		drawComponents(g, buttons, 10, 10);
	}

	public void updateGui() {

		updateComponents();
	}

	public void actionPerformed(GuiComponent btn, int msBtn) {

		if (btn.equals(dev)) {
			game.getGuiHandler().invertDebugState();
		}
		else if (btn.equals(back))
			game.getGuiHandler().previousGui(TransitionType.fade);

	}
}
