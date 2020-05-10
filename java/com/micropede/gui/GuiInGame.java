package com.micropede.gui;


import com.arcadeengine.AnimPanel;
import com.arcadeengine.KeyBinding;
import com.arcadeengine.gui.Gui;
import com.arcadeengine.gui.GuiComponent;
import com.arcadeengine.gui.TransitionType;
import com.micropede.main.Game;
import com.micropede.main.RoundHandler;
import com.micropede.main.entity.Entity;
import com.micropede.main.entity.environment.Mushroom;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

/**
 * Original Author: David Baker
 */
public class GuiInGame extends Gui {

	private Game game = (Game) panel;
	private RoundHandler roundHandler = game.getRoundHandler();
	private KeyBinding gameBinds = new KeyBinding("InGame") {

		@Override
		public boolean canListen() {
			return (game.getGuiHandler().getGui() instanceof GuiInGame);
		}

		@Override
		public void onPress(KeyEvent e, String key) {
			if(key.equals("Escape")) {
				game.getGuiHandler().switchGui(new GuiPaused(game, GuiInGame.this), TransitionType.fade);
			}
		}
	};

	public GuiInGame(AnimPanel animPanel) {
		super(animPanel);

		this.setBGColor(new Color(0,0,0,0));

		game.getKeyBoardHandler().addBindings(gameBinds);
		roundHandler.startNewGame();
	}

	public void drawGui(Graphics g) {

		for (Mushroom mushroom : roundHandler.getMushGrid()) {
			mushroom.draw(g);
		}

		for (Entity entity : roundHandler.getEntityList()) {
			entity.draw(g);
		}

		roundHandler.getPlayer().draw(g);

		String scoreCount = "" + roundHandler.getScore();

		g.setColor(Color.blue);
		Rectangle2D rect = g.getFontMetrics().getStringBounds("Score", g);

		Font font = new Font("Trebuchet MS", Font.BOLD, 14);
		Color color = new Color(132, 116, 255, 254);

		this.drawString("Score", font, color, (int) (600 / 2 - rect.getWidth() / 2), 12, g);
		this.drawString("Round: " + roundHandler.getRound(), font, color, 4, 12, g);

		rect = g.getFontMetrics().getStringBounds(scoreCount, g);
		this.drawString(scoreCount, font, color,  (int) (600 / 2 - rect.getWidth() / 2), 12 + 14, g);
	}


	@Override
	public void updateGui() {

		roundHandler.update();
	}

	@Override
	public void actionPerformed(GuiComponent comp, int msBtn) {

	}
}
