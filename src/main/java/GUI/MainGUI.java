package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

import Main.*;

import static Main.DatabaseComm.*;

public class MainGUI extends JFrame implements ActionListener {
	public static JScrollPane messagesPanel;
	public static int displayedConversationId = 0;
	private static JPanel administratorButtonPanel;
	public static JButton adminButtonMenu;
	private static JPanel moderatorButtonPanel;
	public static JButton moderatorButtonMenu;
	private static JPanel createChatButtonPanel;
	private JButton createChatButtonMenu;
	private static JPanel joinChatButtonPanel;
	private JButton joinChatButtonButtonMenu;
	private static JButton sendButton;
	private static JTextArea newMessageArea;

	public static JScrollPane newMessagePane;
	public static int respondingId = 0;
	private static String currentConversationName;
	public static Image icon;

	public static ArrayList<DatabaseComm.Message> loadedMessages;
	public static JPanel mContentPanel;

	static JPanel cContentPanel;

	MainGUI(){

		messagesPanel = new JScrollPane();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(800, 680);
		this.setLocationRelativeTo(null);
		this.setTitle("Messenger");
		icon = new ImageIcon("src/main/java/textures/appIcon.png").getImage();
		this.setIconImage(icon);
		this.setVisible(true);

		int border = 10;

		messagesPanel.setBorder(BorderFactory.createTitledBorder("Czat"));
		messagesPanel.setBounds(300,0,500-(border*2),680-(4*border)-100);
		messagesPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		mContentPanel = new JPanel();
		mContentPanel.setLayout(new BoxLayout(mContentPanel, BoxLayout.Y_AXIS));
		messagesPanel.setViewportView(mContentPanel);

		newMessageArea = new JTextArea();
		newMessageArea.setLineWrap(true);
		newMessageArea.setFont(new Font("Arial", Font.PLAIN, 14));
		newMessagePane = new JScrollPane(newMessageArea);
		newMessagePane.setBorder(BorderFactory.createTitledBorder("Napisz wiadomość"));
		newMessagePane.setBounds(300, 580 - 4*border, 500-(border*2)-100, 100);
		newMessagePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		Icon sendIcon = new ImageIcon("src/main/java/textures/send.png");
		sendButton = new JButton(sendIcon);
		sendButton.setBounds(700 - border*2 +10,580 - 4*border+10,80,80);
		sendButton.addActionListener(this);


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(BorderFactory.createTitledBorder("Wybór konwersacji"));
		scrollPane.setBounds(0, 0, 300, 610 - (4 * border));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		cContentPanel = new JPanel();
		cContentPanel.setLayout(new BoxLayout(cContentPanel, BoxLayout.Y_AXIS));

		scrollPane.setViewportView(cContentPanel);

		administratorButtonPanel = new JPanel();
		administratorButtonPanel.setBounds(0, 640 - (4 * border), 150, 40);
		this.add(administratorButtonPanel);

		adminButtonMenu = new JButton("Panel Administratora");
		adminButtonMenu.setPreferredSize(new Dimension(150, 30));
		adminButtonMenu.setHorizontalAlignment(SwingConstants.CENTER);
		adminButtonMenu.setVerticalAlignment(SwingConstants.CENTER);
		adminButtonMenu.setFont(new Font("Arial", Font.BOLD, 11));
		adminButtonMenu.addActionListener(this);
		administratorButtonPanel.add(adminButtonMenu);
		if(!getAdminIs()){
			adminButtonMenu.setEnabled(false);
		}


		moderatorButtonPanel = new JPanel();
		moderatorButtonPanel.setBounds(150, 640 - (4 * border), 150, 40);
		this.add(moderatorButtonPanel);

		moderatorButtonMenu = new JButton("Panel Moderatora");
		moderatorButtonMenu.setPreferredSize(new Dimension(150, 30));
		moderatorButtonMenu.setHorizontalAlignment(SwingConstants.CENTER);
		moderatorButtonMenu.setVerticalAlignment(SwingConstants.CENTER);
		moderatorButtonMenu.setFont(new Font("Arial", Font.BOLD, 11));
		moderatorButtonMenu.addActionListener(this);
		moderatorButtonPanel.add(moderatorButtonMenu);
		moderatorButtonMenu.setEnabled(false);

		createChatButtonPanel = new JPanel();
		createChatButtonPanel.setBounds(0, 610 - (4 * border), 150, 40);
		this.add(createChatButtonPanel);

		createChatButtonMenu = new JButton("Stwórz nowy czat");
		createChatButtonMenu.setPreferredSize(new Dimension(150, 30));
		createChatButtonMenu.setHorizontalAlignment(SwingConstants.CENTER);
		createChatButtonMenu.setVerticalAlignment(SwingConstants.CENTER);
		createChatButtonMenu.setFont(new Font("Arial", Font.BOLD, 11));
		createChatButtonMenu.addActionListener(this);
		createChatButtonPanel.add(createChatButtonMenu);

		joinChatButtonPanel = new JPanel();
		joinChatButtonPanel.setBounds(150, 610 - (4 * border), 150, 40);
		this.add(joinChatButtonPanel);

		joinChatButtonButtonMenu = new JButton("Dołącz do czatu");
		joinChatButtonButtonMenu.setPreferredSize(new Dimension(150, 30));
		joinChatButtonButtonMenu.setHorizontalAlignment(SwingConstants.CENTER);
		joinChatButtonButtonMenu.setVerticalAlignment(SwingConstants.CENTER);
		joinChatButtonButtonMenu.setFont(new Font("Arial", Font.BOLD, 11));
		joinChatButtonButtonMenu.addActionListener(this);
		joinChatButtonPanel.add(joinChatButtonButtonMenu);


		// Dodajmy elementów do listy czatów
		ArrayList <Conversation> chats  = getUsersChat();
		for (Conversation chat : chats) {
			conversationListPanelElement element = new conversationListPanelElement(chat);
			cContentPanel.add(element);
		}

		this.add(scrollPane, BorderLayout.CENTER);
		this.add(messagesPanel);
		this.add(newMessagePane);
		this.add(sendButton);

		currentConversationName = "";


		this.addWindowListener(new WindowAdapter() {//TODO Dodać do głównego okna:Jacob
			@Override
			public void windowClosing(WindowEvent e) {
				DatabaseComm.makeInactive();
				// Ta metoda zostanie wywołana po zamknięciu okna
				System.out.println("Okno zostało zamknięte!");
				//Dodać informacje użytkownik nie aktywny
				System.exit(0); // Możesz również użyć frame.dispose() zamiast System.exit(0)
			}
		});

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new RefreshClock(), 0, 2500);


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
			ModeratorPanelFrame moderatorPanelFrame = new ModeratorPanelFrame(currentConversationName);
			moderatorButtonMenu.setEnabled(false);///TODO DOPISĆ:Kamil
			refreshAllConversationsList();
			currentConversationName = "";
		}
		if(e.getSource() == createChatButtonMenu){
			//Wyświetlenie Panelu Moderatora
			System.out.println("PStwórz czat");
			CreateChatGUI createChatGUI = new CreateChatGUI();
			currentConversationName = "";
		}
		if(e.getSource() == joinChatButtonButtonMenu){
			//Wyświetlenie Panelu Moderatora
			System.out.println("Dołącz do konwersacji");
			JoinChatGUI joinChatGUI = new JoinChatGUI();
			currentConversationName = "";
		}
	}

	private static void removeAllConversations() {
		cContentPanel.removeAll();
		cContentPanel.revalidate();
		cContentPanel.repaint();
	}

	public static void refreshAllConversationsList() {
		removeAllConversations();
		ArrayList <Conversation> chats  = getUsersChat();
		for (Conversation chat : chats) {
			conversationListPanelElement element = new conversationListPanelElement(chat);
			cContentPanel.add(element);
		}
	}

	private static void removeAllShownMessages() {
		mContentPanel.removeAll();
		mContentPanel.revalidate();
		mContentPanel.repaint();
	}

	public static synchronized void refreshShownMessages() {
		removeAllShownMessages();
		var messages = Main.databaseComm.getMessages(displayedConversationId);
		loadedMessages = messages;

		for(var message : messages){
			MessagesListElement element = new MessagesListElement(message);
			mContentPanel.add(element);
		}
		mContentPanel.revalidate();
		mContentPanel.repaint();
		System.out.println("odświeżono konwersację");
	}

	public static synchronized void addNewMessages() {
		var messages = Main.databaseComm.getMessages(displayedConversationId);
		if(loadedMessages == null){
			refreshShownMessages();
		}else if(messages.size() >= loadedMessages.size()){
			for (int i = loadedMessages.size(); i<messages.size();i++){
				loadedMessages.add(messages.get(i));
				mContentPanel.add(new MessagesListElement(messages.get(i)));
			}
		}else refreshShownMessages();
		mContentPanel.revalidate();
		mContentPanel.repaint();
		System.out.println("odświeżono konwersację");
	}

	private static class MessagesListElement extends JPanel{

		private static MessagesListElement currentRedLabel;
		boolean isResponding = false;
		JLabel content;
		MessagesListElement(DatabaseComm.Message messageData){


			if(messageData.senderId == Main.databaseComm.userId){
				this.setLayout(new FlowLayout(FlowLayout.RIGHT, 4, 1));
			}else{
				this.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 1));
			}

			JPanel messagePanel = new JPanel();
			messagePanel.setLayout(new BorderLayout());

			DatabaseComm.User tempUser = null;
			while (tempUser == null) tempUser = Main.databaseComm.getUser(messageData.senderId);
			var user = tempUser;

			JLabel iconLabel = new JLabel(user.icon);

			JLabel senderName = new JLabel();
			senderName.setText(user.username);

			content = new JLabel();
			content.setBackground(Color.cyan);
			content.setOpaque(true);
			content.setText(messageData.content);

			var temp = this;
			content.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(!isResponding){
						//content.setBackground(Color.pink);
						MainGUI.newMessagePane.setBorder(BorderFactory.createTitledBorder("Odpowiedź na wiadomość użytkownika " + user.username));
						isResponding = true;
						MainGUI.respondingId = messageData.id;
						if (currentRedLabel != null) {
							currentRedLabel.content.setBackground(Color.cyan);
							currentRedLabel.isResponding = false;
							MainGUI.respondingId = 0;
						}
						content.setBackground(Color.pink);
						currentRedLabel = temp;
					}else{
						content.setBackground(Color.cyan);
						MainGUI.newMessagePane.setBorder(BorderFactory.createTitledBorder("Napisz wiadomość"));
						isResponding = false;
						currentRedLabel = null;
						MainGUI.respondingId = 0;
					}
				}
			});

			messagePanel.add(senderName, BorderLayout.NORTH);
			messagePanel.add(content, BorderLayout.CENTER);

			///REAKCJE
			JPanel emoji = new JPanel();
			emoji.setLayout(new FlowLayout(FlowLayout.RIGHT, 2, 0));
			emoji.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					var reactions = new ReactionsSenders(messageData.id);

				}
			});

			var reactions = Main.databaseComm.getReactions(messageData.id);

			for (var reaction : reactions.entrySet()){
				JLabel r = new JLabel(reaction.getKey() + " " + reaction.getValue());
				emoji.add(r);
			}

			messagePanel.add(emoji, BorderLayout.SOUTH);

			JLabel react = new JLabel("\uD83D\uDE42");
			react.setFont(new Font("Serif", Font.PLAIN, 18));

			react.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("reakcja");
					EmojiChooser emojiChooser = new EmojiChooser(messageData.id);
				}
			});

			if(messageData.senderId == Main.databaseComm.userId){
				this.add(react);
				this.add(messagePanel);
				this.add(iconLabel);
			}else{
				this.add(iconLabel);
				this.add(messagePanel);
				this.add(react);
			}
		}
	}
	private static class conversationListPanelElement extends JPanel {
		JLabel chatName;

		conversationListPanelElement(Conversation conversation) {
			String name = conversation.name;
			AvatarPanel graphicsPanel = new AvatarPanel(conversation.avatar);
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
					MainGUI.displayedConversationId = conversation.id;

					refreshShownMessages();

					moderatorButtonMenu.setEnabled(getModeratorChatIs(name));
					currentConversationName = name;

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

		public AvatarPanel(Image obraz) {
			this.obraz = obraz;
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
	static class RefreshClock extends TimerTask {
		@Override
		public void run() {
			MainGUI.addNewMessages();
		}
	}
}