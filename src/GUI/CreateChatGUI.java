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

import static GUI.MainGUI.refreshAllConversationsList;
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
	private SpecialTextArea chatUserInfoTextArea;

	private JScrollPane portalUserScrollPane;
	private JPanel portalUserListPanel;
	private JTextField portalUrserSearchField;
	private ButtonGroup portalUserButtonGroup;
	private JPanel portalUserInfoTextPanle;
	private SpecialTextArea portalUserInfoTextArea;

	private JScrollPane chatModeratorScrollPane;
	private JPanel chatModeratorListPanel;
	private JTextField chatModeratorSearchField;
	private ButtonGroup chatModeratorButtonGroup;
	private JPanel chatModeratorInfoTextPanle;
	private SpecialTextArea chatModeratorInfoTextArea;
	private Image scaledImage;
	private JToggleButton invitationtoggleButton;
	private JLabel autoInvitationLabel;
	private String filePath = "";

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
		createChatButton.setBounds(border + 595,  border + 5, 300, 100);
		createChatButton.addActionListener(this);
		this.add(createChatButton);

		//Label trybu zapraszania
		autoInvitationLabel = new JLabel("Automatyczne zapraszanie:");
		autoInvitationLabel.setBounds(border +630,  border + 105, 200, 25);
		this.add(autoInvitationLabel);

		//Przełącznik trybu zapraszania
		invitationtoggleButton = new JToggleButton("OFF");
		invitationtoggleButton.setBounds(border + 795,  border + 105, 100, 25);
		invitationtoggleButton.addActionListener(this);
		this.add(invitationtoggleButton);

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
			UsersToggleButton portalUserButton = new UsersToggleButton(users.get(i).id,users.get(i).username,null);
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
		chatUserInfoTextArea = new SpecialTextArea();
		chatUserInfoTextPanle = createInfoTextArea(10 + 300,border + 400, "Wybrany użytkownik", 300, 60, chatUserInfoTextArea);

		// Pole tekstowe z informacjami o wybranym użytkowniku portalu
		portalUserInfoTextArea = new SpecialTextArea();
		portalUserInfoTextPanle = createInfoTextArea(10,border + 400, "Wybrany użytkownik", 300, 60, portalUserInfoTextArea);

		chatModeratorInfoTextArea = new SpecialTextArea();
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
			if (component instanceof UsersToggleButton) {
				UsersToggleButton chatUserButton = (UsersToggleButton) component;
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
			if (component instanceof UsersToggleButton) {
				UsersToggleButton portalUserButton = (UsersToggleButton) component;
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
			if (component instanceof UsersToggleButton) {
				UsersToggleButton chatModeratorButton = (UsersToggleButton) component;
				String buttonText = chatModeratorButton.getText().toLowerCase();
				chatModeratorButton.setVisible(buttonText.contains(searchPhrase));
			}
		}
		chatModeratorListPanel.revalidate();
		chatModeratorListPanel.repaint();
	}
	private Set<Integer> addedUsers = new HashSet<>();  // Zbiór przechowujący identyfikatory dodanych użytkowników

	private void addUserToChat() {
		String userName = portalUserInfoTextArea.getText();
		int userId = portalUserInfoTextArea.id;

		if (userName.equals("")) {
			JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika.", "Błąd", JOptionPane.ERROR_MESSAGE);
		} else if (addedUsers.contains(userId)) {
			JOptionPane.showMessageDialog(null, "Ten użytkownik został już dodany do czatu.", "Błąd", JOptionPane.ERROR_MESSAGE);
		} else {
			UsersToggleButton chatUserButton = new UsersToggleButton(userId, userName, null);
			chatUserButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			Dimension buttonSize = new Dimension(270, 30);
			chatUserButton.setPreferredSize(buttonSize);
			chatUserButton.setMaximumSize(buttonSize);
			chatUserButton.addActionListener(new CreateChatGUI.ChatUserButtonListener());
			chatUserListPanel.add(chatUserButton);
			chatUserButtonGroup.add(chatUserButton);

			// Dodanie identyfikatora użytkownika do zbioru
			addedUsers.add(userId);

			chatUserListPanel.revalidate();
			chatUserListPanel.repaint();
		}
	}

	private void removeUserFromChat() {
		String userNameToRemove = chatUserInfoTextArea.getText();
		int userId = chatUserInfoTextArea.id;

		if (userNameToRemove.equals("")) {
			JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
		} else {
			// Sprawdzenie, czy użytkownik jest dodany do czatu
			if (addedUsers.contains(userId)) {
				// Szukanie przycisku odpowiadającego użytkownikowi
				JToggleButton buttonToRemove = findUserButtonToRemoveUserFromChat(userNameToRemove);

				if (buttonToRemove != null) {
					// Usunięcie przycisku z panelu i grupy przycisków
					chatUserListPanel.remove(buttonToRemove);
					chatUserButtonGroup.remove(buttonToRemove);

					// Usunięcie identyfikatora użytkownika ze zbioru
					addedUsers.remove(userId);

					chatUserInfoTextArea.setText("");

					// Odświeżenie widoku
					chatUserListPanel.revalidate();
					chatUserListPanel.repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Nie znaleziono użytkownika do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
				}
				downModeratorPermissionToUser(userNameToRemove, userId, true);
			} else {
				JOptionPane.showMessageDialog(null, "Ten użytkownik nie jest dodany do czatu.", "Błąd", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	// Metoda pomocnicza do znalezienia przycisku odpowiadającego użytkownikowi
	private JToggleButton findUserButtonToRemoveUserFromChat(String userName) {
		for (Enumeration<AbstractButton> buttons = chatUserButtonGroup.getElements(); buttons.hasMoreElements();) {
			UsersToggleButton button = (UsersToggleButton) buttons.nextElement();
			if (button.getText().equals(userName)) {
				return button;
			}
		}
		return null;
	}

	private Set<Integer> addedModerators = new HashSet<>();  // Zbiór przechowujący identyfikatory dodanych moderatorów

	private void upUserPermissionToModerator() {
		String userName = chatUserInfoTextArea.getText();
		int userId = chatUserInfoTextArea.id;

		if (userName.equals("")) {
			JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika.", "Błąd", JOptionPane.ERROR_MESSAGE);
		} else if (addedModerators.contains(userId)) {
			JOptionPane.showMessageDialog(null, "Ten użytkownik został już dodany jako moderator.", "Błąd", JOptionPane.ERROR_MESSAGE);
		} else {
			UsersToggleButton chatModeratorButton = new UsersToggleButton(userId, userName, null);
			chatModeratorButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			Dimension buttonSize = new Dimension(270, 30);
			chatModeratorButton.setPreferredSize(buttonSize);
			chatModeratorButton.setMaximumSize(buttonSize);
			chatModeratorButton.addActionListener(new CreateChatGUI.ChatModeratorButtonListener());
			chatModeratorListPanel.add(chatModeratorButton);
			chatModeratorButtonGroup.add(chatModeratorButton);

			// Dodanie identyfikatora moderatora do zbioru
			addedModerators.add(userId);

			chatModeratorListPanel.revalidate();
			chatModeratorListPanel.repaint();
		}
	}

	private void downModeratorPermissionToUser(String userNameToRemove, int userId, boolean extraRemove) {
		if (userNameToRemove.equals("")) {
			JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
		} else {
			// Sprawdzenie, czy użytkownik jest dodany jako moderator
			if (addedModerators.contains(userId)) {
				// Szukanie przycisku odpowiadającego użytkownikowi
				JToggleButton buttonToRemove = findUserButtonToDownModeratorPermissionToUser(userNameToRemove);

				if (buttonToRemove != null) {
					// Usunięcie przycisku z panelu i grupy przycisków
					chatModeratorListPanel.remove(buttonToRemove);
					chatModeratorButtonGroup.remove(buttonToRemove);

					// Usunięcie identyfikatora moderatora ze zbioru
					addedModerators.remove(userId);

					chatModeratorInfoTextArea.setText("");

					// Odświeżenie widoku
					chatModeratorListPanel.revalidate();
					chatModeratorListPanel.repaint();
				} else {
					if (!extraRemove) {
						JOptionPane.showMessageDialog(null, "Nie znaleziono użytkownika do usunięcia.", "Błąd", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Ten użytkownik nie jest dodany jako moderator.", "Błąd", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	// Metoda pomocnicza do znalezienia przycisku odpowiadającego użytkownikowi
	private JToggleButton findUserButtonToDownModeratorPermissionToUser(String userName) {
		for (Enumeration<AbstractButton> buttons = chatModeratorButtonGroup.getElements(); buttons.hasMoreElements();) {
			UsersToggleButton button = (UsersToggleButton) buttons.nextElement();
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
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); ///todo tylko grafika do wyboru ::Kamil/Michał
			int result = fileChooser.showOpenDialog(this);
			File file = fileChooser.getSelectedFile(); // Scieżka do utworzenia avatara
			filePath = file.getPath();
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
			downModeratorPermissionToUser(chatModeratorInfoTextArea.getText(),chatModeratorInfoTextArea.id,false);
		}
		if (e.getSource() == createChatButton) {
			boolean invitation;
			if(invitationtoggleButton.getText().equals("ON")){
				invitation = true;
			}else {
				invitation = false;
			}
			createChat(chatNameTextField.getText(),filePath,invitation);
			ArrayList<Integer> addedUsersID = new ArrayList<>(addedUsers);
			addUserListToChat(chatNameTextField.getText(),addedUsersID);
			ArrayList<Integer> addedModeratorsID = new ArrayList<>(addedModerators);
			addModeratorListToChat(chatNameTextField.getText(),addedModeratorsID);
			refreshAllConversationsList();
			dispose();
		}
		if (e.getSource() == invitationtoggleButton) {
			// Zmiana tekstu w zależności od stanu przełącznika
			if (invitationtoggleButton.isSelected()) {
				invitationtoggleButton.setText("ON");
				// Wykonaj dodatkowe działania, gdy przełącznik jest w stanie ON
			} else {
				invitationtoggleButton.setText("OFF");
				// Wykonaj dodatkowe działania, gdy przełącznik jest w stanie OFF
			}
		}
	}

	private class ChatUserButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Możesz dodać tu kod obsługi zdarzenia dla wybranego użytkownika
			// W tej chwili zaznaczony przycisk można sprawdzić używając:
			UsersToggleButton chatUserButton = (UsersToggleButton) e.getSource();
			chatUserInfoTextArea.setText(chatUserButton.getText());
			chatUserInfoTextArea.id = chatUserButton.id;
		}
	}
	private class PortalUserButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Możesz dodać tu kod obsługi zdarzenia dla wybranego użytkownika
			// W tej chwili zaznaczony przycisk można sprawdzić używając:
			UsersToggleButton portalUserButton = (UsersToggleButton) e.getSource();
			portalUserInfoTextArea.setText(portalUserButton.getText());
			portalUserInfoTextArea.id = portalUserButton.id;
		}
	}
	private class ChatModeratorButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Możesz dodać tu kod obsługi zdarzenia dla wybranego użytkownika
			// W tej chwili zaznaczony przycisk można sprawdzić używając:
			UsersToggleButton chatModeratorButton = (UsersToggleButton) e.getSource();
			chatModeratorInfoTextArea.setText(chatModeratorButton.getText());
			chatModeratorInfoTextArea.id = chatModeratorButton.id;
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

	private class UsersToggleButton extends JToggleButton {
		int id;
		String name;
		Icon avatar;
		UsersToggleButton(int id,String name,Icon avatar){
			this.id = id;
			this.name = name;
			this.avatar = avatar;
			setText(name);
		}
	}

	private class SpecialTextArea extends JTextArea {
		public int id;
		public String text;
	}
}