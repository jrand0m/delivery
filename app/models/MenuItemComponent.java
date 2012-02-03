package models;

import play.db.jpa.Model;

import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MenuItemComponent  {
    public static class FIELDS {
        public static final String ITM_ROOT = "itm_root";

    }

    @ManyToOne
    public MenuItem itm_root;
    public String itm_name;
    public String itm_desc;
    public Integer itm_price;
    public boolean itm_avaliable = false;
    @ManyToMany
    @JoinTable(name = "REQUIRED_MAP")
    public List<MenuItemComponent> required = new ArrayList<MenuItemComponent>();
    @ManyToMany
    @JoinTable(name = "NOT_COMPATIBLE_MAP")
    public List<MenuItemComponent> notCompatible = new ArrayList<MenuItemComponent>();
    @ManyToMany(fetch = FetchType.LAZY)
    public Set<OrderItem> usedIn;

    // TODO add internationalization
    public String name() {
        return itm_name;
    }

    // TODO add internationalization
    public String description() {
        return itm_desc;
    }

    public Integer price() {
        return itm_price;
    }

    @Override
    public String toString() {

        return itm_name + " " + itm_price;
    }
}
