package models.dto.extern;

import models.Order;
import models.OrderItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class BasketJSON {
	public BasketJSON(Order o) {
		if (o==null){
			return;
		}
		discount = o.getUserDiscount().multiply(new BigDecimal(100).setScale(2, RoundingMode.HALF_EVEN)).intValue();
		total = o.getGrandTotal();
		delivery = o.getDeliveryPrice();
		for (OrderItem oi :o.items){
			items.add(new OrderItemJSON(oi));
		}
		no = o.getShortHandId();
	}
	
	public String no;
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
