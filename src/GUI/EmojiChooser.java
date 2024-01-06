package GUI;
import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

  class EmojiChooser extends JFrame implements ActionListener {
	private int msId;
	EmojiChooser(int msId){

		this.msId = msId;
		this.setIconImage(MainGUI.icon);
		this.setTitle("Wybierz reakcję");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new GridLayout(2,4));
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		JButton b1 = new JButton("\uD83D\uDC4D");
		JButton b2 = new JButton("\uD83D\uDE42");
		JButton b3 = new JButton("❤");
		JButton b4 = new JButton("❔");
		JButton b5 = new JButton("\uD83D\uDC4E");
		JButton b6 = new JButton("\uD83D\uDE22");
		JButton b7 = new JButton("\uD83D\uDC4F");
		JButton b8 = new JButton("\uD83D\uDC7B");

		b1.setFont(new Font("Serif", Font.PLAIN, 26));
		b2.setFont(new Font("Serif", Font.PLAIN, 26));
		b3.setFont(new Font("Serif", Font.PLAIN, 26));
		b4.setFont(new Font("Serif", Font.PLAIN, 26));
		b5.setFont(new Font("Serif", Font.PLAIN, 26));
		b6.setFont(new Font("Serif", Font.PLAIN, 26));
		b7.setFont(new Font("Serif", Font.PLAIN, 26));
		b8.setFont(new Font("Serif", Font.PLAIN, 26));

		b1.setFocusable(false);
		b2.setFocusable(false);
		b3.setFocusable(false);
		b4.setFocusable(false);
		b5.setFocusable(false);
		b6.setFocusable(false);
		b7.setFocusable(false);
		b8.setFocusable(false);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);

		this.add(b1);
		this.add(b2);
		this.add(b3);
		this.add(b4);
		this.add(b5);
		this.add(b6);
		this.add(b7);
		this.add(b8);

		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(((JButton)e.getSource()).getText());
		Main.databaseComm.sendReaction(msId, ((JButton) e.getSource()).getText());
		dispose();
	}
}