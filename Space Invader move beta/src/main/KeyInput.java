package main;

import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	public static boolean[] keyDown = {false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	public static KeyEvent keyInput;
	private HUD hud;
	private Editmode editmode;
	public static String Name = "I3lackRacer";
	public static int schussLänge = 0;
	public Game game;
	public static DISPLAY display = DISPLAY.Anderes;
	public int timePressed = 0;

	public KeyInput(Handler handler, HUD hud, Game game, Editmode editmode) {
		this.handler = handler;
		this.game = game;
		this.hud = hud;
		this.editmode = editmode;
		Name = new DateiWriter().DateiReader(Game.ort + "lastPlayer.txt");
		if (Name == null) {
			Name = "I3lackRacer";
			new DateiWriter(Game.ort + "lastPlayer.txt", "I3lackRacer", true);
		}
		keyDown[0] = false; // W
		keyDown[1] = false; // A
		keyDown[2] = false; // S
		keyDown[3] = false; // D
		keyDown[4] = false; // SPACE
		keyDown[5] = false; // SHIFT
		keyDown[6] = false; // Hoch
		keyDown[7] = false; // Runter
		keyDown[8] = false; // Links
		keyDown[9] = false; // Rechts
		keyDown[10] = false;// Num 0 (Einfügen)
		keyDown[11] = false;// Num , (Entfernen)
		keyDown[12] = false;// STRG
		keyDown[13] = false;// TAP
	}

	public void keyPressed(KeyEvent e) {
		// Menu
		if (e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z' || e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z' || e.getKeyChar() >= '0' && e.getKeyChar() <= '9' || e.getKeyChar() == '.' || e.getKeyChar() == KeyEvent.VK_BACK_SPACE || e.getKeyChar() == KeyEvent.VK_SPACE) {
			if (Game.menuVisible && Menu.currentWindow == CURRENTMENU.Server) {
				if (Menu.select == 1) {
					if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
						Name = Name + e.getKeyChar();
					} else {
						if (Name.length() >= 1) {
							Name = Name.substring(0, Name.length() - 1);
						}
					}
					return;
				}
				if (Menu.select == 2) {
					if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
						ServerTalk.ip = ServerTalk.ip + e.getKeyChar();
					} else {
						if (Name.length() >= 1) {
							ServerTalk.ip = ServerTalk.ip.substring(0, ServerTalk.ip.length() - 1);
						}
					}
					return;
				}
				
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			System.out.println("TRIGGED");
			if(Game.menuVisible && Menu.currentWindow == CURRENTMENU.Server) {
				Menu.select = (int) Game.clamp(Menu.select-1, 1, 4);
				System.out.println(Menu.select);
			return;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(Game.menuVisible && Menu.currentWindow == CURRENTMENU.Server) {
				Menu.select = (int) Game.clamp(Menu.select+1, 1, 4);
				System.out.println(Menu.select);
			return;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_SCROLL_LOCK) {
			System.out.println("The Cake is a lie");
		}
		if (display == DISPLAY.Anderes) {
			if (Game.menuVisible) {
				if (Menu.currentWindow == CURRENTMENU.Gameover) {
					if (Menu.select == 0) {
						if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
							String backtogether = "";
							for (int i = 0; Name.length() - 1 > i; i++)
								backtogether = backtogether + Name.charAt(i);
							Name = backtogether;
						} else {
							if (e.getKeyCode() != KeyEvent.VK_SHIFT && e.getKeyCode() != KeyEvent.VK_ENTER) {
								Name = Name + e.getKeyChar();
							}
						}
					}
				}
			}

			// Editmode
			if (Game.editmode) {
				int key = e.getKeyCode();
				switch (key) {
				case KeyEvent.VK_1:
					editmode.SelectedObject = ID.NormalerGegner;
					System.out.println("Ausgewählt: NormalerGegner");
					break;
				case KeyEvent.VK_2:
					editmode.SelectedObject = ID.AimGegner;
					System.out.println("Ausgewählt: AimGegner");
					break;
				case KeyEvent.VK_3:
					editmode.SelectedObject = ID.Asteroid;
					System.out.println("Ausgewählt: Asteroid");
					break;
				case KeyEvent.VK_4:
					editmode.SelectedObject = ID.SmartGegner;
					System.out.println("Ausgewählt: SmartGegner");
					break;
				case KeyEvent.VK_0:
					new Safe(editmode.lvlsafe);
					break;
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_G) {
				if (Game.godmode) {
					Game.godmode = false;
				} else {
					Game.godmode = true;
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_T) {
				if (Game.editmode && !Game.editmodetimeon) {
					Game.editmodetimeon = true;
				} else {
					if (Game.editmode && Game.editmodetimeon) {
						Game.editmodetimeon = false;
					}
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_O && Game.editmode) {
				GameObject p = handler.getDirect(ID.Player);
				handler.objects.clear();
				handler.hitlessobjects.clear();
				handler.addObjekt(p);
			}
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_R) {
				Player.reloading = true;
			}
			if (key == KeyEvent.VK_CONTROL) {
				keyDown[12] = true;
			}
			if (key == KeyEvent.VK_L) {
				if (Game.editmode) {
					Game.editmode = false;
				} else {
					Game.editmode = true;
				}
			}

			if (key == KeyEvent.VK_TAB) {
				keyDown[13] = true;
			}

			if (key == KeyEvent.VK_N && e.isShiftDown()) {
				if (Game.BackBlack) {
					Game.BackBlack = false;
				} else {
					Game.BackBlack = true;
				}
			}
			if (key == KeyEvent.VK_NUMPAD5) {
				handler.addObjekt(new SoullessPlayer(Game.WIDTH / 2, Game.HEIGHT - 32, ID.Player2, handler, hud, 0));
			}
			if (key == KeyEvent.VK_2) {
				if (!Game.editmode) {
					Window.frame.setState(Frame.ICONIFIED);
				}
			}
			if (key == KeyEvent.VK_ESCAPE) {
				System.out.println("|-O-|  <---Piefighter");
				Window.frame.setVisible(false);
				try {
					if (!new File(Game.ort + "/stat/").exists()) {
						new File(Game.ort + "/stat/").mkdirs();
						System.out.println("Erstellt: " + Game.ort + "/stat/stat.txt");
						;
					}
					if (!new File(Game.ort + "/stat/stat.txt").exists()) {
						new File(Game.ort + "/stat/stat.txt").createNewFile();
						System.out.println("Erstellt: " + Game.ort + "/stat/stat.txt");
					}
					BufferedReader br = new BufferedReader(new FileReader(Game.ort + "/stat/stat.txt"));
					String all = br.readLine();
					br.close();
					FileWriter fw = new FileWriter(new File(Game.ort + "/stat/stat.txt"));
					if (all != null) {
						fw.write(all + " " + Name + ";" + hud.score + ";" + Game.time);
					} else {
						fw.write(Name + ";" + hud.score + ";" + Game.time);
					}
					fw.flush();
					fw.close();

				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(Game.server != null){
					if(Game.server.server != null) {
						try {
							Game.server.send("bye");
							Game.server.server.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				try {
					if(Game.server != null) {
						Game.server.server.close();
					}
				} catch (IOException e1) {
				}
				System.exit(0);
			}
			if (key == KeyEvent.VK_M) {
				if (!keyDown[13]) {
					if (Window.number == -2) {
						if (Game.menuVisible == false && Menu.currentWindow == CURRENTMENU.Game && Game.running) {
							Game.running = false;
							Game.menuVisible = true;
							Menu.currentWindow = CURRENTMENU.Pause;
							Menu.select = 1;
						} else {
							Game.running = true;
							Game.menuVisible = false;
							Menu.currentWindow = CURRENTMENU.Game;
							Menu.select = 0;
						}
					}
				}
			}

			if (key == KeyEvent.VK_SHIFT) {
				if (keyDown[5] == false) {
					keyDown[5] = true;
					handler.addObjekt(new Schild((int) handler.getDirect(ID.Player).x,
							(int) handler.getDirect(ID.Player).y, ID.Schild, handler, hud));
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_W) {
				if (Game.menuVisible) {
					if (Menu.currentWindow == CURRENTMENU.Gameover) {
						if (Menu.select != 0)
							Menu.select = (int) Game.clamp(Menu.select - 1, 1, 2);
					}
					if (Menu.currentWindow == CURRENTMENU.Pause) {
						if (Menu.select != 0)
							Menu.select = (int) Game.clamp(Menu.select - 1, 1, 3);
					}
					if (Menu.currentWindow == CURRENTMENU.Server) {
						if (Menu.select != 0)
							Menu.select = (int) Game.clamp(Menu.select - 1, 1, 4);
					}
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				if (Game.menuVisible) {
					if (Menu.currentWindow == CURRENTMENU.Pause) {
						if (Menu.select != 0)
							Menu.select = (int) Game.clamp(Menu.select + 1, 1, 3);
					}
					if (Menu.currentWindow == CURRENTMENU.Gameover) {
						if (Menu.select != 0)
							Menu.select = (int) Game.clamp(Menu.select + 1, 1, 2);
					}
					if (Menu.currentWindow == CURRENTMENU.Server) {
						if (Menu.select != 0)
							Menu.select = (int) Game.clamp(Menu.select + 1, 1, 4);
					}

				}
			}
			if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
				if (Game.menuVisible) {
					if (Menu.currentWindow == CURRENTMENU.Gameover) {
						if (Menu.select == 1)
							restart();
						if (Menu.select == 2)
							System.exit(0);
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							new DateiWriter(Game.ort + "lastPlayer.txt", Name, true);
							Menu.select = 1;
						}
					}
					if (Menu.currentWindow == CURRENTMENU.Server) {
						if (Menu.select == 1 && e.getKeyCode() == KeyEvent.VK_ENTER) {
							Menu.select = 2;
							return;
						}
						if (Menu.select == 2) {
							Menu.select = 3;
							return;
						}
						if (Menu.select == 3) {
							Game.server = new ServerTalk(handler, hud);
							return;
						}
						if (Menu.select == 4) {
							Menu.select = 1;
							Menu.currentWindow = CURRENTMENU.Pause;
							return;
						}
					}
					if (Menu.currentWindow == CURRENTMENU.Pause) {
						if (Menu.select == 3) {
							Menu.currentWindow = CURRENTMENU.Server;
							Menu.select = 1;
							return;
						}
						if (Menu.select == 2) {
							System.out.println("|-O-|  <---Piefighter");
							System.exit(0);
						}
						if (Menu.select == 1) {
							Menu.select = 0;
							Menu.currentWindow = CURRENTMENU.Game;
							Game.menuVisible = false;
							Game.running = true;
							Game.MasterRunning = true;
							return;
						}
					}
				}
			}

			if (key == KeyEvent.VK_SPACE) {
				if (Game.menuVisible == true) {
					if (Menu.currentWindow == CURRENTMENU.Gameover) {
						if (Menu.select != 0) {
							new DateiWriter(Game.ort + "score.txt", Name + ";" + hud.score + ";" + Game.time, false);
						}
						if (hud.getHighScore() < hud.getScore()) {
							new DateiWriter(Game.ort + "rekord.txt", Name + "/#/" + hud.getScore() + "/#/" + Game.time,
									true, 1);
							System.out.println("Neuer Rekord eingetragen");
						}
						if (Menu.select == 1) {
							restart();
						}
						if (Menu.select == 2) {
							System.exit(0);
						}
					}
				}
				if (keyDown[4] == false) {
					debugInfo.lastTrigged = "Player Schuss";
					keyDown[4] = true;
				} else {
					schussLänge++;
					Player.loading = true;
				}
			}
			if (key == KeyEvent.VK_PLUS) {
				Game.hud.setLevel(Game.hud.getLevel() + 1);
			}
			if (key == KeyEvent.VK_H) {
				if (Game.debug == false) {
					Game.debug = true;
					debugInfo.lastTrigged = "Debug Aktiviert";
				} else {
					Game.debug = false;
				}
			}
			
			
			
			
			
			
			
			
			
			if (Menu.currentWindow == CURRENTMENU.Game) {
				for (int i = 0; i < handler.objects.size(); i++) {
					GameObject tempObjekt = handler.objects.get(i);
					if (tempObjekt.getId() == ID.Player) {
						// Player 1
						if (key == KeyEvent.VK_W) {
							keyDown[0] = true;
						}
						if (key == KeyEvent.VK_S) {
							keyDown[2] = true;
						}
						if (key == KeyEvent.VK_A) {
							keyDown[1] = true;
						}
						if (key == KeyEvent.VK_D) {
							keyDown[3] = true;
						}
						break;
					}
				}
			}
			// Player2
			if (key == KeyEvent.VK_NUMPAD0) {
				if (keyDown[5 + 6] == false) {
					keyDown[5 + 6] = true;
					handler.addObjekt(new Schild((int) handler.getDirect(ID.Player2).x,
							(int) handler.getDirect(ID.Player2).y, ID.Schild, handler, hud));
				}
			}
			if (key == KeyEvent.VK_COMMA) {
				if (keyDown[4 + 6] == false) {
					debugInfo.lastTrigged = "Player Schuss";
					keyDown[4 + 6] = true;
				} else {
					schussLänge++;
					Player.loading = true;
				}
			}
		}
	}

	private void restart() {
		Menu.newRekord = false;
		Menu.currentWindow = CURRENTMENU.Game;
		Game.time = 0;
		Menu.select = 1;
		Game.menuVisible = false;
		Game.running = true;
		HUD.HEALTH = 100;
		hud.setLevel(1);
		hud.setScore(0);
		Game.time = 0;
		Game.newGametime = System.currentTimeMillis() / 1000 - Game.start;
		handler.objects.clear();
		handler.hitlessobjects.clear();
		Schild.überlasteterSchild = false;
		handler.addObjekt(new Player(Game.WIDTH / 2 - 32, Game.HEIGHT - 90, ID.Player, handler, hud));
		Player.bulletRemain = 12;
		new GameStart(handler, hud);
	}

	public void keyReleased(KeyEvent e) {
		if (display == DISPLAY.Anderes) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_BACK_SPACE) {
				if (editmode.gameobjecteditmode.size() > 0) {
					editmode.lvlsafe.remove(editmode.lvlsafe.size() - 1);
					if (!(editmode.gameobjecteditmode
							.get(editmode.gameobjecteditmode.size() - 1) instanceof NormalerGegner)
							|| !(editmode.gameobjecteditmode
									.get(editmode.gameobjecteditmode.size() - 1) instanceof AimGegner)) {
						handler.removeObjekt(editmode.gameobjecteditmode.get(editmode.gameobjecteditmode.size() - 1));
					} else {
						if (editmode.gameobjecteditmode
								.get(editmode.gameobjecteditmode.size() - 1) instanceof NormalerGegner) {
							NormalerGegner n = (NormalerGegner) editmode.gameobjecteditmode
									.get(editmode.gameobjecteditmode.size() - 1);
							handler.removeHitlessobjekt(n.LiveBar);
							n.removeThis();
						}
						if (editmode.gameobjecteditmode
								.get(editmode.gameobjecteditmode.size() - 1) instanceof AimGegner) {
							AimGegner n = (AimGegner) editmode.gameobjecteditmode
									.get(editmode.gameobjecteditmode.size() - 1);
							handler.removeHitlessobjekt(n.LiveBar);
							n.removeThis();
						}
					}
				}
			}
			if (key == KeyEvent.VK_CONTROL) {
				keyDown[12] = false;
			}

			if (key == KeyEvent.VK_SPACE) {
				if (Player.bulletRemain > 0 && !Player.reloading) {
					if (schussLänge < 2) {
						handler.addObjekt(new Bullet(
								(int) handler.getDirect(ID.Player).x
										+ (int) handler.getDirect(ID.Player).getBounds().getWidth() / 2 - 2,
								(int) handler.getDirect(ID.Player).y, ID.Bullet, handler, hud, false));
					} else {
						handler.addObjekt(new BulletForced(
								(int) handler.getDirect(ID.Player).x
										+ (int) handler.getDirect(ID.Player).getBounds().getWidth() / 2 - 2,
								(int) handler.getDirect(ID.Player).y, ID.BulletForced, handler, hud, false));
					}
					Player.bulletRemain--;
				}
				keyDown[4] = false;
				schussLänge = 0;
				Player.loading = false;
			}
			if (key == KeyEvent.VK_SHIFT) {
				handler.removeObjekt(handler.getDirect(ID.Schild));
				keyDown[5] = false;
			}
			if (key == KeyEvent.VK_NUMPAD0) {
				if (schussLänge < 2) {
					handler.addObjekt(new Bullet(
							(int) handler.getDirect(ID.Player).x
									+ (int) handler.getDirect(ID.Player).getBounds().getWidth() / 2,
							(int) handler.getDirect(ID.Player).y, ID.Bullet, handler, hud, false));
				} else {
					handler.addObjekt(new BulletForced(
							(int) handler.getDirect(ID.Player).x
									+ (int) handler.getDirect(ID.Player).getBounds().getWidth() / 2,
							(int) handler.getDirect(ID.Player).y, ID.BulletForced, handler, hud, false));
				}
				keyDown[4 + 6] = false;
				schussLänge = 0;
				Player.loading = false;
			}
			if (key == KeyEvent.VK_COMMA) {
				for (int i = 0; i < handler.objects.size(); i++) {
					if (handler.objects.get(i).getId() == ID.Schild) {
						handler.removeObjekt(handler.objects.get(i));
						keyDown[5 + 6] = false;
					}
				}
			}
			if (key == KeyEvent.VK_CONTROL) {
				for (int repeat = 0; repeat < 40; repeat++)
					handler.addObjekt(new BulletSpray(
							(int) handler.getDirect(ID.Player).x
									+ (int) handler.getDirect(ID.Player).getBounds().getWidth() / 2,
							(int) handler.getDirect(ID.Player).y
									+ (int) handler.getDirect(ID.Player).getBounds().getHeight() / 2,
							ID.BulletSpray, handler, hud));
			}

			for (int i = 0; i < handler.objects.size(); i++) {
				GameObject tempObjekt = handler.objects.get(i);

				if (tempObjekt.getId() == ID.Player) {
					// Player 1
					
					if (key == KeyEvent.VK_W) {
						keyDown[0] = false;
					}
					if (key == KeyEvent.VK_S) {
						keyDown[2] = false;
					}
					if (key == KeyEvent.VK_A) {
						keyDown[1] = false;
					}
					if (key == KeyEvent.VK_D) {
						keyDown[3] = false;
					}
				}
			}

			for (int i = 0; i < handler.objects.size(); i++) {
				GameObject tempObjekt = handler.objects.get(i);

				if (tempObjekt.getId() == ID.Player2) {
					// Player 1
					
					

					if (key == KeyEvent.VK_UP) {
						if (keyDown[1 + 6] == true) {
							tempObjekt.setVelY((int) (+5));
						} else {
							tempObjekt.setVelY((int) (0));
						}
						keyDown[0 + 6] = false;
					}

					if (key == KeyEvent.VK_DOWN) {
						if (keyDown[0 + 6] == true) {
							tempObjekt.setVelY((int) (-5));
						} else {
							tempObjekt.setVelY((int) (0));
						}
						keyDown[1 + 6] = false;
					}

					if (key == KeyEvent.VK_LEFT) {
						if (keyDown[3 + 6] == true) {
							tempObjekt.setVelX((int) (+5));
						} else {
							tempObjekt.setVelX((int) (0));
						}
						keyDown[2 + 6] = false;
					}

					if (key == KeyEvent.VK_RIGHT) {
						if (keyDown[2 + 6] == true) {
							tempObjekt.setVelX((int) (-5));
						} else {
							tempObjekt.setVelX((int) (0));
						}
						keyDown[3 + 6] = false;
					}

					if (key == KeyEvent.VK_TAB) {
						keyDown[13] = false;
					}
					break;
				}
			}
		}
	}
}