package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Where;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.db.jpa.Model;

@Entity
@Where(clause = "deleted = false")
public class Address extends Model {
    @MaxSize(200)
    public String  additionalInfo;
    @MaxSize(5)
    public String  appartamentsNumber;
    @MaxSize(5)
    public String  buldingNuber;
    public boolean deleted = false;
    @Id
    public Long    id;
    @MaxSize(100)
    @MinSize(5)
    public String  street;
    public User    userId;
}
