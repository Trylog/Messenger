package GUI;

import javax.swing.*;

public class GUI {
	public MainGUI mainGUI;
	public GUI() {
		try {

			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (Exception e) {
			System.out.println("Look and Feel not set");
		}

		LoginGUI loginGui = new LoginGUI();



	}
}
