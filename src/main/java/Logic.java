import java.sql.*;

public class Logic {
    String connectionString;
    Connection connection = null;

    String insertName = "INSERT INTO Waiter (name) VALUES (?)";
    PreparedStatement insertUsername;
    String selectDays = "SELECT day_working FROM Day WHERE day_working = (?)";
    String selectNameStatement = "SELECT name FROM Waiter WHERE name = (?)";
    PreparedStatement selectName;

    public Logic(String connectionString) {

        try {
            this.connectionString = connectionString;
            connection = DriverManager.getConnection(connectionString);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Waiter (name TEXT)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Day (day_working TEXT, order_column TEXT)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Shift (id INT PRIMARY KEY, morning_shift TEXT, afternoon_shift TEXT, day_id INT, FOREIGN KEY (day_id) REFERENCES Day(id))");
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Waiter_Shift " +
                            "(id INT PRIMARY KEY, " +
                            "shift_id INT, " +
                            "waiter_id INT, " +
                            "FOREIGN KEY (waiter_id) REFERENCES Waiter(id), " +
                            "FOREIGN KEY (shift_id) REFERENCES Shift(id))");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addWaiter(String name) {
        try {
            insertUsername = connection.prepareStatement(insertName);
            insertUsername.setString(1, name);
            insertUsername.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
