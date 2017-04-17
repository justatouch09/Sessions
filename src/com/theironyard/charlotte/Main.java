package com.theironyard.charlotte;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

public static User user;
//currently logged  in user to display in html
public static ArrayList<Message> messages = new ArrayList<>();
//2. Create a GET route for "/" and a POST route for
// 3. "/create-user" and
// 4. "/create-message"
    public static void main(String[] args) {
        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    if (user == null) {
                        return new ModelAndView(m, "login.html");
                    } else {
                        m.put("name", user.name);
                        m.put("notes", messages);
                        return new ModelAndView(m, "home.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                ((request, response) -> {
                    String name = request.queryParams("loginName");
                    user = new User(name);
                    response.redirect("/");
                    return "";
                })
        );

       Spark.post(
               "/messages",
               ((request, response) -> {
                   String message = request.queryParams("message-text");
                   Message m = new Message(message);
                   messages.add(m);
                   response.redirect("/");
                   return "";
                   //redirect to root
               })
       );
    }
}
