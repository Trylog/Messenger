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
		this.setLayout(new GridLayout(2,4));
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		scrollPane.setViewportView(contentPanel);

		for (var reaction:reactions.entrySet()) {
			JPanel panel = new JPanel(new BoxLayout(null, BoxLayout.X_AXIS));
			JLabel avatar = new JLabel(reaction.getValue().icon);
			JLabel username = new JLabel(reaction.getValue().username);
			JLabel emoji = new JLabel(reaction.getKey());
			panel.add(avatar);
			panel.add(username);
			panel.add(emoji);
			contentPanel.add(panel);
		}
	}
}
