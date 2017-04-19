package com.theironyard.charlotte;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;
import java.util.HashMap;

public class Main {

    public static HashMap<String, User> users = new HashMap<>();

    public static String MESSAGES = "messages";

    public String NameList = "name";

    private boolean addMessageToUser(String userName, Message m, HashMap<String, User> users){
        if (users == null || users.isEmpty()) return false;

        for(Entry<String, User> entry : users.entrySet()) {
            if (entry.key().equals(userName)) {
                if (entry.value() != null && entry.value().messages != null) {
                    entry.value().messages.add(m);
                    return true;
                }
            }
        }

        return false;
    }

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
                        m.put("notes", MESSAGES);
                        return new ModelAndView(m, "home.html");
                    }
                },
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                (request, response) -> {
                    String name = request.queryParams("nameFromForm");
                    User user = new User(name);
                    if (user == null) {
                        user = new User(name);
                        users.put(name, user);
                    } else {
                    }
                    Session session = request.session();
                    session.attribute("userName", name);

                    response.redirect("/");
                    return "";
                }
        );

        Spark.post("/logout",
                (request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                }
        );
//
//        Spark.get("new-message",
//                (request, response) -> {
//                    return new ModelAndView(null, "message.html");
//                },
//                new MustacheTemplateEngine();
//        );
//
//        Spark.post(
//                "/messages",
//                (request, response) -> {
//                    String message = request.queryParams("message-text");
//                    Message Mk = new Message(message);
//                    MESSAGES.add(Mk);
//                    response.redirect("/");
//                    return "";
//                    //redirect to root
//                }
//        );
//    }
//}
    }
}