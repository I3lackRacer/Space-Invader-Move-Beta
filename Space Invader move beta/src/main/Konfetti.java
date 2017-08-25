package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import main.ID;

public class Konfetti extends GameObject {

	private int size = 100;
	private Color c = Color.RED;
	private Handler handler;
	private float startX;
	private float startY;

	public Konfetti(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.setStartX(x);
		this.setStartY(y);
		// size = new Random().nextInt(3)+1;
		this.handler = handler;
		ArrayList<Color> cl = new ArrayList<Color>();
		cl.add(Color.RED);
		cl.add(Color.BLUE);
		cl.add(Color.GREEN);
		cl.add(Color.YELLOW);
		cl.add(Color.MAGENTA);
		cl.add(Color.ORANGE);
		c = cl.get(new Random().nextInt(6));
		velX = 0.1F;
	}

	@Override
	public void tick() {
//		if (y < 0 || y > Game.HEIGHT)
//			removeThis();
//		if (x < 0 || x > Game.WIDTH)
//			removeThis();
		float a = 66F, b = 10000, c = 1111, kx = x -188;

		y = (a * (kx * kx) + b * kx + c) / 5700;
		x += velX;
		System.out.println("(" + x + " / " + y + ")");
	}

	@Override
	public void render(Graphics g) {
		g.setColor(c);
		g.fillRect((int) x, (int) y, size, size);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) getX(), (int) getY(), size, size);
	}

	@Override
	public void removeThis() {
		handler.removeHitlessobjekt(this);
	}

	public float getStartX() {
		return startX;
	}

	public void setStartX(float startX) {
		this.startX = startX;
	}

	public float getStartY() {
		return startY;
	}

	public void setStartY(float startY) {
		this.startY = startY;
	}

}