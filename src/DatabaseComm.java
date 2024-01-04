import javax.swing.*;

public class DatabaseComm {

	public static class Message{
		int id;
		String content;
		int senderId;
		int answerToId;
		///TODO time of writing
		Message(int id, String content, int senderId, int answerToId){
			this.id = id;
			this.content = content;
			this.senderId = senderId;
			this.answerToId = answerToId;
		}
	}

	public static class User{
		int id;
		String username;
		Icon icon;

		User(int id, String username, Icon icon){
			this.id =id;
			this.username = username;
			this.icon = icon;
		}
	}

	boolean login(String login, String password){
		System.out.println("Logowanie");
		return true;
	}

	void sendMessage(int conversationId, String content, int answerToId){
		System.out.println("Konwersacja: " + conversationId + "; Nowa wiadomość: " + content);
	}

	int getCurrentUserId(){
		return 1;
	}

	User getUser(int id){
		return new User(id, "test username", new ImageIcon("src/textures/avatar.png"));
	}

}
