package models;

import javax.persistence.Entity;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
public class AccountingGroup extends Model {
	public static class FIELDS {
		public final static String AG_DELETED = "deleted";
		public final static String AG_DESCRIPTION = "description";
		public final static String AG_NAME = "name";
	}

	public boolean deleted = false;
	public String description;
	public String name;

}
