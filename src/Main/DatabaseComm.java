package Main;

import javax.swing.*;
import java.util.ArrayList;

public class DatabaseComm {
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

	public boolean login(String login, String password){
		System.out.println("Logowanie");
		return true;
	}

	public void sendMessage(int conversationId, String content, int answerToId){
		System.out.println("Konwersacja: " + conversationId + "; Nowa wiadomość: " + content);
	}

	int getCurrentUserId(){
		return 1;
	}

	public User getUser(int id){
		return new User(id, "test username", new ImageIcon("src/textures/avatar.png"));
	}

	public void sendReaction(int msId, String reactionId){


	}




}
