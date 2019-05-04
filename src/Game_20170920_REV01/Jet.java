package Game_20170920_REV01;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;

public class Jet extends Board{
	
	private URL jetNoFire;
	private Image jet;
	private int count;
	public Jet(int x, int y,double dx,int count) {
		super(x,y);
		jetNoFire = this.getClass().getClassLoader().getResource("images/jetnofire.png");
		try {
			jet = ImageIO.read(jetNoFire);
		}catch(Exception e) {
			System.out.println("jetNoFire IO input error!");
		}
		this.width = 60;
		this.height = 80;
		this.count = count;
	    this.dx = dx;
	}
	
	@Override
	public boolean checkCollision(Ball b) {
		     
		if ( ( b.getY() + b.getRadius() > rely && b.getY() - b.getRadius() < rely + height))
		{	    
			if ((b.getX() + b.getRadius() > x && b.getX() - b.getRadius() < x + width)) {
				b.setRocket(true);
				double newDY = 80 * -3;
				b.setAbsy(b.getAbsy() - b.getRadius());
				b.setDy(newDY);
				return true;
			}
		}
		return false;
	}
	@Override
	public void move() {
		this.x += dx;
		count++;
		if(count> 100) {
			dx = -dx;
			count = 0;
		}
	}
	@Override
	public void paint(Graphics g) {
//		g.setColor(Color.GRAY);
//		g.fillRect((int)x, (int)rely, width, height);
		g.drawImage(jet, (int)x, (int)rely, (int)width, (int)height,null);
	}
}
