package models.dto.extern;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import models.Order;
import models.OrderItem;
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
public class BasketJSON {
	public BasketJSON(Order o) {
		discount = o.getUserDiscount().multiply(new BigDecimal(100).setScale(2, RoundingMode.HALF_EVEN)).intValue();
		total = o.getGrandTotal();
		delivery = o.getDeliveryPrice();
		for (OrderItem oi :o.items){
			items.add(new OrderItemJSON(oi));
		}
	}
	
	
	/** 
	 *  100 = 100%, 30 = 30%
	 * */
	public Integer discount = 0;
	/** 
	 * total of order including delivery including discount 
	 * */ 
	public Integer total = 0;
	/** 
	 * delivery price, value in coins 
	 * */
	public Integer delivery = 0;
	/** 
	 * place holder
	 * */
	public Object events = null;
	/**
	 * items of menu
	 * */
	public List<OrderItemJSON> items = new ArrayList<OrderItemJSON>();

	
	
}
