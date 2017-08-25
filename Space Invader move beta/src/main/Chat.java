package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class Chat {

	public ArrayList<DisplayMessage> allMessage = new ArrayList<DisplayMessage>();
	public int höhe = Game.HEIGHT/100*90;
	public Chat() {
		
	}
	
	public void tick() {
		for(int i = 0; i < allMessage.size(); i++) {
			DisplayMessage e = allMessage.get(i);
			e.tick();
			if(e.displayTime <= 0) {
				allMessage.remove(e);
			}
		}
	}
	
	public void render(Graphics g) {
		DisplayMessage d;
		g.setColor(Color.WHITE);
		g.setFont(new Font("ARIAL", 20, 20));
		for(int i = 0; i < allMessage.size(); i++) {
			d = allMessage.get(((i-allMessage.size())/-1)-1);
			g.setColor(new Color(255, 255, 255, d.alpha));
			g.drawString(d.message, 50-(d.move*20), höhe-(20*i));
		}
	}
	
	public void addMessage(String message) {
		if(allMessage.size() >= 30) {
			allMessage.remove(0);
		}
		if(message.length() >= 50) {
		allMessage.add(new DisplayMessage(message.substring(0, 50) + "..."));
		} else {
			allMessage.add(new DisplayMessage(message));
		}
	}
}
