package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
public class MenuItemGroup extends Model {
    public boolean deleted = false;

    public String  description;
    public Boolean generic = Boolean.FALSE;
    public String  name;
}
