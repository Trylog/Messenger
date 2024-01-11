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

class ModeratorPanelFrame extends JFrame implements ActionListener {
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

	private JButton editChatDataButton;
	private JButton addUserToConversationButton;
	private JButton removeUserFromConversationButton;
	private JButton removeConversationButton;
	private JButton downUserPermissions;
	private JButton upUserPermissions;

	private String chatName;

	ModeratorPanelFrame(String name){
		chatName = name;

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(940, 680);
		this.setLocationRelativeTo(null);
		this.setTitle("Messenger - Panel Moderatora - " + chatName);
		Image icon = new ImageIcon("src/textures/appIcon.png").getImage();
		this.setIconImage(icon);
		this.setVisible(true);

		int border = 10;

		// Panel z przewijaniem dla użytkowników czatu
		chatUserScrollPane = new JScrollPane();
		chatUserScrollPane.setBorder(BorderFactory.createTitledBorder("Lista Użytkowników Czatu"));
		chatUserScrollPane.setBounds(border, border, 300, 400);
		chatUserScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Panel z przewijaniem dla użytkowników poralu
		portalUserScrollPane = new JScrollPane();
		portalUserScrollPane.setBorder(BorderFactory.createTitledBorder("Lista Użytkowników Portalu"));
		portalUserScrollPane.setBounds(border+300, border, 300, 400);
		portalUserScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Panel z przewijaniem dla moderatorów czatu
		chatModeratorScrollPane = new JScrollPane();
		chatModeratorScrollPane.setBorder(BorderFactory.createTitledBorder("Lista Moderatorów Czatu"));
		chatModeratorScrollPane.setBounds(border+600, border, 300, 400);
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

		refreshChatUserList();
		refreshChatModeratorList();
		refreshPortalUserList();

		// Panel z wyszukiwaniem użytkowników czatu
		JPanel chatUserSearchPanel = new JPanel();
		chatUserSearchPanel.setBorder(BorderFactory.createTitledBorder("Wyszukaj Użytkownika"));
		chatUserSearchPanel.setBounds(border, 400 + border, 300, 60);

		// Panel z wyszukiwaniem użytkowników portalu
		JPanel portalUserSearchPanel = new JPanel();
		portalUserSearchPanel.setBorder(BorderFactory.createTitledBorder("Wyszukaj Użytkownika"));
		portalUserSearchPanel.setBounds(border+300, 400 + border, 300, 60);

		// Panel z wyszukiwaniem moderatora chatu
		JPanel chatModeratorSearchPanel = new JPanel();
		chatModeratorSearchPanel.setBorder(BorderFactory.createTitledBorder("Wyszukaj Użytkownika"));
		chatModeratorSearchPanel.setBounds(border+600, 400 + border, 300, 60);

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
		chatUserInfoTextPanle = createInfoTextArea(10,border * 4 + 440, "Wybrany użytkownik", 300, 60, chatUserInfoTextArea);

		// Pole tekstowe z informacjami o wybranym użytkowniku portalu
		portalUserInfoTextArea = new SpecialTextArea();
		portalUserInfoTextPanle = createInfoTextArea(10 + 300,border * 4 + 440, "Wybrany użytkownik", 300, 60, portalUserInfoTextArea);

		chatModeratorInfoTextArea = new SpecialTextArea();
		chatModeratorInfoTextPanle = createInfoTextArea(10 + 600,border * 4 + 440, "Wybrany użytkownik", 300, 60, chatModeratorInfoTextArea);

		int move2 = 10;
		//Przycisk usunięcia użytkownika z czatu
		removeUserFromConversationButton = new JButton("Usunięie użytkownika z czatu");
		createButton(0 + move2,550+ border,300,30,removeUserFromConversationButton);

		//Przycisk dodawania użytkownika do czatu
		addUserToConversationButton = new JButton("Dodaj użytkownika do czatu");
		createButton(300 + move2,550+ border,300,30,addUserToConversationButton);

		//Przycisk obniżenia uprawnień
		downUserPermissions = new JButton("Obniżenie uprawnień");
		createButton(600 + move2,550+ border,300,30,downUserPermissions);

		//Przycisk edycji danych czatu
		editChatDataButton = new JButton("Edycja danych czatu");
		createButton(0 + move2,580+ border,300,30,editChatDataButton);

		//Przycisk podniesienia uprawnień
		upUserPermissions = new JButton("Podniesienie uprawnień");
		createButton(300 + move2,580+ border,300,30,upUserPermissions);

		//Przycisk usunięcia czatu
		removeConversationButton = new JButton("Usunięcie konwersacji");
		createButton(600 + move2,580+ border,300,30,removeConversationButton);
	}

	 // Odświeżanie listy użytkowników czatu
	 private void refreshChatUserList() {
		 for (Enumeration<AbstractButton> buttons = chatUserButtonGroup.getElements(); buttons.hasMoreElements(); ) {
			 AbstractButton button = buttons.nextElement();
			 chatUserListPanel.remove(button);
			 chatUserButtonGroup.remove(button);
		 }
		 chatUserListPanel.revalidate();
		 chatUserListPanel.repaint();
		 addChatUsersToList();
	 }

	 // Odświeżanie listy użytkowników portalu
	 private void refreshPortalUserList() {
		 ArrayList<DatabaseComm.User> moderators = getChatUsersNames(chatName);
		 for (Enumeration<AbstractButton> buttons = portalUserButtonGroup.getElements(); buttons.hasMoreElements(); ) {
			 AbstractButton button = buttons.nextElement();
			 portalUserListPanel.remove(button);
			 portalUserButtonGroup.remove(button);
		 }
		 portalUserListPanel.revalidate();
		 portalUserListPanel.repaint();
		 addPortalUsersToList();
	 }

	// Odświeżanie listy moderatorów czatu
	private void refreshChatModeratorList() {
		for (Enumeration<AbstractButton> buttons = chatModeratorButtonGroup.getElements(); buttons.hasMoreElements(); ) {
			AbstractButton button = buttons.nextElement();
			chatModeratorListPanel.remove(button);
			chatModeratorButtonGroup.remove(button);
		}
		chatModeratorListPanel.revalidate();
		chatModeratorListPanel.repaint();
		addChatModeratorsToList();
	}

	// Dodawanie przycisków użytkowników czatu
	private void addChatUsersToList() {
		ArrayList<DatabaseComm.User> chatUsers = getChatUsersNames(chatName);
		for (int i = 0; i < chatUsers.size(); i++) {
			UsersToggleButton chatUserButton = new UsersToggleButton(chatUsers.get(i).id,chatUsers.get(i).username,chatUsers.get(i).icon);
			chatUserButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			Dimension buttonSize = new Dimension(270, 30);
			chatUserButton.setPreferredSize(buttonSize);
			chatUserButton.setMaximumSize(buttonSize);
			chatUserButton.addActionListener(new ModeratorPanelFrame.ChatUserButtonListener());
			chatUserListPanel.add(chatUserButton);
			chatUserButtonGroup.add(chatUserButton);
		}
		chatUserListPanel.revalidate();
		chatUserListPanel.repaint();
	}

	// Dodawanie przycisków użytkowników portalu
	private void addPortalUsersToList() {
		ArrayList<DatabaseComm.User> portalUsers = getPortalUsersNames();
		for (int i = 0; i < portalUsers.size(); i++) {
			UsersToggleButton portalUserButton = new UsersToggleButton(portalUsers.get(i).id,portalUsers.get(i).username,portalUsers.get(i).icon);
			portalUserButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			Dimension buttonSize = new Dimension(270, 30);
			portalUserButton.setPreferredSize(buttonSize);
			portalUserButton.setMaximumSize(buttonSize);
			portalUserButton.addActionListener(new ModeratorPanelFrame.PortalUserButtonListener());
			portalUserListPanel.add(portalUserButton);
			portalUserButtonGroup.add(portalUserButton);
		}
		portalUserListPanel.revalidate();
		portalUserListPanel.repaint();
	}

	// Dodawanie przycisków moderatorów czatu
	private void addChatModeratorsToList() { ///todo sprawdzic bug z dublowaniem sie nazw? ::Kamil
		ArrayList<DatabaseComm.User> moderators = getChatModeratorsNames(chatName);
		for (int i = 0; i < moderators.size(); i++) {
			UsersToggleButton chatModeratorButton = new UsersToggleButton(moderators.get(i).id,moderators.get(i).username,moderators.get(i).icon);
			chatModeratorButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			Dimension buttonSize = new Dimension(270, 30);
			chatModeratorButton.setPreferredSize(buttonSize);
			chatModeratorButton.setMaximumSize(buttonSize);
			chatModeratorButton.addActionListener(new ModeratorPanelFrame.ChatModeratorButtonListener());
			chatModeratorListPanel.add(chatModeratorButton);
			chatModeratorButtonGroup.add(chatModeratorButton);
		}
		chatModeratorListPanel.revalidate();
		chatModeratorListPanel.repaint();
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

	private boolean isUserInGroup(String username, ButtonGroup buttonGroup) {
		for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
			AbstractButton button = buttons.nextElement();
			if (button instanceof UsersToggleButton && button.getText().equals(username)) {
				return true;
			}
		}
		return false;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addUserToConversationButton){
			if(portalUserInfoTextArea.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika.", "Błąd", JOptionPane.ERROR_MESSAGE);
			}else{
				if(!isUserInGroup(portalUserInfoTextArea.getText(), chatUserButtonGroup)){
					if(addUserToChat(chatName,portalUserInfoTextArea.id)){
						System.out.println("Dodanie użytkownika");
					}else{
						JOptionPane.showMessageDialog(null, "Nie udało się dodać urzytkownika.", "Błąd", JOptionPane.ERROR_MESSAGE);
					}
					refreshChatUserList();
				}else{
					JOptionPane.showMessageDialog(null, "Taki użytkownik znadjuje się już w konwersacji.", "Błąd", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		if(e.getSource() == removeUserFromConversationButton){
			if(chatUserInfoTextArea.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika.", "Błąd", JOptionPane.ERROR_MESSAGE);
			}else{
				if(removeUserFromChat(chatName,chatUserInfoTextArea.id)){
					System.out.println("Usuniecie użytkownika");
				}else{
					JOptionPane.showMessageDialog(null, "Nie udało się usunąć urzytkownika.", "Błąd", JOptionPane.ERROR_MESSAGE);
				}
				refreshChatUserList();
			}
		}
		if(e.getSource() == downUserPermissions){
			if(chatModeratorInfoTextArea.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika.", "Błąd", JOptionPane.ERROR_MESSAGE);
			}else{
				if(downModeratorPermision(chatName,chatModeratorInfoTextArea.id)){
					System.out.println("Obniżenie uprawnień");
				}else{
					JOptionPane.showMessageDialog(null, "Nie udało się obniżyć uprawnień.", "Błąd", JOptionPane.ERROR_MESSAGE);
				}
				refreshChatModeratorList();
			}

		}
		if(e.getSource() == upUserPermissions){
			if(chatUserInfoTextArea.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Nie wybrano użytkownika.", "Błąd", JOptionPane.ERROR_MESSAGE);
			}else{
				if(!isUserInGroup(chatUserInfoTextArea.getText(), chatModeratorButtonGroup)){
					if(upModeratorPermision(chatName,chatUserInfoTextArea.id)){
						System.out.println("Podniesienie uprawnień");
					}else{
						JOptionPane.showMessageDialog(null, "Nie udało się podnieść uprawneń.", "Błąd", JOptionPane.ERROR_MESSAGE);
					}
					refreshChatUserList();
				}else{
					JOptionPane.showMessageDialog(null, "Taki użytkownik ma już uprrawnieniea moderatora dla tego czatu.", "Błąd", JOptionPane.ERROR_MESSAGE);
				}
			}

		}
		if(e.getSource() == editChatDataButton){
			System.out.println("Edycja danych czatu");
			ChatDataModyfieGUI chatDataModyfieGUI = new ChatDataModyfieGUI(chatName);
		}
		if(e.getSource() == removeConversationButton){
			if(removeConversation(chatName)){
				System.out.println("Konwersacja usunięta");
			}else{
				JOptionPane.showMessageDialog(null, "Nie udało się usunąć konwersacji.", "Błąd", JOptionPane.ERROR_MESSAGE);
			}
			dispose();
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
	private JPanel createInfoTextArea(int x, int y, String title, int width, int height, SpecialTextArea infoTextArea ) {
		JPanel infoTextPanel = new JPanel();

		infoTextPanel.setBorder(BorderFactory.createTitledBorder(title));
		infoTextPanel.setBounds(x, y, width, height);

		infoTextArea.setEditable(false);

		Font newFont = new Font("Arial", Font.BOLD, 12); // Zmień 16 na dowolny rozmiar czcionki
		Dimension dimension = new Dimension(width-20, height-40);
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