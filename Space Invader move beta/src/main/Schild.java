package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.ID;

public class Schild extends GameObject {

	private Handler handler;
	private int timer = 5;
	private int color = 255;
	private HUD hud;
	public static boolean �berlasteterSchild = false;

	public Schild(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);

		this.handler = handler;
		this.hud = hud;

		debugInfo.lastTrigged = "SPAWN_SCHILD";

		this.x = handler.objects.getFirst().x - 16;
		this.y = handler.objects.getFirst().y - 30;
		
		if (�berlasteterSchild) {
			this.removeThis();
		}
	}

	public void tick() {
		if (�berlasteterSchild) {
			this.removeThis();
		}
		if (timer > 1) {
			timer--;
		} else {
			timer = 1;
			if (color > 0) {
				color--;
			}

			if (color <= 1) {
				�berlasteterSchild = true;
				Player.setRedHit(250);
				HUD.HEALTH = HUD.HEALTH - 10;
				handler.removeObjekt(this);
				color = 255;
			}

		}

		x = handler.objects.getFirst().x - 16;
		y = handler.objects.getFirst().y - 30;

		collision();
	}

	private void collision() {
		for (int i = 0; i < handler.objects.size(); i++) {

			GameObject tempobjekt = handler.objects.get(i);

			if (tempobjekt.id == ID.Bullet || tempobjekt.id == ID.GegnerSchuss) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					tempobjekt.removeThis();
					if (tempobjekt.id == ID.NormalerGegner) {
						hud.score++;
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if (!�berlasteterSchild) {
			g.setColor(new Color(255, 0, 0, color));
			g.fillRect((int) x, (int) y, 64, 3);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 64, 20);
	}

	@Override
	public void removeThis() {
		handler.removeObjekt(this);
	}

}
