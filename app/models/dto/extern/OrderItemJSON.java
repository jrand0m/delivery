package models.dto.extern;

import models.OrderItem;

import java.util.ArrayList;

public class OrderItemJSON {
		public OrderItemJSON(OrderItem oi) {
			price = oi.totalPriceInclComponents();
			name = oi.name();
			id = oi.getId();
			desc = oi.desc();
			count = oi.count;
			components = oi.selectedComponentsNames();
			
		}
		/**
		 * price is displayed including selected components
		 * */
		public Integer price;
		public String name;
		public Long id;
		public String desc;
		public Integer count;
		public ArrayList<String> components = new ArrayList<String>();
}
