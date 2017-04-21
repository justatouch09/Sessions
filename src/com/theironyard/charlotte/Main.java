package com.theironyard.charlotte;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;
import java.util.HashMap;

public class Main {

    public static HashMap<String, User> users = new HashMap<>();



    public static void main(String[] args) {
        ///make sure each user has own coment of messages hasmap strring to messages;
        //each user object group of messages;
        Spark.get(
                "/",
                (request, response) -> {
                    HashMap m = new HashMap();
                    Session session = request.session();

                    String userName = session.attribute("userName");
                    User user = users.get(userName);

                    if (user == null) {
                        return new ModelAndView(m, "login.html");
                    } else {
                        m.put("name", user.name);
                        //when user logged in add name and message list to model
                        m.put("notes", user.messageList);
                        return new ModelAndView(m, "home.html");
                    }
                },
                new MustacheTemplateEngine()
        );

//        Spark.post(
//                "/login",
//                (request, response) -> {
//                    String name = request.queryParams("loginName");
//                    User user =
//                    if (users.get(user) == null) {
//                        user = new User(name);
//
//                        users.put(name, user);
//                    } else {
//                    }
//                    Session session = request.session();
//                    session.attribute("userName", name);
//
//                    response.redirect("/");
//                    return "";
//                }
//        );

        Spark.post("/logout",
                (request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                }
        );
    }
}