package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class NormalerGegner extends GameObject {

	private Handler handler;
	private HUD hud;
	public LifeBarGegner LiveBar = null;
	public int schusswarscheinlichkeit = 9;
	public boolean beeingTraged = false;

	public NormalerGegner(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);

		this.hud = hud;
		this.handler = handler;

		debugInfo.lastTrigged = "SPAWN_NORMAL";

		velX = 0;
		velY = 1;

		LiveBar = new LifeBarGegner(this, handler, hud, 3);
		handler.addHitlessobjekt(LiveBar);
	}

	public void tick() {
		x += velX;
		y += velY;

		if (y >= Game.HEIGHT + 10)
			this.removeThis();
		if (x >= Game.WIDTH)
			this.removeThis();

		if (!Game.editmode && new Random().nextInt(1000) < schusswarscheinlichkeit) {
			debugInfo.lastTrigged = "SPAWN_GEGNER SCHUSS";
			handler.addObjekt(new BulletGegner((int) this.x + (int) this.getBounds().getWidth() / 2,
					(int) (this.y + this.getBounds().getHeight()), ID.BulletGegner, handler, this, hud));
		}
		if (!handler.hitlessobjects.contains(LiveBar)) {
			handler.addHitlessobjekt(LiveBar);
		}
		collusion();
	}

	private void collusion() {
		for (int i = 0; i < handler.objects.size(); i++) {

			GameObject tempobjekt = handler.objects.get(i);

			if (tempobjekt.id == ID.Schild) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					this.killThis();
					if(tempobjekt instanceof Schild) {
						Schild.überlasteterSchild = true;
					}
					hud.score++;
				}
			}
			
			if(tempobjekt.id == ID.NormalerGegner) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					if(tempobjekt != this) {
						if(tempobjekt instanceof NormalerGegner) {
							NormalerGegner n = (NormalerGegner)tempobjekt;
							n.killThis();
						}
					}
				}
			}
			
			if(tempobjekt.id == ID.AimGegner) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					if(tempobjekt != this) {
						if(tempobjekt instanceof AimGegner) {
							AimGegner a = (AimGegner) tempobjekt;
							a.LiveBar.live--;
							this.killThis();
						}
					}
				}
			}
			
			if(tempobjekt.id == ID.Asteroid) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					if(tempobjekt instanceof Asteroid) {
						Asteroid a = (Asteroid)tempobjekt;
						a.lifeBarGegner.live--;
						a.Live--;
					}
					this.killThis();
				}
			}
			
			if (tempobjekt.id == ID.Bullet) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live--;
					Bullet b = (Bullet) tempobjekt;
					if (b.isCharged() == false) {
						b.removeThis();
					}
				}
			}
			if (tempobjekt.id == ID.GegnerSchuss) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					tempobjekt.removeThis();
				}
			}
			if (tempobjekt.id == ID.BulletSpray) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live--;
					BulletSpray b = (BulletSpray) tempobjekt;
					b.removeThis();
				}
			}
			if (tempobjekt.id == ID.BulletForced) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live -= 3;
					BulletForced b = (BulletForced) tempobjekt;
					if (b.isCharged() == false) {
						b.removeThis();
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {

		if (Game.debug == true) {
			g.setColor(Color.RED);
			g.drawRect((int) x, (int) y, 32, 32);
		}
		if (beeingTraged == true) {
			g.setColor(Color.RED);
			g.drawRect((int) x - 3, (int) y - 3, (int) getBounds().getWidth() + 6, (int) getBounds().getHeight() + 6);
		}
		g.drawImage(ImageHolder.NGegner, (int) x, (int) y, 32, 32, null);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

	@Override
	public void removeThis() {
		LiveBar.removeThis();
		handler.removeObjekt(this);
	}

	public void killThis() {
		handler.addHitlessobjekt(new ExplosionPartikel((int) x, (int) y, ID.Partikel, handler, hud, "ol"));
		handler.addHitlessobjekt(new ExplosionPartikel((int) x + (int) getBounds().getWidth() / 2, (int) y, ID.Partikel,handler, hud, "or"));
		handler.addHitlessobjekt(new ExplosionPartikel((int) x + (int) getBounds().getWidth() / 2,(int) y + (int) getBounds().getHeight(), ID.Partikel, handler, hud, "ur"));
		handler.addHitlessobjekt(new ExplosionPartikel((int) x, (int) y + (int) getBounds().getHeight() / 2,ID.Partikel, handler, hud, "ul"));
		LiveBar.removeThis();
		handler.removeObjekt(this);
	}

}
