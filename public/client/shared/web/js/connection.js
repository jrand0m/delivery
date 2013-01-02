$.extend(_, {
	authorize: function(login, password, onSuccess){
		$.ajax({
		    timeout: 10000,
			url: '/login.php',
			type: 'POST',
			data: {'username':login, 'password':password, remember:true},
			success: function(data) {
				if(data.role==_.role) {
					onSuccess();
				}
			}
		});
	},
	
	getNewOrders: function(parent) {
		if(_.ajaxOk) { 
			_.updateTimes(parent);
			
			$.ajax({
			    timeout: 10000,
				url: '/api/g?from=' + (parent.lastOrderTime),
				success: function(data) {
					//alert(JSON.stringify(data));
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
		    timeout: 10000,
			url: '/api/g',
			success: function(data) {
					//alert(JSON.stringify(data));
				_.parseAllOrders(data, parent);
			}
		});
		
		window.setTimeout(function(){
			_.getNewOrders(parent);
		}, 20000);
	},
	
  	sendOrderConfirmed: function(element, time, onSuccess) {
		$.ajax({
		    timeout: 10000,
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
		    timeout: 10000,
			url: '/api/p',
			data: {'message': $.toJSON({ 'status' : status, 'id' : element.id})},
			success: function(data) {
				if(onSuccess)onSuccess();
			}
		});
	},
	
	sendOrderRejected: function(element, comment, onSuccess) {
		$.ajax({
		    timeout: 10000,
			url: '/api/p',
			data: {'message': $.toJSON({ 'status' : 'DECLINED', 'id' : element.id, 'comment': comment})},
			success: function(data) {
				if(onSuccess)onSuccess();
			}
		});
	},
});