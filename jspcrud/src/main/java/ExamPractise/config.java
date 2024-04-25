package ExamPractise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class config {
    private static String url="jdbc:mysql://localhost:3333/crud";
    private static String username="root";
    private static String pass="1234";
    private static String DriverClass="com.mysql.cj.jdbc.Driver";
    private  static Connection connection=null;

    static {
        try{
            Class.forName(DriverClass);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try{
            if(connection==null || connection.isClosed()){
                connection= DriverManager.getConnection(url,username,pass);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(){
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void closeStatement(Statement state, ResultSet result){
        try{
            if(state != null){
                state.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            if(result != null){
                result.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
