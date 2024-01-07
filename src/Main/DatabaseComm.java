package Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.sql.*;
import java.util.HashMap;
import javax.sql.*;
import javax.tools.Tool;

public class DatabaseComm {
	private final static String DBURL = "jdbc:mysql://localhost:3306/messengerdatabase";
	private static String DBUSER;
	private static String DBPASS;
	private final static String DBDRIVER = "com.mysql.cj.jdbc.Driver";

	private static Connection connection;
	private static Statement statement;
	private static String query;

	public static int userId;

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

	private static User currentuser;

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
		DBUSER = login;
		DBPASS = password;
		try{
			Class.forName(DBDRIVER);
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			query = "SELECT * FROM users WHERE id = '" + login + "'";
			statement = connection.createStatement();
			try{
				ResultSet rs = statement.executeQuery(query);
				rs.next();
				userId = rs.getInt("id");
				String username = rs.getString("first_name") +" "+ rs.getString("last_name");
				byte[] image = null;
				//image = rs.getBytes("avatar");
				//Image img = Toolkit.getDefaultToolkit().createImage(image);
				//ImageIcon icon = new ImageIcon(img);
				currentuser = new User(userId,username,new ImageIcon("src/textures/avatar.png"));
				statement.close();
				connection.close();
			} catch (Exception e){
				e.printStackTrace();
				statement.close();
				connection.close();
				return false;
			}
			DBUSER = String.valueOf(userId);
			DBPASS = password;
			try{
				connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			} catch (Exception e){
				return false;
			}
			connection.close();
		} catch (Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public void sendMessage(int conversationId, String content, int answerToId){
		try{
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			statement = connection.createStatement();
			query = "call send_message(" + "'" + content +"'" + "," + conversationId + "," + answerToId + ")";
			statement.executeQuery(query);
			statement.close();
			connection.close();
		} catch (Exception ex){
			ex.printStackTrace();
			return;
		}
		System.out.println("Konwersacja: " + conversationId + "; Nowa wiadomość: " + content);
	}

	int getCurrentUserId(){
		return userId;
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

	public User getCurrentuser(){
		return currentuser;
	}

	public void sendReaction(int msId, String reactionId){


	}

	public ArrayList<Message> getMessages(){
		ArrayList<Message> messages = new ArrayList<>();

		int id = 1;

		try {
			connection = DriverManager.getConnection(DBURL,"root","1234");
			statement = connection.createStatement();
			query = "call show_messages(" + id + ");";
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()){
				messages.add(new Message(rs.getInt("id"),rs.getString("content"),rs.getInt("user_id"),rs.getInt("answer_to_id")));
			}
			statement.close();
			connection.close();
		} catch (Exception exception){
			exception.printStackTrace();
			return null;
		}

		return messages;
	}
	public static ArrayList <User> getPortalUsersNames(){
		ArrayList <User> usersNames = new ArrayList<>();

		try{
			connection = DriverManager.getConnection(DBURL,"root","1234");
			statement = connection.createStatement();
			query = "SELECT * FROM users";
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				User user = new User(rs.getInt("id"),rs.getString("first_name") + " " + rs.getString("last_name"),null);
				usersNames.add(user);
			}
			statement.close();
			connection.close();
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}

		return  usersNames;
	}

	//UżytkownicyDanegoCzatu
	public static ArrayList <User> getChatUsersNames(String chatName){
		ArrayList <User> usersNames = new ArrayList<>();
		
		try{
			connection = DriverManager.getConnection(DBURL,"root","1234");
			statement = connection.createStatement();
			query = "SELECT * FROM users WHERE name = '" + chatName + "'";
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				User user = new User(rs.getInt("id"),rs.getString("first_name") + " " + rs.getString("last_name"),null);
				usersNames.add(user);
			}
			statement.close();
			connection.close();
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}

		return  usersNames;
	}

	//Nie podaje samego siebie
	//ModeratorzyDanegoCzatu
	public static ArrayList <User> getChatModeratorsNames(String chatName){
		ArrayList <User> usersNames = new ArrayList<>();

		try{
			connection = DriverManager.getConnection(DBURL,"root","1234");
			statement = connection.createStatement();
			query = "SELECT * FROM users WHERE id IN (SELECT user_id from moderators where conversation_id = (SELECT id from conversations where name = 'XD'))";
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				User user = new User(rs.getInt("id"),rs.getString("first_name") + " " + rs.getString("last_name"),null);
				usersNames.add(user);
			}
			statement.close();
			connection.close();
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}

		return  usersNames;
	}

	//Czy Dany Użytkownik Jest Adminem
	public static boolean getAdminIs(){
		boolean isAdmin = false;
		return isAdmin;
	}

	//Czy dany użytkownik Jest Moderatorem Tego Czatu
	public static boolean getModeratorChatIs(String chatName){
		boolean isModerator = true;
		return isModerator;
	}

	//Czaty do których należy użytkownik
	public static ArrayList <Conversation> getUserChats(){
		ArrayList <Conversation> usersChats = new ArrayList<>();
		ArrayList <Integer> conversationsIds = new ArrayList<>();
		try{
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			statement = connection.createStatement();
			query = "call show_conversations(" + userId + ");";
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				conversationsIds.add(rs.getInt("conversation_id"));
			}
			for(Integer integer : conversationsIds){
				query = "SELECT * FROM conversations WHERE ID = " + integer;
				rs = statement.executeQuery(query);
				rs.next();
				usersChats.add(new Conversation(rs.getInt("id"),rs.getString("name"),null));
			}
			statement.close();
			connection.close();
		} catch (Exception ex){
			ex.printStackTrace();
			return null;
		}

		return  usersChats;
	}

	//Wszystkie istniejące czaty
	public static ArrayList <Conversation> getAllChats(){
		ArrayList <Conversation> allChats = new ArrayList<>();

		//Tymczasowe
		Conversation conversation = new Conversation(1,"Konwersacja 2",null);
		allChats.add(conversation);

		return  allChats;
	}

	public static boolean addUserToChat(String  chatName){ ///todo to wlasciwie jest dodawnie czy dolaczanie?
		try {
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			statement = connection.createStatement();
			query = "call join_conversation('" + chatName + "');";
			statement.executeQuery(query);
			statement.close();
			connection.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public static boolean addUserToChat(String  chatName,String  userName){
		boolean operationSuces = true;

		return operationSuces;
	}

	public static boolean createChat(String  chatName, Image avatar){

		try {
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			statement = connection.createStatement(); ///TODO utworzyc parametr do ustawiania opcji zaproszenia i konwersje image na blob
			query = "call new_conversation('" + chatName +"'," + 0 + "," + null + ");";
			statement.executeQuery(query);
			statement.close();
			connection.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	///TODO co to robi?
	public static boolean addUserListToChat(String  chatName,ArrayList<String> usersNames){
		boolean operationSuces = true;

		return operationSuces;
	}

	//Dodatkowo dodaje rownież użytkownika Do moderatorów ///TODO co to robi skoro z automatu dodaje sie do moderatorow w bazie?
	public static boolean addModeratorListToChat(String  chatName, ArrayList<String> usersNames){
		boolean operationSuces = true;

		return operationSuces;
	}

	//Musi sprawdzić czy użytkownik Nie jestModeratorem ///todo - nie ma procedury na usuwanie przez moderatora, istnieje tylko wywalenie samego siebie
	public static boolean removeUserFromChat(String  chatName,String userNames){
		try{
			connection = DriverManager.getConnection(DBURL,"root","1234");
			statement = connection.createStatement();
			query = ""; //remove_user_from_conversation pozwala na wywalenie siebie samego
			statement.executeQuery(query);
			statement.close();
			connection.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	//Obniża swoje uprawnienia
	public static boolean downModeratorPermision(String  chatName){
		try{
			connection = DriverManager.getConnection(DBURL,"root","1234");
			statement = connection.createStatement();
			query = "call delete_moderator('" + chatName + "');";
			statement.executeQuery(query);
			statement.close();
			connection.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public static boolean removeModeratorFromChat(String  chatName, String userNames){
		try{
			connection = DriverManager.getConnection(DBURL,"root","1234");
			statement = connection.createStatement();
			query = "call delete_moderator_by_moderator(" + userId + ",'" + chatName + "');";
			statement.executeQuery(query);
			statement.close();
			connection.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public static boolean removeConversation(String  chatName){
		try{
			connection = DriverManager.getConnection(DBURL,"root","1234");
			statement = connection.createStatement();
			query = "call chat_delete('" + chatName +"');";
			statement.executeQuery(query);
			statement.close();
			connection.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public static boolean setConversationNewData(String  chatName, String newChatName,Image avatar){
		try{
			connection = DriverManager.getConnection(DBURL,"root","1234");
			statement = connection.createStatement(); ///todo opcja edycji zaproszenia w parametrze i zamiana avatara na bloba
			query = "call modify_conversation_data('" + chatName + "','" + newChatName + "',0,null);";
			statement.executeQuery(query);
			statement.close();
			connection.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}

	}

	public static boolean removeUserFromPortal(int removed_user_id){
		try{
			connection = DriverManager.getConnection(DBURL,"root","1234");
			statement = connection.createStatement();
			query = "call remove_user_from_portal(" + removed_user_id + ");";
			statement.executeQuery(query);
			statement.close();
			connection.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}


}
