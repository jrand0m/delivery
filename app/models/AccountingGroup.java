package models;

import javax.persistence.Entity;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = 0")
public class AccountingGroup extends Model {
    public boolean deleted = false;
    public String  description;
    public String  name;

}
