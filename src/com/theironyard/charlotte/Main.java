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

        Spark.post(
                "/login",
                (request, response) -> {
                    //get name form login
                    String name = request.queryParams("loginName");
                    User user = users.get(name);

                    //if user is null make new user put into hasmap
                    if (user == null) {
                        user = new User(name);
                        users.put(name, user);
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

        Spark.post("/messages",
                (req, res) -> {
                    //get username from session
                    //if isnt in session redirect to homepage and return;
                    Session session = req.session();

                    // try to get username from session
                    String userName = session.attribute("userName");

                    // if we found the username in session..
                    if (userName != null) {
                        // retrieve the current user from the users hashmap.
                        User user = users.get(userName);

                        // make a message object from the text that was posted
                        String text = req.queryParams("messageText");
                        Message message = new Message(text);

                        // add the message we just created to the user's arraylist of
                        // messages
                        user.messageList.add(message);
                    }

                    // always redirect back to the homepage
                    res.redirect("/");

                    // return an empty string because that's what Spark.post's lambda expects.
                    return "";
                }
        );


    }
}