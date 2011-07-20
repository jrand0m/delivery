package models;

import java.util.Date;

import siena.Id;
import siena.Model;

public class MenuItem extends Model {
    @Id
    public Long id;
    public String name;
    public String description;
    /**
     * value in coins
     * */
    public Integer price;
    public Boolean avaliable;
    public Date menuItemCreated;
    public MenuItemGroup group;
    public Client client;

}
