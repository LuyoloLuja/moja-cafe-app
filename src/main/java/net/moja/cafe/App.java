package net.moja.cafe;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 2021; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        Spark.staticFiles.location("/public");

        String dbDiskURL = "jdbc:sqlite:file:./mojaCafeDB.db";
        Jdbi jdbi = Jdbi.create(dbDiskURL);
        Handle handle = jdbi.open();

        handle.execute("CREATE TABLE IF NOT EXISTS Waiter (id INTEGER NOT NULL PRIMARY KEY, name TEXT)");
        handle.execute("CREATE TABLE IF NOT EXISTS Day (id INTEGER NOT NULL PRIMARY KEY, day TEXT)");

        handle.execute("CREATE TABLE IF NOT EXISTS Waiter_Shift " +
                "(id INTEGER NOT NULL PRIMARY KEY, " +
                "waiter_id INTEGER, day_id INTEGER, " +
                "FOREIGN KEY (waiter_id) REFERENCES Waiter(id), " +
                "FOREIGN KEY (day_id) REFERENCES Day(id))");

        get("/", (req, res) -> {
            Map<String, Object> map = new HashMap<>();

            return new ModelAndView(map, "home.handlebars");
        }, new HandlebarsTemplateEngine());

        post("/waiter/:username", (req, res) -> {
            Map<String, Object> map = new HashMap<>();

            String username = req.queryParams("username");
            String day = req.queryParams("day");

            username = username.substring(0,1).toUpperCase() + username.substring(1).toLowerCase();

            Logic logic = new Logic(username, day);

            Integer checkName = handle.select("SELECT COUNT(*) FROM Waiter WHERE name = ?", logic.getName()).mapTo(int.class).findOnly();
            Integer checkDay = handle.select("SELECT COUNT(*) FROM DAY WHERE day = ?", logic.getDay()).mapTo(int.class).findOnly();

            if (checkName == 0 && checkDay != null ) {
                handle.execute("INSERT INTO Waiter (name) VALUES (?)", username);
            }
            // not inserting correctly
            handle.execute("INSERT INTO Waiter_Shift (waiter_id, day_id) VALUES (?, ?)", checkName, checkDay);

            // throws an error
            List<String> days = handle.select("SELECT * FROM Day").mapToBean(String.class).list();

            map.put("name", username);
            map.put("days", days.size());
            return new ModelAndView(map , "waiter.handlebars");

        }, new HandlebarsTemplateEngine());

        get("/waiter", (req, res) -> {
            Map<String, String> map = new HashMap<>();

            return new ModelAndView(map, "waiter.handlebars");
        }, new HandlebarsTemplateEngine());

        get("/shift", (req, res) -> {
            Map<String, Object> map = new HashMap<>();

            return new ModelAndView(map, "shift.handlebars");
        }, new HandlebarsTemplateEngine());
    }
}