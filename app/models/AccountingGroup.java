package models;

import javax.persistence.Entity;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
public class AccountingGroup extends Model {
    public static class FIELDS{
	public final static String DELETED = "deleted";
	public final static String DESCRIPTION = "description";
	public final static String NAME = "name";
    }
    public boolean deleted = false;
    public String  description;
    public String  name;

}
