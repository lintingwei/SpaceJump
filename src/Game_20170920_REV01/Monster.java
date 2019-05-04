package Game_20170920_REV01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;

public class Monster extends Board {
	private URL monster;
	private Image iMonster;
	private int dy;
	private int count;

	public Monster (int x,int y) {
		super(x,y);

		monster = this.getClass().getClassLoader().getResource("images/monster.png");
		try {
			iMonster = ImageIO.read(monster);
		}catch(Exception e) {
			System.out.println("Monster IO input error!");
		}
		this.width = 100;
		this.height = 100;
		this.dx = 3;
		this.dy = 1;
		this.count = 0;
	}
	@Override
	public boolean checkCollision(Ball b) {
		     
		if ( ( b.getY() + b.getRadius() > rely && b.getY() - b.getRadius() < rely + height))
		{	    
			if ((b.getX() + b.getRadius() > x && b.getX() - b.getRadius() < x + width)) {
				return true;
			}
		}
		return false;
	}
	

	
	@Override
	public void move() {
		this.x += dx;
		this.absy += dy;
		count++;
		if(x + width > 800 -  width|| x < 0) {
			dx = -dx;
		}
		if(count > 10) {
			dy = -dy;
			count = 0;
		}
	}
	@Override
	public void paint(Graphics g) {
//		g.setColor(Color.black);
//		g.fillRect((int)x, (int)rely, width, height);
		g.drawImage(iMonster, (int)x, (int)rely, (int)width, (int)height,null);
	}
}
