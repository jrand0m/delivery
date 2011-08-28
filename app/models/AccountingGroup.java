package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = false")
public class AccountingGroup extends Model {
    public boolean deleted = false;
    public String  desc;
    @Id
    public Long    id;
    public String  name;

}
