package main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHolder {
	
	public static BufferedImage Player;
	public static BufferedImage NGegner;
	public static BufferedImage PlayerForced;
	public static BufferedImage Herz;
	public static BufferedImage NGegnerol;
	public static BufferedImage NGegnerul;
	public static BufferedImage NGegneror;
	public static BufferedImage NGegnerur;
	public static BufferedImage SGegner;
	public static BufferedImage Lautsprecher;
	public static BufferedImage Asteroid;
	public static BufferedImage SmartGegner;
	public static Image gt;
	public static Image c;
	
	public ImageHolder() {
			try {
			Player =		ImageIO.read(getClass().getResource("/source/Pixelart Raumschiff.png"));
			PlayerForced =	ImageIO.read(getClass().getResource("/source/Raumschiff c.png"));
			NGegner =		ImageIO.read(getClass().getResource("/source/Gegner.png"));
			Herz =			ImageIO.read(getClass().getResource("/source/Herz.png"));
			NGegnerol =		ImageIO.read(getClass().getResource("/source/ol.png"));
			NGegneror =		ImageIO.read(getClass().getResource("/source/or.png"));
			NGegnerur =		ImageIO.read(getClass().getResource("/source/ur.png"));
			NGegnerul =		ImageIO.read(getClass().getResource("/source/ul.png"));
			SGegner =		ImageIO.read(getClass().getResource("/source/SGegner.png"));
			Lautsprecher =	ImageIO.read(getClass().getResource("/source/Lautsprecher.png"));
			Asteroid =		ImageIO.read(getClass().getResource("/source/asteroid.png"));
			gt =			ImageIO.read(getClass().getResource("/source/gt.gif"));
			SmartGegner = 	ImageIO.read(getClass().getResource("/source/smartgegner.png"));
			c = 			ImageIO.read(getClass().getResource("/source/cn.png"));
//			Lautsprecher.getScaledInstance(200, -1, Image.SCALE_FAST);

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}