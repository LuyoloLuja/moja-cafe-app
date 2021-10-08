import java.sql.*;

public class Logic {
    String connectionString;
    Connection connection = null;

    String insertName = "INSERT INTO Waiter (name) VALUES (?)";

    PreparedStatement insertUsername;

    public Logic(String connectionString) {

        try {
            this.connectionString = connectionString;
            connection = DriverManager.getConnection(connectionString);
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Waiter (name TEXT)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Day (day TEXT)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Shift_Type (Morning TEXT, Afternoon TEXT)");
            statement.executeUpdate("" +
                    "CREATE TABLE IF NOT EXISTS Waiter_Shift (" +
                    "waiter_id INT," +
                    "day_id INT," +
                    "shift_type_id INT," +
                    "FOREIGN KEY (waiter_id) REFERENCES Waiter(id)" +
                    "FOREIGN KEY (day_id) REFERENCES Day(id)" +
                    "FOREIGN KEY (shift_type_id) REFERENCES Shift_Type(id))");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public void addWaiter(String name) {
//        try {
//            insertUsername = connection.prepareStatement(insertName);
//            insertUsername.setString(1, name);
//            insertUsername.executeUpdate();
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//    }
    String insertDetailsQuery = "INSERT INTO Waiter_Shift (waiter_id, day_id) VALUES (?, ?)";
    PreparedStatement insertDetails;

    public void addWaiterDetail(String name, String day) throws SQLException {
        insertDetails = connection.prepareStatement(insertDetailsQuery);
        insertDetails.setString(1, name);
        insertDetails.setString(2, day);
        insertDetails.executeUpdate();
    }

    String getDayIdQuery = "SELECT Day WHERE day = (?)";
    PreparedStatement getDayId;
    public void getDayId(String day) throws SQLException {
        getDayId = connection.prepareStatement(getDayIdQuery);
        getDayId.setString(1, day);
        getDayId.executeUpdate();
    }
}
