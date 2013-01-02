package models.dto.extern;

import models.MenuItemComponent;

import java.util.ArrayList;
import java.util.List;

import static helpers.OrderUtils.convertMoneyToCents;

public class MenuComponentsJSON {

    public MenuComponentsJSON(MenuItemComponent mi) {
        no = mi.id;
        name = mi.name();
        descr = mi.description();
        price = convertMoneyToCents(mi.price);
        check = false;
        for (Long i : mi.notCompatible) {
            comp.add(i);
        }
        for (Long i : mi.requiredIds) {
            req.add(i);
        }
    }

    public Long no;
    public String name;
    public String descr;
    public Integer price;
    @Deprecated
    public boolean check = false;
    public List<Long> comp = new ArrayList<Long>();
    public List<Long> req = new ArrayList<Long>();
}
