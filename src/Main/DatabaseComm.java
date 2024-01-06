package Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseComm {
	public int userId; //login userId

	public static boolean isAdmin;

	//public static ArrayList<Message>;

	public static class Message{
		public int id;
		public String content;
		public int senderId;
		int answerToId;
		///TODO time of writing
		public Message(int id, String content, int senderId, int answerToId){
			this.id = id;
			this.content = content;
			this.senderId = senderId;
			this.answerToId = answerToId;
		}
	}

	public static class User{
		int id;
		public String username;
		public Icon icon;

		User(int id, String username, Icon icon){
			this.id = id;
			this.username = username;
			this.icon = icon;
		}
	}

	public static class Conversation{
		public int id;
		public String name;
		public Image avatar;

		Conversation(int id, String name, Image avatar){
			this.id = id;
			this.name = name;
			this.avatar = avatar;
		}
	}

	public boolean login(String login, String password){
		System.out.println("Logowanie");
		return true;
	}

	public void sendMessage(int conversationId, String content, int answerToId){
		System.out.println("Konwersacja: " + conversationId + "; Nowa wiadomość: " + content);
	}

	private int getCurrentUserId(){
		return 1;
	}

	public User getUser(int id){
		return new User(id, "test username", new ImageIcon("src/textures/avatar.png"));
	}

	public HashMap<String, Integer> getReactions(int msId){

		HashMap<String, Integer> reactions = new HashMap<>();
		//dane do testów
		reactions.put("\uD83D\uDC4D", 2);
		reactions.put("❤", 1);
		reactions.put("\uD83D\uDE22", 5);
		return reactions;
	}

	public void sendReaction(int msId, String reactionId){


	}

	public ArrayList<Message> getMessages(){
		ArrayList<Message> messages = new ArrayList<>();

		Message message = new DatabaseComm.Message(0, "<html>by Michał Bernacki-Janson, <br>Kamil Godek and Jakub Klawon<br>asdasdasds</html>",2, 2);

		messages.add(message);
		messages.add(message);

		return messages;
	}

	//Nie podaje samego siebie
	public static ArrayList <User> getPortalUsersNames(){
		ArrayList <User> usersNames = new ArrayList<>();

		//Tymczasowe
		User user = new User(1,"Pan XYZ - PortalUser",null);
		usersNames.add(user);

		return  usersNames;
	}

	//Nie podaje samego siebie
	//UżytkownicyDanegoCzatu
	public static ArrayList <User> getChatUsersNames(String chatName){
		ArrayList <User> usersNames = new ArrayList<>();

		//Tymczasowe
		User user = new User(1,"Pan XYZ - CzatUser",null);
		usersNames.add(user);

		return  usersNames;
	}

	//Nie podaje samego siebie
	//ModeratorzyDanegoCzatu
	public static ArrayList <User> getChatModeratorsNames(String chatName){
		ArrayList <User> usersNames = new ArrayList<>();

		//Tymczasowe
		User user = new User(1,"Pan XYZ - MODERATOR",null);
		usersNames.add(user);

		return  usersNames;
	}

	//Czy Dany Użytkownik Jest Adminem
	public static boolean getAdminIs(){
		boolean isAdmin = true;
		return isAdmin;
	}

	//Czy dany użytkownik Jest Moderatorem Tego Czatu
	public static boolean getModeratorChatIs(String chatName){
		boolean isModerator = true;
		return isModerator;
	}

	//Czaty do których należy użytkownik
	public static ArrayList <Conversation> getUsersChat(){
		ArrayList <Conversation> usersChats = new ArrayList<>();

		//Tymczasowe
		Conversation conversation = new Conversation(1,"Konwersacja 1",null);
		usersChats.add(conversation);

		return  usersChats;
	}

	//Wszystkie istniejące czaty
	public static ArrayList <Conversation> getAllChats(String chatName){
		ArrayList <Conversation> allChats = new ArrayList<>();

		//Tymczasowe
		Conversation conversation = new Conversation(1,"Konwersacja 2",null);
		allChats.add(conversation);

		return  allChats;
	}

	//Dodaje użytkownika do czatu
	public static boolean addUserToChat(String  chatName){
		boolean operationSuces = true;

		return operationSuces;
	}

	public static boolean createChat(String  chatName, Image avatar){
		boolean operationSuces = true;

		return operationSuces;
	}
	public static boolean addUserListToChat(String  chatName,ArrayList<String> usersNames){
		boolean operationSuces = true;

		return operationSuces;
	}

	//Dodatkowo dodaje rownież użytkownika Do moderatorów
	public static boolean addModeratorListToChat(String  chatName, ArrayList<String> usersNames){
		boolean operationSuces = true;

		return operationSuces;
	}

	//Musi sprawdzić czy użytkownik Nie jestModeratorem
	public static boolean removeUserFromChat(String  chatName,String userNames){
		boolean operationSuces = true;

		return operationSuces;
	}

	//Obniża swoje uprawnienia
	public static boolean downModeratorPermision(String  chatName){
		boolean operationSuces = true;

		return operationSuces;
	}

	public static boolean removeModeratorFromChat(String  chatName, String userNames){
		boolean operationSuces = true;

		return operationSuces;
	}

	public static boolean removeConversation(String  chatName){
		boolean operationSuces = true;

		return operationSuces;
	}

	public static boolean setConversationNewData(String  chatName, String newChatName,Image avatar){
		boolean operationSuces = true;

		return operationSuces;
	}

	public static boolean removeUserFromPortal(String userNames){
		boolean operationSuces = true;

		return operationSuces;
	}



}
