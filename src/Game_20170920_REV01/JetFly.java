package Game_20170920_REV01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;

public class JetFly {
	private static URL jetFire;
	private static URL jetFire1;
	private static Image jet;
	private static Image jet1;
	private double x;
	private double y;
	private double dy;
	private int width;
	private int height;
	private int count;
	public JetFly(double x,double y) {
		if(jetFire==null)
			jetFire = this.getClass().getClassLoader().getResource("images/jetfire.png");
		if(jetFire1==null)
			jetFire1 = this.getClass().getClassLoader().getResource("images/jetfire1.png");
		try {
			if(jet==null)
				jet = ImageIO.read(jetFire);
			if(jet1==null)
				jet1 = ImageIO.read(jetFire1);
		}catch(Exception e) {
			System.out.println("jetFire IO input error!");
		}
		this.x = x;
		this.y = y;
		this.width = 60;
		this.height = 80;
		this.dy = 0;
		this.count = 0;
	}
	public double getY() {
		return this.y;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public void fall() {
		this.x += 1;
		dy += 9.8 * 0.2;
		this.y += dy * 0.2 + 0.5 * 9.8 * 0.2 * 0.2;
	}
	public void paint(Graphics g) {
//		g.setColor(Color.GRAY);
//		g.fillRect((int)x, (int)y, width, height);
		count++;
		if(count % 100 <50)
			g.drawImage(jet1, (int)x, (int)y, (int)width, (int)height,null);
		else
			g.drawImage(jet, (int)x, (int)y, (int)width, (int)height,null);
	}
	public double getWidth() {
		return width;
	}
}
