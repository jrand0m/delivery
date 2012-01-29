package models.geo;

import javax.persistence.ManyToOne;

import models.users.BaseUser;

import org.apache.commons.lang.NotImplementedException;

import play.data.validation.Match;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;


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
	@MaxSize(10)
	@MinSize(1)
	@Match("[А-яA-z0-9.,/]+")
	@Required
	public String appartamentsNumber;

	/**
	 * For restaurant addresses
	 * */

	@ManyToOne
	public BaseUser user;

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
