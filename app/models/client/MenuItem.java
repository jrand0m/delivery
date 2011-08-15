package models.client;

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
    
    public MenuItem(){
	
    }
    /**
     * Cloning constructor
     * generates new id
     * */
    public MenuItem(MenuItem item) {
	this(null, item.name, item.description,item.price,item.avaliable,item.menuItemCreated,
		item.group,item.client, item.deleted);
    }

    public MenuItem(Long id, String name, String description, Integer price,
	    Boolean avaliable, Date menuItemCreated, MenuItemGroup group,
	    Client client, Boolean deleted) {
	super();
	this.id = id;
	this.name = name;
	this.description = description;
	this.price = price;
	this.avaliable = avaliable;
	this.menuItemCreated = menuItemCreated;
	this.group = group;
	this.client = client;
	this.deleted = deleted;
    }
    
    
}
