package models;

import siena.Generator;
import siena.Id;
import siena.Model;
import siena.NotNull;
import siena.Table;

@Table("Adress")
public class Adress extends Model {
	@Id(Generator.AUTO_INCREMENT)
	@NotNull
	public Long id;
	public String street;
	public String appartamentsNumber;
	public String buldingNuber;
	public String additionalInfo;
	@NotNull
	public User userId;
}
