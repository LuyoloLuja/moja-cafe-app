package net.moja.cafe;

import java.sql.*;

public class Logic {
    String connectionString;
    Connection connection = null;

    public Logic(String connectionString) {

        try {
            this.connectionString = connectionString;
            connection = DriverManager.getConnection(connectionString);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Waiter (id INTEGER NOT NULL PRIMARY KEY, name TEXT)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Day (id INTEGER NOT NULL PRIMARY KEY, day TEXT)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Shift_Type (id INTEGER NOT NULL PRIMARY KEY, name TEXT)");
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Waiter_Shift " +
                            "(id INTEGER NOT NULL PRIMARY KEY, " +
                            "waiter_id INTEGER, " +
                            "day_id INTEGER, " +
                            "shift_type_id INTEGER, " +
                            "FOREIGN KEY (waiter_id) REFERENCES Waiter(id), " +
                            "FOREIGN KEY (day_id) REFERENCES Day(id) " +
                            "FOREIGN KEY (shift_type_id) " +
                            "REFERENCES Shift_Type(id))");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String insertDetailsQuery = "INSERT INTO Waiter_Shift (waiter_id, day_id) VALUES (?, ?)";
    String insertNameQuery = "INSERT INTO Waiter (name) VALUES (?)";
    String getDayIdQuery = "SELECT id FROM Waiter_Shift WHERE day_id = (?)";
    PreparedStatement preparedStatement;

    public void addWaiterDetail(String name, String day) throws SQLException {

        insertName(name);
        int daysId = getDay(day);

        preparedStatement = connection.prepareStatement(insertDetailsQuery);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, daysId);
        preparedStatement.executeUpdate();
    }

    public void insertName(String name) throws SQLException {
        preparedStatement = connection.prepareStatement(insertNameQuery);
        preparedStatement.setString(1, name);
        preparedStatement.executeUpdate();
    }

    public int getDay(String day) throws SQLException {
        preparedStatement = connection.prepareStatement(getDayIdQuery);
        preparedStatement.setString(1, day);
        return preparedStatement.executeUpdate();
    }
}
