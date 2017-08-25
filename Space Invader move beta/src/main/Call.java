package main;

public class Call {
	
	public HUD hud ;
	public Handler handler;
	
	public Call(HUD hud, Handler handler) {
		this.hud = hud;
		this.handler = handler;
	}
	
	public GameObject getAimSpammer(int x, int y){
		AimGegner a = new AimGegner(x, y, ID.AimGegner, handler, hud);
		a.schusswarscheinlichkeit = 1000;
		return a;
	}
	
	public GameObject getSpammer(int x, int y){
		NormalerGegner a = new NormalerGegner(x, y, ID.NormalerGegner, handler, hud);
		a.schusswarscheinlichkeit = 1000;
		return a;
	}
	
	public GameObject getOutOfAmmo(int x, int y) {
		NormalerGegner a = new NormalerGegner(x, y, ID.NormalerGegner, handler, hud);
		a.setVelX(0);
		a.setVelY(0);
		a.schusswarscheinlichkeit = 0;
		return a;
	}
	
	public GameObject getDummy(int x, int y) {
		NormalerGegner a = new NormalerGegner(x, y, ID.NormalerGegner, handler, hud);
		a.velX = 0;
		a.velY = 0;
		return a;
	}

}
