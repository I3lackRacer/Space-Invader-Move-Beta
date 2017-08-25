package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import main.ID;

public class BackgroundStars extends GameObject {
	
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	private int größe= 1;
	public BackgroundStars(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);
		
		this.setHud(hud);
		this.handler = handler;
		
		velX = 0;
		velY = 19;
		
		größe = r.nextInt(5);
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		if(y >= Game.HEIGHT-32) {
			handler.hitlessobjects.remove(this);
		}
		if(x <= 0 || x >= Game.WIDTH-32) {
			handler.hitlessobjects.remove(this);
		}
		
	}

	@Override
	public void render(Graphics g) {
		
		g.setColor(new Color(255, 255, 255, 100));
		g.fillRect((int)x, (int)y, größe, größe);
		
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 1, 1);
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
