package models;

import siena.Id;
import siena.Model;
import siena.NotNull;

public class MenuItemGroup extends Model {
    @Id
    public Long id;
    @NotNull
    public String name;
    public String description;
    public Boolean generic = Boolean.FALSE;
    public Boolean deleted = false;
}
