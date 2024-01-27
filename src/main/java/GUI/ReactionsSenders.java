package GUI;

import Main.Main;

import javax.swing.*;
import java.awt.*;

public class ReactionsSenders extends JFrame {
	ReactionsSenders(int msId){
		var reactions = Main.databaseComm.getReactionsUsers(msId);

		this.setIconImage(MainGUI.icon);
		this.setTitle("Reakcje");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0,0,150, 400);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		scrollPane.setViewportView(contentPanel);

		for (var reaction:reactions.entrySet()) {
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
			JLabel avatar = new JLabel(reaction.getKey().icon);
			JLabel username = new JLabel("   " + reaction.getKey().username);
			JLabel emoji = new JLabel("    "  + reaction.getValue());
			emoji.setFont(new Font("Serif", Font.PLAIN, 18));
			panel.add(avatar);
			panel.add(username);
			panel.add(emoji);
			contentPanel.add(panel);
		}
		this.add(scrollPane, BorderLayout.CENTER);
		this.pack();
	}
}
