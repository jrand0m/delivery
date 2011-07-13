package models;

import siena.Id;
import siena.Model;

public class Adress extends Model {
    @Id
    public Long id;
    public String street;
    public String appartamentsNumber;
    public String buldingNuber;
    public String additionalInfo;
}
