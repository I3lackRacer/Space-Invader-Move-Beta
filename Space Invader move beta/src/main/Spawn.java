package main;

import java.util.Random;

public class Spawn {

	public Handler handler;
	public HUD hud;
	public int lastXPos = 0;
	public Random r = new Random();
	public int eckenkriecher = 0;

	public Spawn(Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
	}

	public void tick() {
		// NormalerGegner
		if (Game.server == null) {
			if (r.nextInt(1000) <= 9 + (hud.getLevel() * 5)) {
				handler.addObjekt(new NormalerGegner(r.nextInt(Game.WIDTH - 32), -64, ID.NormalerGegner, handler, hud));
			}
			// Asteroid
			if (r.nextInt(1000) <= 1) {
				handler.addObjekt(new Asteroid(r.nextInt(Game.WIDTH - 32), -64, ID.Asteroid, handler, hud));
			}
			// AimGegner
			if (r.nextInt(1000) <= 1) {
				handler.addObjekt(new AimGegner(r.nextInt(Game.WIDTH - 32), -64, ID.AimGegner, handler, hud));
			}

			// if (handler.getDirect(ID.Player).getX() > Game.WIDTH - 20)
			// eckenkriecher++;
			// if (eckenkriecher >= 500 && Game.levelhandler == null) {
			// Game.levelhandler = new LevelHandler("rtrap.txt", hud, handler);
			// eckenkriecher = 0;
			// }
			if (lastXPos == handler.getDirect(ID.Player).getX()) {
				eckenkriecher++;
				if (eckenkriecher > 500) {
					handler.addObjekt(Game.call.getSpammer(lastXPos, 0));
					eckenkriecher = 0;
				}
			} else
				eckenkriecher = 0;
		}

		// Backgroundstar
		handler.addHitlessobjekt(new BackgroundStars(r.nextInt(Game.WIDTH - 2) + 1, -64, ID.Back, handler, hud));
		lastXPos = (int) handler.getDirect(ID.Player).getX();
	}
}