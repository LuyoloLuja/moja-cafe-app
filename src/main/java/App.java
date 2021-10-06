import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        port(2021);
        staticFiles.location("/public");
        Connect.connect();
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:mojaCafeDB.db");
            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Waiter (id INT PRIMARY KEY, name TEXT)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Day (id INT PRIMARY KEY, day_working TEXT, order_column TEXT)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Shift (id INT PRIMARY KEY, morning_shift TEXT, afternoon_shift TEXT, day_id INT, FOREIGN KEY (day_id) REFERENCES Day(id))");

            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Waiter_Shift " +
                            "(id INT PRIMARY KEY, " +
                            "shift_id INT, " +
                            "waiter_id INT, " +
                            "FOREIGN KEY (waiter_id) REFERENCES Waiter(id), " +
                            "FOREIGN KEY (shift_id) REFERENCES Shift(id))");

            statement.executeUpdate("INSERT INTO Day (day_working) VALUES ('Monday')");
            statement.executeUpdate("INSERT INTO Day (day_working) VALUES ('Tuesday')");
            statement.executeUpdate("INSERT INTO Day (day_working) VALUES ('Wednesday')");
            statement.executeUpdate("INSERT INTO Day (day_working) VALUES ('Thursday')");
            statement.executeUpdate("INSERT INTO Day (day_working) VALUES ('Friday')");
            statement.executeUpdate("INSERT INTO Day (day_working) VALUES ('Saturday')");
            statement.executeUpdate("INSERT INTO Day (day_working) VALUES ('Sunday')");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        get("/waiter/:username", (req, res) -> {
            return "Waiter";
        });
        post("/waiter/:username", (req, res) -> {
            return "";
        });

    }
}
