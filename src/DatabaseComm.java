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
	boolean login(String login, String password){
		System.out.println("Logowanie");
		return true;
	}

	void sendMessage(int conversationId, String content){
		System.out.println("Konwersacja: " + conversationId + "; Nowa wiadomość: " + content);
	}

}
