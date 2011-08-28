package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = false")
public class MenuItemGroup extends Model {
    public boolean deleted = false;

    public String  description;
    public Boolean generic = Boolean.FALSE;
    @Id
    public Long    id;
    public String  name;
}
