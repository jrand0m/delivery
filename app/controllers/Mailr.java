package controllers;

import models.users.User;
import play.mvc.Mailer;

public class Mailr extends Mailer {
    public static void lostPassword(User user) {
        String newpassword = user.password;
        setFrom("no-reply <robot@vdoma.com.ua>");
        setSubject("Your password has been reset");
        addRecipient(user.email);
        send(user, newpassword);
    }
}
