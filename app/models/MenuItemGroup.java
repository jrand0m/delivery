package models;

import siena.Id;
import siena.Model;

public class MenuItemGroup extends Model {
    @Id
    public Long id;
    public String name;
    public String description;
    public Boolean generic = Boolean.FALSE;

}
