import java.sql.*;

public class Test {

    private static Connection con;
    private static String url = "jdbc:mysql://localhost:3306/esprit1al2";
    private static String user = "root";
    private static String pass = "";
    private static Statement stmt;

    public static void main(String[] args) {

        try {
            con = DriverManager.getConnection(url, user, pass);
            System.out.println(con);
            System.out.println("connexion établie");
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            stmt = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
        String req = "INSERT INTO `personne` (`id`, `nom`, `prenom`, `age`) VALUES (NULL, 'ben fadhel', 'sana', '19');";

        try {
            stmt.executeUpdate(req);
            System.out.println("personne ajoutée");
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            ResultSet rs=stmt.executeQuery("select  * from personne");
            while (rs.next()) {
                int id=rs.getInt(1);
                String nom=rs.getString("nom");
                String prenom=rs.getString(3);
                int age=rs.getInt("age");

                System.out.println("id :"+id+"nom : "+nom+" prenom :"+prenom+" age : "+age);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }


    }
}
