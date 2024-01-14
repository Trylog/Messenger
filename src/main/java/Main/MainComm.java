package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MainComm {
    private final static String DBURL = "jdbc:mysql://localhost:3306/messengerdatabase";
    private final static String DBDRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String DBOPERATOR = "procedury";
    private final static String DBROOTPASS = "Proc@1234";

    private static Connection connection;
    private static Statement statement;
    private static String query;
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
