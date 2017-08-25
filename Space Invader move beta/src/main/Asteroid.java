package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.ID;

public class Asteroid extends GameObject {

	private Handler handler;
	private HUD hud;
	public int Live = 5;
	private boolean fromLeft = true;
	public LifeBarGegner lifeBarGegner;
	private int pos = -100, rot = 1;
	private float vel = 0.03F;

	public Asteroid(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);

		this.hud = hud;
		this.handler = handler;

		this.lifeBarGegner = new LifeBarGegner(this, handler, hud, 5);
		handler.addHitlessobjekt(lifeBarGegner);
		debugInfo.lastTrigged = "SPAWN_NORMAL";

		velX = -3F;
		velY = 0.5F;

		if (velX != 0) vel = (velX * -1) / 100;
	}

	public void tick() {
		if (!handler.hitlessobjects.contains(lifeBarGegner)) {
			handler.addHitlessobjekt(lifeBarGegner);
		}
		x += velX;
		y += velY;
		if (Live <= 0)
			this.killThis();
		if (y >= Game.HEIGHT +10)
			this.removeThis();

		if (fromLeft == true && pos >= 100) {
			fromLeft = false;
		}
		if (pos <= -100 && fromLeft == false) {
			fromLeft = true;
		}
		if (fromLeft == false) {
			pos--;
		} else {
			pos++;
		}
		if (fromLeft == true) {
			velX += vel;
		} else {
			velX -= vel;
		}
		collusion();
	}

	private void collusion() {
		for (int i = 0; i < handler.objects.size(); i++) {

			GameObject tempobjekt = handler.objects.get(i);

			if (tempobjekt.id == ID.Schild) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					killThis();
					if(tempobjekt instanceof Schild) {
						Schild.überlasteterSchild = true;
					}
					hud.score++;
				}
			}
			if (tempobjekt.id == ID.Bullet) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					Bullet b = (Bullet) tempobjekt;
					lifeBarGegner.live--;
					if (b.isCharged() == false) {
						b.removeThis();
						Live--;
					}
				}
			}
			if (tempobjekt.id == ID.BulletSpray) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					BulletSpray b = (BulletSpray) tempobjekt;
					lifeBarGegner.live--;
					b.removeThis();
					Live--;
				}
			}
			if (tempobjekt.id == ID.BulletForced) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					BulletForced b = (BulletForced) tempobjekt;
					lifeBarGegner.live -= 2;
					if (b.isCharged() == false) {
						b.removeThis();
						Live -= 2;
					}
				}
			}
			if(tempobjekt.id == ID.Asteroid && tempobjekt != this) {
				if(tempobjekt.getBounds().intersects(this.getBounds())) {
					this.Live--;
					this.lifeBarGegner.live--;
				}
			}
			if (tempobjekt.id == ID.GegnerSchuss) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					lifeBarGegner.live--;
					BulletGegner b = (BulletGegner) tempobjekt;
					b.removeThis();
					Live--;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		
		g.setColor(Color.ORANGE);
//		g.fillOval((int)x, (int)y, (int)getBounds().getWidth(), (int)getBounds().getHeight());
//		g.drawImage(new AffineTransformOp(AffineTransform.getRotateInstance(rot), AffineTransformOp.TYPE_BILINEAR).filter(ImageHolder.Asteroid, null), (int)x, (int)y, null);
		g.drawImage(ImageHolder.Asteroid, (int)x, (int)y, null);
		if (Game.debug == true) {
			g.setColor(Color.RED);
			g.drawRect((int) x, (int) y, 32, 32);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

	@Override
	public void removeThis() {
		handler.removeObjekt(this);
	}

	public void killThis() {
		handler.removeObjekt(this);
		for (int repeat = 0; repeat < 40; repeat++) {
			handler.addObjekt(new BulletSpray((int) this.x + (int) this.getBounds().getWidth() / 2, (int) this.y + (int) this.getBounds().getHeight() / 2, ID.BulletSpray, handler, hud));
		}
		hud.score += 2;
	}

	public int getLive() {
		return Live;
	}

	public void setLive(int live) {
		Live = live;
	}

	public int getRot() {
		return rot;
	}

	public void setRot(int rot) {
		this.rot = rot;
	}

}
