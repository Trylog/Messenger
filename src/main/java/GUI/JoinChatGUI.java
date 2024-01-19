package GUI;

import Main.DatabaseComm;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Main.*;

import static GUI.MainGUI.refreshAllConversationsList;
import static Main.DatabaseComm.*;

class JoinChatGUI extends JFrame implements ActionListener {
	private JScrollPane chatScrollPane;
	private JPanel chatListPanel;
	private JTextField chatSearchField;
	private ButtonGroup chatButtonGroup;
	private JPanel chatInfoTextPanel;
	private JTextArea chatInfoTextArea;
	private JButton joinSelectedChat;
	JoinChatGUI() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(460, 380);
		this.setLocationRelativeTo(null);
		this.setTitle("Okno Dołaczania do czatu");
		Image icon = new ImageIcon("src/main/java/textures/appIcon.png").getImage();
		this.setIconImage(icon);
		this.setVisible(true);

		int border = 10;

		// Panel z przewijaniem dla czatu
		chatScrollPane = new JScrollPane();
		chatScrollPane.setBorder(BorderFactory.createTitledBorder("Lista Czatów"));
		chatScrollPane.setBounds(border, border, 420, 170);
		chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// Panel z komponentami czatu (dodaj swoje komponenty)
		chatListPanel = new JPanel();
		chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
		chatScrollPane.setViewportView(chatListPanel);

		// Grupa dla przycisków czatów
		chatButtonGroup = new ButtonGroup();

		// Dodajmy elementów do listy czatów
		ArrayList <Conversation> chats  = getAllChats(); ///todo getAllChatsWithoutUser(int userid) ::Kamil/Jacob
		if(chats != null){
			for (Conversation chat : chats) {
				JToggleButton chatButton = new JToggleButton(chat.name);
				chatButton.setAlignmentX(Component.LEFT_ALIGNMENT); // Ustawienie przycisku na lewo
				Dimension buttonSize = new Dimension(390, 30);
				chatButton.setPreferredSize(buttonSize);
				chatButton.setMaximumSize(buttonSize);
				chatButton.addActionListener(new ChatButtonListener());
				chatListPanel.add(chatButton);
				chatButtonGroup.add(chatButton);
			}
		}else{
			JOptionPane.showMessageDialog(null, "Błąd wczytywania czatów.", "Błąd", JOptionPane.ERROR_MESSAGE);
		}

		// Panel z wyszukiwaniem czatów
		JPanel chatSearchPanel = new JPanel();
		chatSearchPanel.setBorder(BorderFactory.createTitledBorder("Wyszukaj Czat"));
		chatSearchPanel.setBounds(border, 170 + border, 420, 60);

		// Pole tekstowe do wprowadzania frazy wyszukiwania czatów
		chatSearchField = new JTextField();
		chatSearchField.setPreferredSize(new Dimension(400, 25));
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

		// Dodanie komponentów do ramki
		this.add(chatScrollPane);
		this.add(chatSearchPanel);

		// Pole tekstowe z informacjami o wybranym czacie
		chatInfoTextArea = new JTextArea();
		chatInfoTextPanel = createInfoTextArea(border, border + 230, "Wybrany czat", 420, 60, chatInfoTextArea);

		//Przyciski Aktywnosci

		joinSelectedChat = new JButton("Dołącz do czatu");
		joinSelectedChat.setBounds(border,290+ border,420,30);
		joinSelectedChat.addActionListener(this);
		this.add(joinSelectedChat);
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
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == joinSelectedChat){
			if(chatInfoTextArea.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "Nie wybrano chatu", "Błąd", JOptionPane.ERROR_MESSAGE);
			}else{

				System.out.println("Dołączono do czatu - " + chatInfoTextArea.getText()); ///todo dodac wywolanie funkcji dolaczania do czatu
				if(!(DatabaseComm.addUserToChat(chatInfoTextArea.getText()))){
					JOptionPane.showMessageDialog(null, "Nie Udało się dodać użytkownika", "Błąd", JOptionPane.ERROR_MESSAGE);
				}
				refreshAllConversationsList();
				dispose();
			}
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
}