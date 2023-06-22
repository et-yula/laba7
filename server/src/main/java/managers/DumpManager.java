package managers;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import models.Difficulty;
import models.LabWork;
import utility.Console;
import utility.User;
import java.sql.SQLException;
import java.util.LinkedList;

public class DumpManager {
	private final DataBaseManager dataBaseManager;
	private final Console console;
	private final DataBaseUserManager dataBaseUserManager;
	private final DataBaseLabWorkManager dataBaseLabWorkManager;

	public DumpManager(DataBaseManager dataBaseManager, Console console) {
		this.console = console;
		this.dataBaseManager = dataBaseManager;
		this.dataBaseLabWorkManager = new DataBaseLabWorkManager(dataBaseManager);
		this.dataBaseUserManager = new DataBaseUserManager(dataBaseManager);
	}

	public boolean initializeTables() {
		try {
			if (!dataBaseManager.initialize()) { System.exit(1); }
			
			var stmt = dataBaseManager.getStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS LabWorkUser(id serial PRIMARY KEY, login text NOT NULL UNIQUE, password text NOT NULL);");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Difficulty(id serial PRIMARY KEY, name varchar(40) NOT NULL);");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Locations(id serial PRIMARY KEY, x INTEGER NOT NULL, y INTEGER NOT NULL, z INTEGER NOT NULL, name varchar(40));");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Coordinates(id serial PRIMARY KEY, x INTEGER NOT NULL, y BIGINT NOT NULL);");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Person(id serial PRIMARY KEY, name text NOT NULL, birthday timestamp NOT NULL, passportID text NOT NULL, id_location INTEGER REFERENCES Locations);");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS LabWork(id SERIAL PRIMARY KEY, name text NOT NULL, id_coordinates INTEGER REFERENCES Coordinates NOT NULL, creationDate timestamp NOT NULL, minimalPoint BIGINT NOT NULL, id_difficulty INTEGER REFERENCES Difficulty, id_person INTEGER REFERENCES Person, id_user INTEGER REFERENCES LabWorkUser);");
			stmt.close();
			
			var pstmt = dataBaseManager.getPreparedStatement("INSERT INTO Difficulty (id, name) VALUES (?, ?) ON CONFLICT (id) DO UPDATE SET name = excluded.name;");
			for (Difficulty difficulty:Difficulty.values()) {
				pstmt.setInt(1, difficulty.ordinal()+1);
				pstmt.setString(2, difficulty.toString());
				pstmt.executeUpdate();
			}
			return true;
		} catch (SQLException e) {
			console.printError(e.toString());
			return false;
		}
	}

	public LinkedList<DataBaseLabWorkManager.LabWorkAndUserID> selectLabWork() {
		return dataBaseLabWorkManager.select();
	}
	public boolean insertLabWork(LabWork labWork, long userID) {
		return dataBaseLabWorkManager.insert(labWork, userID);
	}
	public boolean updateLabWork(LabWork labWork) {
		return dataBaseLabWorkManager.update(labWork);
	}
	public boolean removeLabWork(long id) {
		return dataBaseLabWorkManager.remove(id);
	}

	public LinkedList<User> selectUser() { return dataBaseUserManager.select(); }
	public boolean insertUser(User user) { return dataBaseUserManager.insert(user); }
	public boolean updateUser(User user) { return dataBaseUserManager.update(user); }
}

