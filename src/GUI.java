import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI {
	GUI() {
		try {

			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (Exception e) {
			System.out.println("Look and Feel not set");
		}

		LoginGUI loginGui = new LoginGUI();
		MainGUI mainGUI;


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
		MainGUI(){
			JPanel chatSelectionPanel = new JPanel();
			JPanel conversationPanel = new JPanel();

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setLayout(null);
			this.setSize(800, 680);
			this.setVisible(true);

			int border = 10;

			chatSelectionPanel.setBorder(BorderFactory.createTitledBorder("Wybór konwersacji"));
			chatSelectionPanel.setBounds(0, 0, 300, 680 - (4 * border));
			chatSelectionPanel.setBackground(Color.green);
			chatSelectionPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

			conversationPanel.setBorder(BorderFactory.createTitledBorder("Czat"));
			conversationPanel.setBounds(300,0,500-(border*2),680-(4*border));
			conversationPanel.setBackground(Color.red);

			//JScrollPane chatScrollPane = new JScrollPane(chatSelectionPanel);
			//chatScrollPane.setBounds(10,10,100,100);

			//chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			this.add(chatSelectionPanel);
			this.add(conversationPanel);

			JPanel panel = new JPanel();
			panel.setBounds(0,0,10,10);
			panel.setBackground(Color.CYAN);
			chatSelectionPanel.add(panel);


			// Dodajmy kilka przykładowych elementów do listy konwersacji
			for (int i = 0; i < 10; i++) {
				conversationListPanelElement element = new conversationListPanelElement("Konwersacja " + (i + 1));
				chatSelectionPanel.add(element);
			}

			// Obsługa przewijania listy konwersacji za pomocą myszy
            /*chatScrollPane.addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    JScrollBar verticalScrollBar = chatScrollPane.getVerticalScrollBar();
                    verticalScrollBar.setValue(verticalScrollBar.getValue() - e.getWheelRotation() * verticalScrollBar.getUnitIncrement());
                }
            });*/
		}
		@Override
		public void actionPerformed(ActionEvent e) {

		}


	}
	private static class conversationListPanelElement extends JPanel{
		JLabel chatName;
		conversationListPanelElement(String name){
			AvatarPanel graphicsPanel = new AvatarPanel();
			chatName = new JLabel(name);

			JLabel chatName = new JLabel();
			this.setBorder(BorderFactory.createCompoundBorder());
			this.setLayout(new BorderLayout());
			this.add(graphicsPanel, BorderLayout.WEST);
			this.add(chatName, BorderLayout.CENTER);
			this.setBounds(0,0,10,10);
			this.setBackground(Color.CYAN);
		}
	}

	private static class AvatarPanel extends JPanel {

		private Image obraz;
		public AvatarPanel() {
			// Wczytaj obraz z pliku
			obraz = new ImageIcon("sciezka/do/twojego/pliku/obrazu.jpg").getImage();
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Rysuj obraz na panelu
			g.drawImage(obraz, 50, 50, this);
		}
	}
}
