package main;

import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Editmode {

	public ID SelectedObject = ID.NormalerGegner;
	public Handler handler;
	public HUD hud;
	public ArrayList<GameObject> gameobjecteditmode = new ArrayList<GameObject>();
	public ArrayList<String> lvlsafe = new ArrayList<String>();

	public Editmode(Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
	}

	public void FileReader(int lvl) {
		File folder = new File(Game.ort);
		File f = new File(Game.ort + lvl + ".txt");
		if (!folder.exists()) {
			folder.mkdir();
		}
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(f + " konnte nicht erstellt werden");
			}
		}
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(f);
			String allInput = sc.nextLine();
			System.out.println(allInput);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public FileReader FileWriter(int lvl) {
		File folder = new File("C://Program Files (x86)/si");
		File f = new File("C://Program Files (x86)/si/lvl" + lvl + ".txt");
		if (!folder.exists()) {
			folder.mkdir();
		}
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(f + " konnte nicht erstellt werden");
			}
		}
		try {
			return new FileReader(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("FileWriter: Konnte datei nicht finden");
		}
		return null;
	}

	public void removeAll() {
		handler.objects.clear();
	}

	public void click(int x, int y) {
		if (KeyInput.keyDown[12] == false) {
			if (SelectedObject == ID.Player || SelectedObject == ID.NormalerGegner || SelectedObject == ID.AimGegner
					|| SelectedObject == ID.Asteroid) {
				x -= 16;
				y -= 16;
			}
			if (SelectedObject == ID.NormalerGegner) {
				NormalerGegner a = new NormalerGegner(x, y, ID.NormalerGegner, handler, hud);
				gameobjecteditmode.add(a);
				handler.addObjekt(a);
			}
			if (SelectedObject == ID.SmartGegner) {
				SmartGegner a = new SmartGegner(x, y, ID.SmartGegner, handler, hud);
				gameobjecteditmode.add(a);
				handler.addObjekt(a);
			}
			if (SelectedObject == ID.AimGegner) {
				AimGegner a = new AimGegner(x, y, ID.AimGegner, handler, hud);
				gameobjecteditmode.add(a);
				handler.addObjekt(a);
			}
			if (SelectedObject == ID.Asteroid) {
				Asteroid a = new Asteroid(x, y, ID.Asteroid, handler, hud);
				gameobjecteditmode.add(a);
				handler.addObjekt(a);
			}
			double time = (Game.editmodetime);
			lvlsafe.add(x + ";" + y + ";" + time + ";" + SelectedObject);
		}else{
			for(int i = 0; lvlsafe.size() > i; i++) {
				@SuppressWarnings("unused")
				GameObject tmp;
			}
		}
	}
}
