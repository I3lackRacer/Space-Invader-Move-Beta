package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.ID;

public class Bullet extends GameObject {
	
	private Handler handler;
	private HUD hud;
	public boolean charged;

	public boolean isCharged() {
		return charged;
	}

	public void setCharged(boolean charged) {
		this.charged = charged;
	}

	public Bullet(int x, int y, ID id, Handler handler, HUD hud, boolean charged) {
		super(x, y, id);
		
		this.handler = handler;
		this.setHud(hud);
		this.charged = charged;
		
		velX = 0;
		velY = -9;
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(y <= 0)handler.removeObjekt(this);;
		if(x <= 0 || x >= Game.WIDTH-16) handler.removeObjekt(this);
		collision();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)x, (int)y, 4, 4);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 4, 4);
	}
	
	private void collision() {
		for(int i = 0; i < handler.objects.size(); i++) {
			
			GameObject tempobjekt = handler.objects.get(i);
			
			if(tempobjekt.id == ID.GegnerSchuss) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					handler.removeObjekt(tempobjekt);
				}
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
