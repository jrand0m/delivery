package models.geo;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import models.users.EndUser;
import models.users.User;

import org.apache.commons.lang.NotImplementedException;
import org.hibernate.annotations.Where;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.db.jpa.Model;

@Entity
public class UserAddress extends Address {

	public final static class FIELDS {
		public final static String ADDRESS_ADDITIONAL_INFO = "additionalInfo";
		public final static String USER = "user";
		public final static String APPARTAMENTS_NUMBER = "appartamentsNumber";
		public final static String DOOR_CODE = "doorCode";
	}

	@MaxSize(200)
	public String additionalInfo;
	public String doorCode;
	@MaxSize(5)
	public String appartamentsNumber;

	/**
	 * For restaurant addresses
	 * */

	@ManyToOne
	public User user;

	@Override
	public String toString() {
		return String.format("%s, %s / %s%s", street, buldingNuber,
				appartamentsNumber,
				doorCode != null && !doorCode.isEmpty() ? " (code:" + doorCode
						+ ")" : "");
	}

	public final String localize(String lang) {
		throw new NotImplementedException();
	}
}
