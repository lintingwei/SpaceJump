package Game_20170920_REV01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;



public class BoardB extends Board{
	private static URL board;
	private static Image iBoard;
	private int count;
	public BoardB(int x,int y,double dx,int count) {
		super(x,y);
		if(board==null)
			board = this.getClass().getClassLoader().getResource("images/boardB.png");
		//System.out.println(board);
		try {
			if(iBoard==null)
				iBoard = ImageIO.read(board);
		}catch(Exception e) {
			System.out.println("boardB IO input error!");
		}
		this.dx = dx;
		this.count = count;  
	}
	
	@Override
	public void move() {
		this.x += dx;
		count++;
		if(count >100) {
			dx = -dx;
			count = 0;
		}
	}
	
	@Override
	public void paint(Graphics g) {
//		g.setColor(Color.BLUE);
//		g.fillRect((int)x, (int)rely, width, height);
		g.drawImage(iBoard, (int)x, (int)rely, (int)width, (int)height,null);
	}
}
