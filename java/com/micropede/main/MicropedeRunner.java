package com.micropede.main;

import com.arcadeengine.AnimPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MicropedeRunner {

	AnimPanel world = new Game();

	// ==============================================================================
	// --- Typically you will never need to edit any of the code below this ---
	// ==============================================================================

	private JFrame myFrame;

	public MicropedeRunner() {

		this.myFrame = new JFrame();
		this.myFrame.addWindowListener(new Closer());

		addFrameComponents();

		this.myFrame.pack();

		this.myFrame.setVisible(true);
		this.myFrame.setResizable(world.isResizable());

		this.myFrame.setLocationRelativeTo(null);

		startAnimation();
	}

	public void addFrameComponents() {

		this.myFrame.setTitle(this.world.getMyName());
		this.myFrame.add(this.world);
	}

	public void startAnimation() {

		Thread anim = new Thread() {
			@Override
			public void run() {

				Timer timer = new Timer(1000 / 60, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						MicropedeRunner.this.myFrame.getComponent(0).repaint();
					}
				});
				timer.start();
			}
		};

		Thread process = new Thread() {

			@Override
			public void run() {

				Timer timer = new Timer(1000 / MicropedeRunner.this.world.getTimerDelay(), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (!MicropedeRunner.this.myFrame.isResizable())
							MicropedeRunner.this.myFrame.pack();
						MicropedeRunner.this.world.runProcess();
						MicropedeRunner.this.world.setComponentClicked(false);
					}
				});
				timer.start();
			}
		};

		anim.start();
		process.start();
	}

	public static void main(String[] args) {

		new MicropedeRunner();
	}

	private static class Closer extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {

			System.out.println("Closing...");
			System.exit(0);
		} // ======================
	}

}
