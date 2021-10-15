package net.moja.cafe;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.Arrays;
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

//        String[] weekDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
//        Arrays.asList(weekDays).forEach( day -> {
//            handle.execute("insert into Day (day) values (?)", day);
//        } );

        get("/", (req, res) -> {
            Map<String, Object> map = new HashMap<>();

            return new ModelAndView(map, "home.handlebars");
        }, new HandlebarsTemplateEngine());

        get("/waiter/:username", (req, res) -> {
            String username = req.params("username");

            Map<String, Object> map = new HashMap<>();
            map.put("username", username);

            return new ModelAndView(map, "home.handlebars");

        }, new HandlebarsTemplateEngine());

        post("/waiter/:username", (req, res) -> {

            Map<String, String> map = new HashMap<>();
            String username = req.queryParams("username");
            String[] days = req.queryParamsValues("day");

            username = username.substring(0,1).toUpperCase() + username.substring(1).toLowerCase();

            // find the user id from the username
            int waiterCount = handle.select("SELECT count(*) FROM Waiter where name = ?", username)
                    .mapTo(Integer.class)
                    .findOnly();

            // add the waiter if it doesn't exist
            if (waiterCount == 0 ){
                handle.execute("INSERT INTO Waiter (name) VALUES (?)",
                        username);
            }

            Waiter waiter = handle.select("SELECT * FROM Waiter where name = ?", username)
                    .mapToBean(Waiter.class)
                    .findOnly();

            for (String dayName : days) {
                // find the day id for each day
                Day day = handle.select("SELECT * FROM Day WHERE day = ?", dayName)
                        .mapToBean(Day.class)
                        .findOnly();

                handle.execute("INSERT INTO Waiter_Shift (waiter_id, day_id) VALUES (?, ?)",
                                    waiter.getId(),
                                    day.getId());
            }

            return new ModelAndView(map, "home.handlebars");
        }, new HandlebarsTemplateEngine());

        get("/waiter", (req, res) -> {
            Map<String, String> map = new HashMap<>();

            return new ModelAndView(map, "waiter.handlebars");
        }, new HandlebarsTemplateEngine());

        get("/shift", (req, res) -> {
            Map<String, Object> map = new HashMap<>();

            List<Shift> shifts = handle.select(
                        "SELECT name AS waiterName, day as dayName FROM Waiter_shift " +
                                "JOIN Waiter ON waiter_id = Waiter.id " +
                                "JOIN Day ON day_id = Day.id")
                    .mapToBean(Shift.class)
                    .list();

            map.put("shifts", shifts);

            return new ModelAndView(map, "shift.handlebars");
        }, new HandlebarsTemplateEngine());
    }
}



