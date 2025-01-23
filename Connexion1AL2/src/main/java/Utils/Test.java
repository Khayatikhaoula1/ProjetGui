package Utils;

import Entites.User;
import Services.ServiceUser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        User p1=new User("test 1","test1",12);

        ServiceUser ser=new ServiceUser();

        try {
            ser.ajouter(p1);
            System.out.println("User ajoutée");
        } catch (SQLException e) {
            System.out.println(p1);
        }

try {
    List<User> l1=ser.getAll();
    l1.forEach(e-> System.out.println(e));
}catch(SQLException e)
{
    System.out.println(e);
}

    }
}
