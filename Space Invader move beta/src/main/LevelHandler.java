package main;

import java.util.ArrayList;

public class LevelHandler {

	public ArrayList<GameObject> level = new ArrayList<GameObject>();
	public ArrayList<String> Time = new ArrayList<String>();
	public int point = 0;
	public Handler handler;
	public HUD hud;
	public String lvl;
	public boolean abgeschlossen = false;

	public LevelHandler(String lvl, HUD hud, Handler handler) {
		this.lvl = lvl;
		this.handler = handler;
		this.hud = hud;
		lvl = new DateiWriter().DateiReader(Game.ort + "/lvl/" + lvl);
		if (lvl != null && lvl.length() > 2) {
			lvl = lvl.substring(1, lvl.length() - 1);
			String[] onespawnnosupdivide = lvl.split(", ");
			
			for (int i = 0; onespawnnosupdivide.length > i; i++) {
				String[] subdividespawn = onespawnnosupdivide[i].split(";");
				switch (subdividespawn[3]) {
				case "NormalerGegner":
					level.add(new NormalerGegner(Integer.valueOf(subdividespawn[0]), Integer.valueOf(subdividespawn[1]),
							ID.NormalerGegner, handler, hud));
					break;
				case "Asteroid":
					level.add(new Asteroid(Integer.valueOf(subdividespawn[0]), Integer.valueOf(subdividespawn[1]),
							ID.Asteroid, handler, hud));
					break;
				case "AimGegner":
					level.add(new AimGegner(Integer.valueOf(subdividespawn[0]), Integer.valueOf(subdividespawn[1]),
							ID.AimGegner, handler, hud));
					break;

				default:
					System.out.println("Unregestrietes Gameobjekt im lvl");
					break;
				}
				Time.add(subdividespawn[2]);
			}
		}else{
			System.out.println("Die Datei konnte nicht gelesen werden\nEventuell ist sie leer");
		}
	}

	public void tick() {
		if (point >= Time.size()) {
			System.out.println("LEVEL: " + lvl + " ABGESCHLOSSEN");
			abgeschlossen = true;
			Game.levelhandler = null;
			return;
		}
		if (Game.time >= Double.valueOf(Time.get(point))) {
			handler.addObjekt(level.get(point));
			point++;
		}
	}

}