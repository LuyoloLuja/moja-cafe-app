import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        port(2021);
        Spark.staticFiles.location("/public");
        Logic logic = new Logic("jdbc:sqlite:mojaCafeDB.db");
//        Map<String, Object> map = new HashMap<>();

        post("/waiter", (req, res) -> {
            Map<String, String> map = new HashMap<>();

            String username = req.queryParams("username");
            logic.addWaiter(username);

            map.put("name", username);
//            logic.submitDetails(username);
            return new ModelAndView(map, "waiter.handlebars");

        }, new HandlebarsTemplateEngine());

        get("/waiter", (req, res) -> {
            Map<String, Object> map = new HashMap<>();

            String name = req.queryParams("username");

//            System.out.println(map);
//            for (String names: map.keySet()) {
//                System.out.println(names);
//            }

            return new ModelAndView(map, "waiter.handlebars");
        }, new HandlebarsTemplateEngine());

        get("/shift", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "shift.handlebars");
        }, new HandlebarsTemplateEngine());

        get("/", (req, res) -> {
           Map<String, Object> map = new HashMap<>();
            String nameL = req.queryParams("username");
           return new ModelAndView(map, "home.handlebars");
        }, new HandlebarsTemplateEngine());

    }
}
//            statement.executeUpdate("INSERT INTO Day (day_working) VALUES ('Monday')");
//            statement.executeUpdate("INSERT INTO Day (day_working) VALUES ('Tuesday')");
//            statement.executeUpdate("INSERT INTO Day (day_working) VALUES ('Wednesday')");
//            statement.executeUpdate("INSERT INTO Day (day_working) VALUES ('Thursday')");
//            statement.executeUpdate("INSERT INTO Day (day_working) VALUES ('Friday')");
//            statement.executeUpdate("INSERT INTO Day (day_working) VALUES ('Saturday')");
//            statement.executeUpdate("INSERT INTO Day (day_working) VALUES ('Sunday')");