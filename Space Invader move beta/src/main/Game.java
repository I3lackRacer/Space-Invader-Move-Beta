package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -24084060053378354L;
	public static final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width,
			HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height - 40;
	public static boolean BackBlack = false;
	public static long editmodetime = 0;
	private Thread thread;
	private Handler handler;
	public static HUD hud;
	public static String ort = "/Space Invader/";
	public debugInfo dInfo;
	public static Editmode em;
	public static Call call;
	public static int FPS = 0;
	public Spawn spawn;
	public Window window;
	public static Player player;
	public static boolean MasterRunning = false, menuVisible = false, timerunning = true, editmode = false,
			editmodetimeon = false, debug = false, running = false, lastTickEditmode = false;
	public Menu m;
	public static boolean godmode = false;
	public static LevelHandler levelhandler;
	public static double time = 0, newGametime = 0, start = System.currentTimeMillis() / 1000, zeitsprung = 0;
	public static Game game;
	public static String lastRekordName;
	public static double lastRekordTime;
	public static ServerTalk server = null;
	public static Chat chat = new Chat();

	public static void main(String[] args) {
		game = new Game();
	}

	public synchronized void start() {
		debugInfo.lastTrigged = this.getClass().getName();
		thread = new Thread(this);
		thread.start();
		running = true;
		MasterRunning = true;
	}

	public synchronized void stop() {
		try {
			debugInfo.lastTrigged = "Game.stop()";
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		this.requestFocus();
		long letztesMal = System.nanoTime();
		double anzahlvonTicks = 60.0;
		double ns = 1000000000 / anzahlvonTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (MasterRunning) {
			if (editmode && !lastTickEditmode) {
				lastTickEditmode = true;
				zeitsprung = System.currentTimeMillis();
			}
			if (!editmode && lastTickEditmode) {
				lastTickEditmode = false;
				zeitsprung -= System.currentTimeMillis();
			}
			delta += (System.nanoTime() - letztesMal) / ns;
			letztesMal = System.nanoTime();
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (MasterRunning) {
				render();
				frames++;
				if (editmode) {
					timerunning = false;
					if (editmodetimeon) {
						editmodetime++;
					}
				} else {
					timerunning = true;
					if (editmodetime != 0) {
						editmodetime = 0;
						editmodetimeon = false;
					}
				}
				if (timerunning) {
					time++;
				}
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				FPS = frames;
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {
		chat.tick();
		if (running) {
			if (!editmode || editmode && editmodetimeon) {
				hud.tick();
				if(godmode ) {
					HUD.HEALTH = 100;
				}
				handler.tick();
			}
			if (!editmode) {
				if (levelhandler != null) {
					levelhandler.tick();
				}
				spawn.tick();
			}
		}
		window.tick();
		if (menuVisible) {
			m.tick();
//			if(server != null) server.tick();
		}
		if (debug == true) {
			dInfo.tick();
		}

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setFont(new Font("Arial Black", 12, 12));
		if(!BackBlack) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, WIDTH, HEIGHT);
		}
		chat.render(g);
		window.render(g);
		if (running) {
			g.setFont(new Font("Arial", 30, 30));
			g.drawImage(ImageHolder.Lautsprecher, WIDTH - 100, 100 - 64, null);
			handler.render(g);
			hud.render(g);
		}
		if (menuVisible) {
			m.render(g);
		}
		if (debug) {
			dInfo.render(g, handler);
		}
		g.dispose();
		bs.show();
		this.getGraphics();
	}

	public Game() {
		getRekord();
		new ImageHolder();
		handler = new Handler();
		hud = new HUD();
		spawn = new Spawn(handler, hud);
		call = new Call(hud, handler);
		player = new Player(WIDTH / 2 - 32, HEIGHT - 90, ID.Player, handler, hud);
		handler.addObjekt(player);
		em = new Editmode(handler, hud);
		window = new Window(WIDTH, HEIGHT, "Space Invader", this, handler, hud);
		this.addKeyListener(new KeyInput(handler, hud, this, em));
		this.addMouseListener(new MouseInput(hud, handler));
		dInfo = new debugInfo();
		m = new Menu(handler, hud);
		new GameStart(handler, hud);
		
	}

	private void getRekord() {
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(new File(ort + "rekord.txt")));
			lastRekordName = br.readLine();
			br.readLine();
			lastRekordTime = Double.valueOf(br.readLine());
		} catch (Exception e) {
			System.out.println("Rekord konnte nicht gelesen werden.");
		}
	}

	public static float clamp(float var, float min, float max) {
		if (var >= max) {
			return var = max;
		} else if (var <= min) {
			return var = min;
		} else {
			return var;
		}
	}

	public void test() {
		stop();
		MasterRunning = false;
	}
}