package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.ID;

public class LifeBarGegner extends GameObject {

	private Handler handler;
	private GameObject owner;
	private float fixX;
	private float fixY;
	private Rectangle rec = new Rectangle((int) x, (int) y, 100, 3);
	public int live = 3;
	private HUD hud;

	public LifeBarGegner(GameObject owner, Handler handler, HUD hud, int Leben) {
		super(owner.x / 2, owner.y - 10, ID.Back);
		
		this.live = Leben;
		this.handler = handler;
		this.owner = owner;
		this.hud = hud;
		fixX = 0;
		fixY = -10;
		if (owner.id == ID.NormalerGegner) {
			live = 3;
		}
		if (owner.id == ID.SmartGegner) {
			live = 3;
		}
	}

	@Override
	public void tick() {
		x = owner.getX() + fixX;
		y = owner.getY() + fixY;
		if (!handler.objects.contains(owner)) {
			removeThis();
		}
	}

	@Override
	public void render(Graphics g) {
		int fix = 9;
		g.setColor(Color.RED);
		if (owner instanceof NormalerGegner || owner instanceof AimGegner || owner instanceof SmartGegner) {
			if (live == 3) {
				g.fillRect((int) x - fix, (int) y, 50 / 3 * 3, 3);
			}
			if (live == 2) {
				g.fillRect((int) x - fix, (int) y, 50 / 3 * 2, 3);
			}
			if (live == 1) {
				g.fillRect((int) x - fix, (int) y, 50 / 3 * 1, 3);
			}
			if (live <= 0) {
				hud.score++;
				if (owner instanceof NormalerGegner) {
					((NormalerGegner) owner).killThis();
				} else {
					owner.removeThis();
				}
				removeThis();
			}
		}
		if (owner instanceof Asteroid) {
			if (live == 5) {
				g.fillRect((int) x - fix, (int) y, 50 / 5 * 5, 3);
			}
			if (live == 4) {
				g.fillRect((int) x - fix, (int) y, 50 / 5 * 4, 3);
			}
			if (live == 3) {
				g.fillRect((int) x - fix, (int) y, 50 / 5 * 3, 3);
			}
			if (live == 2) {
				g.fillRect((int) x - fix, (int) y, 50 / 5 * 2, 3);
			}
			if (live == 1) {
				g.fillRect((int) x - fix, (int) y, 50 / 5 * 1, 3);
			}
			if (live <= 0) {
				hud.score++;
				if (owner instanceof Asteroid) {
					((Asteroid) owner).killThis();
				} else {
					owner.removeThis();
				}
				removeThis();
			}
		}
		g.setColor(Color.RED);

		g.drawRect((int) x - fix, (int) y, 48, 3);
	}

	@Override
	public Rectangle getBounds() {
		return rec;
	}

	@Override
	public void removeThis() {
		handler.removeHitlessobjekt(this);
	}
}