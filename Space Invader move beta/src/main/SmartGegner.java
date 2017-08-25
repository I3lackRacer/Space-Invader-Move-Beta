package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import main.ID;

public class SmartGegner extends GameObject {

	private Handler handler;
	private HUD hud;
	public int schusswarscheinlichkeit = 3, timer = 10, backtimer = 0, cooldown = 0;
	public LifeBarGegner LiveBar;
	public boolean beeingTraged;
	private boolean alarm = false, left = true;
	private Random r = new Random();

	public SmartGegner(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);

		this.hud = hud;
		this.handler = handler;

		debugInfo.lastTrigged = "SPAWN_SMART";

		velX = 0;
		velY = 2;

		LiveBar = new LifeBarGegner(this, handler, hud, 3);
		handler.addHitlessobjekt(LiveBar);
	}

	public void tick() {
		if(velX == 0 && velY == 0) {
			velY = 2;
		}
		if(y <= -64) {
			y = -32;
			setVelY(0);
			x = r.nextInt(Game.WIDTH-32);
		}
		if(x >= Game.WIDTH) {
			x = Game.WIDTH-32;
		}
		if(x < 0) {
			x = 0;
		}
		x += velX;
		y += velY;
		if(cooldown > 0) {
			cooldown--;
		}

		if(alarm) {
			if(left) {
				x += -12;
			}
			else {
				x += +12;
			}
			timer--;
			if(timer < 1) {
				alarm = false;
				timer = 10;
			}
		}
		// Wenn er am ende der Map angekommen ist...
		if (y >= Game.HEIGHT-32) {
			setVelY(0);
			setVelX(23);
		}
		
		// Wenn es gegen die rechte Seite stößt...
		if (x >= Game.WIDTH-32) {
			setVelX(0);
			setX(Game.WIDTH-32);
			//Wenn es unten rechts ist...
			if(y >= Game.HEIGHT-32) {
				setVelY(-23);
			}
		}
		// Wenn er oben rechts angekommen ist...
		if(y <= 0 && x >= Game.WIDTH-32) {
			backtimer = r.nextInt(35);
			y = 0;
			setVelY(0);
			setVelX(-23);
		}
		// Wenn die in Variable 'backtimer', welche die Variable ist die bestimmt wie weit der Smartgegner von oben-rechts nach links geht, größer als eins ist...
		if(backtimer > 1) backtimer--;
			
		if(backtimer == 1) {
			backtimer = 0;
			setVelX(0);
			setVelY(2);
		}

		if (!Game.editmode && new Random().nextInt(1000) <= schusswarscheinlichkeit) {
			debugInfo.lastTrigged = "SPAWN_GEGNER SCHUSS";
			handler.addObjekt(new BulletGegner((int) x + 16, (int) y + 32, ID.AimBullet, handler, null, hud));
		}
		collusion();
	}

	private void collusion() {
		for (int i = 0; i < handler.objects.size(); i++) {

			GameObject tempobjekt = handler.objects.get(i);
			
			//Wenn von unten eine Bullet oder ein Bulletspray kommt...
			if(tempobjekt.id == ID.Bullet && cooldown == 0|| tempobjekt.id == ID.BulletSpray && cooldown == 0) {
				if(tempobjekt.getBounds().intersects(new Rectangle((int)x, (int)y, (int)(getBounds().getWidth()), (int)(getBounds().getHeight()+50)))) {
					alarm  = true;
					left = r.nextBoolean();
					cooldown = 50;
				}
			}
			
			if(tempobjekt.id == ID.Player) {
				ausweichen(tempobjekt);
			}

			if (tempobjekt.id == ID.Schild) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					killThis();
					if(tempobjekt instanceof Schild) {
						Schild.überlasteterSchild = true;
					}
					hud.score++;
				}
				ausweichen(tempobjekt);
			}
			if (tempobjekt.id == ID.Bullet) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live--;
					Bullet b = (Bullet) tempobjekt;
					if (b.isCharged() == false) {
						b.removeThis();
					}
				}
			}
			
			if(tempobjekt.id == ID.SmartGegner) {
				ausweichen(tempobjekt);
			}
			
			if (tempobjekt.id == ID.GegnerSchuss) {
				ausweichen(tempobjekt);
				if (getBounds().intersects(tempobjekt.getBounds())) {
					tempobjekt.removeThis();
				}
			}
			if (tempobjekt.id == ID.BulletSpray) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live--;
					BulletSpray b = (BulletSpray) tempobjekt;
					b.removeThis();
				}
			}
			// Wenn der Smarte gegner in einen der drei Untenstehenden rasst während er am ende der map angekommen ist...
			if(tempobjekt.id == ID.NormalerGegner || tempobjekt.id == ID.Asteroid || tempobjekt.id == ID.AimGegner) {
				if(tempobjekt.getBounds().intersects(new Rectangle( (int)x, (int)y, 100, 32))) {
					if(velY >= 20) {
						setVelY(0);
						setVelX(2);
						setX((int)getX()-100);
					}
				}
			}
			
			if(tempobjekt.id == ID.NormalerGegner) {
				ausweichen(tempobjekt);
			}
			
			if(tempobjekt.id == ID.AimGegner) {
				ausweichen(tempobjekt);
			}
			
			if(tempobjekt.id == ID.Asteroid) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					if(tempobjekt instanceof Asteroid) {
						Asteroid a = (Asteroid)tempobjekt;
						a.lifeBarGegner.live--;
						a.Live--;
					}
					this.killThis();
				}
				ausweichen(tempobjekt);
			}
			
			if (tempobjekt.id == ID.BulletForced) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live -= 3;
					BulletForced b = (BulletForced) tempobjekt;
					if (b.isCharged() == false) {
						b.removeThis();
					}
				}
				ausweichen(tempobjekt);
			}
		}
	}
	
	public void ausweichen(GameObject tempobjekt) {
		//RIGHT
		if(tempobjekt.getBounds().intersects(new Rectangle((int)(x+getWidth()), (int)y, (int)getBounds().getWidth(),(int)getBounds().getHeight()))) {
			alarm = true;
			left = true;
			cooldown = 50;
		}
		//Left
		if(tempobjekt.getBounds().intersects(new Rectangle((int)(x-getWidth()), (int)y, (int)getBounds().getWidth(),(int)getBounds().getHeight()))) {
			alarm = true;
			left= false;
			cooldown = 50;
		}
		//Bottom
		if(tempobjekt.getBounds().intersects(new Rectangle((int)(x), (int)(y+getHeight()), (int)getBounds().getWidth(),(int)getBounds().getHeight()))) {
			alarm = true;
			left= r.nextBoolean();
			cooldown = 50;
			y -= 20;
		}
		//Top
		if(tempobjekt.getBounds().intersects(new Rectangle((int)(x), (int)(y-getHeight()), (int)getBounds().getWidth(),(int)getBounds().getHeight()))) {
			if(velY < 0) {
				alarm = true;
				left= true;
				cooldown = 50;
			}
			else {
				alarm = true;
				left= r.nextBoolean();
				y += 20;
			}
		}
	}

	@Override
	public void render(Graphics g) {

		if (Game.debug == true) {
			g.setColor(Color.RED);
			g.drawRect((int) x, (int) y, 32, 32);
		}
		g.drawImage(ImageHolder.SmartGegner, (int) x, (int) y, 32, 32, null);
		if (beeingTraged == true) {
			g.setColor(Color.RED);
			g.drawRect((int) x - 3, (int) y - 3, (int) getBounds().getWidth() + 6, (int) getBounds().getHeight() + 6);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

	@Override
	public void removeThis() {
		LiveBar.removeThis();
		handler.removeObjekt(this);
	}

	public void killThis() {
		handler.addHitlessobjekt(new ExplosionPartikel((int) x, (int) y, ID.Partikel, handler, hud, "ol"));
		handler.addHitlessobjekt(new ExplosionPartikel((int) x + (int) getBounds().getWidth() / 2, (int) y, ID.Partikel,handler, hud, "or"));
		handler.addHitlessobjekt(new ExplosionPartikel((int) x + (int) getBounds().getWidth() / 2,(int) y + (int) getBounds().getHeight(), ID.Partikel, handler, hud, "ur"));
		handler.addHitlessobjekt(new ExplosionPartikel((int) x, (int) y + (int) getBounds().getHeight() / 2,ID.Partikel, handler, hud, "ul"));
		LiveBar.removeThis();
		handler.removeObjekt(this);
	}
}