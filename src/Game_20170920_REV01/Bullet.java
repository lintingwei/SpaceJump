package Game_20170920_REV01;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet{
	private double x;
	private double y;
	private double dy;
	private int width;
	private int height;
	public Bullet(double x,double y) {
		this.x = x;
		this.y = y;
		this.width = 10;
		this.height = 10;
		this.dy = -10;
	}
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	public void move() {
		this.y += dy;
	}

	public boolean checkCollision(Board m) {
		if ( m.getRelY() + m.getHeight() > y && m.getRelY() < y )
		{	    
			if (m.getX() < x && m.getX() +m.getWidth() > x ) {
				return true;
			}
		}
		return false;
	}

	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect((int)x, (int)y, width, height);
	}
}
