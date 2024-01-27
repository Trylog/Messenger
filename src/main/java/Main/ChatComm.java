package Main;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;

public class ChatComm {
    private final static String DBURL = "jdbc:mysql://localhost:3306/messengerdatabase?serverTimezone=CET";
    private final static String DBDRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String DBOPERATOR = "procedury";
    private final static String DBROOTPASS = "Proc@1234";

    private static Connection connection;
    private static Statement statement;
    private static String query;

    //Musi sprawdzić czy użytkownik Nie jestModeratorem
    public static boolean removeUserFromChat(String  chatName,int userId){//TODO:Kamil - Jeżeli usuwa użytkownika to musi sprawdzić czy jest moderatorem i też go usunąć
        System.out.println(userId);
        try{
            connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
            statement = connection.createStatement();
            query = "call remove_user_from_conversation_by_id(" + userId + ", '" + chatName + "');";
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

    public static boolean setConversationNewData(String  chatName, String newChatName,String avatar){
        try{
            connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
            statement = connection.createStatement();

            PreparedStatement prs = connection.prepareStatement("call modify_conversation_data(?, ?, ?);");
            prs.setString(1,chatName);
            prs.setString(2,newChatName);
            try {
                FileInputStream fin = new FileInputStream(avatar);
                prs.setBlob(3,fin);
            } catch (Exception e){
                e.printStackTrace();
                query = "SELECT avatar FROM conversations WHERE name = '" + chatName + "';";
                ResultSet rs = statement.executeQuery(query);
                try {
                    rs.next();
                    Blob blob = rs.getBlob("avatar");
                    prs.setBlob(3,blob);
                } catch (Exception exception){
                    prs.setNull(3,Types.BLOB);
                }
            }

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
    public static boolean addUserListToChat(String  chatName, ArrayList<Integer> usersID){
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
}
