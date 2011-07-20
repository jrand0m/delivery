package models;

import siena.Id;
import siena.Model;

public class Day extends Model {
    @Id
    public Long id;
    final static public String TIME_FORMAT = "HH:mm";
    public String from;
    public String to;
    public WorkHours workDay;
}
