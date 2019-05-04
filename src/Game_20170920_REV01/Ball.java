package Game_20170920_REV01;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Ball {

	private double x;
	private double rely;
	private double absy;
	private double width;
	private double height;
	private double bounceY;
	private double dx = 0;
	private double dy = 0;
	private double radius = 40;
	private double gravity = 9.8;
	private double dt = 0.1;
	private double boardTravel;
	private double base;
	private boolean run;
	private boolean bounce;
	private boolean spring;
	private boolean rocket;
	private boolean animation;
	private boolean fire;
	private boolean yell;
	private float frame;
	private int tester = 0;
	private Image ball;
	private BufferedImage spriteSheet;
	private BufferedImage[] player = new BufferedImage[12];
	private int ballWidth, ballHeight;
	private Clip bounceSound, rocketSound, springSound, monsterSound;
	private URL resourceURL, resourceURL2;
	private AudioInputStream audioStream3, audioStream4, audioStream5, audioStream6;

	public Ball(int i, int j, int width, int height) {
		resourceURL2 = this.getClass().getClassLoader().getResource("images/Doodle_spirit sheet.png");
		try {
			ball = ImageIO.read(resourceURL2);
			ballWidth = ball.getWidth(null) / 2;
			ballHeight = ball.getHeight(null) / 6;
			spriteSheet = ImageIO.read(resourceURL2);
			player[0] = grabImage(spriteSheet, 0, 0, ballWidth, ballHeight);
			player[1] = grabImage(spriteSheet, 0, 1, ballWidth, ballHeight);
			player[2] = grabImage(spriteSheet, 0, 2, ballWidth, ballHeight);
			player[3] = grabImage(spriteSheet, 0, 3, ballWidth, ballHeight);
			player[4] = grabImage(spriteSheet, 0, 4, ballWidth, ballHeight);
			player[5] = grabImage(spriteSheet, 0, 5, ballWidth, ballHeight);

			player[6] = grabImage(spriteSheet, 1, 0, ballWidth, ballHeight);
			player[7] = grabImage(spriteSheet, 1, 1, ballWidth, ballHeight);
			player[8] = grabImage(spriteSheet, 1, 2, ballWidth, ballHeight);
			player[9] = grabImage(spriteSheet, 1, 3, ballWidth, ballHeight);
			player[10] = grabImage(spriteSheet, 1, 4, ballWidth, ballHeight);
			player[11] = grabImage(spriteSheet, 1, 5, ballWidth, ballHeight);

		} catch (Exception e) {
			System.out.println("Ball iO input error!");
		}

		bounce = false;
		rocket = false;
		spring = false;
		yell = false;
		run = true;
		this.width = width;
		this.height = height;
		x = i;
		rely = j - 0.5 * this.height;
		absy = j - 0.5 * this.height;
		base = j - height + 100;

		try {
			resourceURL = this.getClass().getClassLoader().getResource("Music/jump.wav");
			audioStream3 = AudioSystem.getAudioInputStream(resourceURL);
			bounceSound = AudioSystem.getClip();
			bounceSound.open(audioStream3);
			setVol(1, bounceSound);
			audioStream4 = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("Music/rocket.wav"));
			rocketSound = AudioSystem.getClip();
			rocketSound.open(audioStream4);
			setVol(0.4, rocketSound);

			audioStream5 = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("Music/spring.wav"));
			springSound = AudioSystem.getClip();
			springSound.open(audioStream5);
			setVol(0.5, springSound);
//			audioStream6 = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("Music/monster.wav"));
//			monsterSound = AudioSystem.getClip();
//			monsterSound.open(audioStream6);
//			setVol(0.5, monsterSound);
		} catch (Exception e) {
			System.out.println("Rocket sound Exception!");
		}
	}

	public double getY() {
		return this.rely;
	}

	public void setYell(boolean input) {
		this.yell = input;
	}

	public boolean getYell() {
		return yell;
	}

	public void setFire(boolean fire) {
		this.fire = fire;
	}

	public void setRocket(boolean input) {
		this.rocket = input;
	}

	public void setSpring(boolean input) {
		this.spring = input;
	}

	public void move() {
		if (x + dx > width - 1) {
			x = radius - 1;
		} else if (x + dx < 0) {
			x = width - 1 - radius;
		} else {
			x += dx;
		}
		if (absy > base + height - radius - 1) {
			run = false;
			System.out.println("Game over!!");
		} else {
			// velocity formula
			dy += gravity * dt;
			// position formula
			absy += dy * dt + 0.5 * gravity * dt * dt;
		}
		rely = absy - base;
		if (bounce == true) {
			try {
				bounceSound.setMicrosecondPosition(0);
				// bounceSound.stop();
				// bounceSound.flush();
				// bounceSound.setFramePosition(0);
				bounceSound.start();
				bounce = false;
			} catch (Exception e) {
				System.out.println("Exception!!");
			}

		}
		if (rocket == true) {
			try {
				rocketSound.setMicrosecondPosition(0);
				rocketSound.start();
				rocket = false;
			} catch (Exception e) {
				System.out.println("Exception!!");
			}
		}
		if (spring == true) {
			try {
				springSound.setMicrosecondPosition(0);
				springSound.start();
				spring = false;
			} catch (Exception e) {
				System.out.println("Exception!!");
			}
		}

		if (yell == true) {
			try {
				monsterSound.setMicrosecondPosition(0);
				monsterSound.start();
				yell = false;
			} catch (Exception e) {
				System.out.println("Exception!!");
			}
		}

	}

	private static void setVol(double vol, Clip clip) {
		FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		float dB = (float) (Math.log(vol) / Math.log(10) * 20);
		gain.setValue(dB);
	}

	public void setBounce(boolean input) {
		this.bounce = input;
	}

	public boolean getRun() {
		return this.run;
	}

	public void setRun(boolean input) {
		this.run = input;
	}

	public double getBase() {
		return base;
	}

	public void setBase(double base) {
		this.base = base;
	}

	public double getHeight() {
		return height;
	}

	public double getBounceY() {
		return bounceY;
	}

	public void setBounceY(double bounceY) {
		this.bounceY = bounceY;
	}

	public double getAbsy() {
		return absy;
	}

	public double getRely() {
		return rely;
	}

	public void setRely(double rely) {
		this.rely = rely;
	}

	public void setBoardTravel(double boardTravel) {
		this.boardTravel = boardTravel;
	}

	public double getBoardTravel() {
		return boardTravel;
	}

	public double getX() {
		return x;
	}

	public double getRadius() {
		return radius;
	}

	public double getGravity() {
		return gravity;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.rely = y;
	}

	public void setAbsy(double absy) {
		this.absy = absy;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}

	public void moveRight() {
		if (dx < 2)
			dx = 0;
		if (dx < 5)
			dx += 2;

	}

	public void moveleft() {
		if (dx > -2)
			dx = 0;
		if (dx > -5)
			dx -= 2;
	}

	public BufferedImage grabImage(BufferedImage image, int col, int row, int width, int height) {
		BufferedImage img = image.getSubimage((col * ballWidth), (row * ballHeight), width, height);
		return img;
	}

	public void paint(Graphics g) {
		if (bounce == true) {
			animation = true;
		}
		if (animation == true) {
			tester = (int) (frame + 0.1);
			if (tester < 5)
				frame += .1;
			else
				frame = 0;

			if (fire == true) {
				if (dx > 0) {
					if (tester < 1) {
						System.out.println("fire");
						g.drawImage(player[11], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
								(int) (2 * radius), null);
					} else
						fire = false;
				} else {
					if (tester < 1) {
						System.out.println("fire");
						g.drawImage(player[5], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
								(int) (2 * radius), null);
					} else
						fire = false;
				}
			} else if (dx > 0) {
				if (tester == 0)
					g.drawImage(player[8], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
							(int) (2 * radius), null);
				else if (tester == 1)
					g.drawImage(player[9], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
							(int) (2 * radius), null);
				else if (tester == 2)
					g.drawImage(player[10], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
							(int) (2 * radius), null);
				else if (tester == 3)
					g.drawImage(player[9], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
							(int) (2 * radius), null);
				else if (tester == 4)
					g.drawImage(player[8], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
							(int) (2 * radius), null);
				else
					animation = false;
			} else {
				if (tester == 0)
					g.drawImage(player[2], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
							(int) (2 * radius), null);
				else if (tester == 1)
					g.drawImage(player[3], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
							(int) (2 * radius), null);
				else if (tester == 2)
					g.drawImage(player[4], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
							(int) (2 * radius), null);
				else if (tester == 3)
					g.drawImage(player[3], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
							(int) (2 * radius), null);
				else if (tester == 4)
					g.drawImage(player[2], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
							(int) (2 * radius), null);
				else
					animation = false;
			}
		} else if (fire == true) {

			tester = (int) (frame + 0.1);
			if (tester < 2)
				frame += .1;
			else
				frame = 0;

			if (dx > 0) {
				if (tester < 2)
					g.drawImage(player[11], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
							(int) (2 * radius), null);
				else
					fire = false;
			} else {
				if (tester < 2)
					g.drawImage(player[5], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
							(int) (2 * radius), null);
				else
					fire = false;
			}

		} else {
			if (dx > 0) {
				g.drawImage(player[6], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
						(int) (2 * radius), null);
			} else {
				g.drawImage(player[0], (int) (x - radius), (int) (rely - radius), (int) (2 * radius),
						(int) (2 * radius), null);
			}
		}
		// g.drawImage(img, x, y, width, height, observer);
		// g.drawImage(ball, (int)(x-radius), (int)(rely-radius),null);
	}

}
