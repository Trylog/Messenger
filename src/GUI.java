import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class GUI {
	public MainGUI mainGUI;
	GUI() {
		try {

			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (Exception e) {
			System.out.println("Look and Feel not set");
		}

		LoginGUI loginGui = new LoginGUI();



	}
	private static class LoginGUI extends JFrame implements ActionListener{

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
			this.setLocationRelativeTo(null);
			this.setLayout(null);
			this.setSize(500, 450);
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
				if(Main.databaseComm.login(login, password)){
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

	public static class MainGUI extends JFrame implements ActionListener{
		public static JScrollPane messagesPanel;
		public static int displayedConversationId = 1;
		private static JPanel administratorButtonPanel;
		public static JButton adminButtonMenu;
		private static JPanel moderatorButtonPanel;
		public static JButton moderatorButtonMenu;
		private static JButton sendButton;
		private static JTextArea newMessageArea;

		public static JScrollPane newMessagePane;
		public static Icon reactIcon;
		public static int respondingId = -1;
		MainGUI(){

			reactIcon = new ImageIcon("src/textures/react.png");
			messagesPanel = new JScrollPane();

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setLayout(null);
			this.setSize(800, 680);
			this.setTitle("Messenger");
			Image icon = new ImageIcon("src/textures/appIcon.png").getImage();
			this.setIconImage(icon);
			this.setVisible(true);

			int border = 10;

			messagesPanel.setBorder(BorderFactory.createTitledBorder("Czat"));
			messagesPanel.setBounds(300,0,500-(border*2),680-(4*border)-100);
			messagesPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			JPanel mContentPanel = new JPanel();
			mContentPanel.setLayout(new BoxLayout(mContentPanel, BoxLayout.Y_AXIS));
			messagesPanel.setViewportView(mContentPanel);

			for (int i = 0; i<10; i++){
				MessagesListElement melement = new MessagesListElement(new DatabaseComm.Message(0,
						"<html>by Michał Bernacki-Janson, <br>Kamil Godek and Jakub Klawon<br>asdasdasds</html>",2, 2
						));
				mContentPanel.add(melement);
			}


			newMessageArea = new JTextArea();
			newMessageArea.setLineWrap(true);
			newMessageArea.setFont(new Font("Arial", Font.PLAIN, 14));
			newMessagePane = new JScrollPane(newMessageArea);
			newMessagePane.setBorder(BorderFactory.createTitledBorder("Napisz wiadomość"));
			newMessagePane.setBounds(300, 580 - 4*border, 500-(border*2)-100, 100);
			newMessagePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			Icon sendIcon = new ImageIcon("src/textures/send.png");
			sendButton = new JButton(sendIcon);
			sendButton.setBounds(700 - border*2 +10,580 - 4*border+10,80,80);
			sendButton.addActionListener(this);


			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBorder(BorderFactory.createTitledBorder("Wybór konwersacji"));
			scrollPane.setBounds(0, 0, 300, 680 - (4 * border));
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			JPanel cContentPanel = new JPanel();
			cContentPanel.setLayout(new BoxLayout(cContentPanel, BoxLayout.Y_AXIS));

			scrollPane.setViewportView(cContentPanel);

			administratorButtonPanel = new JPanel();
			administratorButtonPanel.setBounds(0, 640 - (4 * border), 150, 40);
			//administratorButtonPanel.setBackground(Color.RED);
			//this.add(administratorButtonPanel);

			adminButtonMenu = new JButton("Panel Administratora");
			adminButtonMenu.setPreferredSize(new Dimension(150, 30));
			adminButtonMenu.setHorizontalAlignment(SwingConstants.CENTER);
			adminButtonMenu.setVerticalAlignment(SwingConstants.CENTER);
			adminButtonMenu.setFont(new Font("Arial", Font.BOLD, 11));
			adminButtonMenu.addActionListener(this);
			administratorButtonPanel.add(adminButtonMenu);

			moderatorButtonPanel = new JPanel();
			moderatorButtonPanel.setBounds(150, 640 - (4 * border), 150, 40);
			//moderatorButtonPanel.setBackground(Color.CYAN);
			//this.add(moderatorButtonPanel);

			moderatorButtonMenu = new JButton("Panel Moderatora");
			moderatorButtonMenu.setPreferredSize(new Dimension(150, 30));
			moderatorButtonMenu.setHorizontalAlignment(SwingConstants.CENTER);
			moderatorButtonMenu.setVerticalAlignment(SwingConstants.CENTER);
			moderatorButtonMenu.setFont(new Font("Arial", Font.BOLD, 11));
			moderatorButtonMenu.addActionListener(this);
			moderatorButtonPanel.add(moderatorButtonMenu);


			// Dodajmy kilka przykładowych elementów do listy konwersacji
			for (int i = 0; i < 20; i++) {
				conversationListPanelElement element = new conversationListPanelElement(i + 1);
				cContentPanel.add(element);
			}

			this.add(scrollPane, BorderLayout.CENTER);
			this.add(messagesPanel);
			this.add(newMessagePane);
			this.add(sendButton);

		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == sendButton){
				Main.databaseComm.sendMessage(displayedConversationId, newMessageArea.getText(), respondingId);
			}
			if(e.getSource() == adminButtonMenu){
				//Wyświetlenie Panelu Admin
				System.out.println("Panel Admin");
				AdminPanelFrame adminPanelFrame = new AdminPanelFrame();
			}
			if(e.getSource() == moderatorButtonMenu){
				//Wyświetlenie Panelu Moderatora
				System.out.println("Panel Mod");
				ModeratorPanelFrame moderatorPanelFrame = new ModeratorPanelFrame();
			}
		}


	}

	private static class MessagesListElement extends JPanel{

		boolean isResponding = false;
		MessagesListElement(DatabaseComm.Message messageData){

			if(messageData.senderId == MainGUI.displayedConversationId){
				this.setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 1));
			}else{
				this.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 1));
			}

			JPanel messagePanel = new JPanel();
			messagePanel.setLayout(new BorderLayout());

			DatabaseComm.User user = Main.databaseComm.getUser(messageData.senderId);
			JLabel iconLabel = new JLabel(user.icon);
			this.add(iconLabel);

			JLabel senderName = new JLabel();
			senderName.setText(user.username);

			JLabel content = new JLabel();
			content.setBackground(Color.cyan);
			content.setOpaque(true);
			content.setText(messageData.content);
			content.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(!isResponding){
						content.setBackground(Color.pink);
						MainGUI.newMessagePane.setBorder(BorderFactory.createTitledBorder("Odpowiedź na wiadomość użytkownika " + user.username));
						isResponding = true;
						MainGUI.respondingId = messageData.id;
						//TODO wszystkie inne powinny się graficznie odznaczyć
					}else{
						content.setBackground(Color.cyan);
						MainGUI.newMessagePane.setBorder(BorderFactory.createTitledBorder("Napisz wiadomość"));
						isResponding = false;
						MainGUI.respondingId = -1;
					}
				}
			});

			messagePanel.add(senderName, BorderLayout.NORTH);
			messagePanel.add(content, BorderLayout.CENTER);

			JPanel emoji = new JPanel();
			//TODO fabryka, emoji przechowywane w mapie
			emoji.setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 0));
			JLabel la1 = new JLabel("⌚ " + 2);
			emoji.add(la1);
			JLabel la2 = new JLabel("❔ " + 1);
			emoji.add(la2);

			messagePanel.add(emoji, BorderLayout.SOUTH);
			this.add(messagePanel);

			JLabel react = new JLabel();
			react.setIcon(MainGUI.reactIcon);
			react.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("reakcja"); //TODO menu reakcji
					EmojiChooser emojiChooser = new EmojiChooser();
				}
			});
			this.add(react);
		}
	}

	private static class EmojiChooser extends JFrame{
		EmojiChooser(){
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setSize(200,300);
			this.setVisible(true);
		}
	}
	private static class conversationListPanelElement extends JPanel {
		JLabel chatName;

		conversationListPanelElement(int tempId) { //TODO wejściem powinien być obiekt zawierający wszystkie dane o konwersacji
			String name = "Konwersacja " + tempId;
			AvatarPanel graphicsPanel = new AvatarPanel();
			chatName = new JLabel(name);

			this.setBorder(BorderFactory.createTitledBorder(""));
			this.setLayout(new BorderLayout());
			this.add(graphicsPanel, BorderLayout.WEST);
			this.setBackground(Color.CYAN);

			// Ustawienie layoutu dla chatName na FlowLayout z wyśrodkowaniem
			JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
			namePanel.add(chatName);
			this.add(namePanel, BorderLayout.CENTER);

			setPreferredSize(new Dimension(190, 50));  // Ustawia preferowany rozmiar dla tego elementu
			setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));  // Ustawia maksymalny rozmiar szerokości

			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// Po kliknięciu wypisz nazwę konwersacji w konsoli
					System.out.println("Kliknięto konwersację: " + name);
					MainGUI.messagesPanel.setBorder(BorderFactory.createTitledBorder("Czat - " + name));
					MainGUI.displayedConversationId = tempId; //TODO zapisywane powinno być faktyczne id konwersacji
					//MainGUI.messangesPanel.revalidate(); //TODO odświeżanie zawartości messagesPanel
					//MainGUI.messangesPanel.repaint();
				}
			});
		}

		// Metoda ustawiająca tekst dla chatName
		public void setChatNameText(String text) {
			chatName.setText(text);
		}

		// Metoda ustawiająca obrazek dla AvatarPanel
		public void setAvatarImage(Image image) {
			((AvatarPanel) getComponent(0)).setObraz(image);
		}
	}

	private static class AvatarPanel extends JPanel {
		private Image obraz;

		public AvatarPanel() {
			// Domyślny obrazek, można go później zmienić używając setObraz(Image image)
			obraz = new ImageIcon("src/textures/avatar2.png").getImage();
			setPreferredSize(new Dimension(50, 40));
			setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));  // Ustawia maksymalny rozmiar szerokości
		}

		public void setObraz(Image obraz) {
			this.obraz = obraz;
			repaint();  // Ponowne rysowanie panelu po zmianie obrazu
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Rysowanie obrazu w kształcie koła
			Graphics2D g2d = (Graphics2D) g.create();
			Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
			g2d.setClip(circle);
			g2d.drawImage(obraz, 0, 0, getWidth(), getHeight(), this);
			g2d.dispose();

			// Rysuj obraz na panelu - kwadrat
			//g.drawImage(obraz, 0, 0, getWidth(), getHeight(), this);
		}
	}

	private static class AdminPanelFrame extends JFrame implements ActionListener{
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
			this.setLocationRelativeTo(null);
			this.setLayout(null);
			this.setSize(640, 640);
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
			moderatorScrollPane.setBorder(BorderFactory.createTitledBorder("Lista Czatów"));
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
				userButton.addActionListener(new UserButtonListener());
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
				chatButton.addActionListener(new ChatButtonListener());
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
				moderatorButton.addActionListener(new ModeratorButtonListener());
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
	private static class ModeratorPanelFrame extends JFrame{
		ModeratorPanelFrame(){
			JButton upUserPermissions;
			JButton downUserPermissions;
			JButton removeChat;
			JButton removeUserFormChat;
			JButton addUserToChat;
			JButton changeChatData;

			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setLayout(null);
			this.setSize(800, 680);
			this.setTitle("Messenger - Panel Moderatora");
			Image icon = new ImageIcon("src/textures/appIcon.png").getImage();
			this.setIconImage(icon);
			this.setVisible(true);

			int border = 10;

		}
	}
}
