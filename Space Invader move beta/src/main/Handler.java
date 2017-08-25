package main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	public LinkedList<GameObject> hitlessobjects = new LinkedList<GameObject>();

	public void tick() {
		for (int i = 0; i < hitlessobjects.size(); i++) {
			GameObject tempObjekt = hitlessobjects.get(i);
			tempObjekt.tick();
		}
		for (int i = 0; i < objects.size(); i++) {
			GameObject tempObjekt = objects.get(i);
			tempObjekt.tick();
		}
	}

	public void render(Graphics g) {
		try{
		for (int i = 0; i < hitlessobjects.size(); i++) {
			GameObject tempObjekt = hitlessobjects.get(i);
			tempObjekt.render(g);
		}
		for (int i = 0; i < objects.size(); i++) {
			GameObject tempObjekt = objects.get(i);
			tempObjekt.render(g);
		}
		}catch(NullPointerException e) {
			System.out.println("Eine Handler änderung während des aufrufes veruhrsachte eine NullpoiterException");
		}
	}

	public void addObjekt(GameObject objekt) {
		this.objects.add(objekt);
 	}

	public void removeObjekt(GameObject objekt) {
		this.objects.remove(objekt);
	}

	public void addHitlessobjekt(GameObject object) {
		this.hitlessobjects.add(object);
	}

	public void removeHitlessobjekt(GameObject object) {
		this.hitlessobjects.remove(object);
	}

	public GameObject getDirect(ID id) {
		for (int i = 0; i < objects.size(); i++) {
			GameObject tempObjekt = objects.get(i);
			if(tempObjekt.getId() == id) {
				return tempObjekt;
			}
		}
		return null;
	}
}
