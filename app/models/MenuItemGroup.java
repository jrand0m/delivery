package models;

import siena.Id;
import siena.Model;

public class MenuItemGroup extends Model {
    @Id
    Long id;
    String name;
    String description;
    Boolean generic = Boolean.FALSE;

}
