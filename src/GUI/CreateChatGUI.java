package GUI;

import Main.DatabaseComm;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import static Main.DatabaseComm.*;

class CreateChatGUI extends JFrame implements ActionListener {
	private JPanel chatNameTextPanel;
	private JTextField chatNameTextField;
	private JPanel avatarPanel;
	private JLabel avatarLabel;
	private JButton chooseAvatarButton;
	private JButton confirmNameAndAvatarButton;
	private JButton createChatButton;

	private JButton addUserToChat;
	private JButton removeUserFromChat;
	private JButton upUserPermissionToModerator;
	private JButton downModeratorPermissionToUser;

	private JScrollPane chatUserScrollPane;
	private JPanel chatUserListPanel;
	private JTextField chatUrserSearchField;
	private ButtonGroup chatUserButtonGroup;
	private JPanel chatUserInfoTextPanle;
	private JTextArea chatUserInfoTextArea;

	private JScrollPane portalUserScrollPane;
	private JPanel portalUserListPanel;
	private JTextField portalUrserSearchField;
	private ButtonGroup portalUserButtonGroup;
	private JPanel portalUserInfoTextPanle;
	private JTextArea portalUserInfoTextArea;

	private JScrollPane chatModeratorScrollPane;
	private JPanel chatModeratorListPanel;
	private JTextField chatModeratorSearchField;
	private ButtonGroup chatModeratorButtonGroup;
	private JPanel chatModeratorInfoTextPanle;
	private JTextArea chatModeratorInfoTextArea;
	private Image scaledImage;

	CreateChatGUI() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(940, 580);
		this.setLocationRelativeTo(null);
		this.setTitle("Okno tworzenia czatu");
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

		// Przycisk do zatwierdzenia podstawowych danych
		createChatButton = new JButton("Utwórz");
		createChatButton.setBounds(border + 595,  border + 5, 300, 125);
		createChatButton.addActionListener(this);
		this.add(createChatButton);

		// Panel z przewijaniem dla użytkowników czatu
		chatUserScrollPane = new JScrollPane();
		chatUserScrollPane.setBorder(BorderFactory.createTitledBorder("Lista Użytkowników Czatu"));
		chatUserScrollPane.setBounds(border+300, border+140, 300, 200);
		chatUserScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Panel z przewijaniem dla użytkowników poralu
		portalUserScrollPane = new JScrollPane();
		portalUserScrollPane.setBorder(BorderFactory.createTitledBorder("Lista Użytkowników Portalu"));
		portalUserScrollPane.setBounds(border, border+140, 300, 200);
		portalUserScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Panel z przewijaniem dla moderatorów czatu
		chatModeratorScrollPane = new JScrollPane();
		chatModeratorScrollPane.setBorder(BorderFactory.createTitledBorder("Lista Moderatorów Czatu"));
		chatModeratorScrollPane.setBounds(border+600, border+140, 300, 200);
		chatModeratorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


		// Panel z komponentami użytkowników czatu(dodaj swoje komponenty)
		chatUserListPanel = new JPanel();
		chatUserListPanel.setLayout(new BoxLayout(chatUserListPanel, BoxLayout.Y_AXIS));
		chatUserScrollPane.setViewportView(chatUserListPanel);

		// Panel z komponentami użytkowników portalu (dodaj swoje komponenty)
		portalUserListPanel = new JPanel();
		portalUserListPanel.setLayout(new BoxLayout(portalUserListPanel, BoxLayout.Y_AXIS));
		portalUserScrollPane.setViewportView(portalUserListPanel);

		// Panel z komponentami moderatora chatu (dodaj swoje komponenty)
		chatModeratorListPanel = new JPanel();
		chatModeratorListPanel.setLayout(new BoxLayout(chatModeratorListPanel, BoxLayout.Y_AXIS));
		chatModeratorScrollPane.setViewportView(chatModeratorListPanel);

		// Grupa dla przycisków użytkowników czatu
		chatUserButtonGroup = new ButtonGroup();

		// Grupa dla przycisków użytkowników portalu
		portalUserButtonGroup = new ButtonGroup();

		// Grupa dla przycisków moderatora czatu
		chatModeratorButtonGroup = new ButtonGroup();

		// Dodawanie użytkowników
		ArrayList <DatabaseComm.User> users = getPortalUsersNames();
		for (int i = 0; i < users.size(); i++) {
			JToggleButton portalUserButton = new JToggleButton(users.get(i).username);
			portalUserButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Ustawienie przycisku na lewo
			Dimension buttonSize = new Dimension(270, 30);
			portalUserButton.setPreferredSize(buttonSize);
			portalUserButton.setMaximumSize(buttonSize);
			portalUserButton.addActionListener(new CreateChatGUI.PortalUserButtonListener());
			portalUserListPanel.add(portalUserButton);
			portalUserButtonGroup.add(portalUserButton);
		}

		// Panel z wyszukiwaniem użytkowników czatu
		JPanel chatUserSearchPanel = new JPanel();
		chatUserSearchPanel.setBorder(BorderFactory.createTitledBorder("Wyszukaj Użytkownika"));
		chatUserSearchPanel.setBounds(border + 300, 340 + border, 300, 60);

		// Panel z wyszukiwaniem użytkowników portalu
		JPanel portalUserSearchPanel = new JPanel();
		portalUserSearchPanel.setBorder(BorderFactory.createTitledBorder("Wyszukaj Użytkownika"));
		portalUserSearchPanel.setBounds(border, 340 + border, 300, 60);

		// Panel z wyszukiwaniem moderatora chatu
		JPanel chatModeratorSearchPanel = new JPanel();
		chatModeratorSearchPanel.setBorder(BorderFactory.createTitledBorder("Wyszukaj Użytkownika"));
		chatModeratorSearchPanel.setBounds(border+600, 340 + border, 300, 60);

		// Pole tekstowe do wprowadzania frazy wyszukiwania użytkowników czatu
		chatUrserSearchField = new JTextField();
		chatUrserSearchField.setPreferredSize(new Dimension(280, 25));
		chatUrserSearchField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				performSearchChatUsers();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				performSearchChatUsers();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				performSearchChatUsers();
			}
		});
		chatUserSearchPanel.add(chatUrserSearchField);

		// Pole tekstowe do wprowadzania frazy wyszukiwania użytkowników portalu
		portalUrserSearchField = new JTextField();
		portalUrserSearchField.setPreferredSize(new Dimension(280, 25));
		portalUrserSearchField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				performSearchPortalUsers();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				performSearchPortalUsers();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				performSearchPortalUsers();
			}
		});
		portalUserSearchPanel.add(portalUrserSearchField);

		// Pole tekstowe do wprowadzania frazy wyszukiwania moderatora chatu
		chatModeratorSearchField = new JTextField();
		chatModeratorSearchField.setPreferredSize(new Dimension(280, 25));
		chatModeratorSearchField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				performSearchChatModerator();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				performSearchChatModerator();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				performSearchChatModerator();
			}
		});
		chatModeratorSearchPanel.add(chatModeratorSearchField);

		// Dodanie komponentów do ramki
		this.add(chatUserScrollPane);
		this.add(chatUserSearchPanel);
		this.add(portalUserScrollPane);
		this.add(portalUserSearchPanel);
		this.add(chatModeratorScrollPane);
		this.add(chatModeratorSearchPanel);

		// Pole tekstowe z informacjami o wybranym użytkowniku czatu
		chatUserInfoTextArea = new JTextArea();
		chatUserInfoTextPanle = createInfoTextArea(10 + 300,border + 400, "Wybrany użytkownik", 300, 60, chatUserInfoTextArea);

		// Pole tekstowe z informacjami o wybranym użytkowniku portalu
		portalUserInfoTextArea = new JTextArea();
		portalUserInfoTextPanle = createInfoTextArea(10,border + 400, "Wybrany użytkownik", 300, 60, portalUserInfoTextArea);

		chatModeratorInfoTextArea = new JTextArea();
		chatModeratorInfoTextPanle = createInfoTextArea(10 + 600,border + 400, "Wybrany użytkownik", 300, 60, chatModeratorInfoTextArea);

		addUserToChat = new JButton("Dodaj użytkownika do czatu");
		createButton(border,border+460,300,60,addUserToChat);
		removeUserFromChat = new JButton("Usuń użytkownika z czatu");
		createButton(border + 300,border+460,300,30,removeUserFromChat);
		upUserPermissionToModerator = new JButton("Podnieś uprawnienia użytkownika do Moderatora");
		createButton(border + 300,border+490,300,30,upUserPermissionToModerator);
		downModeratorPermissionToUser = new JButton("Obniż uprawnienia Moderatora do Użytkownika");
		createButton(border + 600,border+460,300,60,downModeratorPermissionToUser);

		createChatButton.setEnabled(false);
		addUserToChat.setEnabled(false);
		removeUserFromChat.setEnabled(false);
		upUserPermissionToModerator.setEnabled(false);
		downModeratorPermissionToUser.setEnabled(false);
	}

	private void performSearchChatUsers() {
		String searchPhrase = chatUrserSearchField.getText().toLowerCase();
		Component[] components = chatUserListPanel.getComponents();

		for (Component component : components) {
			if (component instanceof JToggleButton) {
				JToggleButton chatUserButton = (JToggleButton) component;
				String buttonText = chatUserButton.getText().toLowerCase();
				chatUserButton.setVisible(buttonText.contains(searchPhrase));
			}
		}
		chatUserListPanel.revalidate();
		chatUserListPanel.repaint();
	}
	private void performSearchPortalUsers() {
		String searchPhrase = portalUrserSearchField.getText().toLowerCase();
		Component[] components = portalUserListPanel.getComponents();

		for (Component component : components) {
			if (component instanceof JToggleButton) {
				JToggleButton portalUserButton = (JToggleButton) component;
				String buttonText = portalUserButton.getText().toLowerCase();
				portalUserButton.setVisible(buttonText.contains(searchPhrase));
			}
		}
		portalUserListPanel.revalidate();
		portalUserListPanel.repaint();
	}

	private void performSearchChatModerator() {
		String searchPhrase = chatModeratorSearchField.getText().toLowerCase();
		Component[] components = chatModeratorListPanel.getComponents();

		for (Component component : components) {
			if (component instanceof JToggleButton) {
				JToggleButton chatModeratorButton = (JToggleButton) component;
				String buttonText = chatModeratorButton.getText().toLowerCase();
				chatModeratorButton.setVisible(buttonText.contains(searchPhrase));
			}
		}
		chatModeratorListPanel.revalidate();
		chatModeratorListPanel.repaint();
	}
	 private Set<String> addedUsers = new HashSet<>();
	 private void addUserToChat() {
		 String userName = portalUserInfoTextArea.getText();

		 if (userName.equals("")) {
			 JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika.", "Błąd", JOptionPane.ERROR_MESSAGE);
		 } else if (addedUsers.contains(userName)) {
			 JOptionPane.showMessageDialog(null, "Ten użytkownik został już dodany do czatu.", "Błąd", JOptionPane.ERROR_MESSAGE);
		 } else {
			 JToggleButton chatUserButton = new JToggleButton(userName);
			 chatUserButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			 Dimension buttonSize = new Dimension(270, 30);
			 chatUserButton.setPreferredSize(buttonSize);
			 chatUserButton.setMaximumSize(buttonSize);
			 chatUserButton.addActionListener(new CreateChatGUI.ChatUserButtonListener());
			 chatUserListPanel.add(chatUserButton);
			 chatUserButtonGroup.add(chatUserButton);

			 // Dodanie użytkownika do zbioru
			 addedUsers.add(userName);

			 chatUserListPanel.revalidate();
			 chatUserListPanel.repaint();
		 }
	 }
	private void removeUserFromChat() {
		String userNameToRemove = chatUserInfoTextArea.getText();

		if (userNameToRemove.equals("")) {
			JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
		} else {
			// Szukanie przycisku odpowiadającego użytkownikowi
			JToggleButton buttonToRemove = findUserButtonToRemoveUserFromChat(userNameToRemove);

			if (buttonToRemove != null) {
				// Usunięcie przycisku z panelu i grupy przycisków
				chatUserListPanel.remove(buttonToRemove);
				chatUserButtonGroup.remove(buttonToRemove);
				addedUsers.remove(userNameToRemove);

				chatUserInfoTextArea.setText("");

				// Odświeżenie widoku
				chatUserListPanel.revalidate();
				chatUserListPanel.repaint();
			} else {
				JOptionPane.showMessageDialog(null, "Nie znaleziono użytkownika do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
			}
			downModeratorPermissionToUser(userNameToRemove,true);
		}
	}
	// Metoda pomocnicza do znalezienia przycisku odpowiadającego użytkownikowi
	private JToggleButton findUserButtonToRemoveUserFromChat(String userName) {
		for (Enumeration<AbstractButton> buttons = chatUserButtonGroup.getElements(); buttons.hasMoreElements();) {
			JToggleButton button = (JToggleButton) buttons.nextElement();
			if (button.getText().equals(userName)) {
				return button;
			}
		}
		return null;
	}
	private Set<String> addedModerators = new HashSet<>();

	private void upUserPermissionToModerator() {
		String userName = chatUserInfoTextArea.getText();

		if (userName.equals("")) {
			JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika.", "Błąd", JOptionPane.ERROR_MESSAGE);
		} else if (addedModerators.contains(userName)) {
			JOptionPane.showMessageDialog(null, "Ten użytkownik został już dodany jako moderator.", "Błąd", JOptionPane.ERROR_MESSAGE);
		} else {
			JToggleButton chatModeratorButton = new JToggleButton(userName);
			chatModeratorButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			Dimension buttonSize = new Dimension(270, 30);
			chatModeratorButton.setPreferredSize(buttonSize);
			chatModeratorButton.setMaximumSize(buttonSize);
			chatModeratorButton.addActionListener(new CreateChatGUI.ChatModeratorButtonListener());
			chatModeratorListPanel.add(chatModeratorButton);
			chatModeratorButtonGroup.add(chatModeratorButton);

			// Dodanie moderatora do zbioru
			addedModerators.add(userName);

			chatModeratorListPanel.revalidate();
			chatModeratorListPanel.repaint();
		}
	}
	private void downModeratorPermissionToUser(String userNameToRemove, boolean extraRemove){
		if (userNameToRemove.equals("")) {
			JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
		} else {
			// Szukanie przycisku odpowiadającego użytkownikowi
			JToggleButton buttonToRemove = findUserButtonToDownModeratorPermissionToUser(userNameToRemove);

			if (buttonToRemove != null) {
				// Usunięcie przycisku z panelu i grupy przycisków
				chatModeratorListPanel.remove(buttonToRemove);
				chatModeratorButtonGroup.remove(buttonToRemove);
				addedModerators.remove(userNameToRemove);

				chatModeratorInfoTextArea.setText("");

				// Odświeżenie widoku
				chatModeratorListPanel.revalidate();
				chatModeratorListPanel.repaint();
			} else {
				if(!extraRemove){
					JOptionPane.showMessageDialog(null, "Nie znaleziono użytkownika do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	// Metoda pomocnicza do znalezienia przycisku odpowiadającego użytkownikowi
	private JToggleButton findUserButtonToDownModeratorPermissionToUser(String userName) {
		for (Enumeration<AbstractButton> buttons = chatModeratorButtonGroup.getElements(); buttons.hasMoreElements();) {
			JToggleButton button = (JToggleButton) buttons.nextElement();
			if (button.getText().equals(userName)) {
				return button;
			}
		}
		return null;
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
			if(avatarLabel == null ){
				JOptionPane.showMessageDialog(null, "Nie wybrano avatara.", "Błąd", JOptionPane.ERROR_MESSAGE);
			}else if(chatNameTextField.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Nie wybrano nazwy czatu.", "Błąd", JOptionPane.ERROR_MESSAGE);

			}else{
				String chatName = chatNameTextField.getText();
				createChatButton.setEnabled(true);
				addUserToChat.setEnabled(true);
				removeUserFromChat.setEnabled(true);
				upUserPermissionToModerator.setEnabled(true);
				downModeratorPermissionToUser.setEnabled(true);

				confirmNameAndAvatarButton.setEnabled(false);
				chooseAvatarButton.setEnabled(false);
				chatNameTextField.setEnabled(false);
			}
		}
		if (e.getSource() == addUserToChat) {
			addUserToChat();
		}
		if (e.getSource() == removeUserFromChat) {
			removeUserFromChat();

		}
		if (e.getSource() == upUserPermissionToModerator) {
			upUserPermissionToModerator();
		}
		if (e.getSource() == downModeratorPermissionToUser) {
			downModeratorPermissionToUser(chatModeratorInfoTextArea.getText(),false);
		}
		if (e.getSource() == createChatButton) {
			createChat(chatNameTextField.getText(),scaledImage); ///TODO po wywolaniu powinien byc update listy konwersacji uzytkownika?
			ArrayList<String> addedUsersList = new ArrayList<>(addedUsers);
			addUserListToChat(chatNameTextField.getText(),addedUsersList);
			ArrayList<String> addedModeratorsList = new ArrayList<>(addedModerators);
			addModeratorListToChat(chatNameTextField.getText(),addedModeratorsList);
			dispose();
		}
	}

	private class ChatUserButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Możesz dodać tu kod obsługi zdarzenia dla wybranego użytkownika
			// W tej chwili zaznaczony przycisk można sprawdzić używając:
			JToggleButton chatUserButton = (JToggleButton) e.getSource();
			chatUserInfoTextArea.setText(chatUserButton.getText());
		}
	}
	private class PortalUserButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Możesz dodać tu kod obsługi zdarzenia dla wybranego użytkownika
			// W tej chwili zaznaczony przycisk można sprawdzić używając:
			JToggleButton portalUserButton = (JToggleButton) e.getSource();
			portalUserInfoTextArea.setText(portalUserButton.getText());
		}
	}
	private class ChatModeratorButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Możesz dodać tu kod obsługi zdarzenia dla wybranego użytkownika
			// W tej chwili zaznaczony przycisk można sprawdzić używając:
			JToggleButton chatModeratorButton = (JToggleButton) e.getSource();
			chatModeratorInfoTextArea.setText(chatModeratorButton.getText());
		}
	}
	private JPanel createInfoTextArea(int x, int y, String title, int width, int height, JTextArea infoTextArea ) {
		JPanel infoTextPanel = new JPanel();

		infoTextPanel.setBorder(BorderFactory.createTitledBorder(title));
		infoTextPanel.setBounds(x, y, width, height);

		infoTextArea.setEditable(false);

		Font newFont = new Font("Arial", Font.BOLD, 12); // Zmień 16 na dowolny rozmiar czcionki
		Dimension dimension = new Dimension(width - 20, height - 40);
		infoTextArea.setPreferredSize(dimension);
		infoTextArea.setFont(newFont);

		infoTextPanel.add(infoTextArea);
		this.add(infoTextPanel);

		return infoTextPanel;
	}
	private void createButton(int x, int y, int width,int height, JButton button) {
		button.setBounds(x,y,width,height);
		button.addActionListener(this);
		this.add(button);
	}
}