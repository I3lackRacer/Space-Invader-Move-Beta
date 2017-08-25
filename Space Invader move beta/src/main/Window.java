package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class Window extends Canvas {

	private static final long serialVersionUID = -24084060053378354L;

	private Handler handler;
	private HUD hud;
	public static JFrame frame = new JFrame("");
	public static int MouseX = 0;
	public static int MouseY = 0;
	public static int number = -2;
	private int size = -1;

	@SuppressWarnings("static-access")
	public Window(int weite, int höhe, String titel, Game game, Handler handler, HUD hud) {

		this.setHud(hud);
		frame.setTitle(titel);
		this.setHandler(handler);
		frame.setPreferredSize(new Dimension(weite, höhe));
		frame.setMaximumSize(new Dimension(weite, höhe));
		frame.setMinimumSize(new Dimension(weite, höhe));

		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		Cursor c = getToolkit().createCustomCursor(ImageHolder.c, getLocation(), "FadenKreuz");
		frame.setCursor(c);
//		frame.setUndecorated(true);
		frame.setIconImage(ImageHolder.NGegner);
		frame.setBackground(Color.BLACK);
		frame.setVisible(true);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
//				Game.running = false;
//				number = -1;
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
//				if(!Game.menuVisible) {
//					if (number == -1) {
//						number = 3;
//					}
//				}
			}
		});
		frame.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				MouseX = e.getX();
				MouseY = e.getY();
				handler.addObjekt(new Bullet((int) handler.getDirect(ID.Player).x + (int) handler.getDirect(ID.Player).getBounds().getWidth() / 2,(int) handler.getDirect(ID.Player).y, ID.Bullet, handler, hud, false));
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		game.start();
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public HUD getHud() {
		return hud;
	}

	public void setHud(HUD hud) {
		this.hud = hud;
	}

	public void render(Graphics g) {
		if (number > 0) {
			g.setFont(new Font("ARIAL BLACK", 10, size));
			g.setColor(Color.GREEN);
			g.drawString(String.valueOf(number), Game.WIDTH / 2, Game.HEIGHT / 2);
		}
	}

	public void tick() {
		if (number > -1) {
			if (size > 0) {
				size--;
			} else {
				number--;
				size = 100;
			}
			if (number == 0) {
				Game.running = true;
			}
		}

	}
}
