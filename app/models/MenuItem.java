package models;

import java.util.Date;

import siena.Id;
import siena.Model;
import siena.NotNull;

public class MenuItem extends Model {
    @Id
    public Long id;
    @NotNull
    public String name;
    public String description;
    /**
     * value in coins
     * */
    @NotNull
    public Integer price;
    public Boolean avaliable = false;
    @NotNull
    public Date menuItemCreated;
    public MenuItemGroup group;
    @NotNull
    public Client client;
    public Boolean deleted = false;

}
