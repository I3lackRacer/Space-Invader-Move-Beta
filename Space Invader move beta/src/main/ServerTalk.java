package main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerTalk implements Runnable{
	
	public static String ip = "localhost";
	public Socket server;
	public Scanner in;
	public PrintStream out;
	private boolean keepRunning = true;
	public Handler handler;
	public long lastInput = System.currentTimeMillis();
	public HUD hud;
	public long lastSend = 0;
	public ArrayList<SoullessPlayer> sP = new ArrayList<SoullessPlayer>();

	public ServerTalk(Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
		Thread t1 = new Thread(this);
		t1.start();
	}

	@Override
	public void run() {
		verbinde();
	}

	private void verbinde() {
		try {
			server = new Socket(ip, 4308);
			out = new PrintStream(server.getOutputStream());
			in = new Scanner(server.getInputStream());
			out.println(KeyInput.Name);
			handler.objects.clear();
			handler.hitlessobjects.clear();
			handler.addObjekt(Game.player);
			Game.chat.addMessage("[INFO] Verbindung mit " + ip + " geglückt");
			tick();
		} catch (UnknownHostException e) {
			Menu.timer = 400;
			e.printStackTrace();
		} catch (IOException e) {
			Menu.timer = 400;
			e.printStackTrace();
		}
	}

	public void tick() {
		float[] lastPos = new float[2];
		lastPos[0] = handler.getDirect(ID.Player).getVelX();
		lastPos[1] = handler.getDirect(ID.Player).getVelY();
		lastSend = System.currentTimeMillis();
		String input;
		while(keepRunning) {
			if(lastPos != new float[]{handler.getDirect(ID.Player).getVelX(), handler.getDirect(ID.Player).getVelY()}) {
				send("p" + (int) handler.getDirect(ID.Player).getX() + ";" + (int) handler.getDirect(ID.Player).getY());
			}
			if((System.currentTimeMillis() -lastSend) >= 50) {
				send("&");
			}
			if(!server.isConnected()) {
				try {
					server.close();
					System.out.println("Server geschlossen ");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			try {
			if(in.hasNext()) {
				lastInput = System.currentTimeMillis();
				input = in.next();
//				Game.chat.addMessage("<--" + input);
				if(input.charAt(0) == '.') {
					Game.chat.addMessage(input.substring(1, input.length()).replace('/', ' '));
				}
				if(input == "bye") {
					stop();
					return;
				}
				if(input.charAt(0) == 'n') {
//					Game.chat.addMessage("Normaler");
					handler.addObjekt(new NormalerGegner( Integer.valueOf(input.substring(1).split(";")[0]), Integer.valueOf(input.substring(1).split(";")[1]), ID.NormalerGegner, handler, hud));
				}
				if(input.charAt(0) == 'a') {
//					Game.chat.addMessage("AimGegner");
					handler.addObjekt(new AimGegner( Integer.valueOf(input.substring(1).split(";")[0]), Integer.valueOf(input.substring(1).split(";")[1]), ID.AimGegner, handler, hud));
				}
				if(input.charAt(0) == 'm') {
//					Game.chat.addMessage("Asteroid");
					handler.addObjekt(new Asteroid( Integer.valueOf(input.substring(1).split(";")[0]), Integer.valueOf(input.substring(1).split(";")[1]), ID.Asteroid, handler, hud));
				}
				if(input.charAt(0) == 'p') {
					Game.chat.addMessage("SoullessPlayer");
					SoullessPlayer s = new SoullessPlayer(Integer.valueOf( input.substring(1).split(";")[1] ), Integer.valueOf( input.substring(1).split(";")[2] ), ID.Player2, handler, hud, Integer.valueOf( input.substring(1).split(";")[0] ));
					handler.addHitlessobjekt(s);
					sP.add(s);
				}
				if(input.charAt(0) == 'k') {
					Game.chat.addMessage("Nr." + input.substring(1).split(";")[0]);
					SoullessPlayer s = sP.get(Integer.valueOf( input.substring(1).split(";")[0] ));
					s.setX(Integer.valueOf( input.substring(1).split(";")[1] ));
					s.setY(Integer.valueOf( input.substring(1).split(";")[2] ));
				}
			}
			if((System.currentTimeMillis() - lastInput ) >= 5*1000) {
				try {
					send("T");
				}catch(Exception e) {
					stop();
				}
			}
			
			}catch (Exception e) {
				try {
					e.printStackTrace();
					server.close();
					Game.chat.addMessage("Server geschlossen durch Excepiton");
					keepRunning = false;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Game.chat.addMessage("Verbindung Verloren");
			}
		}
	}
	
	public void stop() {
		Game.chat.addMessage("Die Verbindung " + ip + " wurde vom Host geschlossen.");
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Game.server = null;
	}

	public void send(String tmp) {
		out.append("\n" + tmp);
	}
}
