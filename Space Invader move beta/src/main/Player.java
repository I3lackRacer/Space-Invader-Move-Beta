package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.ID;

public class Player extends GameObject {

	public float höhe;
	public float breite;
	private HUD hud;
	Handler handler;
	private static int redHit = 0;
	private NormalerGegner n = null;
	public static boolean loading = false;
	public static boolean reloading = false;
	public boolean Charged = false;
	public static int REDSTRENGH = 10, REDTIME = 5;
	private int reloadtime = -1;
	public static int magazinSize = 24;
	public static int bulletRemain = magazinSize;
	public int timer = 3;

	public Player(int x, int y, ID id, Handler handler, HUD hud) {

		super(x, y, id);
		this.handler = handler;
		this.setHud(hud);

	}

	public void tick() {
		float speed = 1F;
		if (KeyInput.keyDown[0]) {
			velY -= speed;
		}
		if (KeyInput.keyDown[1]) {
			velX -= speed;
		}
		if (KeyInput.keyDown[2]) {
			velY += speed;
		}
		if (KeyInput.keyDown[3]) {
			velX += speed;
		}
		if (velX != 0) {
			if (velX > 0) {
				setVelX(Game.clamp(getVelX() / 1.05F, 0, 255));
			} else {
				setVelX(Game.clamp(getVelX() / 1.05F, -255, 0));
			}
//			if(velX < 0.02F && velX > 0 || velX > -0.02F && velX > 0) setVelX(0);
		}
		if (velY != 0) {
			if (velY > 0) {
				setVelY(Game.clamp(getVelY() / 1.05F, 0, 255));
			} else {
				setVelY(Game.clamp(getVelY() / 1.05F, -255, 0));
			}
//			if(velY < 0.02F && velY > 0 || velY > -0.02F && velY > 0) setVelY(0);
		}

		if (redHit > 0) {
			redHit -= REDTIME;
		}
		if (timer > 0)
			timer--;
		else {
			timer = 1;
			handler.addHitlessobjekt(new Rocketdust((int) (this.getX() + getBounds().getWidth() / 2),
					(int) (this.getY() + getBounds().getHeight()), ID.Rocketdust, handler, hud));
		}

		if (!loading) {
			x += velX;
			y += velY;
		}
		if (reloading == true && reloadtime == -1) {
			reloadtime = 100;
		}
		if (reloadtime >= 1) {
			reloadtime--;
		}
		if (reloadtime == 0) {
			reloadtime = -1;
			reloading = false;
			bulletRemain = magazinSize;
		}
		x = Game.clamp(x, 0, Game.WIDTH - 37);
		y = Game.clamp(y, 0, Game.HEIGHT - 60);

		breite = y;
		höhe = x;

		if (KeyInput.schussLänge > 3) {
			Charged = true;
		}
		if (KeyInput.schussLänge < 3) {
			Charged = false;
		}

		collision();
	}

	public void render(Graphics g) {

		if (Charged == false) {
			g.drawImage(ImageHolder.Player, (int) x, (int) y, null);
		} else {
			g.drawImage(ImageHolder.PlayerForced, (int) x, (int) y, null);
		}
		if (getRedHit() > 0) {
			Color c = new Color(255, 0, 0, getRedHit());
			g.setColor(c);
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		}

		g.drawString("velX: " + velX + " || velY" + velY, Game.WIDTH / 2, Game.HEIGHT / 2);

		if (Game.debug == true) {
			g.setColor(Color.GREEN);
			g.drawRect((int) x, (int) y, 32, 32);
			if (reloading)
				g.setColor(new Color(255, 0, 0, 50));
			else
				g.setColor(new Color(0, 255, 0, 50));
			g.drawRect((int) (x + getBounds().getWidth() / 2), (int) y - 2000, 1, 2000);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

	private void collision() {
		for (int i = 0; i < handler.objects.size(); i++) {

			GameObject tempobjekt = handler.objects.get(i);

			if (tempobjekt.getId() == ID.NormalerGegner) {
				if (new Rectangle((int) (x + getBounds().getWidth() / 2), (int) y - 2000, 1, 2000)
						.intersects(tempobjekt.getBounds())) {
					NormalerGegner nb = (NormalerGegner) tempobjekt;
					nb.beeingTraged = true;
				} else {
					NormalerGegner nb = (NormalerGegner) tempobjekt;
					nb.beeingTraged = false;
				}
				if (getBounds().intersects(tempobjekt.getBounds())) {
					HUD.HEALTH = HUD.HEALTH - 35;
					tempobjekt.removeThis();
					setRedHit(REDSTRENGH);

					handler.removeObjekt(tempobjekt);

					setN((NormalerGegner) tempobjekt);

				}
			}

			if (tempobjekt.getId() == ID.AimGegner) {
				if (new Rectangle((int) (x + getBounds().getWidth() / 2), (int) y - 2000, 1, 2000)
						.intersects(tempobjekt.getBounds())) {
					if (tempobjekt instanceof AimGegner) {
						AimGegner nb = (AimGegner) tempobjekt;
						nb.beeingTraged = true;
					}
				} else {
					if (tempobjekt instanceof AimGegner) {
						AimGegner nb = (AimGegner) tempobjekt;
						nb.beeingTraged = false;
					}
				}
				if (getBounds().intersects(tempobjekt.getBounds())) {
					HUD.HEALTH = HUD.HEALTH - 35;
					tempobjekt.removeThis();
					setRedHit(REDSTRENGH);
					handler.removeObjekt(tempobjekt);
				}
			}

			if (tempobjekt.getId() == ID.SmartGegner) {
				if (new Rectangle((int) (x + getBounds().getWidth() / 2), (int) y - 2000, 1, 2000)
						.intersects(tempobjekt.getBounds())) {
					if (tempobjekt instanceof SmartGegner) {
						SmartGegner nb = (SmartGegner) tempobjekt;
						nb.beeingTraged = true;
					}
				} else {
					if (tempobjekt instanceof SmartGegner) {
						SmartGegner nb = (SmartGegner) tempobjekt;
						nb.beeingTraged = false;
					}
				}
				if (getBounds().intersects(tempobjekt.getBounds())) {
					HUD.HEALTH = HUD.HEALTH - 35;
					tempobjekt.removeThis();
					setRedHit(REDSTRENGH);
					handler.removeObjekt(tempobjekt);
				}
			}

			if (tempobjekt.id == ID.SpeedGegner) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					HUD.HEALTH -= 50;
					tempobjekt.removeThis();
					setRedHit(REDSTRENGH);
					handler.removeObjekt(tempobjekt);
				}
			}

			if (tempobjekt.id == ID.GegnerSchuss) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					HUD.HEALTH = HUD.HEALTH - 10;
					handler.removeObjekt(tempobjekt);
					setRedHit(REDSTRENGH);
				}
			}

			if (tempobjekt.getId() == ID.Asteroid) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					Asteroid a = (Asteroid) tempobjekt;
					a.killThis();
					HUD.HEALTH -= 50;
					setRedHit(REDSTRENGH);
				}
			}
		}
	}

	public static int getRedHit() {
		return redHit;
	}

	public static void setRedHit(int redHit) {
		Player.redHit = redHit;
	}

	public void removeThis() {
		handler.removeObjekt(this);
	}

	public HUD getHud() {
		return hud;
	}

	public void setHud(HUD hud) {
		this.hud = hud;
	}

	public NormalerGegner getN() {
		return n;
	}

	public void setN(NormalerGegner n) {
		this.n = n;
	}
}
