$.extend(_, {
	
	getNewOrders: function(parent) {
		var thisObj = this;
		$.ajax({
			url: '/api/g?id=1&from=' + thisObj.lastOrderTime,
			success: function(data) {
				_.parseOrders(data, parent);
			}
		});
		
		this.updateTimes(parent.activeOrders);
		
		window.setTimeout(function(){
			_.getNewOrders(parent);
		}, 20000);
		
	}, 
	
	getAllOrders: function(parent) {
		var thisObj = this;
		
		$.ajax({
			url: '/api/g?id=1',
			success: function(data) {
				_.parseOrders(data, parent);
				
				//}
			}
		});
		
		window.setTimeout(function(){
			_.getNewOrders(parent);
		}, 20000);
	},
	
	sendOrderActivated: function(element) {
		$.ajax({
			url: '/api/p',
			dataType: "json",
			data: {'message': $.toJSON({ 'status' : 'ACCEPTED', 'id' : element.id, 'time' : element.time })},
			success: function(data) {
				//element.timeStarded = data.timeStarted;
			}
		});
	},
	
	sendOrderStatusChanged: function(element, status) {
		$.ajax({
			url: '/api/p',
			data: {'message': $.toJSON({ 'status' : status, 'id' : element.id})},
			success: function(data) {
			}
		});
	},
	
	sendOrderRejected: function(element, comment) {
		$.ajax({
			url: '/api/p',
			data: {'message': $.toJSON({ 'status' : 'DECLINED', 'id' : element.id, 'comment': comment})},
			success: function(data) {
				element.timeStarded = data.timeStarted;
			}
		});
	}
});