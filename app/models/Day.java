package models;

import siena.Id;
import siena.Model;

public class Day extends Model {
    final static public String TIME_FORMAT = "HH:mm";

    @Id
    public Long id;
    public String from;
    public String to;
}
