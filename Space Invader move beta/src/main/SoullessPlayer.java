package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.ID;

public class SoullessPlayer extends GameObject {

	public float höhe;
	public float breite;
	public int nr;
	public HUD hud;
	public Handler handler;

	public SoullessPlayer(int x, int y, ID id, Handler handler, HUD hud, int PlayerNummer) {
		super(x, y, id);
		this.nr = PlayerNummer;
		this.handler = handler;
		this.setHud(hud);
	}

	public void tick() {
		breite = y;
		höhe = x;
	}

	public void render(Graphics g) {
			g.drawImage(ImageHolder.Player, (int) this.x, (int) this.y, null);

		if (Game.debug == true) {
			g.setColor(Color.GREEN.darker());
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

	public HUD getHud() {
		return hud;
	}

	public void setHud(HUD hud) {
		this.hud = hud;
	}
}
