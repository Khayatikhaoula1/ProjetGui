package Entites;

public class User {
    private int id;
    private String username;
    private String password;
    private Role role;
    private String email;


    // Constructeur
    public User(int id, String username, String password, Role role, String email ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;

    }

    // Getters et Setters
    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}