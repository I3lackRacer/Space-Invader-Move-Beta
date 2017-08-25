package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

	public static float MouseX;
	public static float MouseY;
	private Handler handler;
	private HUD hud;

	public MouseInput(HUD hud, Handler handler) {
		this.handler = handler;
		this.hud = hud;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (Game.editmode) {
			Game.em.click(e.getX(), e.getY());
		} else {
			if (Player.bulletRemain > 0) {
				Player.bulletRemain--;
				MouseX = e.getX();
				MouseY = e.getY();
				double PlayerX = handler.getDirect(ID.Player).x;
				double PlayerY = handler.getDirect(ID.Player).y;
				double distance = Math
						.sqrt(((MouseX - PlayerX) * (MouseX - PlayerX)) + ((MouseY - PlayerY) * (MouseY - PlayerY)));
				double speed = 11;
				Bullet b = new Bullet(
						(int) handler.getDirect(ID.Player).x
								+ (int) handler.getDirect(ID.Player).getBounds().getWidth() / 2,
						(int) handler.getDirect(ID.Player).y, ID.Bullet, handler, hud, false);
				b.setVelX((float) ((MouseX - PlayerX) /distance  * speed));
				b.setVelY((float) ((MouseY - PlayerY) / distance  * speed));
				handler.addObjekt(b);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
