package GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 class ModeratorPanelFrame extends JFrame implements ActionListener {
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

		// Przykładowe dodanie kilku użytkowników czatu
		for (int i = 0; i < 20; i++) {
			JToggleButton chatUserButton = new JToggleButton("Użytkownik " + i);
			chatUserButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Ustawienie przycisku na lewo
			Dimension buttonSize = new Dimension(270, 30);
			chatUserButton.setPreferredSize(buttonSize);
			chatUserButton.setMaximumSize(buttonSize);
			chatUserButton.addActionListener(new ModeratorPanelFrame.ChatUserButtonListener());
			chatUserListPanel.add(chatUserButton);
			chatUserButtonGroup.add(chatUserButton);
		}

		// Przykładowe dodanie kilku użytkowników portalu
		for (int i = 0; i < 20; i++) {
			JToggleButton portalUserButton = new JToggleButton("Użytkownik " + i);
			portalUserButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Ustawienie przycisku na lewo
			Dimension buttonSize = new Dimension(270, 30);
			portalUserButton.setPreferredSize(buttonSize);
			portalUserButton.setMaximumSize(buttonSize);
			portalUserButton.addActionListener(new ModeratorPanelFrame.PortalUserButtonListener());
			portalUserListPanel.add(portalUserButton);
			portalUserButtonGroup.add(portalUserButton);
		}

		// Przykładowe dodanie kilku użytkowników portalu
		for (int i = 0; i < 20; i++) {
			JToggleButton chatModeratorButton = new JToggleButton("Użytkownik " + i);
			chatModeratorButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Ustawienie przycisku na lewo
			Dimension buttonSize = new Dimension(270, 30);
			chatModeratorButton.setPreferredSize(buttonSize);
			chatModeratorButton.setMaximumSize(buttonSize);
			chatModeratorButton.addActionListener(new ModeratorPanelFrame.ChatModeratorButtonListener());
			chatModeratorListPanel.add(chatModeratorButton);
			chatModeratorButtonGroup.add(chatModeratorButton);
		}

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
		chatUserInfoTextArea = new JTextArea();
		chatUserInfoTextPanle = createInfoTextArea(10,border * 4 + 440, "Wybrany użytkownik", 300, 60, chatUserInfoTextArea);

		// Pole tekstowe z informacjami o wybranym użytkowniku portalu
		portalUserInfoTextArea = new JTextArea();
		portalUserInfoTextPanle = createInfoTextArea(10 + 300,border * 4 + 440, "Wybrany użytkownik", 300, 60, portalUserInfoTextArea);

		chatModeratorInfoTextArea = new JTextArea();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addUserToConversationButton){
			System.out.println("Dodanie użytkownika");
		}
		if(e.getSource() == removeUserFromConversationButton){
			System.out.println("Usuniecie użytkownika");
		}
		if(e.getSource() == downUserPermissions){
			System.out.println("Obniżenie uprawnień");
		}
		if(e.getSource() == upUserPermissions){
			System.out.println("Podniesienie uprawnień");
		}
		if(e.getSource() == editChatDataButton){
			System.out.println("Edycja danych czatu");
			ChatDataModyfieGUI chatDataModyfieGUI = new ChatDataModyfieGUI(chatName);
		}
		if(e.getSource() == removeConversationButton){
			System.out.println("Konwersacja usunięta");
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
}