$.extend(_, {
	authorize: function(login, password, onSuccess){
		$.ajax({
			url: '/api/a',
			data: {'message': {'login':login, 'password': password}},
			success: function(data) {
				if(data.status) {
					onSuccess();
				}
			}
		});
	},
	
	getNewOrders: function(parent) {
		if(_.ajaxOk) { 
			_.updateTimes(parent);
			
			$.ajax({
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
		$.ajax({
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
		$.ajax({
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
		$.ajax({
			url: '/api/p',
			data: {'message': $.toJSON({ 'status' : status, 'id' : element.id})},
			success: function(data) {
				if(onSuccess)onSuccess();
			}
		});
	},
	
	sendOrderRejected: function(element, comment, onSuccess) {
		$.ajax({
			url: '/api/p',
			data: {'message': $.toJSON({ 'status' : 'DECLINED', 'id' : element.id, 'comment': comment})},
			success: function(data) {
				if(onSuccess)onSuccess();
			}
		});
	},
});