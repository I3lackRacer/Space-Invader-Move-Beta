package main;


public class BulletCirle {

	public BulletCirle(float x, float y,Handler handler, HUD hud) {
		BulletGegner bg = new BulletGegner((int)x, (int)y, ID.GegnerSchuss, handler, null, hud);
		
		bg.setVelX(1);
		bg.setVelY(0);
		handler.addObjekt(bg);
		bg.setVelX(1);
		bg.setVelY(1);
		handler.addObjekt(bg);
		bg.setVelX(0);
		bg.setVelY(0);
		handler.addObjekt(bg);
		bg.setVelX(0);
		bg.setVelY(1);
		handler.addObjekt(bg);
	}
}
