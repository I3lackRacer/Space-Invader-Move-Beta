package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HUD {

	public static int HEALTH = 100;
	private int lastTickHealth = 100;
	public int score = 0;
	private int level = 1;
	private int HighScore = 0;



	public int newLife = 25;
	public int blink = 80;

	public void tick() {

		if (getscore() % 10 == 0 && getscore() != 0) {
			if (score / 10 >= level) {
				level++;
			}
		}

		boolean durchHeal = false;

		if (newLife != 0) {
			newLife--;
		} else {
			if (HEALTH < 100) {
				HEALTH++;
				durchHeal = true;
			}
			newLife = 25;

		}

		if (lastTickHealth != HEALTH && durchHeal == false) {
			newLife = 150;
		}
		lastTickHealth = HEALTH;
		HEALTH = (int) Game.clamp(HEALTH, 0, 100);

	}

	public HUD() {
		if (!(new File("/Space Invader/rekord.txt").exists())) {
			try {
				new File("/Space Invader").mkdir();
				new File("/Space Invader/rekord.txt").createNewFile();
				FileWriter fw = new FileWriter(new File("/Space Invader/rekord.txt"));
				fw.write("0");
				fw.flush();
				fw.close();
				System.out.println("Datein erstellt: REKORD");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("/Space Invader/rekord.txt")));
			br.readLine();
			try {
				HighScore = Integer.valueOf(br.readLine());
				br.close();
			} catch (NumberFormatException e) {
				br.close();
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/Space Invader/rekord.txt")));
				bw.write("I3lackRacer" + System.getProperty("line.separator"));
				bw.write("0" + System.getProperty("line.separator"));
				bw.write("0" + System.getProperty("line.separator"));
				bw.close();
				HighScore = 0;
				System.out.println(
						"Rekord datei konnte nicht gelesen werden und wurde aus diesem grund mit Standartparametern beschrieben.(0, I3lackRacer, 0)");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(15, 15, 200, 32);
		if (HEALTH >= 0) {
			g.setColor(Color.RED);
		}
		if (HEALTH >= 33) {
			g.setColor(Color.YELLOW);
		}
		if (HEALTH >= 66) {
			g.setColor(Color.GREEN);
		}
		if (HEALTH <= 0) {
			if (HighScore < score) {
				Menu.newRekord = true;
				new DateiWriter(Game.ort + "rekord.txt", KeyInput.Name + "/#/" + score
						+ "/#/" + Game.newGametime, true, 1);
				Game.lastRekordTime = Game.time;
				HighScore = score;
			}
			Menu.currentWindow = CURRENTMENU.Gameover;
			Game.menuVisible = true;
			Game.running = false;
			Game.newGametime = (System.currentTimeMillis() / 1000 - Game.start - Game.newGametime);
		}

		g.fillRect(15, 15, HEALTH * 2, 32);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(15, 15, HEALTH * 2, 32);
		g.setColor(Color.GREEN);
		g.setFont(new Font("faf", 10, 10));
		g.drawString("Punkte: " + score, 15, 64);
		g.drawString("Level: " + level, 15, 80);
		g.drawString("Zeit: " + String.valueOf((System.currentTimeMillis() / 1000 - Game.start - Game.newGametime)), 15,
				80 + 80 - 44);
		g.drawString("Rekord: " + HighScore, 15, 80 * 2 - 64);
		g.setFont(new Font("ARIAL BLACK", 10, 15));
		g.setColor(Color.GREEN);
		if (Player.bulletRemain <= 5)
			g.setColor(Color.RED);
		if (Player.reloading) {
			if (blink <= 40) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.RED);
			}
			if (blink <= 0) {
				blink = 80;
			} else {
				blink--;
			}
		}
		g.drawString("Schüsse: " + Player.bulletRemain + "/" + Player.magazinSize, 20, Game.HEIGHT - 50);
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getscore() {
		return score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}	
	
	public static int getHEALTH() {
		return HEALTH;
	}

	public static void setHEALTH(int hEALTH) {
		HEALTH = hEALTH;
	}

	public int getLastTickHealth() {
		return lastTickHealth;
	}

	public void setLastTickHealth(int lastTickHealth) {
		this.lastTickHealth = lastTickHealth;
	}

	public int getHighScore() {
		return HighScore;
	}

	public void setHighScore(int highScore) {
		HighScore = highScore;
	}

	public int getNewLife() {
		return newLife;
	}

	public void setNewLife(int newLife) {
		this.newLife = newLife;
	}

	public int getBlink() {
		return blink;
	}

	public void setBlink(int blink) {
		this.blink = blink;
	}

	public int getScore() {
		return score;
	}
}