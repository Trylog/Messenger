package Main;

import GUI.*;

//Project by Michał Bernacki-Janson, Kamil Gondek and Jakub Klawon
//01.2024
public class Main {

	public final static String version = "v0.9";
	public static DatabaseComm databaseComm;
	public static GUI gui;
	public static void main(String[] args) {
		databaseComm = new DatabaseComm();
		gui = new GUI();
	}
}