package GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JTextArea userInfoTextArea;
	private JPanel chatInfoTextPanel;
	private JTextArea chatInfoTextArea;
	private JPanel moderatorInfoTextPanel;
	private JTextArea moderatorInfoTextArea;

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

		// Przykładowe dodanie kilku użytkowników
		for (int i = 0; i < 20; i++) {
			JToggleButton userButton = new JToggleButton("Użytkownik " + i);
			userButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Ustawienie przycisku na lewo
			Dimension buttonSize = new Dimension(270, 30);
			userButton.setPreferredSize(buttonSize);
			userButton.setMaximumSize(buttonSize);
			userButton.addActionListener(new AdminPanelFrame.UserButtonListener());
			userListPanel.add(userButton);
			userButtonGroup.add(userButton);
		}

		// Grupa dla przycisków czatów
		chatButtonGroup = new ButtonGroup();

		// Przykładowe dodanie kilku czatów
		for (int i = 0; i < 20; i++) {
			JToggleButton chatButton = new JToggleButton("Czat " + i);
			chatButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Ustawienie przycisku na lewo
			Dimension buttonSize = new Dimension(270, 30);
			chatButton.setPreferredSize(buttonSize);
			chatButton.setMaximumSize(buttonSize);
			chatButton.addActionListener(new AdminPanelFrame.ChatButtonListener());
			chatListPanel.add(chatButton);
			chatButtonGroup.add(chatButton);
		}

		// Grupa dla przycisków moderatorów
		moderatorButtonGroup = new ButtonGroup();

		// Przykładowe dodanie kilku moderatorów
		for (int i = 0; i < 20; i++) {
			JToggleButton moderatorButton = new JToggleButton("Użytkownik " + i);
			moderatorButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Ustawienie przycisku na lewo
			Dimension buttonSize = new Dimension(270, 30);
			moderatorButton.setPreferredSize(buttonSize);
			moderatorButton.setMaximumSize(buttonSize);
			moderatorButton.addActionListener(new AdminPanelFrame.ModeratorButtonListener());
			moderatorListPanel.add(moderatorButton);
			moderatorButtonGroup.add(moderatorButton);
		}

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
		userInfoTextArea = new JTextArea();
		userInfoTextPanle = createInfoTextArea(move,border * 4 + 440, "Wybrany użytkownik", 180, 60, userInfoTextArea);

		// Pole tekstowe z informacjami o wybranym czacie
		chatInfoTextArea = new JTextArea();
		chatInfoTextPanel = createInfoTextArea(200 + move, border * 4 + 440, "Wybrany czat", 180, 60, chatInfoTextArea);

		// Pole tekstowe z informacjami o wybranym moderatorze
		moderatorInfoTextArea = new JTextArea();
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
	private void performSearchUsers() {
		String searchPhrase = urserSearchField.getText().toLowerCase();
		Component[] components = userListPanel.getComponents();

		for (Component component : components) {
			if (component instanceof JToggleButton) {
				JToggleButton userButton = (JToggleButton) component;
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
			if (component instanceof JToggleButton) {
				JToggleButton chatButton = (JToggleButton) component;
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
			if (component instanceof JToggleButton) {
				JToggleButton moderatorButton = (JToggleButton) component;
				String buttonText = moderatorButton.getText().toLowerCase();
				moderatorButton.setVisible(buttonText.contains(searchPhrase));
			}
		}
		moderatorListPanel.revalidate();
		moderatorListPanel.repaint();
	}

	private JPanel createInfoTextArea(int x, int y, String title, int width, int height, JTextArea infoTextArea ) {
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
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == removeUserFormPortalButton){
			System.out.println("Użytkownik Usuniety");
		}
		if(e.getSource() == removeUserFormChatButton){
			System.out.println("Moderator usunięty");
		}
	}


	private class UserButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Możesz dodać tu kod obsługi zdarzenia dla wybranego użytkownika
			// W tej chwili zaznaczony przycisk można sprawdzić używając:
			JToggleButton userButton = (JToggleButton) e.getSource();
			userInfoTextArea.setText(userButton.getText());
		}
	}
	private class ChatButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Możesz dodać tu kod obsługi zdarzenia dla wybranego użytkownika
			// W tej chwili zaznaczony przycisk można sprawdzić używając:
			JToggleButton chatButton = (JToggleButton) e.getSource();
			chatInfoTextArea.setText(chatButton.getText());
		}
	}
	private class ModeratorButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Możesz dodać tu kod obsługi zdarzenia dla wybranego użytkownika
			// W tej chwili zaznaczony przycisk można sprawdzić używając:
			JToggleButton moderatorButton = (JToggleButton) e.getSource();
			moderatorInfoTextArea.setText(moderatorButton.getText());
		}
	}
}