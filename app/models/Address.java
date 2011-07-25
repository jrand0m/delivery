package models;

import siena.Generator;
import siena.Id;
import siena.Model;
import siena.NotNull;
import siena.Table;

@Table("Address")
public class Address extends Model {
    @Id(Generator.AUTO_INCREMENT)
    public Long id;
    public String street;
    public String appartamentsNumber;
    public String buldingNuber;
    public String additionalInfo;
    @NotNull
    public User userId;
    public Boolean deleted = false;
}
