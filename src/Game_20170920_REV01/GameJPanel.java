package Game_20170920_REV01;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImageFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;

public class GameJPanel extends JPanel {
	private int now;
	private double criterionY;
	private double height;
	private double width;
	private boolean changeY;
	private boolean checkY;
	private int sCount;
	private int jCount;
	private Ball ac;
	private Board[] bm;
	private Board[] s;
	private Board[] jet;
	private Board[] monster;
	private JetFly jetFly;
	private Bullet bullet;
	private boolean jetFlyFall;
	private int score;
	private static Image backgroundMenu, backgroundPlay, backgroudGameOver, backgroundHelp;
	private Clip test, gameOver, click, shoot, monsterSound;
	private URL resourceURL, resourceURL2, resourceURL3;
	private AudioInputStream audioStream, audioStream2, audioStream3, audioStream4, audioStream5;
	private Menu menu;

	private static enum STATE {
		MENU, PLAY, HELP, EXIT, GAMEOVER
	}

	public static STATE state = STATE.MENU;

	public GameJPanel(int width, int height) {

		try {

			resourceURL = this.getClass().getClassLoader().getResource("Music/Breeze.wav");
			audioStream = AudioSystem.getAudioInputStream(resourceURL);
			test = AudioSystem.getClip();
			test.open(audioStream);
			setVol(0.1, test);
			test.loop(Clip.LOOP_CONTINUOUSLY);
			resourceURL2 = this.getClass().getClassLoader().getResource("Music/gameover.wav");
			audioStream2 = AudioSystem.getAudioInputStream(resourceURL2);
			gameOver = AudioSystem.getClip();
			gameOver.open(audioStream2);
			setVol(0.5, gameOver);

			audioStream3 = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("Music/click.wav"));
			click = AudioSystem.getClip();
			click.open(audioStream3);
			setVol(0.4, click);

			audioStream4 = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("Music/shoot.wav"));
			shoot = AudioSystem.getClip();
			shoot.open(audioStream4);
			setVol(0.4, click);

			audioStream5 = AudioSystem
					.getAudioInputStream(getClass().getClassLoader().getResource("Music/monster.wav"));
			monsterSound = AudioSystem.getClip();
			monsterSound.open(audioStream5);
			setVol(0.4, click);
		} catch (Exception e) {
			System.out.println("Background sound Exception!");
		}

		try {
			resourceURL2 = this.getClass().getClassLoader().getResource("images/menu.png");
			backgroundMenu = ImageIO.read(resourceURL2);
			backgroundHelp = ImageIO.read(this.getClass().getClassLoader().getResource("images/menuHelp.png"));
			backgroundPlay = ImageIO.read(this.getClass().getClassLoader().getResource("images/moon.jpg"));
			backgroudGameOver = ImageIO.read(this.getClass().getClassLoader().getResource("images/gameover.png"));
		} catch (Exception e) {
			System.out.println("background not found");
		}

		this.width = width;
		this.height = height;
		menu = new Menu(width, height);
		score = 0;
		this.addKeyListener(new CMyListener1());
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		this.addMouseListener(new CMyListener2());
		this.addMouseMotionListener(new CMyListener2());

		CMyListener1 listener1 = new CMyListener1();
		this.addKeyListener(listener1);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		Timer t1 = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (state == STATE.PLAY) {
					score = (int) now - (int) ac.getBase() - (int) (1.5 * height) + 100;
					transform();
					//backgroundCheck();
					ac.move();
					for (int i = 0; i < jCount; i++) {
						jet[i].move();
						if (jet[i].checkCollision(ac)) {
							jetFly = new JetFly(ac.getX(), ac.getY());
							jetFlyFall = false;
							for (int j = i; j < jCount - 1; j++) {
								jet[j] = jet[j + 1];
							}
							jCount--;
						}
					}
					if (bullet != null) {
						bullet.move();
						for (int i = 0; i < monster.length; i++) {
							if (bullet.checkCollision(monster[i])) {
								for (int j = i; j < monster.length - 1; j++) {
									monster[j] = monster[j + 1];
								}
								monster = Arrays.copyOf(monster, monster.length - 1);
								bullet = null;
								break;
							}
						}
						if (bullet != null) {
							if (bullet.getY() <= 0)
								bullet = null;
						}
					}
					if (jetFly != null) {
						if (!jetFlyFall) {
							if (ac.getDx() > 0) {
								jetFly.setX(ac.getX() - ac.getRadius() / 2 - jetFly.getWidth());
								jetFly.setY(ac.getY() - ac.getRadius());
							} else {
								jetFly.setX(ac.getX() + ac.getRadius() / 2);
								jetFly.setY(ac.getY() - ac.getRadius());
							}

						}
						if (ac.getDy() >= 0)
							jetFlyFall = true;
						if (jetFlyFall) {
							jetFly.fall();
						}
						if (jetFly.getY() > 800) {
							jetFlyFall = false;
							jetFly = null;
						}
					}

					for (int i = 0; i < monster.length; i++) {
						monster[i].move();
						if (monster[i].checkCollision(ac) && jetFly == null) {
							monsterSound.setMicrosecondPosition(0);
							monsterSound.start();
							state = STATE.GAMEOVER;
							break;
						}
					}
					for (int i = 0; i < sCount; i++) {
						s[i].move();
						if (s[i].checkCollision(ac))
							ac.setBounce(true);
					}
					for (int i = 0; i < bm.length; i++) {
						bm[i].move();
						if (bm[i].checkCollision(ac)) {
							ac.setBounce(true);
							if (bm[i] instanceof BoardW) {
								for (int j = i; j < bm.length - 1; j++) {
									bm[j] = bm[j + 1];
								}
								bm = Arrays.copyOf(bm, bm.length - 1);
							}
						}
					}
					if (checkY == true) {
						if (ac.getAbsy() < criterionY) {
							changeY = true;
							checkY = false;
						}
					}
					if (!ac.getRun()) {
						try {
							gameOver.setMicrosecondPosition(0);
							gameOver.start();
						} catch (Exception e) {
							System.out.println("Exception!!");
						}
						state = STATE.GAMEOVER;
					}
				}

			}
		});
		t1.start();
	}

	class CMyListener2 implements MouseListener, MouseMotionListener {
		public void mouseMoved(MouseEvent e) {
			int mx = e.getX();
			int my = e.getY();
			if (state == STATE.MENU) {

				// Play Button
				if (mx >= menu.getPlayButtonX() && mx <= menu.getPlayButtonX() + menu.getButtonWidth()) {
					if (my >= menu.getPlayButtonY() && my <= menu.getPlayButtonY() + menu.getButtonHeight()) {
						menu.setMouseDetectPlay(true);
						menu.setMouseDetectHelp(false);
						menu.setMouseDetectExit(false);
					} else if (my >= menu.getHelpButtonY() && my <= menu.getHelpButtonY() + menu.getButtonHeight()) {
						menu.setMouseDetectPlay(false);
						menu.setMouseDetectHelp(true);
						menu.setMouseDetectExit(false);
					} else if (my >= menu.getQuitButtonY() && my <= menu.getQuitButtonY() + menu.getButtonHeight()) {
						menu.setMouseDetectPlay(false);
						menu.setMouseDetectHelp(false);
						menu.setMouseDetectExit(true);
					} else {
						menu.setMouseDetectPlay(false);
						menu.setMouseDetectHelp(false);
						menu.setMouseDetectExit(false);
					}
				} else {
					menu.setMouseDetectPlay(false);
					menu.setMouseDetectHelp(false);
					menu.setMouseDetectExit(false);
				}
			} else if (state == STATE.GAMEOVER) {
				if (mx >= menu.getPlayButtonX() && mx <= menu.getPlayButtonX() + menu.getButtonWidth()) {
					if (my >= menu.getHelpButtonY() && my <= menu.getHelpButtonY() + menu.getButtonHeight()) {
						menu.setMouseDetectPlay(true);
						menu.setMouseDetectHelp(false);
						menu.setMouseDetectExit(false);
					} else if (my >= menu.getQuitButtonY() && my <= menu.getQuitButtonY() + menu.getButtonHeight()) {
						menu.setMouseDetectPlay(false);
						menu.setMouseDetectHelp(false);
						menu.setMouseDetectExit(true);
					} else {
						menu.setMouseDetectPlay(false);
						menu.setMouseDetectHelp(false);
						menu.setMouseDetectExit(false);
					}
				} else {
					menu.setMouseDetectPlay(false);
					menu.setMouseDetectHelp(false);
					menu.setMouseDetectExit(false);
				}
			}
			else if (state == STATE.HELP) {
				if (mx >= menu.getBackButtonX() && mx <= menu.getBackButtonX() + menu.getButtonWidth()) {
					if (my >= menu.getBackButtonY() && my <= menu.getBackButtonY() + menu.getButtonHeight()) {
						menu.setMouseDetectBack(true);
					}
				}else {
					menu.setMouseDetectBack(false);
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int mx = e.getX();
			int my = e.getY();

			if (state == STATE.MENU) {

				// Play Button
				if (mx >= menu.getPlayButtonX() && mx <= menu.getPlayButtonX() + menu.getButtonWidth()) {
					if (my >= menu.getPlayButtonY() && my <= menu.getPlayButtonY() + menu.getButtonHeight()) {
						try {
							click.setMicrosecondPosition(0);
							click.start();
						} catch (Exception e1) {
							System.out.println("Exception!!");
						}
						init();
						if (!ac.getRun())
							ac.setRun(true);
						state = STATE.PLAY;
					} else if (my >= menu.getHelpButtonY() && my <= menu.getHelpButtonY() + menu.getButtonHeight()) {
						try {
							click.setMicrosecondPosition(0);
							click.start();
						} catch (Exception e1) {
							System.out.println("Exception!!");
						}
						state = STATE.HELP;
					} else if (my >= menu.getQuitButtonY() && my <= menu.getQuitButtonY() + menu.getButtonHeight()) {
						try {
							click.setMicrosecondPosition(0);
							click.start();
						} catch (Exception e1) {
							System.out.println("Exception!!");
						}
						state = STATE.EXIT;
					}
				}
			}

			else if (state == STATE.GAMEOVER) {
				if (mx >= menu.getPlayButtonX() && mx <= menu.getPlayButtonX() + menu.getButtonWidth()) {
					if (my >= menu.getHelpButtonY() && my <= menu.getHelpButtonY() + menu.getButtonHeight()) {
						try {
							click.setMicrosecondPosition(0);
							click.start();
						} catch (Exception e1) {
							System.out.println("Exception!!");
						}
						init();
						if (!ac.getRun())
							ac.setRun(true);
						state = STATE.PLAY;
						menu.setMouseDetectPlay(false);
					} else if (my >= menu.getQuitButtonY() && my <= menu.getQuitButtonY() + menu.getButtonHeight()) {
						try {
							click.setMicrosecondPosition(0);
							click.start();
						} catch (Exception e1) {
							System.out.println("Exception!!");
						}
						state = STATE.EXIT;
						menu.setMouseDetectExit(false);
					}
				}
			} else if (state == STATE.HELP) {
				if (mx >= menu.getBackX() && mx <= menu.getBackX() + menu.getButtonWidth()) {
					if (my >= menu.getBackY() && my <= menu.getBackY() + menu.getButtonHeight()) {
						
						try {
							click.setMicrosecondPosition(0);
							click.start();
						} catch (Exception e1) {
							System.out.println("Exception!!");
						}
						state = STATE.MENU;
					}
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	class CMyListener1 extends KeyAdapter implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (ac.getDx() > 0)
					ac.setDx(0);
				ac.moveleft();
				break;
			case KeyEvent.VK_RIGHT:
				if (ac.getDx() < 0)
					ac.setDx(0);
				ac.moveRight();

				break;
			case KeyEvent.VK_UP:
				if (bullet == null) {
					bullet = new Bullet(ac.getX(), ac.getRely() - ac.getRadius());
					ac.setFire(true);
					try {
						shoot.setMicrosecondPosition(0);
						shoot.start();
					} catch (Exception e1) {
						System.out.println("Exception!!");
					}
				}
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
	}



	private static void setVol(double vol, Clip clip) {
		FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		float dB = (float) (Math.log(vol) / Math.log(10) * 20);
		gain.setValue(dB);
	}

	public void init() {
		System.out.println("start");
		score = 0;
		changeY = false;
		checkY = true;
		bm = new Board[500];
		s = new Board[500];
		jet = new Board[100];
		monster = new Board[7];
		now = 0;
		sCount = 0;
		jCount = 0;
		bullet = null;
		for (int i = 0; i < bm.length; i++) {
			int x = (int) (Math.random() * 600);
			now += Math.abs((i / 100) * 50 - 250);
			if (i == 490) {
				bm[i] = new BoardR(290, now);
			}

			else if (i > 350) {
				bm[i] = new BoardR(x, now);
				if (i == 450) {
					monster[0] = new Monster(325, now + 200);
				} else if (i == 360) {
					monster[1] = new Monster(325, now + 200);
				}

				else if (((int) (Math.random() * 15) == 0)) {
					int bias = (int) (Math.random() * 50);
					x += bias;
					s[sCount++] = new Spring(x, now - 35, bm[i].getDx(), 0);
				}
				else if(((int) (Math.random() * 40) == 0)) {
					int bias = (int) (Math.random() * 50);
					x += bias;
					jet[jCount++] = new Jet(x, now - 70, bm[i].getDx(), 0);
				}
			} else if (i > 250) {
				int count = (int) (Math.random() * 50);
				double speed = (((int) (Math.random() * 2) == 0) ? -1 : 1) * (Math.random() / 2 + 0.5);
				bm[i] = new BoardB(x, now, speed, count);
				if (i == 270) {
					monster[2] = new Monster(325, now + 200);
				}
				if (((int) (Math.random() * 15) == 0)) {
					int bias = (int) (Math.random() * 50);
					x += bias;
					s[sCount++] = new Spring(x, now - 35, bm[i].getDx(), count);
				}
				else if(((int) (Math.random() * 30) == 0)) {
					int bias = (int) (Math.random() * 50);
					x += bias;
					jet[jCount++] = new Jet(x, now - 70, bm[i].getDx(), count);
				}
			} else if (i > 200) {
				bm[i] = new BoardW(x, now);
				if (i == 230) {
					monster[3] = new Monster(325, now + 200);
				}
			} else {
				if (i == 180) {
					monster[4] = new Monster(325, now + 200);
				}
				if (i == 120) {
					monster[5] = new Monster(325, now + 200);
					monster[6] = new Monster(0, now + 400);
				}
				int r = (int) (Math.random() * 3);
				if (r == 0) {
					bm[i] = new BoardR(x, now);
					if (((int) (Math.random() * 15) == 0)) {
						int bias = (int) (Math.random() * 50);
						x += bias;
						s[sCount++] = new Spring(x, now - 35, bm[i].getDx(), 0);
					}
					else if(((int) (Math.random() * 30) == 0)) {
						int bias = (int) (Math.random() * 50);
						x += bias;
						jet[jCount++] = new Jet(x, now - 70, bm[i].getDx(), 0);
					}
				} else if (r == 1) {
					int count = (int) (Math.random() * 50);
					double speed = (((int) (Math.random() * 2) == 0) ? -1 : 1) * (Math.random() / 2 + 0.5);
					bm[i] = new BoardB(x, now, speed, count);
					if (((int) (Math.random() * 15) == 0)) {
						int bias = (int) (Math.random() * 50);
						x += bias;
						s[sCount++] = new Spring(x, now - 35, bm[i].getDx(), count);
					}
					else if(((int) (Math.random() * 30) == 0)) {
						int bias = (int) (Math.random() * 50);
						x += bias;
						jet[jCount++] = new Jet(x, now - 70, bm[i].getDx(), count);
					}
				} else
					bm[i] = new BoardW(x, now);
			}
		}
		criterionY = (double) now - height;
		double initialY = (int) now - height / 2;
		ac = new Ball(325, (int) initialY, (int) width, (int) height);
	}

	public void transform() {
		if (state == STATE.PLAY) {
			for (int i = 0; i < jCount; i++) {
				jet[i].setBoard(jet[i].getAbsY() - ac.getBase());
			}
			for (int i = 0; i < monster.length; i++) {
				monster[i].setBoard(monster[i].getAbsY() - ac.getBase());
			}
			for (int i = 0; i < bm.length; i++) {
				bm[i].setBoard(bm[i].getAbsY() - ac.getBase());
			}
			for (int i = 0; i < sCount; i++) {
				s[i].setBoard(s[i].getAbsY() - ac.getBase());
			}
			if (changeY == true) {
				if (ac.getDy() < 0) {
					criterionY = ac.getAbsy();
					ac.setBase(ac.getAbsy() - 0.4 * height);
				} else {
					checkY = true;
					changeY = false;
				}
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (state == STATE.PLAY) {

			g2d.drawImage(backgroundPlay, 0, 0, (int) width, (int) height, null);

			for (int i = 0; i < bm.length; i++) {
				bm[i].paint(g);
			}
			for (int i = 0; i < sCount; i++) {
				s[i].paint(g);
			}
			for (int i = 0; i < jCount; i++) {
				jet[i].paint(g);
			}
			for (int i = 0; i < monster.length; i++) {
				monster[i].paint(g);
			}

			if (jetFly != null) {
				jetFly.paint(g);
			}
			if (bullet != null) {
				bullet.paint(g);
			}
			if (ac.getRun() == true)
				ac.paint(g);

			String r = Integer.toString(score);
			Font font = new Font("arial", Font.BOLD, 32);
			g.setFont(font);
			g.setColor(Color.black);
			g.drawString(r, (int) width - 150 + 2, 50);
			g.setColor(new Color(198, 226, 255));
			g.drawString(r, (int) width - 150, 50);

		} else if (state == STATE.MENU) {
			g2d.drawImage(backgroundMenu, 0, 0, (int) width, (int) height, null);
			menu.paint(g);
		} else if (state == STATE.HELP) {
			g2d.drawImage(backgroundHelp, 0, 0, (int) width, (int) height, null);
			menu.paintHelp(g);
		} else if (state == STATE.GAMEOVER) {
			g2d.drawImage(backgroudGameOver, 0, 0, (int) width, (int) height, null);
			menu.setScore(score);
			menu.paintGameOver(g);
		} else if (state == STATE.EXIT) {
			System.exit(0);
		}
	}
}
