package GUI;

import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

 class LoginGUI extends JFrame implements ActionListener {

	JLabel l1;
	JLabel l2;
	JTextField t1;
	JTextField t2;
	JButton b1;
	JButton b2;

	LoginGUI(){

		l1 = new JLabel();
		l1.setText("Messenger " + Main.version);
		l1.setBounds(150,50,200,25);
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setFont(new Font("Arial", Font.BOLD, 24));

		l2 = new JLabel();
		l2.setText("<html>by Michał Bernacki-Janson, <br>Kamil Godek and Jakub Klawon</html>");
		l2.setBounds(150,75,200,50);
		l2.setHorizontalAlignment(SwingConstants.CENTER);
		l2.setFont(new Font("Arial", Font.PLAIN, 10));


		t1 = new JTextField();
		t1.setBounds(200, 175, 100, 25);
		t1.setText("Login");
		t1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				t1.selectAll();
			}
		});

		t2 = new JTextField();
		t2.setBounds(200, 225, 100, 25);
		t2.setText("Password");
		t2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				t2.selectAll();
			}
		});

		b1 = new JButton();
		b1.setText("Login");
		b1.setBounds(125, 300,100,25);
		b1.setFocusable(false);
		b1.addActionListener(this);

		b2 = new JButton();
		b2.setText("Register");
		b2.setBounds(275, 300,100,25);
		b2.setFocusable(false);
		b2.addActionListener(this);


		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(500, 450);
		this.setLocationRelativeTo(null);
		this.setTitle("Messenger");
		Image icon = new ImageIcon("src/textures/appIcon.png").getImage();
		this.setIconImage(icon);
		this.setVisible(true);

		this.add(b1);
		this.add(b2);
		this.add(l1);
		this.add(l2);
		this.add(t1);
		this.add(t2);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1){
			String login = t1.getText();
			String password = t2.getText();
			if(Main.databaseComm.login(login, password)){ ///todo zmiana procedury logowania, nie dawac wszystkich procedur, tylko te 4 wybrane wczesniej ale dac selecta na wszystko
				dispose();
				Main.gui.mainGUI = new MainGUI();
			}else{
				//wrong login credentials
				JFrame f = new JFrame();
				JOptionPane.showMessageDialog(f,"Login unsuccessful");
			}

		} else if (e.getSource() == b2) {
			String login = t1.getText();
			String password = t2.getText();

		}
	}
}
