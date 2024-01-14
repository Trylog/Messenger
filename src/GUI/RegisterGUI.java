package GUI;

import Main.Main;
import Main.DatabaseComm;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class RegisterGUI extends JFrame implements ActionListener {

	JButton b1;
	JButton b2;
	JButton b3;
	JTextField t1;
	JTextField t2;
	JTextField t3;
	JLabel l2;
	String path;
	String fileName;
	RegisterGUI(){
		JLabel l1;




		l1 = new JLabel();
		l1.setText("Register");
		l1.setBounds(150,50,200,25);
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setFont(new Font("Arial", Font.BOLD, 24));

		t1 = new JTextField();
		t1.setBounds(200, 125, 100, 25);
		t1.setText("First Name");
		t1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(t1.getText().equals("First Name"))t1.selectAll();
			}
		});

		t2 = new JTextField();
		t2.setBounds(200, 175, 100, 25);
		t2.setText("Last Name");
		t2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(t2.getText().equals("Last Name"))t2.selectAll();
			}
		});

		t3 = new JTextField();
		t3.setBounds(200, 225, 100, 25);
		t3.setText("Password");
		t3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(t3.getText().equals("Password"))t3.selectAll();
			}
		});

		b1 = new JButton();
		b1.setText("Cancel");
		b1.setBounds(125, 350,100,25);
		b1.setFocusable(false);
		b1.addActionListener(this);

		b2 = new JButton();
		b2.setText("Register");
		b2.setBounds(275, 350,100,25);
		b2.setFocusable(false);
		b2.addActionListener(this);

		b3 = new JButton();
		b3.setText("Add Avatar");
		b3.setBounds(125, 275,100,25);
		b3.setFocusable(false);
		b3.addActionListener(this);

		l2 = new JLabel();
		l2.setText("");
		l2.setBounds(275, 275,100,25);
		l2.setFocusable(false);
		l2.setHorizontalAlignment(SwingConstants.CENTER);
		l2.setBorder(BorderFactory.createLineBorder(Color.gray, 1));

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(500, 450);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setTitle("Messenger");
		Image icon = new ImageIcon("src/textures/appIcon.png").getImage();
		this.setIconImage(icon);
		this.setVisible(true);

		this.add(b1);
		this.add(b2);
		this.add(b3);
		this.add(l1);
		this.add(l2);
		this.add(t3);
		this.add(t1);
		this.add(t2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b2){
			String firstName = t1.getText();
			String lastName = t2.getText();
			String password = t3.getText();
			if (password.length()>8 && !firstName.isBlank() && !firstName.equals("First Name") &&
					!lastName.isBlank() && !lastName.equals("Last Name") && !path.isEmpty()){
				String login = Main.databaseComm.register(firstName, lastName, password, path);
				if(login != null){
					if(JOptionPane.showConfirmDialog(null, "<html>" + "This is your login: " + login +
									"<br>" + "This is your password: " + password + "<br>" + "Do you want to log in?" + "</html>",
							"Credentials", JOptionPane.YES_NO_OPTION) == 1)
					{
						dispose();
						LoginGUI loginGUI = new LoginGUI();
					}else{
						dispose();
						Main.gui.mainGUI = new MainGUI();
					}
				}
			}

		} else if (e.getSource() == b3) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Choose Avatar file");
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Image (.png, .jpg, .jpeg, .bmp)", "png", "jpg", "jpeg", "bmp");
			fileChooser.setFileFilter(filter);

			if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				path = fileChooser.getSelectedFile().getAbsolutePath();
				fileName = fileChooser.getSelectedFile().getName();
				l2.setText(fileName);
			}
		} else if (e.getSource() == b1) {
			dispose();
			LoginGUI loginGUI = new LoginGUI();
		}
	}
}
