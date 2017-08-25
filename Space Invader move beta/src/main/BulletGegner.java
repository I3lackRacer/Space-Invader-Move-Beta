package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.ID;

public class BulletGegner extends GameObject {

	private Handler handler;
	private HUD hud;

	public BulletGegner(int x, int y, ID id, Handler handler, NormalerGegner spawner, HUD hud) {
		super(x, y, id);
		this.handler = handler;
		this.setHud(hud);
		if (Game.server != null) {
			

			velX = 0;
			velY = 9;

			if (id == ID.AimBullet) {
				float playerX = handler.getDirect(ID.Player).getX() + 16;
				float playerY = handler.getDirect(ID.Player).getY() + 16;

				float sup = (float) Math.sqrt(((playerX - x) * (playerX - x)) + ((playerY - y) * (playerY - y)));
				float speed = 8F;
				velX = ((playerX - x) / sup) * speed;
				velY = ((playerY - y) / sup) * speed;
			}
		} else {
			handler.removeObjekt(this);
		}
	}

	public void tick() {
		if(Game.server != null) return;
		x += velX;
		y += velY;

		if (y >= Game.HEIGHT)
			handler.removeObjekt(this);
		if (x <= 0 || x >= Game.WIDTH - 16)
			handler.removeObjekt(this);
		collision();
	}

	@Override
	public void render(Graphics g) {
		if(Game.server != null) return;
		g.setColor(Color.RED);
		g.fillRect((int) x, (int) y, 4, 4);
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 4, 4);
	}

	private void collision() {
		for (int i = 0; i < handler.objects.size(); i++) {

			GameObject tempobjekt = handler.objects.get(i);
			if (tempobjekt.id == ID.Player) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					HUD.HEALTH = HUD.HEALTH - 11;
					handler.removeObjekt(this);
					Player.setRedHit(255);
				}
			}

			if (tempobjekt.id == ID.Schild) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					handler.removeObjekt(this);
				}
			}

			if (tempobjekt.id == ID.AimGegner) {
				removeThis();
			}
			if (tempobjekt.id == ID.NormalerGegner) {
				removeThis();
			}
		}
	}

	@Override
	public void removeThis() {
		handler.removeObjekt(this);
	}

	public HUD getHud() {
		return hud;
	}

	public void setHud(HUD hud) {
		this.hud = hud;
	}

}
