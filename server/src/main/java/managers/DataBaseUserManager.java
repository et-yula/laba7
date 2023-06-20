package managers;

import utility.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class DataBaseUserManager {
    private final DataBaseManager dataBaseManager;
    public DataBaseUserManager(DataBaseManager dataBaseManager) {

        this.dataBaseManager = dataBaseManager;
    }

    public LinkedList<User> select() {
        LinkedList<User> linkedListUsers = new LinkedList<>();
        try {
            ResultSet rs = dataBaseManager.getStatement().executeQuery("SELECT * FROM LabWorkUser;");
            while (rs.next()) {
                linkedListUsers.add(new User(rs.getInt("id"), rs.getString("login"), rs.getString("password")));
            }
            rs.close();
            return linkedListUsers;
        } catch (SQLException e) {
            System.out.println(e);
            return linkedListUsers;
        }
    }
    public boolean insert(User user) {
        try (PreparedStatement stmt = dataBaseManager.getPreparedStatementRGK("INSERT INTO LabWorkUser(login, password) VALUES (?, ?)"); ) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            if (stmt.executeUpdate() == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet gk = stmt.getGeneratedKeys()) {
                if (gk.next()) {
                    user.setID(gk.getLong(1));
                    return true;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    public boolean update(User user) {
        try {
            PreparedStatement stmt = dataBaseManager.getPreparedStatement("UPDATE LabWorkUser SET login = ?, password = ? WHERE id = ?");
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setLong(3, user.getID());
            if (stmt.executeUpdate() == 0) {
                throw new SQLException("Update user failed, no rows affected.");
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
}