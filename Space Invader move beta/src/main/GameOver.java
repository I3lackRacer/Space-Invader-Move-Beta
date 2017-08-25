package main;

import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

import java.awt.Canvas;
import javax.swing.JLabel;

public class GameOver extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 175765754546564L;

	/**
	 * Create the panel.
	 */
	public GameOver() {
		setBackground(Color.BLACK);
		this.setSize(480, (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()-40));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 480, (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()-40));
		add(panel);
		panel.setLayout(null);
		
		Canvas canvas = new Canvas();
		canvas.setBounds(100, 100, 480-200, (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()-40)-200);
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(3);
		}

		Graphics g = bs.getDrawGraphics();
		g.drawLine(0, 0, 480-200, (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()-40)-200);
		g.dispose();
		bs.show();
		panel.add(canvas);
		
		JLabel lblGameOver = new JLabel("Game Over");
		lblGameOver.setBounds(193, 11, 86, 14);
		panel.add(lblGameOver);
	}
}
