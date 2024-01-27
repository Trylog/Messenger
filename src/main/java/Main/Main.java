package Main;

import GUI.*;

//Project by Michał Bernacki-Janson, Kamil Gondek and Jakub Klawon
public class Main {

	public final static String version = "v0.7";
	public static DatabaseComm databaseComm;
	public static GUI gui;
	public static void main(String[] args) {
		databaseComm = new DatabaseComm();
		gui = new GUI();
	}
}