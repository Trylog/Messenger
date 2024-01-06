package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import Main.*;

public class MainGUI extends JFrame implements ActionListener {
	public static JScrollPane messagesPanel;
	public static int displayedConversationId = 1;
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
	public static int respondingId = -1;
	private static String curentConversationName;
	public static Image icon;

	MainGUI(){

		messagesPanel = new JScrollPane();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(800, 680);
		this.setLocationRelativeTo(null);
		this.setTitle("Messenger");
		icon = new ImageIcon("src/textures/appIcon.png").getImage();
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
		scrollPane.setBounds(0, 0, 300, 610 - (4 * border));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		JPanel cContentPanel = new JPanel();
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

		createChatButtonPanel = new JPanel();
		createChatButtonPanel.setBounds(0, 610 - (4 * border), 150, 40);
		this.add(createChatButtonPanel);

		createChatButtonMenu = new JButton("Stwóż nowy czat");
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
			ModeratorPanelFrame moderatorPanelFrame = new ModeratorPanelFrame(curentConversationName);
		}
		if(e.getSource() == createChatButtonMenu){
			//Wyświetlenie Panelu Moderatora
			System.out.println("PStwórz czat");
			CreateChatGUI createChatGUI = new CreateChatGUI();
		}
		if(e.getSource() == joinChatButtonButtonMenu){
			//Wyświetlenie Panelu Moderatora
			System.out.println("Dołącz do konwersacji");
			JoinChatGUI joinChatGUI = new JoinChatGUI();
		}
	}
	private static class MessagesListElement extends JPanel{

		boolean isResponding = false;
		MessagesListElement(DatabaseComm.Message messageData){

			if(messageData.senderId == MainGUI.displayedConversationId){
				this.setLayout(new FlowLayout(FlowLayout.RIGHT, 4, 1));
			}else{
				this.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 1));
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

			JLabel react = new JLabel("\uD83D\uDE42");
			react.setFont(new Font("Serif", Font.PLAIN, 18));

			react.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println("reakcja"); //TODO menu reakcji
					EmojiChooser emojiChooser = new EmojiChooser(messageData.id);
				}
			});
			this.add(react);
		}
	}
	private static class conversationListPanelElement extends JPanel {
		JLabel chatName;

		conversationListPanelElement(int tempId) { //TODO wejściem powinien być obiekt zawierający wszystkie dane o konwersacji
			String name = "Konwersacja " + tempId;
			MainGUI.curentConversationName = name;
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
					//GUI.MainGUI.messangesPanel.revalidate(); //TODO odświeżanie zawartości messagesPanel
					//GUI.MainGUI.messangesPanel.repaint();
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
}