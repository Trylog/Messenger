package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static Main.DatabaseComm.*;

class ChatDataModyfieGUI extends JFrame implements ActionListener {

	 private JPanel chatNameTextPanel;
	 private JTextField chatNameTextField;
	 private JPanel avatarPanel;
	 private JLabel avatarLabel;
	 private JButton chooseAvatarButton;
	 private JButton confirmNameAndAvatarButton;


	private Image scaledImage;
	private String oldChatName;
	ChatDataModyfieGUI(String chatName) {
		oldChatName = chatName;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(640, 200);
		this.setLocationRelativeTo(null);
		this.setTitle("Modyfikacja danych czatu - " + chatName);
		Image icon = new ImageIcon("src/textures/appIcon.png").getImage();
		this.setIconImage(icon);
		this.setVisible(true);

		int border = 10;

		// Panel dla pola tekstowego
		chatNameTextPanel = new JPanel();
		chatNameTextPanel.setBounds(border, border, 450, 50 + border);
		chatNameTextPanel.setBorder(BorderFactory.createTitledBorder("Nazwa czatu"));
		this.add(chatNameTextPanel);

		// Panel tekstowy do wprowadzania nazwy czatu
		chatNameTextField = new JTextField();
		Dimension preferSize = new Dimension(425, 25);
		chatNameTextField.setPreferredSize(preferSize);
		chatNameTextPanel.add(chatNameTextField);

		// Etykieta dla avatara (wykorzystuje JLabel jako kontener)
		avatarPanel = new JPanel();
		avatarPanel.setBounds( border + 460, border, 135, 135);
		avatarPanel.setBorder(BorderFactory.createTitledBorder("Avatar"));
		this.add(avatarPanel);

		// Przycisk do wyboru avatara
		chooseAvatarButton = new JButton("Wybierz Avatar");
		chooseAvatarButton.setBounds(border, border + 70, 450, 25);
		chooseAvatarButton.addActionListener(this);
		this.add(chooseAvatarButton);

		// Przycisk do zatwierdzenia podstawowych danych
		confirmNameAndAvatarButton = new JButton("Zatwierdź nazwę i Avatara");
		confirmNameAndAvatarButton.setBounds(border, 4 * border + 75, 450, 25);
		confirmNameAndAvatarButton.addActionListener(this);
		this.add(confirmNameAndAvatarButton);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
			if (e.getSource() == chooseAvatarButton) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					ImageIcon originalIcon = new ImageIcon(selectedFile.getAbsolutePath());

					// Przeskalowanie obrazu
					scaledImage = originalIcon.getImage().getScaledInstance(avatarPanel.getWidth() - 20, avatarPanel.getHeight() - 30, Image.SCALE_SMOOTH);
					ImageIcon scaledIcon = new ImageIcon(scaledImage);

					// Ustawienie ikony na JLabel wewnątrz avatarPanel
					avatarLabel = new JLabel(scaledIcon);
					avatarPanel.removeAll();
					avatarPanel.add(avatarLabel);

					// Odświeżenie widoku
					avatarPanel.revalidate();
					avatarPanel.repaint();
				}
			}
			if (e.getSource() == confirmNameAndAvatarButton) {
				if(avatarLabel == null && chatNameTextField.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Nie wprowadzono żadnych zmian.", "Błąd", JOptionPane.ERROR_MESSAGE);
				}else{
					String chatName = chatNameTextField.getText();
					if(chatName.equals("")){
						setConversationNewData(oldChatName,oldChatName,scaledImage);
					}else if(avatarLabel == null){
						setConversationNewData(oldChatName,chatName,null);
					}else{
						setConversationNewData(oldChatName,chatName,scaledImage);
					}
					dispose();
				}
			}
	}
}