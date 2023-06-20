package managers;

import utility.Console;

import java.sql.*;

public class DataBaseManager {
    private final Console console;
    private final String DB_URL;
    private final String USER;
    private final String PASS;
    private Connection connection = null;

    public DataBaseManager(String DB_URL, String USER, String PASS, Console console) {
        this.console = console;
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASS = PASS;
    }

    public boolean initialize() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            console.printError("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            return false;
        }
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            getStatement().executeQuery("SELECT pg_catalog.set_config('search_path', 's367177', false);");
        } catch (SQLException e) {
            console.printError("Connection failed");
            return false;
        }

        if (connection == null) {
            console.printError("Failed to make connection to database");
            return false;
        }

        return true;
    }
    public Statement getStatement() throws SQLException { return connection.createStatement(); }
    public PreparedStatement getPreparedStatement(String s) throws SQLException { return connection.prepareStatement(s); }
    public PreparedStatement  getPreparedStatementRGK(String s) throws SQLException { return connection.prepareStatement(s, Statement.RETURN_GENERATED_KEYS); }
}
