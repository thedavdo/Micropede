package com.micropede.main;

import com.arcadeengine.AnimPanel;
import com.arcadeengine.KeyBinding;
import com.arcadeengine.ResourceUtil;
import com.arcadeengine.gui.DebugLine;
import com.micropede.gui.GuiInGame;
import com.micropede.gui.GuiMainMenu;
import com.micropede.main.entity.enemy.Bee;
import com.micropede.main.entity.enemy.Beetle;
import com.micropede.main.entity.enemy.Segment;
import com.micropede.main.entity.enemy.Spider;
import com.micropede.main.entity.environment.DDT;
import com.micropede.main.entity.environment.Mushroom;
import com.micropede.main.entity.player.Player;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Main engine of the millipede game.
 *
 * Original Authors: Jonah Austin, Brian Pierson, Gary Drake
 *
 * Updated by: David Baker
 */
public class Game extends AnimPanel {

	// Static CONSTANTS **** All objects should use these constants ****
	public static final int GRID_SIZE = 24;
	public static final int PLAYER_ZONE_HEIGHT = GRID_SIZE * 4;

	public static final int GRID_WIDTH = 25;
	public static final int GRID_HEIGHT = 29;

	public static final int SCREEN_WIDTH = GRID_SIZE * GRID_WIDTH;
	public static final int SCREEN_HEIGHT = GRID_SIZE * GRID_HEIGHT;

	private static BufferedImage bg = ResourceUtil.loadInternalImage("gui/bg.png");

	private AudioInputStream theme;
	private Clip clip;

	private RoundHandler roundHandler;


	private KeyBinding bindings = new KeyBinding("SystBinds") {

		@Override
		public void onPress(KeyEvent e, String key) {

			if(key.equals("F2")) {
				getGuiHandler().invertDebugState();
			}
			if(key.equals("F3")) {
				if (guiHandler.getGui() instanceof GuiInGame)
					roundHandler.skipRound();
			}
			if(key.equals("F4")) {
				if (guiHandler.getGui() instanceof GuiInGame)
					roundHandler.startNewGame();
			}

			if(key.equals("F9")) {
				clip.start();
			}
			if(key.equals("F10")) {
				clip.stop();
			}
		}

		public void whilePressed(KeyEvent e, String key) {

			if(key.equals("1")) {
				roundHandler.addEntity(new DDT(roundHandler, getMousePosition().x, getMousePosition().y));
			}

			if(key.equals("2")) {
				roundHandler.addEntity(new Mushroom(roundHandler, getMousePosition().x, getMousePosition().y, false));
			}

			if(key.equals("0")) {
				roundHandler.addEntity(new Spider(roundHandler));
			}
		}

		@Override
		public void onRelease(KeyEvent e, String key) {

		}
	};

	private DebugLine systLine = new DebugLine("SystDebug") {

		public String[] getLines() {

			return new String[]{
					"FPS: " + getFPS(),
					addBreak(),
					"Mouse X: " + getMousePosition().x,
					"Mouse Y: " + getMousePosition().y,
					addBreak(),
					"Opponents: " + roundHandler.getEntityList().size(),
					"Mushrooms: " + roundHandler.getMushGrid().size(),
					"Player Lives: " + roundHandler.getLives(),
			};
		}
	};

	//private PointCurve pc;

	/**
	 * Constructor for objects of class Game
	 */
	public Game() {

		this.setResizable(false);

		this.getKeyBoardHandler().addBindings(this.bindings);

		this.createGuiHandler(new GuiMainMenu(this));
		this.getGuiHandler().setDebugState(false);
		this.getGuiHandler().addDebugLine(systLine);
		this.createInstance("Micropede", SCREEN_WIDTH, SCREEN_HEIGHT);

		roundHandler = new RoundHandler(this);

		//pc = new PointCurve(this);
	}

	public RoundHandler getRoundHandler() {

		return roundHandler;
	}

	/**
	 * Runs the game, turn to turn. Locked at 60 FPS.
	 *
	 * @param g graphics context
	 */
	@Override
	public Graphics renderFrame(Graphics g) {
		//Special Actions----(actions mostly taken care of in GuiInGame)-------

		g.drawImage(bg, 0, 0, null);

		g.drawImage(null, 0,0,0,0, null);

		this.drawGui(g);

		//pc.draw(g);

		this.calculateRenderFPS();

		return g;
	}//--end of renderFrame()--

	@Override
	public void process() {

		//pc.update();
		this.updateGui();
	}

	@Override
	public void initRes() {

		Player.loadRes();
		Mushroom.loadRes();
		DDT.loadRes();
		Segment.loadRes();
		Bee.loadRes();
		Beetle.loadRes();
		Spider.loadRes();

		theme = ResourceUtil.loadClip("com.micropede.res", "zone2-1.wav"/*"zone3-2c.wav"*/);
		try {
			clip = AudioSystem.getClip();
			clip.open(theme);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
