package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.ID;

public class SpeedGegner extends GameObject {
	
	private Handler handler;
	private HUD hud;
	private LifeBarGegner LiveBar;

	public SpeedGegner(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);
		
		this.hud = hud;
		this.handler = handler;
		
		debugInfo.lastTrigged = "SPAWN_SPEED";
		
		velX = 0;

		LiveBar = new LifeBarGegner(this, handler, hud, 3);
		handler.addObjekt(LiveBar);
		
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(y >= Game.HEIGHT-32 || y <= 0)  velY *=-1;
		if(x <= 0 || x >= Game.WIDTH-32) velX *=-1;
		collusion();
	}

	private void collusion() {
		for(int i = 0; i<handler.objects.size(); i++) {
			
			GameObject tempobjekt = handler.objects.get(i);
			
			if(tempobjekt.id == ID.Schild) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					killThis();
					hud.score++;
				}
			}
			if(tempobjekt.id == ID.Bullet) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live--;
					Bullet b = (Bullet) tempobjekt;
					if(b.isCharged() == false) {
						b.removeThis();
					}
				}
			}
			if(tempobjekt.id == ID.BulletSpray) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live--;
					BulletSpray b = (BulletSpray) tempobjekt;
					b.removeThis();
				}
			}
			if(tempobjekt.id == ID.BulletForced) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live -= 3;
					BulletForced b = (BulletForced) tempobjekt;
					if(b.isCharged() == false) {
						b.removeThis();
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		
		if(Game.debug == true) {
			g.setColor(Color.RED);
			g.drawRect((int)x, (int)y, 32, 32);
		}
		
		g.drawImage(ImageHolder.SGegner, (int)x, (int)y, 32, 32, null);
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, 32, 32);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}

	@Override
	public void removeThis() {
		LiveBar.removeThis();
		handler.removeObjekt(this);
	}
	
	public void killThis() {
		handler.addObjekt(new ExplosionPartikel((int)x, (int)y, ID.Partikel, handler, hud, "ol"));
		handler.addObjekt(new ExplosionPartikel((int)x+(int)getBounds().getWidth()/2, (int)y, ID.Partikel, handler, hud, "or"));
		handler.addObjekt(new ExplosionPartikel((int)x+(int)getBounds().getWidth()/2, (int)y+(int)getBounds().getHeight(), ID.Partikel, handler, hud, "ur"));
		handler.addObjekt(new ExplosionPartikel((int)x, (int)y+(int)getBounds().getHeight()/2, ID.Partikel, handler, hud, "ul"));
		LiveBar.removeThis();
		handler.removeObjekt(this);
	}

}
