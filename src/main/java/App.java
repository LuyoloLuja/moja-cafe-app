import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.HashMap;
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
        Logic logic = new Logic("jdbc:sqlite:mojaCafeDB.db");
//        Map<String, Object> map = new HashMap<>();

        post("/waiter", (req, res) -> {
            Map<String, String> map = new HashMap<>();

            String username = req.queryParams("username");
            String day = req.queryParams("monday");
//            logic.addWaiter(username);
            logic.addWaiterDetail(username, day);

            map.put("name", username);
            map.put("day", day);
            return new ModelAndView(map, "waiter.handlebars");

        }, new HandlebarsTemplateEngine());

        get("/waiter", (req, res) -> {
            Map<String, Object> map = new HashMap<>();

            String name = req.queryParams("username");

            return new ModelAndView(map, "waiter.handlebars");
        }, new HandlebarsTemplateEngine());

        get("/shift", (req, res) -> {
            Map<String, Object> map = new HashMap<>();
            return new ModelAndView(map, "shift.handlebars");
        }, new HandlebarsTemplateEngine());

        get("/", (req, res) -> {
           Map<String, Object> map = new HashMap<>();
           return new ModelAndView(map, "home.handlebars");
        }, new HandlebarsTemplateEngine());

    }
}