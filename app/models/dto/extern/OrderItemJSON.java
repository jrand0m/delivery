package models.dto.extern;

import java.util.ArrayList;

import models.OrderItem;

public class OrderItemJSON {
		public OrderItemJSON(OrderItem oi) {
			price = oi.totalPriceInclComponents();
			name = oi.name();
			id = oi.getId();
			desc = oi.desc();
			count = oi.count;
			components = oi.selectedComponentsNames();
			if (!components.isEmpty()){
				desc += "<br /> (";
				for (int i = 0 ; i<components.size(); i ++){
					desc += components.get(i);
					if (i<components.size()-1){
						desc+= ", ";
					}
				}
				desc+= ") ";
			}
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
