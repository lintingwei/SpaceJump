package Game_20170920_REV01;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;

public class BoardR extends Board {
	private static URL board;
	private static Image iBoard;
	public BoardR(int x,int y) {
		super(x,y);
		if(board==null)
			board = this.getClass().getClassLoader().getResource("images/boardR.png");
		try {
			if(iBoard==null)
				iBoard = ImageIO.read(board);
		}catch(Exception e) {
			System.out.println("boardR IO input error!");
		}
		this.dx = 0;
	}
	@Override
	public void move() {
		
	}
	@Override
	public void paint(Graphics g) {
//		g.setColor(Color.RED);
//		g.fillRect((int)x, (int)rely, width, height);
		g.drawImage(iBoard, (int)x, (int)rely, (int)width, (int)height,null);
	}
}
