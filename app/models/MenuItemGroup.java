package models;

import javax.persistence.Entity;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
public class MenuItemGroup extends Model {
    public static final class FIELDS {
	public static final String MENU_ITEM_GROUP_DELETED = "deleted";
	public static final String MENU_ITEM_GROUP_DESCRIPTION = "description";
	public static final String MENU_ITEM_GROUP_GENERIC = "generic";
	public static final String MENU_ITEM_GROUP_NAME = "name";
    }

    public boolean deleted = false;

    public String description;
    public boolean generic = Boolean.FALSE;
    public String name;
}
