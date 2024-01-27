package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ModeratorComm {
    private final static String DBURL = "jdbc:mysql://localhost:3306/messengerdatabase?serverTimezone=CET";
    private final static String DBDRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String DBOPERATOR = "procedury";
    private final static String DBROOTPASS = "Proc@1234";

    private static Connection connection;
    private static Statement statement;
    private static String query;


    //Nie podaje samego siebie
    //ModeratorzyDanegoCzatu
    public static ArrayList<DatabaseComm.User> getChatModeratorsNames(String chatName){
        ArrayList <DatabaseComm.User> usersNames = new ArrayList<>();

        try{
            connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
            statement = connection.createStatement();
            query = "SELECT * FROM users WHERE id IN (SELECT user_id from moderators where conversation_id = (SELECT id from conversations where name = '" + chatName + "'))";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                if(rs.getString("is_deleted").equals("yes")) continue; // bez avatarow, wystarcza same nazwy
                DatabaseComm.User user = new DatabaseComm.User(rs.getInt("id"),rs.getString("first_name") + " " + rs.getString("last_name"),null);
                usersNames.add(user);
            }
            statement.close();
            connection.close();
            System.out.println("B1");
        } catch (Exception e){
            System.out.println("B2");
            e.printStackTrace();
            return null;
        }

        return  usersNames;
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

    //Obniża swoje uprawnienia
    public static boolean downModeratorPermision(String  chatName, int userId){
        try{
            connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
            statement = connection.createStatement();
            query = "call delete_moderator_by_moderator(" + userId +",'" + chatName + "');";
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

    public static boolean removeModeratorFromChat(String  chatName, int user_id){
        System.out.println(user_id);
        try{
            connection = DriverManager.getConnection(DBURL,DBOPERATOR,DBROOTPASS);
            statement = connection.createStatement();
            query = "call delete_moderator_by_moderator(" + user_id + ",'" + chatName + "');";
            statement.executeQuery(query);
            query = "call delete_user_from_conversation_by_id(" + user_id + ", '" + chatName + "');";
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
