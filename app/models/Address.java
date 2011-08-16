package models;

import models.user.User;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import siena.Generator;
import siena.Id;
import siena.Model;
import siena.NotNull;
import siena.Table;

@Table("Address")
public class Address extends Model {
    @Id(Generator.AUTO_INCREMENT)
    public Long id;
    @NotNull
    @MaxSize(100)
    @MinSize(5)
    public String street;
    @MaxSize(5)
    public String appartamentsNumber;
    @MaxSize(5)
    public String buldingNuber;
    @MaxSize(200)
    public String additionalInfo;
    @NotNull
    public User userId;
    public Boolean deleted = false;
}
