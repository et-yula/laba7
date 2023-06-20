package managers;

import models.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.LinkedList;

public class DataBaseLabWorkManager {
    private final DataBaseManager dataBaseManager;

    static final class LabWorkAndUserID {
        public LabWork labwork;
        public long user;
        public LabWorkAndUserID(LabWork labwork, long user) {
            this.labwork = labwork;
            this.user = user;
        }
    }

    public DataBaseLabWorkManager(DataBaseManager dataBaseManager) {

        this.dataBaseManager = dataBaseManager;
    }

    /**
     * выбираем лабораторные работы и их авторов (при наличии)
     * @return LinkedList с найденными лабораторными работами и их авторами (при их наличии)
     */
    public LinkedList<LabWorkAndUserID> select() {
        LinkedList<LabWorkAndUserID> linkedListLabWorkAndUserID = new LinkedList<>();
        try {
            ResultSet rs = dataBaseManager.getStatement().executeQuery("SELECT LabWork.id AS id, LabWork.name AS name, Coordinates.x AS coordinates_x, Coordinates.y AS coordinates_y, LabWork.creationDate AS creationDate, LabWork.minimalPoint AS minimalPoint, Difficulty.name AS difficulty, Person.name AS person_name, Person.birthday AS person_birthday, Person.passportID AS person_passportid, Locations.x AS person_location_x, Locations.y AS person_location_y, Locations.z AS person_location_z, Locations.name AS person_location_name, LabWork.id_user AS id_user FROM LabWork "
                    + "JOIN Coordinates ON Coordinates.id = LabWork.id_coordinates "
                    + "LEFT JOIN Difficulty ON LabWork.id_difficulty = difficulty.id "
                    + "LEFT JOIN Person ON LabWork.id_person = Person.id "
                    + "LEFT JOIN Locations ON Person.id_location = Locations.id;");

            while (rs.next()) {
                Long minimalPoint = null;
                try {
                    minimalPoint = rs.getLong("minimalPoint");
                } catch (NullPointerException | IllegalArgumentException  e) { }
                Difficulty difficulty = null;
                try {
                    difficulty = Difficulty.valueOf(rs.getString("difficulty"));
                }
                catch (NullPointerException | IllegalArgumentException  e) { }
                var coordinates = new Coordinates(rs.getInt("coordinates_x"), rs.getLong("coordinates_y"));
                Location location = null;
                try {
                    location = new Location(rs.getInt("person_location_x"), rs.getInt("person_location_y"),rs.getInt("person_location_z"),rs.getString("person_location_name"));
                }
                catch (NullPointerException | IllegalArgumentException  e) { }
                Person author = null;
                if (rs.getString("person_name")!=null) {
                    author = new Person(rs.getString("person_name"), rs.getObject("person_birthday", LocalDate.class), rs.getString("person_passportid"),location);
                }
                var labWork = new LabWork(rs.getInt("id"), rs.getString("name"), coordinates, rs.getObject("creationDate", LocalDate.class),minimalPoint,difficulty,author);
                if (labWork.validate()) {
                    linkedListLabWorkAndUserID.add(new LabWorkAndUserID(labWork, rs.getInt("id_user")));
                }
            }
            rs.close();
            return linkedListLabWorkAndUserID;
        } catch (SQLException e) {
            System.out.println(e);
            return linkedListLabWorkAndUserID;
        }
    }

    /**
     * добавляет информацию о локации в базу данных
     * @param location - данные о локации
     * @return id локации
     * @throws SQLException
     */
    private long insert(Location location) throws SQLException {
        try ( PreparedStatement stmt = dataBaseManager.getPreparedStatementRGK("INSERT INTO Location(x, y, z, name) VALUES (?, ?, ?, ?)"); ) {
            stmt.setInt(1, location.getX());
            stmt.setInt(2, location.getY());
            stmt.setInt(3,location.getZ());
            stmt.setString(4,location.getName());
            if (stmt.executeUpdate() == 0) { throw new SQLException("Creating location failed, no rows affected."); }
            try (ResultSet gk = stmt.getGeneratedKeys()) {
                if (gk.next()) {
                    return gk.getLong(1);
                } else {
                    throw new SQLException("Creating location failed, no ID obtained.");
                }
            }
        }
    }

    /**
     * добавляет в базу данных информацию о координатах
     * @param coordinates - данные о координатах
     * @return id координат
     * @throws SQLException
     */
    private long insert(Coordinates coordinates) throws SQLException {
        try ( PreparedStatement stmt = dataBaseManager.getPreparedStatementRGK("INSERT INTO Coordinates(x, y) VALUES (?, ?)"); ) {
            stmt.setInt(1, coordinates.getX());
            stmt.setLong(2, coordinates.getY());
            if (stmt.executeUpdate() == 0) { throw new SQLException("Creating coordinates failed, no rows affected."); }
            try (ResultSet gk = stmt.getGeneratedKeys()) {
                if (gk.next()) {
                    return gk.getLong(1);
                } else {
                    throw new SQLException("Creating coordinates failed, no ID obtained."); }
            }
        }
    }

    /**
     * добавляет в базу данных данные об авторе
     * @param author - автор для лабораторной работы
     * @return id автора
     * @throws SQLException
     */
    private long insert(Person author) throws SQLException {
        try ( PreparedStatement stmt = dataBaseManager.getPreparedStatementRGK("INSERT INTO Person(name, birthday, passportID, id_location) VALUES (?, ?, ?, ?)"); ) {
            stmt.setString(1, author.getName());
            stmt.setObject(2, author.getBirthday());
            stmt.setString(3, author.getPassportID());
            if (author.getLocation() == null) {
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setLong(4, insert(author.getLocation()));
            }
            if (stmt.executeUpdate() == 0) { throw new SQLException("Creating author failed, no rows affected."); }
            try (ResultSet gk = stmt.getGeneratedKeys()) {
                if (gk.next()) {
                    return gk.getLong(1);
                } else {
                    throw new SQLException("Creating author failed, no ID obtained.");
                }
            }
        }
    }

    /**
     * добавляет новую лабораторную работу в базу данных
     * @param labWork - лабораторная работа для заполнения
     * @param userID - id пользователя, создавшего лабораторную работу
     * @return успешность выполнения
     */
    public boolean insert(LabWork labWork, long userID) {
        try ( PreparedStatement stmt = dataBaseManager.getPreparedStatementRGK("INSERT INTO LabWork(name, id_coordinates, creationDate, minimalPoint, id_difficulty, id_person, id_user) VALUES (?, ?, ?, ?, ?, ?, ?)") ) {
            stmt.setString(1, labWork.getName());
            stmt.setLong(2, insert(labWork.getCoordinates()));
            stmt.setObject(3, labWork.getCreationDate());
            stmt.setLong(4, labWork.getMinimalPoint());
            if (labWork.getDifficulty() == null){
                stmt.setNull(5, Types.INTEGER);
            } else {
                stmt.setInt(5, labWork.getDifficulty().ordinal()+1);
            }
            if (labWork.getAuthor()==null) {
                stmt.setNull(6, Types.INTEGER);
            } else {
                stmt.setLong(6, insert(labWork.getAuthor()));
            }
            stmt.setLong(7, userID);

            if (stmt.executeUpdate() == 0) {
                throw new SQLException("Creating labWork failed, no rows affected.");
            }
            try (ResultSet gk = stmt.getGeneratedKeys()) {
                if (gk.next()) {
                    labWork.setId(gk.getInt(1));
                    return true;
                } else {
                    throw new SQLException("Creating labWork failed, no ID obtained."); }
            }
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * обновляет данные в лабораторной работе
     * @param labWork - измененная лабораторная работа
     * @return успешность выполнения
     */
    public boolean update(LabWork labWork) {
        try {
            PreparedStatement stmt = dataBaseManager.getPreparedStatement("UPDATE LabWork SET name = ?, id_coordinates = ?, minimalPoint = ?, id_difficulty = ?, id_person = ?  WHERE id = ?");
            stmt.setString(1, labWork.getName());
            stmt.setLong(2, insert(labWork.getCoordinates()));
            stmt.setLong(3, labWork.getMinimalPoint());
            if (labWork.getDifficulty() == null){
                stmt.setNull(4, Types.INTEGER);
            } else {
                stmt.setInt(4, labWork.getDifficulty().ordinal()+1);
            }
            if (labWork.getAuthor()==null) {
                stmt.setNull(5, java.sql.Types.INTEGER);
            } else {
                stmt.setLong(5, insert(labWork.getAuthor()));
            }
            stmt.setLong(6,labWork.getId());
            if (stmt.executeUpdate()==0) throw new SQLException("Update labWork failed, no rows affected.");
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * удаляет лабораторную работу по id из базы данных
     * @param id - id лабораторной работы
     * @return успешность выполнения
     */
    public boolean remove(long id) {
        try {
            PreparedStatement deleteStmt = dataBaseManager.getPreparedStatement("DELETE FROM LabWork WHERE id = ?");
            deleteStmt.setLong(1, id);
            return deleteStmt.executeUpdate()>0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
}
