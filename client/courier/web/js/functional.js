$.extend(_, {
	
	parseAllOrders: function(data, parent) {
		$(data).each(function(index){
			if(this.status == "NEW") {
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
			} else if(this.status == "ACCEPTED") {
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
		console.log(JSON.stringify(parent.orders));
		$(data).each(function(index){
			if(this.status == "NEW") {
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
					_.newDialog(_.getInfoDialog());

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
		var header = _.createDiv('DCWOrderHeaderWrapper')
			.append(_.createDiv('DCWOrderFrom').text(element.from))
			.append(_.createDiv('DCWOrderTo').text(element.to));
		
		if(element.status == 'ACCEPTED') {
			header.append( _.createDiv('DCWTimeToPrepare')
					.html(Math.ceil((element.timePrepared - new Date().getTime())/60000)))
				.append( _.createDiv('DCWTimeToDeliver')
					.html(Math.ceil((element.timeDelivered - new Date().getTime())/60000)));
		}
		return header;
	},
	
	getNewOrderDiv: function(orderElem, parent) {
		var newOrders = parent.orders;
		
		var buttonsDiv = _.createDiv("DCWOrderButtonsDiv")
			.append(_.createTimeButton(orderElem, 10, parent))
			.append(_.createTimeButton(orderElem, 20, parent))
			.append(_.createTimeButton(orderElem, 30, parent))
			.append(_.createTimeButton(orderElem, 40, parent))
			.append(_.createTimeButton(orderElem, 50, parent))
			
			.append(_.getRejectBtn(orderElem, newOrders));
		
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
	
		btnDiv.append(_.getRejectBtn(orderElem, activeOrders));
		btnDiv.append(_.createButton(_.lang.taken, 'DCWOrderButton')
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
			if(array[i].status == 'NEW') {
				newOrdersCount++;
			} else if(array[i].status == 'CONFIRMED') {
				pendingOrdersCount++;
			} else if(array[i].status == 'ACCEPTED') {
				activeOrdersCount++;
			}
		};
		
		$('#DCWNewOrdersCount').text(newOrdersCount);
		$('#DCWPendingOrdersCount').text(pendingOrdersCount);
		$('#DCWActiveOrdersCount').text(activeOrdersCount);
	},
	
	getDishesList: function(dishes) {
		var parentDishesDiv = _.createDiv();
		$(dishes).each(function(elem){
			var dishDom = _.createDiv('DCWDishContent');
			dishDom.append(_.createDiv('DCWDishName').text(dishes[elem].name));
			dishDom.append(_.createDiv('DCWDishCount').text(dishes[elem].count));
			dishDom.append(_.createDiv('DCWDishPrice').text(dishes[elem].pricePerItem));
			parentDishesDiv.append(dishDom);
		});
		return parentDishesDiv;
	},
		
	getRejectBtn: function(orderElem, orders) {
		var btnRject = _.createButton(_.lang.reject, 'DCWOrderButton');
		
		$(btnRject).click(function(el){
			_.newDialog(_.getRejectDialog(orderElem, orders));
		});
		
		return btnRject;
	},
	
	createTimeButton: function(orderElem, time, parent) {
		var btn = _.createButton(time + "", 'DCWOrderButton');
		
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
	
	updateTimes: function(activeOrders) {
		$(activeOrders).each(function(){
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
	}
});