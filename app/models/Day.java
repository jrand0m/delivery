package models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import play.db.jpa.Model;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="DAY_TYPE",
    discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue("BASIC_DAY")
public class Day extends Model {
    final static public String TIME_FORMAT = "HH:mm";
    @Column(name="from_time")
    public String              from;
    @Column(name="to_time")
    public String              to;
}
