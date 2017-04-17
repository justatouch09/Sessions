package com.theironyard.charlotte;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;
import sun.plugin2.message.Message;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

public static User user;

public static Message message;

    public static void main(String[] args) {
        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    if (user == null) {
                        return new ModelAndView(m, "login.html");
                    } else {
                        m.put("name", user.name);
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

        Spark.get(
                "/",
                ((request, response) -> {
                    ArrayList<Message> messages = new ArrayList<>();
                    message = new Message("message", user.message);
                }
    }
}
