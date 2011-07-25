package models;

import siena.Id;
import siena.Model;
import siena.NotNull;

public class Day extends Model {
	final static public String TIME_FORMAT = "HH:mm";

	@Id
	@NotNull
	public Long id;
	public String from;
	public String to;
	public WorkHours workDay;
}
