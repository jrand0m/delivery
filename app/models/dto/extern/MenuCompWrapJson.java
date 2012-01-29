package models.dto.extern;

import java.util.List;

public class MenuCompWrapJson {
	/**
     * menuitem name
     * */
    public String nm;
    /**
     * menuitem descripton
     * */
	public String dc;
    /**
     * menuitem id
     * */
	public String no;
	public List<MenuComponentsJSON> items;
    /**
     * base(menuitem) price
     * */
	public Integer pc;
}
