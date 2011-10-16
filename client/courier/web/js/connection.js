$.extend(_, {
	ajax: function(object) {
		var data;
		if(object.url.indexOf("from") != -1) {
			data = [
				{
					"id":"2332klks",
					"time":1315440000032,
					"status":"NEW",
					"paymentStatus":"NOT_PAID",
					"from":"1",
					"to":"1",
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
					"id":"i3244i24",
					"time":1315440000076,
					"status":"ACCEPTED",
					"timeToPrepared":23352323,
					"timeToDelivered": 5345353,
					"paymentStatus":"NOT_PAID",
					"from":"2",
					"to":"2",
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
					"id":"32kjl253",
					"time":1315440000023,
					"status":"REJECTED",
					"paymentStatus":"NOT_PAID",
					"from":"3",
					"to":"3",
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
				}
			];
		} else {
			data = [
				{
					"id":"3432khh2",
					"time":1315440000000,
					"status":"NEW",
					"paymentStatus":"NOT_PAID",
					"from":"4",
					"to":"4",
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
					"id":"i3244i24",
					"time":1315440000000,
					"status":"CONFIRMED",
					"timeToFinish":2518208798,
					"paymentStatus":"NOT_PAID",
					"from":"5",
					"timeToDeliver": "131544000000",
					"to":"5",
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
				},
				{
					"id":"32kjl253",
					"time":1315440000000,
					"status":"ACCEPTED",
					"timeToPrepared":435533,
					"timeToDelivered":2518208798,
					"paymentStatus":"NOT_PAID",
					"from":"6",
					"to":"6",
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
		}
			
		object.success(data);
	},
	
	getNewOrders: function(parent) {
		if(_.ajaxOk) { 
			_.updateTimes(parent.orders);
			
			_.ajax({
				url: '/api/g?id=1&from=' + parent.lastOrderTime,
				success: function(data) {
					_.parseNewOrders(data, parent);
				}
			});
			
			window.setTimeout(function(){
				_.getNewOrders(parent);
			}, 20000);
		}
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
	
  	sendOrderConfirmed: function(element, time, onSuccess) {
		_.ajax({
			url: '/api/p',
			dataType: "json",
			
			data: {
				'message': $.toJSON({ 
					'status' : 'ACCEPTED', 'id' : element.id, 'time' : time 
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