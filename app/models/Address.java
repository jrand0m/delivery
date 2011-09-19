package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import models.users.User;

import org.hibernate.annotations.Where;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
public class Address extends Model {

    public final static class FIELDS {
	public final static String ADDRESS_ADDITIONAL_INFO = "additionalInfo";
	public final static String ADDRESS_APPARTAMENTS_NUMBER = "appartamentsNumber";
	public final static String ADDRESS_BULDING_NUBER = "buldingNuber";
	public final static String ADDRESS_DELETED = "deleted";
	public final static String ADDRESS_STREET = "street";
	public final static String ADDRESS_USER = "user";
    }

    @MaxSize(200)
    public String additionalInfo;
    @MaxSize(5)
    public String appartamentsNumber;
    @MaxSize(5)
    public String buldingNuber;
    public boolean deleted = false;

    @MaxSize(100)
    @MinSize(5)
    public String street;
    @ManyToOne
    public User user;
}
