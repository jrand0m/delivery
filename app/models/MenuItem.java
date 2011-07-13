package models;

import java.util.Date;

import siena.Id;
import siena.Model;

public class MenuItem extends Model {
    @Id
    Long id;
    String name;
    String description;
    /**
     * value in coins
     * */
    Integer price;
    Boolean avaliable;
    Date menuItemCreated;

}
