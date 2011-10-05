package models.dto.extern;

import java.util.ArrayList;
import java.util.List;

public class BasketJSON {
	/** 
	 * float 1 = 100%, 0.3 = 30%
	 * */
	public Float discount = 0.0F;
	/** 
	 * total of order excluding delivery 
	 * */ 
	public Integer total = 0;
	/** 
	 * price, value in coins 
	 * */
	public Integer delivery = 0;
	/** 
	 * place holder
	 * */
	public Object events = null;
	/**
	 * items of menu
	 * */
	public List items = new ArrayList();
	/**
	 * items:[
		{	
			mi: 1212,    // menu item id
			ip: 1321,    // item price in coins
			cnt: 1,      // count
			comps:[      // array of components. if object has no components this field is null; if no components choosen it is empty array;
					{
						title:"", // name to display
						desc: "", //description 
						price: 123, price in coins
					},
					{..}], 
			tit: "",     // title text
			des: "",     //description text
		}, 
		{...}
	]
	 * 
	 * */
	
	
}
