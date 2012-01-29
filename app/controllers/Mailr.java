package controllers;

import models.users.BaseUser;
import play.mvc.Mailer;

public class Mailr extends Mailer {
	public static void lostPassword(BaseUser user) {
	      String newpassword = user.password;
	      setFrom("no-reply <robot@vdoma.com.ua>");
	      setSubject("Your password has been reset");
	      addRecipient(user.email);
	      send(user, newpassword);
	}
}
