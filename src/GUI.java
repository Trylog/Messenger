import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class GUI {
	GUI() {
		try {

			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (Exception e) {
			System.out.println("Look and Feel not set");
		}

		//LoginGUI loginGui = new LoginGUI();
		MainGUI mainGUI = new MainGUI();


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

	private static class MainGUI extends JFrame implements ActionListener{
		public static JPanel conversationPanel;
		MainGUI(){
			conversationPanel = new JPanel();

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setLayout(null);
			this.setSize(800, 680);
			this.setVisible(true);

			int border = 10;

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBorder(BorderFactory.createTitledBorder("Wybór konwersacji"));
			scrollPane.setBounds(0, 0, 300, 680 - (4 * border));
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			conversationPanel.setBorder(BorderFactory.createTitledBorder("Czat"));
			conversationPanel.setBounds(300,0,500-(border*2),680-(4*border));

			JPanel contentPanel = new JPanel();
			contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

			scrollPane.setViewportView(contentPanel);

			JPanel panel = new JPanel();
			panel.setBounds(0,0,10,10);
			panel.setBackground(Color.CYAN);

			// Dodajmy kilka przykładowych elementów do listy konwersacji
			for (int i = 0; i < 20; i++) {
				conversationListPanelElement element = new conversationListPanelElement("Konwersacja " + (i + 1));
				contentPanel.add(element);
			}

			this.add(scrollPane, BorderLayout.CENTER);
			this.add(conversationPanel);

		}
		@Override
		public void actionPerformed(ActionEvent e) {

		}


	}
	private static class conversationListPanelElement extends JPanel {
		JLabel chatName;

		conversationListPanelElement(String name) {
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
					MainGUI.conversationPanel.setBorder(BorderFactory.createTitledBorder("Czat - " + name));
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
