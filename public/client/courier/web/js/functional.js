$.extend(_, {
	role: 'CourierUser',
	
	parseAllOrders: function(data, parent) {
		$(data).each(function(index){
			if(this.status == "SENT") {
				parent.orders.push(this);
				parent.newOrdersContent.append(_.getNewOrderDiv(this, parent));
				if(parent.tabOpened != 0) {
					parent.updatedNewOrders++;
				}
			} else if(this.status == "CONFIRMED") {
				parent.orders.push(this);
				parent.pendingOrdersContent.append(_.getPendingOrderDiv(this, parent));
				if(parent.tabOpened != 1) {
					parent.updatedPendingOrders++;
				}
			} else if(this.status == "ACCEPTED"||this.status == "COOKED"||this.status == "DELIVERING") {
				parent.orders.push(this);
				parent.activeOrdersContent.append(_.getActiveOrderDiv(this, parent));
				if(parent.tabOpened != 2) {
					parent.updatedActiveOrders++;
				}
			}
			
			if(this.time > parent.lastOrderTime)parent.lastOrderTime = this.time;
			_.updateCounters(parent.orders);
			_.updateUpdatedOrdersCount(parent);
			//$(parent.myScroll).each(function(el){
			//	this.refresh();
			//});
		});
	},
	
	updateUpdatedOrdersCount: function(parent) {
		$('#DCWUpdatedNewOrdersCount').text(parent.updatedNewOrders);
		$('#DCWUpdatedPendingOrdersCount').text(parent.updatedPendingOrders);
		$('#DCWUpdatedActiveOrdersCount').text(parent.updatedActiveOrders);
	},
	
	parseNewOrders: function(data, parent) {
		$(data).each(function(index){
			if(this.status == "SENT") {
				parent.orders.push(this);
				parent.newOrdersContent.append(_.getNewOrderDiv(this, parent));
				if(parent.tabOpened != 0) {
					parent.updatedNewOrders++;
				}
			} else if(this.status == "ACCEPTED") {
				var elem = _.getElementById(parent.orders, this.id);
				if(elem && elem.status == 'CONFIRMED') {
					elem.domElem.remove();
					_.removeElement(parent.orders, elem);
					parent.orders.push(this);
					parent.activeOrdersContent.append(_.getActiveOrderDiv(this, parent));
					if(parent.tabOpened != 2) {
						parent.updatedActiveOrders++;
					}
				}
			} else if(this.status == "REJECTED") {
				var elem = _.getElementById(parent.orders, this.id);
				if(elem) {
					if(elem.status != 'SENT') {
						_.newDialog(_.getInfoDialog(_.lang.wasRejected));
					}
					
					elem.domElem.remove();
					_.removeElement(parent.orders, elem);
				}
			}
			
			if(this.time > parent.lastOrderTime)parent.lastOrderTime = this.time;
			_.updateCounters(parent.orders);
			_.updateUpdatedOrdersCount(parent);
			//$(parent.myScroll).each(function(el){
			//	this.refresh();
			//});
		});
	},
	
	createOrderHeader: function(element) {
		var header = _.createDiv('DCWOrderHeaderWrapper');
		var orderData = _.createDiv('DCWOrderDataWrapper');
		var aderessWrapper = _.createDiv('DCWOrderAdressWrapper');
		orderData.append(_.createDiv('DCWOrderId').text(element.id));
		aderessWrapper.append(orderData);
		header.append(_.createDiv('DCWOrderPriceWrapper')
				.append(_.createDiv('DCWOrderPhone').text(element.phone))
				.append(_.createDiv('DCWOrderPrice').text(_.lang.price + _.formatCurrencyString(element.price+''))));
		aderessWrapper.append(_.createDiv('DCWOrderFrom').text(_.lang.adressFrom + element.from))
			.append(_.createDiv('DCWOrderTo').text(_.lang.adressTo + element.to));
		header.append(aderessWrapper);
		if(element.status == 'ACCEPTED') {
			var timesWrapper = _.createDiv('DCWOrderTimeWrapper');
			timesWrapper.append(_.createDiv()
				.append(_.lang.timeTake)
				.append(_.createSpan('DCWTimeToPrepare')
					.text(Math.ceil((element.timePrepared - new Date().getTime())/60000)))
				.append(_.lang.timeItem));
			timesWrapper.append(_.createDiv()
				.append(_.lang.timeDeliver)
				.append( _.createSpan('DCWTimeToDeliver')
						.text(Math.ceil((element.timeDelivered - new Date().getTime())/60000)))
				.append(_.lang.timeItem));
			header.append(timesWrapper);
		}
		return header;
	},
	
	getNewOrderDiv: function(orderElem, parent) {
		var newOrders = parent.orders;
		
		var buttonsDiv = _.createDiv("DCWOrderButtonsDiv")
			.append(_.getRejectBtn(orderElem, newOrders))
			.append(_.createTimeButton(orderElem, 10, parent))
			.append(_.createTimeButton(orderElem, 20, parent))
			.append(_.createTimeButton(orderElem, 30, parent))
			.append(_.createTimeButton(orderElem, 40, parent))
			.append(_.createTimeButton(orderElem, 50, parent));
		
		var orderDiv = _.createDiv("DCWOrderDiv")
			.append(_.createOrderHeader(orderElem))
			.append(_.getDishesList(orderElem.list))
			.append(buttonsDiv);
		
		orderElem.domElem = orderDiv;	
		return orderDiv;
	},
	
	getPendingOrderDiv: function(orderElem, parent) {
		var pendingOrders = parent.pendingOrders;
		var orderDiv = _.createDiv('DCWOrderDiv')
			.append(_.createOrderHeader(orderElem))
			.append(_.getDishesList(orderElem.list));
			
		orderElem.domElem = orderDiv;
		return orderDiv;
	},
	
	getActiveOrderDiv: function(orderElem, parent) {
		orderElem.timePrepared = new Date().getTime() + orderElem.timeToPrepared;
		orderElem.timeDelivered = new Date().getTime() + orderElem.timeToDelivered;
					
		var activeOrders = parent.activeOrders;
		var orderDiv = _.createDiv('DCWOrderDiv')
			.append(_.createOrderHeader(orderElem))
			.append(_.getDishesList(orderElem.list))
			.append(_.getActiveOrderBtns(orderElem, 
					parent.orders));
		
		orderElem.domElem = orderDiv;
		return orderDiv;
	},
	
	getActiveOrderBtns: function(orderElem, activeOrders) {
		var btnDiv = _.createDiv('DCWActiveOrdersBtnsWrapper');
	
		//btnDiv.append(_.getRejectBtn(orderElem, activeOrders));
		btnDiv.append(_.createButton(_.lang.delivered, 'DCWOrderButton')
			.click(function() {
					orderElem.domElem.remove();
					_.removeElement(activeOrders, orderElem);
					_.sendOrderStatusChanged(orderElem, 'DELIVERED');
				}));
		
		return btnDiv;
	},
	
	updateCounters: function(array) {
		var newOrdersCount = 0;
		var pendingOrdersCount = 0;
		var activeOrdersCount = 0;
		
		for(var i=0; i<array.length; i++) {
			if(array[i].status == 'SENT') {
				newOrdersCount++;
			} else if(array[i].status == 'CONFIRMED') {
				pendingOrdersCount++;
			} else if(array[i].status == "ACCEPTED"||array[i].status == "COOKED"||array[i].status == "DELIVERING") {
				activeOrdersCount++;
			}
		};
		
		$('#DCWNewOrdersCount').text(newOrdersCount);
		$('#DCWPendingOrdersCount').text(pendingOrdersCount);
		$('#DCWActiveOrdersCount').text(activeOrdersCount);
	},
		
	getRejectBtn: function(orderElem, orders) {
		var btnRject = _.createButton(_.lang.reject, 'DCWOrderButton DCWRejectButton');
		
		$(btnRject).click(function(el){
			_.newDialog(_.getRejectDialog(orderElem, orders));
		});
		
		return btnRject;
	},
	
	createTimeButton: function(orderElem, time, parent) {
		var btn = _.createButton(time + "", 'DCWOrderButton DCWOrderTimeButton');
		
		$(btn).click(function(el){
			orderElem.status = 'CONFIRMED';
			orderElem.domElem.remove();
			//orderElem.timeToFinish = time * 60000;
			parent.pendingOrdersContent.append(
				_.getPendingOrderDiv(orderElem, parent));
			_.sendOrderConfirmed(orderElem, time);
			_.updateCounters(parent.orders);
		});
		return btn;
	},
	
	getRejectDialog: function(element, parentArray) {
		var rejectDialog = _.createDiv();
		var reasonTexts = [_.lang.errorNoIngrediants, _.lang.notEnoughtTimeBeforeClose];
		$(reasonTexts).each(function(el){
			var id = 'DCWRejectRadio' + el;
			
			var rejectRadio = $(document.createElement('input'))
				.attr('type', 'radio')
				.attr('value', this)
				.attr('name', 'rejectBtn')
				.attr('id', id);
				
			var rejectLabel = $(document.createElement('label'))
				.attr('for', id)
				.css('display', 'block');
				
			rejectLabel.text(this);
			rejectLabel.prepend(rejectRadio);
			rejectDialog.append(rejectLabel);
		});
		
		rejectDialog.append(_.createButton(_.lang.reject)
			.click(function(){
				var text = $(":radio[name=rejectBtn]")
					.filter(":checked")
					.val();
					
				if(text) {
					element.domElem.remove();
					_.removeElement(parentArray, element);
					_.nextDialog();
					_.sendOrderRejected(element, text);
				}
				_.updateCounters(parentArray);
			}
		));
		
		rejectDialog.append(_.createButton(_.lang.cancel).click(function(){
			_.nextDialog();
		}));
		
		return rejectDialog;
	},
	
	getInfoDialog: function(text) {
		var dialog = _.createDiv();
		var btn = _.createButton(_.lang.ok);
		btn.click(function() {
			_.nextDialog()
		});
		
		if(text){
			dialog.append(_.createDiv().text(text));
		}
		dialog.append(btn);
		return dialog;
	},
	
	updateTimes: function(parent) {
		$(parent.orders).each(function(){
			if(this.status == 'ACCEPTED') {
				var timeToDeliver = (this.timeDelivered - new Date().getTime()) / 60000;
				var timeToPrepare = (this.timePrepared - new Date().getTime()) / 60000;
				$('.DCWTimeToDeliver', this.domElem)
					.html(Math.ceil(timeToDeliver));
				$('.DCWTimeToPrepare', this.domElem)
					.html(Math.ceil(timeToPrepare));
				
				if(timeToDeliver < 0 || timeToPrepare < 0) {
					this.domElem.addClass('DCWDelayedOrder');
				}
			}
		});
	},
	
	isTest: function() {
		$.extend({
			ajax: function(object) {
				var data;
				if(object.url.indexOf("from") != -1) {
					data = [
						{
							"id":"2332klks",
							"time":1315440000032,
							"status":"SENT",
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
							"status":"SENT",
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
			}
		});
	}
});