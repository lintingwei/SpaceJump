package Game_20170920_REV01;

import javax.swing.JFrame;
import javax.swing.Timer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {

	public static void main(String[] args) {
		final JFrame f = new JFrame();
		f.setTitle("Space jumper");
//		f.setSize(700, 650);
		f.setResizable(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameJPanel gm = new GameJPanel(700,800);
		gm.setPreferredSize(new Dimension(700,800));
		f.add(gm);
		f.pack();
		f.setVisible(true);
		Timer timer = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				f.repaint();
			}
		});
		timer.start();
	}
}
