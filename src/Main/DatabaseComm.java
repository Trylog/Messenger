package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.sql.*;
import java.util.HashMap;

public class DatabaseComm {
	private final static String DBURL = "jdbc:mysql://localhost:3306/messengerdatabase";
	private static String DBUSER;
	private static String DBPASS;
	private final static String DBDRIVER = "com.mysql.cj.jdbc.Driver";
	private final static String DBOPERATOR = "procedury";
	private final static String DBROOTPASS = "Proc@1234";

	private static Connection connection;
	private static Statement statement;
	private static String query;

	public static int userId;

	public static boolean isAdmin;

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
		public int id;
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

	//funkcja do rejestrowania nowego uzytkownika
	public boolean register(String Imie, String Nazwisko, String password, String filepath){ ///todo okienko do rejestracji = doslownie 3 pola tekstowe, ew. jakas opcja weryfikacji admina, dodanie avatara
		try{
			Class.forName(DBDRIVER);
			connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
			statement = connection.createStatement();
			PreparedStatement prs = connection.prepareStatement("call add_new_user(?, ?, ?, ?, False);");

			prs.setString(1,password);
			prs.setString(2,Imie);
			prs.setString(3,Nazwisko);
			FileInputStream fin = new FileInputStream(filepath);
			prs.setBlob(4,fin);

			prs.executeQuery();
			prs.close();
			statement.close();
			connection.close();
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean login(String login, String password){ ///todo zweryfikowac wplyw logowania na status, dodac funkcje ktora przy wylaczeniu aplikacji przestawi w tryb not_active
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
				isAdmin = rs.getBoolean("is_admin");
				String username = rs.getString("first_name") +" "+ rs.getString("last_name");
				Image img;
					try{
						Blob blob = rs.getBlob("avatar");
						InputStream in = blob.getBinaryStream();
						img = ImageIO.read(in);
					} catch (Exception e){
						img = null;
					}
				try{
					currentuser = new User(userId,username,new ImageIcon(img));
				} catch (Exception e){
					e.printStackTrace();
					currentuser = new User(userId,username,null);
				}
				statement.close();
				connection.close();
			} catch (Exception e){
				e.printStackTrace();
				statement.close();
				connection.close();
				return false;
			}
			DBUSER = String.valueOf(userId);
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
		User user;
		try{
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			statement = connection.createStatement();
			query = "SELECT * FROM users WHERE ID = " + id + ";";
			ResultSet rs = statement.executeQuery(query);
			rs.next();
				Image img;
				try{
					Blob blob = rs.getBlob("avatar");
					InputStream in = blob.getBinaryStream();
					img = ImageIO.read(in);
				} catch (Exception e){
					img = null;
				}
			try {
				user = new User(rs.getInt("id"), rs.getString("first_name") + " " + rs.getString("last_name"), new ImageIcon(img));
			} catch (Exception e){
				user = new User(rs.getInt("id"), rs.getString("first_name") + " " + rs.getString("last_name"), null);
			}
			statement.close();
			connection.close();
			return user;
		} catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	public HashMap<String, Integer> getReactions(int msId){
		HashMap<String, Integer> reactions = new HashMap<>();
		int[] counts = {0,0,0,0,0,0,0,0};
		String[] reaction = {"❤","❤","❤","❤","❤","❤","❤","❤"}; ///todo dodac rodzaje emotikonow
		try{
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			statement = connection.createStatement();
			query = "SELECT * FROM interactions WHERE MESSAGE_ID = " + msId + ";";
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				switch (rs.getInt("type_of_interaction")){
					case 0:
						counts[0]++;
						break;
					case 1:
						counts[1]++;
						break;
					case 2:
						counts[2]++;
						break;
					case 3:
						counts[3]++;
						break;
					case 4:
						counts[4]++;
						break;
					case 5:
						counts[5]++;
						break;
					case 6:
						counts[6]++;
						break;
					case 7:
						counts[7]++;
						break;
				}
			}
			statement.close();
			connection.close();
		} catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
		for(int i=0;i<7;i++){
			if (counts[i]>0){
				reactions.put(reaction[i],counts[i]);
			}
		}
		//dane do testów
		return reactions;
	}

	public User getCurrentuser(){
		return currentuser;
	}

	public void sendReaction(int msId, String reactionId){
		int typeOfReaction = -1;
		String[] reaction = {"❤","❤","❤","❤","❤","❤","❤","❤"}; ///todo dodac rodzaje emotikonow
		for(int i=0;i<7;i++){
			if(reactionId.equals(reaction[i])) typeOfReaction = i;
		}
		if (typeOfReaction==-1) return;
		try{
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			statement = connection.createStatement();
			query = "call send_interaction(" + typeOfReaction + ", " + msId + ");";
			statement.executeQuery(query);
			statement.close();
			connection.close();
			System.out.println("Wyslano reakcje do wiadomosci o id: " + msId + ". Id reakcji: " + typeOfReaction);
		} catch (Exception exception){
			exception.printStackTrace();
		}
	}

	public ArrayList<Message> getMessages(){
		ArrayList<Message> messages = new ArrayList<>();

		int id;

		try {
			ResultSet rs;
			connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
			statement = connection.createStatement();
			try{
				query = "SELECT conversation_id FROM conversation_members WHERE user_id = " + userId + ";";
				rs = statement.executeQuery(query);
				rs.next();
				id = rs.getInt("conversation_id");
			} catch (Exception e){
				e.printStackTrace();
				id = 1;
			}
			query = "call show_messages(" + id + ");";
			rs = statement.executeQuery(query);
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
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			statement = connection.createStatement();
			query = "SELECT * FROM users";
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				if(rs.getString("is_deleted").equals("yes")) continue;
				if(rs.getInt("id")==userId) continue;
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
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			statement = connection.createStatement();
			query = "select * from users where id in (select user_id from conversation_members where conversation_id = (select id from conversations where name = '" + chatName + "'))";
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				if(rs.getString("is_deleted").equals("yes")) continue; // bez avatarow, wystarcza same nazwy
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
			connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
			statement = connection.createStatement();
			query = "SELECT * FROM users WHERE id IN (SELECT user_id from moderators where conversation_id = (SELECT id from conversations where name = '" + chatName + "'))";
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()){
				if(rs.getString("is_deleted").equals("yes")) continue; // bez avatarow, wystarcza same nazwy
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
		return isAdmin;
	}

	//Czy dany użytkownik Jest Moderatorem Tego Czatu
	public static boolean getModeratorChatIs(String chatName){
		try {
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			statement = connection.createStatement();
			query = "SELECT user_id FROM moderators WHERE conversation_id = (SELECT id FROM conversations WHERE name = '" + chatName + "');";
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()){
				if(rs.getInt("user_id")==userId) return true;
			}
			statement.close();
			connection.close();
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}

	//Czaty do których należy użytkownik
	public static ArrayList <Conversation> getUsersChat(){
		ArrayList <Conversation> usersChats = new ArrayList<>();
		ArrayList <Integer> conversationsIds = new ArrayList<>();
		BufferedImage img = null;
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
				try{
					Blob blob = rs.getBlob("avatar");
					InputStream in = blob.getBinaryStream();
					img = ImageIO.read(in);
				} catch (Exception e){
					img = null;
				}
				usersChats.add(new Conversation(rs.getInt("id"),rs.getString("name"),img));
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
	public static ArrayList <Conversation> getAllChats(){ ///todo wywalic te w ktorych jest user, dodac userid  ::Jacob
		ArrayList<Conversation> allChats = new ArrayList<>();
		try {
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			statement = connection.createStatement();
			query = "SELECT * FROM conversations";
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()){
				allChats.add(new Conversation(rs.getInt("id"),rs.getString("name"),null));
			}
		} catch (Exception exception){
			exception.printStackTrace();
			return null;
		}
		return allChats;
	}

	public static boolean addUserToChat(String  chatName){
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

	public static boolean addUserToChat(String  chatName,int user_Id){
		try{
			connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
			statement = connection.createStatement();
			query = "call add_user_by_id(" + user_Id + ", '" + chatName + "');";
			statement.executeQuery(query);
			statement.close();
			connection.close();
			return true;
		} catch (Exception exception){
			exception.printStackTrace();
			return false;
		}
	}

	public static boolean createChat(String  chatName, String avatar,boolean invitationMode){
		System.out.println(avatar);
		try {
			connection = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
			statement = connection.createStatement();

			PreparedStatement prs = connection.prepareStatement("call new_conversation(?, ?, ?);");
			prs.setString(1,chatName);
			prs.setBoolean(2,invitationMode);
			FileInputStream fin = new FileInputStream(avatar);

			prs.setBlob(3,fin);
			prs.executeQuery();
			prs.close();

			statement.close();
			connection.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	//dodaje uztykownikow z listy id do chatu
	public static boolean addUserListToChat(String  chatName,ArrayList<Integer> usersID){
		try {
			connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
			statement = connection.createStatement();
			for(Integer user : usersID){
				query = "call add_user_by_id(" + user + ", '" + chatName + "');";
				statement.executeQuery(query);
			}
			statement.close();
			connection.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	//dodaje moderatorow z listy id do modow chatu
	public static boolean addModeratorListToChat(String  chatName, ArrayList<Integer> usersID){
		if(usersID.isEmpty()) return false;
		try {
			connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
			statement = connection.createStatement();
			for(Integer user : usersID){
				query = "call add_moderator_by_id(" + user + ", '" + chatName + "');";
				statement.executeQuery(query);
			}
			statement.close();
			connection.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	//Musi sprawdzić czy użytkownik Nie jestModeratorem ///todo - nie ma procedury na usuwanie przez moderatora, istnieje tylko wywalenie samego siebie ::Jacob
	public static boolean removeUserFromChat(String  chatName,int userId){
		System.out.println(userId);
		try{
			connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
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
	public static boolean downModeratorPermision(String  chatName, int userId){ ///todo delete_moderator_by_moderator ::Jacob
		try{
			connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
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

	//podnosi uprawnienia uzytkownika
	public static boolean upModeratorPermision(String  chatName, int userId){
		try{
			connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
			statement = connection.createStatement();
			query = "call add_moderator_by_id(" + userId +", '" + chatName + "');";
			statement.executeQuery(query);
			statement.close();
			connection.close();
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public static boolean removeModeratorFromChat(String  chatName, int user_id){ ///todo usuniecie z czatu moderatora i jako uzytkownika ::Jacob
		System.out.println(user_id);
		try{
			connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
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
			connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
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
			connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
			statement = connection.createStatement(); ///todo zamiana avatara na bloba ::Jacob
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
		System.out.println(removed_user_id);
		try{
			connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
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
