package main;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundHandler {
	
	public static boolean soundOn = false;

	public SoundHandler() {
		
	}
	
	public static void playSound(File name) {
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(name));
			clip.start();
		}
		catch (Exception e) {
			System.out.println(name + " wurde nicht gefunden");
		}
	}

}
