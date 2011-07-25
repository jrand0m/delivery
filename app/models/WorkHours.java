package models;

import siena.Id;
import siena.Model;
import siena.NotNull;

public class WorkHours extends Model {
	@Id
	public Long id;
	@NotNull
	public Client client;
	public String description;
}
