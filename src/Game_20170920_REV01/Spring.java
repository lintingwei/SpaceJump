package Game_20170920_REV01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;


public class Spring extends Board{
	private static URL spring;
	private  URL spring1;
	private static Image iSpring;
	private static Image iSpring1;
	private int count;
	private int second;
	public Spring(int x,int y,double dx,int count) {
		
		super(x,y);
		try {
			if(spring==null)
				spring = this.getClass().getClassLoader().getResource("images/spring.png");
			if(spring1==null)
				spring1 = this.getClass().getClassLoader().getResource("images/spring1.png");
		}
		catch(Exception e){
			System.out.println("spring IO input error!");
		}
		
		try {
			if(iSpring==null)
				iSpring = ImageIO.read(spring);
			if(iSpring1==null)
				iSpring1 = ImageIO.read(spring1);
		}catch(Exception e) {
			System.out.println("spring IO input error!");
		}
		this.width = 50;
		this.height = 50;
	    this.count = count;
	    this.dx = dx;
	    this.second = 0;
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
	public boolean checkCollision(Ball b) {
		     
		if ( b.getY() + b.getRadius() > rely && b.getY() + b.getRadius() < rely + height)
		{	    
			
			if (b.getX() > x && b.getX() < x + width && b.getDy() > 0) {
				b.setSpring(true);
				double newDY = 80 * -1.5;
				b.setAbsy(b.getAbsy() - b.getRadius());
				b.setDy(newDY);
				second = 1;
				return true;
			}
		}
		return false;
	}
	public void paint(Graphics g) {
//		g.setColor(Color.BLACK);
//		g.fillRect((int)x, (int)rely, width, height);
		if(second < 15 && second !=0) {
			g.drawImage(iSpring, (int)x, (int)rely, (int)width, (int)height,null);
			second++;
		}
			
		else {
			g.drawImage(iSpring1, (int)x, (int)rely, (int)width, (int)height,null);
			second = 0;
		}
			
	}
}
