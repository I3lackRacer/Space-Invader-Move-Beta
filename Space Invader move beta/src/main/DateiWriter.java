package main;

import java.io.*;

public class DateiWriter {

	public DateiWriter(String file, String Message, boolean override, int line) {
		if (override) {
			try {
				FileWriter fw = new FileWriter(new File(file));
				BufferedWriter bw = new BufferedWriter(fw);
				for (int i = 1; line > i; i++) {
					bw.write(System.getProperty("line.separator"));
				}
				String[] allsep = Message.split("/#/");
				for (int i = 0; allsep.length > i; i++) {
					bw.write(allsep[i]);
					bw.newLine();
				}
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				String beforeunsplit = DateiWriterMultiLine(file);
				String[] before = null;
				if (beforeunsplit != null) {
					before = beforeunsplit.split("/+/");
				}
				FileWriter fw = new FileWriter(new File(file));
				BufferedWriter bw = new BufferedWriter(fw);
				for(int i = 0; line-1 >= i; i++) {
					bw.write(before[i]);
				}
				if (before != null) {
					for (int i = 0; line > i; i++) {
						bw.write(before[i] + System.getProperty("line.separator"));
					}
				}
				String[] allsep = Message.split("/#/");
				for (int i = 0; allsep.length > i; i++) {
					bw.write(allsep[i]);
					bw.newLine();
				}
				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public DateiWriter(String file, String message, boolean override) {

		try {
			if (!new File(file).exists()) {
				if(file == Game.ort + "/stat/") {
					new File(Game.ort + "/stat/").mkdir();
				}
				new File(file).createNewFile();
			}
			String before = DateiReader(file);
			File f = new File(file);
			FileWriter fw = new FileWriter(f);
			if (before != null && override == false) {
				fw.write(before + "-" + message);
			} else {
				fw.write(message);
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DateiWriter() {
	}

	public String DateiReader(String file) {
		File f = new File(file);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				System.out.println("Datei: " + f + " gab es nicht und wurde deshalb erstellt");
				e.printStackTrace();
			}
		}
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(f));
			try {
				String all = br.readLine();
				return all;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String DateiWriterMultiLine(String file) {
		try {
			File f = new File(file);
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String all = null;
			String zeile = "";
			while ((zeile = br.readLine()) != null) {
				all = all + "/+/" + zeile;
			}
			br.close();
			return all;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
