package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import main.ID;

public class Rocketdust extends GameObject{
	
	public HUD hud;
	public Handler handler;
	public Random r = new Random();
	public Color c = new Color(255, 255, 255, 255);
	public int size = r.nextInt(10)+6, timer = 5;

	public Rocketdust(float x, float y, ID id, Handler handler,HUD hud) {
		super(x, y, id);
		this.handler = handler;
		this.hud = hud;
		velX = r.nextFloat()-0.5F;
		velY = r.nextFloat()+0.2F;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		if(timer > 0) timer--;
		else{
			timer = 5;
			if(size > 0) {
				size--;
				c = new Color(255, 255, 255, c.getAlpha()-1);
			} else removeThis();
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect((int)(x-getBounds().getWidth()/2), (int)(y-getBounds().getHeight()/2), (int)getBounds().getWidth(), (int)getBounds().getHeight());
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, size, size);
	}

	@Override
	public void removeThis() {
		handler.removeHitlessobjekt(this);
	}
	
	

}
