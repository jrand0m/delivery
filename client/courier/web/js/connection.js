$.extend(_, {
	ajax: function(object) {
		var data = [
			{
				"id":"djlgud45",
				"time":1315440000000,
				"status":"CONFIRMED",
				"paymentStatus":"NOT_PAID",
				"from":"adsdgsgsgg",
				"to":"abereer rewh e",
				"price":22012,"list":[
					{
						"name":"Chelntano 1",
						"count":2,
						"pricePerItem":3006
					},
					{	"name":"Sup",
						"count":14,
						"pricePerItem":1000
					}
				]
			},
			{
				"id":"acb056cd",
				"time":1315440000000,
				"status":"ACCEPTED",
				"paymentStatus":"NOT_PAID",
				"from":"adsdgsgsgg",
				"to":"abereer rewh e",
				"price":22012,"list":[
					{
						"name":"Chelntano 1",
						"count":2,
						"pricePerItem":3006
					},
					{	"name":"Sup",
						"count":14,
						"pricePerItem":1000
					}
				]
			},
			{
				"id":"acb056cd",
				"time":1315440000000,
				"status":"ACCEPTED",
				"paymentStatus":"NOT_PAID",
				"from":"adsdgsgsgg",
				"to":"abereer rewh e",
				"price":22012,"list":[
					{
						"name":"Chelntano 1",
						"count":2,
						"pricePerItem":3006
					},
					{	"name":"Sup",
						"count":14,
						"pricePerItem":1000
					}
				]
			},
			{
				"id":"37ca592d",
				"time":1315440000000,
				"status":"COOKED",
				"timeToFinish":2518208798,
				"paymentStatus":"NOT_PAID",
				"from":"adsdgsgsgg",
				"to":"abereer rewh e",
				"price":16018,
				"list":[
					{
						"name":"Chelntano 1",
						"count":3,
						"pricePerItem":3006
					},
					{
						"name":"Sup",
						"count":5,
						"pricePerItem":1000
					}
				]
			}
		];
						
		object.success(data);
	},
	
	getNewOrders: function(parent) {
		_.ajax({
			url: '/api/g?id=1&from=' + parent.lastOrderTime,
			success: function(data) {
				_.parseNewOrders(data, parent);
			}
		});
		
		_.updateTimes(parent.activeOrders);
		
		window.setTimeout(function(){
			_.getNewOrders(parent);
		}, 20000);
	}, 
	
	getAllOrders: function(parent) {
		_.ajax({
			url: '/api/g?id=1',
			success: function(data) {
				_.parseAllOrders(data, parent);
			}
		});
		
		window.setTimeout(function(){
			_.getNewOrders(parent);
		}, 20000);
	},
	
  	sendOrderActivated: function(element, onSuccess) {
		_.ajax({
			url: '/api/p',
			dataType: "json",
			
			data: {
				'message': $.toJSON({ 
					'status' : 'ACCEPTED', 'id' : element.id, 'time' : element.time 
				})
			},
			
			success: function(data) {
				if(onSuccess)onSuccess();
			}
		});
	},
	
	sendOrderStatusChanged: function(element, status, onSuccess) {
		_.ajax({
			url: '/api/p',
			data: {'message': $.toJSON({ 'status' : status, 'id' : element.id})},
			success: function(data) {
				if(onSuccess)onSuccess();
			}
		});
	},
	
	sendOrderRejected: function(element, comment, onSuccess) {
		_.ajax({
			url: '/api/p',
			data: {'message': $.toJSON({ 'status' : 'DECLINED', 'id' : element.id, 'comment': comment})},
			success: function(data) {
				if(onSuccess)onSuccess();
			}
		});
	},
});