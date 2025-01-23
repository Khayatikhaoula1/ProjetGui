package Utils;
import java.sql.*;
public class DataSource {

    private Connection conn;

    private  String url = "jdbc:mysql://localhost:3306/guiprojectdb2";
    private  String user = "root";
    private  String pass = "";
    private static DataSource data;

    private DataSource() {

        try {
            conn=DriverManager.getConnection(url,user,pass);
            System.out.println("connexion établie");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Connection getConn() {
        return conn;
    }

    public static DataSource getInstance(){
        if(data == null){

            data = new DataSource();
        }
        return data;
    }
}
