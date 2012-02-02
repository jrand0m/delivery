package models.dto.extern;

import models.MenuItemComponent;

import java.util.ArrayList;
import java.util.List;

public class MenuComponentsJSON {
	
	public MenuComponentsJSON(MenuItemComponent mi ) {
		no = mi.getId();
		name = mi.name();
		descr = mi.description();
		price = mi.itm_price;
		check = mi.itm_avaliable;
		for (MenuItemComponent i :mi.notCompatible){
			comp.add(i.getId());
		}
		for (MenuItemComponent i :mi.required){
			req.add(i.getId());
		}
	}
	public Long no;
	public String name;
	public String descr;
	public Integer price;
	public boolean check = false;
	public List<Long> comp = new ArrayList<Long>();
	public List<Long> req = new ArrayList<Long>();
}
