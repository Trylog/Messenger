package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 class ChatDataModyfieGUI extends JFrame implements ActionListener {
	ChatDataModyfieGUI(String chatName) {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(640, 640);
		this.setLocationRelativeTo(null);
		this.setTitle("Modyfikacja danych czatu - " + chatName);
		Image icon = new ImageIcon("src/textures/appIcon.png").getImage();
		this.setIconImage(icon);
		this.setVisible(true);

		int border = 10;

		//TODO Możliwość zmiany Nazwy czatu + możliwość zmiany awatara czatu
	}
	@Override
	public void actionPerformed(ActionEvent e) {
			/*if(e.getSource() == addUserToConversationButton){
				System.out.println("Dodanie użytkownika");
			}*/
	}
}