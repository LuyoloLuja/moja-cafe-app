import java.sql.*;

public class Connect {
    public static void connect() {
        Connection connection = null;

        try {
            String url = "jdbc:sqlite:mojaCafeDB.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
