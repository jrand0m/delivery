package controllers;

import java.lang.annotation.Annotation;

import models.users.User;
import play.data.validation.Email;
import play.libs.Mail;
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
