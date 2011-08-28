package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.Model;

@Entity
public class Day extends Model {
    final static public String TIME_FORMAT = "HH:mm";

    public String              from;
    @Id
    public Long                id;
    public String              to;
}
