package Game_20170920_REV01;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Menu {
	private int width;
	private int height;
	private int btnWidth;
	private int btnHeight;
	private int score;

	public Rectangle playButton, playButton_back;
	public Rectangle helpButton, helpButton_back;
	public Rectangle quitButton;
	public Rectangle backButton;
	private boolean mouseDetectPlay = false;
	private boolean mouseDetectHelp = false;
	private boolean mouseDetectExit = false;
	private boolean mouseDetectBack = false;
	//private Color c =new Color(1f,0f,0f,.5f );
	private Color detect =new Color(255,255,255,127 );
	private Color noDetect =new Color(0,0,0,0 );

	public Menu(int width, int height) {
		btnWidth = 200;
		btnHeight = 50;
		this.width = width;
		this.height = height;
		playButton = new Rectangle(width / 2 - btnWidth / 2, 350 - btnHeight / 2, btnWidth, btnHeight);
		helpButton = new Rectangle(width / 2 - btnWidth / 2, 450 - btnHeight / 2, btnWidth, btnHeight);
		quitButton = new Rectangle(width / 2 - btnWidth / 2, 550 - btnHeight / 2, btnWidth, btnHeight);
		backButton = new Rectangle(450, 720 - btnHeight / 2, btnWidth, btnHeight);
	}
	
	
	public void setScore(int score) {
		this.score = score;
	}
	public boolean getMouseDetectPlay() {
		return mouseDetectPlay;
	}
	
	public void setMouseDetectPlay(boolean input) {
		this.mouseDetectPlay = input;
	}
	public boolean getMouseDetectHelp() {
		return mouseDetectHelp;
	}
	
	public void setMouseDetectHelp(boolean input) {
		this.mouseDetectHelp = input;
	}
	public boolean getMouseDetectExit() {
		return mouseDetectExit;
	}
	
	public void setMouseDetectExit(boolean input) {
		this.mouseDetectExit = input;
	}
	
	public void setMouseDetectBack(boolean input) {
		this.mouseDetectBack = input;
	}
	public int getBackButtonX() {
		return backButton.x;
	}

	public int getBackButtonY() {
		return backButton.y;
	}
	public int getPlayButtonX() {
		return playButton.x;
	}

	public int getPlayButtonY() {
		return playButton.y;
	}

	public int getHelpButtonY() {
		return helpButton.y;
	}

	public int getQuitButtonY() {
		return quitButton.y;
	}

	public int getButtonWidth() {
		return (int) playButton.getWidth();
	}

	public int getButtonHeight() {
		return (int) playButton.getHeight();
	}
	
	public int getBackX() {
		return (int) backButton.x;
	}
	public int getBackY() {
		return (int) backButton.y;
	}
	
	
	
	public void paint(Graphics g) {
		String str0 = "SUPER JUMPER", str1 = "PLAY", str2 = "HOW TO PLAY", str3 = "EXIT";


		if (mouseDetectPlay == true) {
			g.setColor(detect);
			g.fillRect(width / 2 - btnWidth / 2, 350 - btnHeight / 2, btnWidth, btnHeight);
		}else {
			g.setColor(noDetect);
			g.fillRect(width / 2 - btnWidth / 2, 350 - btnHeight / 2, btnWidth, btnHeight);

		}
		if (mouseDetectHelp == true) {
			g.setColor(detect);
			g.fillRect(width / 2 - btnWidth / 2, 450 - btnHeight / 2, btnWidth, btnHeight);
		}else {
			g.setColor(noDetect);
			g.fillRect(width / 2 - btnWidth / 2, 450 - btnHeight / 2, btnWidth, btnHeight);
		}
		if (mouseDetectExit == true) {
			g.setColor(detect);
			g.fillRect(width / 2 - btnWidth / 2, 550 - btnHeight / 2, btnWidth, btnHeight);
		}else {
			g.setColor(noDetect);
			g.fillRect(width / 2 - btnWidth / 2, 550 - btnHeight / 2, btnWidth, btnHeight);
		}
		
		// Set up Option button
		Font fntBTN = new Font("arial", Font.BOLD, 25);
		FontMetrics metrics = g.getFontMetrics(fntBTN);
		g.setColor(Color.WHITE);
		int x1 = playButton.x + (playButton.width - metrics.stringWidth(str1)) / 2;
		int y1 = playButton.y + ((playButton.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(fntBTN);
		g.drawString(str1, x1, y1);

		int x2 = helpButton.x + (helpButton.width - metrics.stringWidth(str2)) / 2;
		int y2 = helpButton.y + ((helpButton.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(fntBTN);
		g.drawString(str2, x2, y2);

		int x3 = quitButton.x + (quitButton.width - metrics.stringWidth(str3)) / 2;
		int y3 = quitButton.y + ((quitButton.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(fntBTN);
		g.drawString(str3, x3, y3);

		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(playButton);
		g2d.draw(helpButton);
		g2d.draw(quitButton);

	}

	public void paintHelp(Graphics g) {
		String str1 = "Back";
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.WHITE);
		g2d.draw(backButton);
		// Set up Option button
		Font fntBTN = new Font("arial", Font.BOLD, 25);
		FontMetrics metrics = g.getFontMetrics(fntBTN);
		
		int x1 = backButton.x + (playButton.width - metrics.stringWidth(str1)) / 2;
		int y1 = backButton.y + ((playButton.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(fntBTN);
		g.setColor(Color.WHITE);
		g.drawString(str1, x1, y1);
		
		
		if (mouseDetectBack == true) {
			g.setColor(detect);
			g.fillRect(450, 720 - btnHeight / 2, btnWidth, btnHeight);
		}else {
			g.setColor(noDetect);
			g.fillRect(450, 720 - btnHeight / 2, btnWidth, btnHeight);
		}
	}
	public void paintGameOver(Graphics g) {
		String str0 = Integer.toString(score);
		// Setup score
			Font fntTitle = new Font("arial", Font.BOLD, 100);
			FontMetrics metrics = g.getFontMetrics(fntTitle);
			int x0 = this.width / 2 - metrics.stringWidth(str0) / 2;
			int y0 = 380;
			g.setFont(fntTitle);
			g.setColor(Color.ORANGE);
			g.drawString(str0, x0, y0);
			Graphics2D g2d = (Graphics2D) g;
			g.setColor(Color.WHITE);
			g2d.draw(helpButton);
			g2d.draw(quitButton);
		
		String str2 = "PLAY AGAIN", str3 = "EXIT";
		if (mouseDetectPlay == true) {
			g.setColor(detect);
			g.fillRect(width / 2 - btnWidth / 2, 450 - btnHeight / 2, btnWidth, btnHeight);
		}else {
			g.setColor(noDetect);
			g.fillRect(width / 2 - btnWidth / 2, 450 - btnHeight / 2, btnWidth, btnHeight);
		}
		if (mouseDetectExit == true) {
			g.setColor(detect);
			g.fillRect(width / 2 - btnWidth / 2, 550 - btnHeight / 2, btnWidth, btnHeight);
		}else {
			g.setColor(noDetect);
			g.fillRect(width / 2 - btnWidth / 2, 550 - btnHeight / 2, btnWidth, btnHeight);
		}
		
		// Set up Option button
		Font fntBTN = new Font("arial", Font.BOLD, 25);
		metrics = g.getFontMetrics(fntBTN);
		g.setColor(Color.WHITE);


		int x2 = helpButton.x + (helpButton.width - metrics.stringWidth(str2)) / 2;
		int y2 = helpButton.y + ((helpButton.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(fntBTN);
		g.drawString(str2, x2, y2);

		int x3 = quitButton.x + (quitButton.width - metrics.stringWidth(str3)) / 2;
		int y3 = quitButton.y + ((quitButton.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.setFont(fntBTN);
		g.drawString(str3, x3, y3);


	}
}
