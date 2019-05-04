package Game_20170920_REV01;


import java.awt.Color;
import java.awt.Graphics;
abstract public class Board {
	protected double x;
	protected double absy;	
	protected double rely;
	protected int width;
	protected int height;
	protected double dx;
	
	public Board(int x,int y) {
		this.x = x;
		this.absy = y;
		this.rely = y;
		this.width = 100;
		this.height = 20;
	}
	public double getX() {
		return this.x;
	}
	public double getDx() {
		return dx;
	}
	public void setY(double y) {
		this.absy = y;
	}
	public void setBoard(double y) {
		this.rely = y;
	}
	public double getAbsY() {
		return absy;
	}
	public double getRelY() {
		return rely;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public boolean checkCollision(Ball b) {

		if ( b.getY() + b.getRadius() > rely && b.getY() + b.getRadius() < rely + height)
		{	
			if (b.getX() > x && b.getX() < x + width && b.getDy() > 0) {
				double newDY = -1 * 80;
				b.setAbsy(b.getAbsy() - b.getRadius());
				b.setDy(newDY);
				return true;
			}
		}
		return false;
	}
	abstract public void paint(Graphics g);
	abstract public void move(); 
}
