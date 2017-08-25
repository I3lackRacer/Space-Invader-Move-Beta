package main;

public class DisplayMessage {

	public int displayTime = 900;
	public int move = 50;
	public int alpha = 255;
	public String message;
	
	public DisplayMessage(String message) {
		this.message = message;
	}
	
	public void tick() {
		displayTime--;
		if(move > 0) {
			move--;
		}
			alpha = (int) Game.clamp(displayTime-50, 0, 255);
	}
}
