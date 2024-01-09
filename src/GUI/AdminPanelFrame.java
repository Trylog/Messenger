package GUI;

import Main.DatabaseComm;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import static Main.DatabaseComm.*;

class AdminPanelFrame extends JFrame implements ActionListener {
	private JScrollPane userScrollPane;
	private JPanel userListPanel;
	private JTextField urserSearchField;
	private ButtonGroup userButtonGroup;

	private JScrollPane chatScrollPane;
	private JPanel chatListPanel;
	private JTextField chatSearchField;
	private ButtonGroup chatButtonGroup;

	private JScrollPane moderatorScrollPane;
	private JPanel moderatorListPanel;
	private JTextField moderatorSearchField;
	private ButtonGroup moderatorButtonGroup;

	private JPanel userInfoTextPanle;
	private SpecialTextArea userInfoTextArea;
	private JPanel chatInfoTextPanel;
	private SpecialTextArea chatInfoTextArea;
	private JPanel moderatorInfoTextPanel;
	private SpecialTextArea moderatorInfoTextArea;

	private JButton removeUserFormChatButton;
	private JButton removeUserFormPortalButton;

	AdminPanelFrame(){
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(640, 640);
		this.setLocationRelativeTo(null);
		this.setTitle("Messenger - Panel Administratora");
		Image icon = new ImageIcon("src/textures/appIcon.png").getImage();
		this.setIconImage(icon);
		this.setVisible(true);

		int border = 10;

		// Panel z przewijaniem dla użytkowników
		userScrollPane = new JScrollPane();
		userScrollPane.setBorder(BorderFactory.createTitledBorder("Lista Użytkowników"));
		userScrollPane.setBounds(border, border, 300, 400);
		userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Panel z przewijaniem dla czatu
		chatScrollPane = new JScrollPane();
		chatScrollPane.setBorder(BorderFactory.createTitledBorder("Lista Czatów"));
		chatScrollPane.setBounds(border+300, border, 300, 170);
		chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Panel z przewijaniem dla moderatorów
		moderatorScrollPane = new JScrollPane();
		moderatorScrollPane.setBorder(BorderFactory.createTitledBorder("Lista Moderatorów czatu"));
		moderatorScrollPane.setBounds(border+300, border+230, 300, 170);
		moderatorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Panel z komponentami użytkowników (dodaj swoje komponenty)
		userListPanel = new JPanel();
		userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
		userScrollPane.setViewportView(userListPanel);

		// Panel z komponentami czatu (dodaj swoje komponenty)
		chatListPanel = new JPanel();
		chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
		chatScrollPane.setViewportView(chatListPanel);

		// Panel z komponentami moderatorów (dodaj swoje komponenty)
		moderatorListPanel = new JPanel();
		moderatorListPanel.setLayout(new BoxLayout(moderatorListPanel, BoxLayout.Y_AXIS));
		moderatorScrollPane.setViewportView(moderatorListPanel);

		// Grupa dla przycisków użytkowników
		userButtonGroup = new ButtonGroup();
		//Dodawanie Urzytkowników portalu
		refreshUserList();

		// Grupa dla przycisków czatów
		chatButtonGroup = new ButtonGroup();
		// Załadowanie listy czatów
		refreshChatList();

		// Grupa dla przycisków moderatorów
		moderatorButtonGroup = new ButtonGroup();
		//Lista tworzona dopiero w momencie wybrania

		// Panel z wyszukiwaniem użytkowników
		JPanel userSearchPanel = new JPanel();
		userSearchPanel.setBorder(BorderFactory.createTitledBorder("Wyszukaj Użytkownika"));
		userSearchPanel.setBounds(border, 400 + border, 300, 60);

		// Panel z wyszukiwaniem czatów
		JPanel chatSearchPanel = new JPanel();
		chatSearchPanel.setBorder(BorderFactory.createTitledBorder("Wyszukaj Czat"));
		chatSearchPanel.setBounds(border+300, 170 + border, 300, 60);

		// Panel z wyszukiwaniem moderatorów
		JPanel moderatorSearchPanel = new JPanel();
		moderatorSearchPanel.setBorder(BorderFactory.createTitledBorder("Wyszukaj Moderatora"));
		moderatorSearchPanel.setBounds(border+300, 400 + border, 300, 60);

		// Pole tekstowe do wprowadzania frazy wyszukiwania użytkowników
		urserSearchField = new JTextField();
		urserSearchField.setPreferredSize(new Dimension(280, 25));
		urserSearchField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				performSearchUsers();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				performSearchUsers();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				performSearchUsers();
			}
		});
		userSearchPanel.add(urserSearchField);

		// Pole tekstowe do wprowadzania frazy wyszukiwania czatów
		chatSearchField = new JTextField();
		chatSearchField.setPreferredSize(new Dimension(280, 25));
		chatSearchField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				performSearchChats();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				performSearchChats();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				performSearchChats();
			}
		});
		chatSearchPanel.add(chatSearchField);

		// Pole tekstowe do wprowadzania frazy wyszukiwania moderatorów
		moderatorSearchField = new JTextField();
		moderatorSearchField.setPreferredSize(new Dimension(280, 25));
		moderatorSearchField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				performSearchModerators();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				performSearchModerators();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				performSearchModerators();
			}
		});
		moderatorSearchPanel.add(moderatorSearchField);

		// Dodanie komponentów do ramki
		this.add(userScrollPane);
		this.add(userSearchPanel);
		this.add(chatScrollPane);
		this.add(chatSearchPanel);
		this.add(moderatorScrollPane);
		this.add(moderatorSearchPanel);

		int move = 20;

		// Pole tekstowe z informacjami o wybranym użytkowniku
		userInfoTextArea = new SpecialTextArea();
		userInfoTextPanle = createInfoTextArea(move,border * 4 + 440, "Wybrany użytkownik", 180, 60, userInfoTextArea);

		// Pole tekstowe z informacjami o wybranym czacie
		chatInfoTextArea = new SpecialTextArea();
		chatInfoTextPanel = createInfoTextArea(200 + move, border * 4 + 440, "Wybrany czat", 180, 60, chatInfoTextArea);

		// Pole tekstowe z informacjami o wybranym moderatorze
		moderatorInfoTextArea = new SpecialTextArea();
		moderatorInfoTextPanel = createInfoTextArea(400 + move, border * 4 + 440, "Wybrany moderator", 180, 60,moderatorInfoTextArea);

		//Przyciski Aktywnosci
		int move2 = 80;

		removeUserFormPortalButton = new JButton("Usuń Użytkownika z Portalu");
		removeUserFormPortalButton.setBounds(0 + move2,550+ border,200,30);
		removeUserFormPortalButton.addActionListener(this);
		this.add(removeUserFormPortalButton);

		removeUserFormChatButton = new JButton("Usuń Moderatora z Czatu");
		removeUserFormChatButton.setBounds(250 + move2,550+ border,200,30);
		removeUserFormChatButton.addActionListener(this);
		this.add(removeUserFormChatButton);

	}

	//Funkcje odpowiedzialne za odświerzanie listy urzytkowników portalu
	private void refreshUserList(){
		for (Enumeration<AbstractButton> buttons = userButtonGroup.getElements(); buttons.hasMoreElements(); ) {
			AbstractButton button = buttons.nextElement();
			userListPanel.remove(button);
			userButtonGroup.remove(button);
		}
		//userListPanel.revalidate();
		//userListPanel.repaint();
		addUserToList();
	}
	private void addUserToList(){
		ArrayList <DatabaseComm.User> users = getPortalUsersNames();
		for (int i = 0; i < users.size(); i++) {
			UsersToggleButton userButton = new UsersToggleButton(users.get(i).id,users.get(i).username,null);
			userButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Ustawienie przycisku na lewo
			Dimension buttonSize = new Dimension(270, 30);
			userButton.setPreferredSize(buttonSize);
			userButton.setMaximumSize(buttonSize);
			userButton.addActionListener(new AdminPanelFrame.UserButtonListener());
			userListPanel.add(userButton);
			userButtonGroup.add(userButton);
		}
		userListPanel.revalidate();
		userListPanel.repaint();
	}

	//Funkcja odpowiedzialna za odświerzanie listy chatów
	private void refreshChatList() {
		for (Enumeration<AbstractButton> buttons = chatButtonGroup.getElements(); buttons.hasMoreElements(); ) {
			AbstractButton button = buttons.nextElement();
			chatListPanel.remove(button);
			chatButtonGroup.remove(button);
		}
		//chatListPanel.revalidate();
		//chatListPanel.repaint();
		addChatToList();
	}
	private void addChatToList() {
		ArrayList <DatabaseComm.Conversation> chats = getAllChats();
		if(chats != null){
			for (int i = 0; i < chats.size(); i++) {
				UsersToggleButton chatButton = new UsersToggleButton(chats.get(i).id,chats.get(i).name,null);
				chatButton.setAlignmentX(Component.LEFT_ALIGNMENT);
				Dimension buttonSize = new Dimension(270, 30);
				chatButton.setPreferredSize(buttonSize);
				chatButton.setMaximumSize(buttonSize);
				chatButton.addActionListener(new AdminPanelFrame.ChatButtonListener());
				chatListPanel.add(chatButton);
				chatButtonGroup.add(chatButton);
			}
		}else{
			JOptionPane.showMessageDialog(null, "Błąd wczytywania czatów.", "Błąd", JOptionPane.ERROR_MESSAGE);
		}

		chatListPanel.revalidate();
		chatListPanel.repaint();
	}

	//Funkcja odpowiedzialna za odświezanie listy moderatorów
	private void refreshModeratorList(String chatName) {
		for (Enumeration<AbstractButton> buttons = moderatorButtonGroup.getElements(); buttons.hasMoreElements(); ) {
			AbstractButton button = buttons.nextElement();
			moderatorListPanel.remove(button);
			moderatorButtonGroup.remove(button);
		}
		//moderatorListPanel.revalidate();
		//moderatorListPanel.repaint();
		addModeratorToList(chatName);
	}
	private void addModeratorToList(String chatName) {
		ArrayList <DatabaseComm.User> moderators = getChatModeratorsNames(chatName);
		for (int i = 0; i < moderators.size(); i++) {
			UsersToggleButton moderatorButton = new UsersToggleButton(moderators.get(i).id,moderators.get(i).username,null);
			moderatorButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			Dimension buttonSize = new Dimension(270, 30);
			moderatorButton.setPreferredSize(buttonSize);
			moderatorButton.setMaximumSize(buttonSize);
			moderatorButton.addActionListener(new AdminPanelFrame.ModeratorButtonListener());
			moderatorListPanel.add(moderatorButton);
			moderatorButtonGroup.add(moderatorButton);
		}
		moderatorListPanel.revalidate();
		moderatorListPanel.repaint();
	}

	private void performSearchUsers() {
		String searchPhrase = urserSearchField.getText().toLowerCase();
		Component[] components = userListPanel.getComponents();

		for (Component component : components) {
			if (component instanceof UsersToggleButton) {
				UsersToggleButton userButton = (UsersToggleButton) component;
				String buttonText = userButton.getText().toLowerCase();
				userButton.setVisible(buttonText.contains(searchPhrase));
			}
		}
		userListPanel.revalidate();
		userListPanel.repaint();
	}

	private void performSearchChats() {
		String searchPhrase = chatSearchField.getText().toLowerCase();
		Component[] components = chatListPanel.getComponents();

		for (Component component : components) {
			if (component instanceof UsersToggleButton) {
				UsersToggleButton chatButton = (UsersToggleButton) component;
				String buttonText = chatButton.getText().toLowerCase();
				chatButton.setVisible(buttonText.contains(searchPhrase));
			}
		}
		chatListPanel.revalidate();
		chatListPanel.repaint();
	}

	private void performSearchModerators() {
		String searchPhrase = moderatorSearchField.getText().toLowerCase();
		Component[] components = moderatorListPanel.getComponents();

		for (Component component : components) {
			if (component instanceof UsersToggleButton) {
				UsersToggleButton moderatorButton = (UsersToggleButton) component;
				String buttonText = moderatorButton.getText().toLowerCase();
				moderatorButton.setVisible(buttonText.contains(searchPhrase));
			}
		}
		moderatorListPanel.revalidate();
		moderatorListPanel.repaint();
	}

	private JPanel createInfoTextArea(int x, int y, String title, int width, int height, SpecialTextArea infoTextArea ) {
		JPanel infoTextPanel = new JPanel();

		infoTextPanel.setBorder(BorderFactory.createTitledBorder(title));
		infoTextPanel.setBounds(x, y, width, height);

		infoTextArea.setEditable(false);

		Font newFont = new Font("Arial", Font.BOLD, 12); // Zmień 16 na dowolny rozmiar czcionki
		//infoTextArea.setBounds(x, y, width, height);
		Dimension dimension = new Dimension(width-20, height-40);
		infoTextArea.setPreferredSize(dimension);
		infoTextArea.setFont(newFont);

		infoTextPanel.add(infoTextArea);
		this.add(infoTextPanel);

		return infoTextPanel;
	}
	@Override
	public void actionPerformed(ActionEvent e) { ///todo po usunieciu nie aktualizuje, sprawdzic jaka funkcja dostarcza uzytkownikow, dodac zeby sprawdzala czy is_deleted jest true
		if(e.getSource() == removeUserFormPortalButton){
			if(userInfoTextArea.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Nie wybrano uzytkownika.", "Błąd", JOptionPane.ERROR_MESSAGE);
			}else {
				if(removeUserFromPortal(userInfoTextArea.id)){
					System.out.println("Uzytkownik usunięty z portalu " + userInfoTextArea.getText());
					refreshUserList();
					userInfoTextArea.setText("");
				}else{
					JOptionPane.showMessageDialog(null, "Nie udało się usunąć uzytkownika powód nieznany.", "Błąd", JOptionPane.ERROR_MESSAGE);
				}

			}

		}
		if(e.getSource() == removeUserFormChatButton){
			if(moderatorInfoTextArea.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Nie wybrano moderatora.", "Błąd", JOptionPane.ERROR_MESSAGE);
			}else {
				if(removeModeratorFromChat(chatInfoTextArea.getText(),moderatorInfoTextArea.id)){
					System.out.println("Moderator usunięty z czatu " + chatInfoTextArea.getText());
					refreshModeratorList(chatInfoTextArea.getText());
					moderatorInfoTextArea.setText("");
				}else{
					JOptionPane.showMessageDialog(null, "Nie udało się usunąć moderatora powód nieznany.", "Błąd", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}


	private class UserButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Możesz dodać tu kod obsługi zdarzenia dla wybranego użytkownika
			// W tej chwili zaznaczony przycisk można sprawdzić używając:
			UsersToggleButton userButton = (UsersToggleButton) e.getSource();
			userInfoTextArea.setText(userButton.getText());
			userInfoTextArea.id = userButton.id;
		}
	}
	private class ChatButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Możesz dodać tu kod obsługi zdarzenia dla wybranego użytkownika
			// W tej chwili zaznaczony przycisk można sprawdzić używając:
			UsersToggleButton chatButton = (UsersToggleButton) e.getSource();
			chatInfoTextArea.setText(chatButton.getText());
			chatInfoTextArea.id = chatButton.id;
			// Załadowanie listy moderatorów
			refreshModeratorList(chatButton.getText());
		}
	}
	private class ModeratorButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Możesz dodać tu kod obsługi zdarzenia dla wybranego użytkownika
			// W tej chwili zaznaczony przycisk można sprawdzić używając:
			UsersToggleButton moderatorButton = (UsersToggleButton) e.getSource();
			moderatorInfoTextArea.setText(moderatorButton.getText());
			moderatorInfoTextArea.id = moderatorButton.id;
		}
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