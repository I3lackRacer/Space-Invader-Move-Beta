package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class debugInfo {

	public static String lastTrigged = "";
	public String version = "2.6";
	public ArrayList<String> debug = new ArrayList<>();

	public debugInfo() {
		File file = new File("E:/workbench/Space Invader/bin/source/version.txt");
		if (file.exists()) {
			try {
				@SuppressWarnings("resource")
				Scanner s = new Scanner(file);
				String line = "";
				do {
					line = s.nextLine();
				} while (s.hasNextLine());
				version = line.substring(0, 3);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("File: Version konnte nicht gefunden");
			}
		}
	}

	public void tick() {
		if( lastTrigged != "") {
			debug.add(lastTrigged);
			lastTrigged = "";
		}
	}

	public void render(Graphics g, Handler handler) {
		g.setFont(new Font("ARIAL", 10, 10));
		g.setColor(Color.GREEN);
		g.drawRect(Game.WIDTH - 200 - 10, 80, 180, 90);
		g.drawString("DEBUG INFO VERSION: " + version, Game.WIDTH - 200, 100);
		g.drawString("FPS: " + Game.FPS, Game.WIDTH - 200, 115);
		g.drawString("Anzahl von Entitys: " + handler.objects.size() + " + " + handler.hitlessobjects.size(),
				Game.WIDTH - 200, 130);
		g.drawString("Last Trigged: " + lastTrigged, Game.WIDTH - 200, 130 + 15 * 2);
	}

}
