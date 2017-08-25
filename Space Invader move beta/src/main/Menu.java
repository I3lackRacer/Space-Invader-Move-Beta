package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

public class Menu {

	public Handler handler;
	public HUD hud;
	public GameObject m1;
	private int pos1 = 1, pos2 = 3, blink1 = 0;
	public GameObject m2;
	public static int timer = 0;
	public static boolean newRekord = false;
	public static int select = 1; // 2 == EXIT, 1 == RESUME
	public static CURRENTMENU currentWindow = CURRENTMENU.Game;
	public int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
	public Color c1, c2;

	public Menu(Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
		this.m1 = new NormalerGegner(0, 0, ID.NormalerGegner, handler, hud);
		this.m2 = new NormalerGegner(Game.WIDTH - 64, Game.HEIGHT - 64, ID.NormalerGegner, handler, hud);
		m1.setVelX(5);
		m1.setVelY(0);
		m2.setVelX(-5);
		m2.setY(0);
	}

	public void tick() {
		int speed = 5;
		// OBEN RECHTS >^
		if (pos1 == 1 && m1.getX() >= Game.WIDTH - 32) {
			pos1++;
			m1.setVelX(0);
			m1.setVelY(speed);
		}
		// UNTEN RECHTS >V
		if (pos1 == 2 && m1.getY() >= Game.HEIGHT - 64) {
			pos1++;
			m1.setVelX(-speed);
			m1.setVelY(0);
		}
		// UNTEN LINKS <V
		if (pos1 == 3 && m1.getX() <= 0) {
			pos1++;
			m1.setVelX(0);
			m1.setVelY(-speed);
		}
		// OBEN LINKS <^
		if (pos1 == 4 && m1.getY() <= 0) {
			pos1 = 1;
			m1.setVelX(speed);
			m1.setVelY(0);
		}

		// OBEN RECHTS >^
		if (pos2 == 1 && m2.getX() >= Game.WIDTH - 32) {
			pos2++;
			m2.setVelX(0);
			m2.setVelY(speed);
		}
		// UNTEN RECHTS >V
		if (pos2 == 2 && m2.getY() >= Game.HEIGHT - 64) {
			pos2++;
			m2.setVelX(-speed);
			m2.setVelY(0);
		}
		// UNTEN LINKS <V
		if (pos2 == 3 && m2.getX() <= 0) {
			pos2++;
			m2.setVelX(0);
			m2.setVelY(-speed);
		}
		// OBEN LINKS <^
		if (pos2 == 4 && m2.getY() <= 0) {
			pos2 = 1;
			m2.setVelX(speed);
			m2.setVelY(0);
		}
		m2.x += m2.velX;
		m2.y += m2.velY;
		m1.x += m1.velX;
		m1.y += m1.velY;
	}

	public void render(Graphics g) {
		
		//Server
		if(currentWindow == CURRENTMENU.Server) {
			g.setColor(Color.GREEN);
			g.setFont(new Font("Arial", 50, 50));
			g.drawRect(Game.WIDTH/2 - 200, 50, Game.WIDTH/4, Game.HEIGHT - 100);
			g.setColor(Color.GREEN);
			if(select == 1) g.setColor(Color.WHITE);
			g.drawString("Username: " + KeyInput.Name, Game.WIDTH / 2 - 60, Game.HEIGHT / 2 - 100);
			g.setColor(Color.GREEN);
			if(select == 2) g.setColor(Color.WHITE);
			g.drawString("IP: " + ServerTalk.ip, Game.WIDTH / 2 - 60, Game.HEIGHT / 2 );
			g.setColor(Color.GREEN);
			if(select == 3) g.setColor(Color.WHITE);
			if(timer > 0) {
				timer--;
				g.setColor(Color.RED);
			}
			g.drawString("Connect", Game.WIDTH / 2 - 60, Game.HEIGHT / 2 + 100);
			g.setColor(Color.GREEN);
			if(select == 4) g.setColor(Color.WHITE);
			g.drawString("Close", Game.WIDTH / 2 - 60, Game.HEIGHT / 2 + 200);
		}
		//Pause
		if (currentWindow == CURRENTMENU.Pause) {
			Game.timerunning = false;
			g.setFont(new Font("Arial", 50, 50));
			g.setColor(Color.GREEN);
			g.drawRect(Game.WIDTH/2 - 200, 50, Game.WIDTH/4, Game.HEIGHT - 100);
			g.setColor(Color.GREEN);
			if (select == 1) {
				g.setColor(Color.WHITE);
			}
			g.drawString("RESUME", Game.WIDTH / 2 - 60, Game.HEIGHT / 2 - 100);
			g.setColor(Color.GREEN);
			if (select == 2) {
				g.setColor(Color.WHITE);
			}
//			else {
//				g.setColor(Color.GREEN);
//			}
			g.drawString("EXIT", Game.WIDTH / 2 - 60, Game.HEIGHT / 2);
			g.setColor(Color.GREEN);
			if (select == 3) {
				g.setColor(Color.WHITE);
			}
//			else {
//				g.setColor(Color.GREEN);
//			}
			g.drawString("Multiplayer", Game.WIDTH / 2 - 60, Game.HEIGHT / 2 + 100);
		}

		//Gameover
		if (currentWindow == CURRENTMENU.Gameover) {
			Game.timerunning = false;
			g.setColor(Color.GREEN);
			g.drawRect(Game.WIDTH / 2 - 120, Game.HEIGHT / 2 - 120 * 2, 240, 240 * 2);
			g.drawImage(ImageHolder.NGegner, (int) m1.getX(), (int) m1.getY(), null);
			g.drawImage(ImageHolder.NGegner, (int) m2.getX(), (int) m2.getY(), null);
			g.setColor(Color.RED);
			g.setFont(new Font("ARIAL BLACK", 30, 30));

			if (blink1 >= 500) {
				Random r = new Random();
				int out = 100, in = 200;
				x1 = r.nextInt(Game.WIDTH - in) + out;
				x2 = r.nextInt(Game.WIDTH - in) + out;
				y1 = r.nextInt(Game.HEIGHT - in) + out;
				y2 = r.nextInt(Game.HEIGHT - in) + out;
				c1 = new Color(r.nextInt(200) + 55, r.nextInt(200) + 55, r.nextInt(200) + 55);
				c2 = new Color(r.nextInt(200) + 55, r.nextInt(200) + 55, r.nextInt(200) + 55);
				blink1 = 0;
			} else {
				this.blink1++;
			}
			g.setColor(Color.MAGENTA);
			g.drawString("Player:", Game.WIDTH / 2 - 55, 30);
			g.setColor(Color.GREEN);
			if (select == 0) {
				g.setColor(Color.WHITE);
			}
			g.drawString(KeyInput.Name, Game.WIDTH / 2 - 5 - (KeyInput.Name.length() * 10), 60);
			g.setColor(Color.GREEN);
			g.setColor(c1);
			g.drawString("GAME", x1, y1);
			g.setColor(c2);
			g.drawString("OVER", x2, y2);
			g.setFont(new Font("ARIAL BLACK", 10, 30));
			g.setColor(Color.GREEN);
			if (select == 1) {
				g.setColor(Color.WHITE);
			}
			g.drawString("RESTART", Game.WIDTH / 2 - 60, Game.HEIGHT / 2 - 200);
			g.setColor(Color.GREEN);
			if (select == 2) {
				g.setColor(Color.WHITE);
			}
			g.drawString("QUIT", Game.WIDTH / 2 - 60, Game.HEIGHT / 2 - 100);
			g.setColor(Color.RED);
			g.setFont(new Font("ARIAL BLACK", 10, 15));
			g.drawString("Letzter Rekord: " + hud.getHighScore(), Game.WIDTH / 2 - 60, Game.HEIGHT / 2);
			if(!newRekord) {
				g.drawString("Bei: " + Game.lastRekordName, Game.WIDTH / 2 - 60, Game.HEIGHT / 2 + 25);
				g.drawString("Innerhalb von: " + Math.round(Game.lastRekordTime/100/10) + " Sek." , Game.WIDTH / 2 - 60, Game.HEIGHT / 2 + 50);
			}else{
				g.drawString("Bei: " + KeyInput.Name, Game.WIDTH / 2 - 60, Game.HEIGHT / 2 + 25);
				g.drawString("Innerhalb von: " + hud.getScore() + " Sek." , Game.WIDTH / 2 - 60, Game.HEIGHT / 2 + 50);
			}
			try{
			
			}catch(NumberFormatException e) {
				g.drawString("Innerhalb von: " + "ERROR" + " Sek." , Game.WIDTH / 2 - 60, Game.HEIGHT / 2 + 50);
				System.out.println("Zeit konnte nicht ausgerechnet werden.");
			}
			g.drawString("Deine Punkte: " + hud.getscore(), Game.WIDTH / 2 - 60, Game.HEIGHT / 2 + 125);
			g.drawString("Überlebt: " + Game.newGametime + "s", Game.WIDTH / 2 - 60, Game.HEIGHT / 2 + 150);
		}
		if (currentWindow == CURRENTMENU.Game) {
			Game.timerunning = true;
		}
	}

}