public class DatabaseComm {
	boolean login(String login, String password){
		System.out.println("Logowanie");
		return true;
	}

	void sendMessage(int conversationId, String content){
		System.out.println("Konwersacja: " + conversationId + "; Nowa wiadomość: " + content);
	}

}
