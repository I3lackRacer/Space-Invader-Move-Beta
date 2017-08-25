package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.ID;

public class ExplosionPartikel extends GameObject {
	
	private Handler handler;
	private HUD hud;
	private int alpha = 255;
	private String Richtung = "";

	public ExplosionPartikel(int x, int y, ID id, Handler handler, HUD hud, String Richtung) {
		super(x, y, id);
		
		this.Richtung = Richtung;
		this.setHud(hud);
		this.handler = handler;
		
		debugInfo.lastTrigged = "SPAWN_Explosion_Partikel";
	}

	public void tick() {
		
		if(y >= Game.HEIGHT-32) handler.removeObjekt(this);;
		
		if(alpha <= 20) {
			removeThis();
		}else{
			alpha -= 10;
		}
		
		if(Richtung == "ol") {
			x -= 1;
			y -= 1;
		}
		if(Richtung == "or") {
			y -= 1;
			x += 1;
		}
		if(Richtung == "ur") {
			y += 1;
			x += 1;
		}
		if(Richtung == "ul") {
			y += 1;
			x -= 1;
		}
	}


	@Override
	public void render(Graphics g) {
		if(Richtung == "ol") {
			g.drawImage(ImageHolder.NGegnerol, (int)x, (int)y, new Color(0, 0, 0, alpha), null);
		}
		if(Richtung == "or") {
			g.drawImage(ImageHolder.NGegneror, (int)x, (int)y, new Color(0, 0, 0, alpha), null);
		}
		if(Richtung == "ur") {
			g.drawImage(ImageHolder.NGegnerul, (int)x, (int)y, new Color(0, 0, 0, alpha), null);
		}
		if(Richtung == "ul") {
			g.drawImage(ImageHolder.NGegnerur, (int)x, (int)y, new Color(0, 0, 0, alpha), null);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

	@Override
	public void removeThis() {
		handler.removeHitlessobjekt(this);		
	}

	public HUD getHud() {
		return hud;
	}

	public void setHud(HUD hud) {
		this.hud = hud;
	}

}
