$.extend(_, {
	parseAllOrders: function(data, parent) {
		$(data).each(function(index){
			if(this.status == "CONFIRMED") {
				parent.newOrdersContent.append(_.getNewOrderDiv(this, parent));
			} else if(this.status == "ACCEPTED") {
				parent.pendingOrdersContent.append(_.getPendingOrderDiv(this, parent));
			} else if(this.status == "COOKED") {
				parent.activeOrdersContent.append(_.getActiveOrderDiv(this, parent));
			}
			
			//TODO: last time... should take be different for each order type
			//if(this.time > parent.lastOrderTime)parent.lastOrderTime = this.time;
			
			//$(parent.myScroll).each(function(el){
			//	this.refresh();
			//});
		});
	},
	
	parseNewOrders: function(data, parent) {
		$(data).each(function(index){
			if(this.status == "CONFIRMED") {
				parent.newOrdersContent.append(_.getNewOrderDiv(this, parent));
			} else if(this.status == "COOKED") {
				_.removeElement(parent.pendingOrders, _.getElementById(parent.pendingOrders, this.id));
				parent.activeOrdersContent.append(_.getActiveOrderDiv(this, parent));
			}
			
			//TODO: last time... should take be different for each order type
			//if(this.time > parent.lastOrderTime)parent.lastOrderTime = this.time;
			
			//$(parent.myScroll).each(function(el){
			//	this.refresh();
			//});
		});
	},
	
	getElementById: function(array, id) {
		var element;
		$(array).each(function() {
			if(this.id == id) {
				element = this;
				break;
			}
		});
		return element;
	},
	
	createOrderHeader: function(element) {
		return _.createDiv('DCWOrderHeaderWrapper')
			.append(_.createDiv('DCWOrderFrom').text(element.from))
			.append(_.createDiv('DCWOrderTo').text(element.to));
	},
	
	getNewOrderDiv: function(orderElem, parent) {
		var newOrders = parent.newOrders;
		newOrders.push(orderElem);
		
		var buttonsDiv = _.createDiv("DCWOrderButtonsDiv")
			.append(_.createTimeButton(orderElem, 15, parent))
			.append(_.createTimeButton(orderElem, 30, parent))
			.append(_.createTimeButton(orderElem, 45, parent))
			.append(_.createTimeButton(orderElem, 60, parent))
			.append(_.createTimeButton(orderElem, 100, parent))
			
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
		pendingOrders.push(orderElem);
		var orderDiv = _.createDiv('DCWOrderDiv')
			.append(_.createOrderHeader(orderElem))
			.append(_.getDishesList(orderElem.list));
			
		orderElem.domElem = orderDiv;
		return orderDiv;
	},
	
	getActiveOrderDiv: function(orderElem, parent) {
		var activeOrders = parent.activeOrders;
		activeOrders.push(orderElem);
		var orderDiv = _.createDiv('DCWOrderDiv')
			.append(_.createOrderHeader(orderElem))
			.append(_.getDishesList(orderElem.list))
			.append(_.getActiveOrderBtns(orderElem, 
					parent.activeOrders));
		
		orderElem.domElem = orderDiv;
		return orderDiv;
	},
	
	getActiveOrderBtns: function(orderElem, activeOrders) {
		var btnDiv = _.createDiv('DCWActiveOrdersBtnsWrapper');
		var timeToFinish = _.createDiv('DCWTimeToFinish')
			.html(Math.ceil(orderElem.time/60000));
			
		btnDiv.append(timeToFinish);
		btnDiv.append(_.getRejectBtn(orderElem, activeOrders));
		btnDiv.append(_.createButton(_.lang.taken, 'DCWOrderButton')
			.click(function() {
					_.sendOrderStatusChanged(orderElem, 'DELIVERING');
					orderElem.domElem.remove();
					_.removeElement(activeOrders, orderElem);
				}));
		
		return btnDiv;
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
			_.showDialog(_.getRejectDialog(orderElem, orders));
		});
		
		return btnRject;
	},
	
	createTimeButton: function(orderElem, time, parent) {
		var btn = _.createButton(time + "", 'DCWOrderButton');
		
		$(btn).click(function(el){
			orderElem.domElem.remove();
			orderElem.time = time * 60000;
			parent.activeOrdersContent.append(
				_.getActiveOrderDiv(orderElem, parent));
			_.sendOrderActivated(orderElem);
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
					_.sendOrderRejected(element, text);
					element.domElem.remove();
					_.removeElement(parentArray, element);
					_.dialogFrame.hide();
				}
			}
		));
		
		return rejectDialog;
	},
	
	updateTimes: function(activeOrders) {
		$(activeOrders).each(function(){
			this.time = this.time-20000;
			var isPositive = this.time > 0;
			$('.DCWTimeToFinish', this.domElem)
				.html(Math.ceil(this.time / 60000));
			
			if(!isPositive) {
				this.domElem.addClass('DCWDelayedOrder');
			}
		});
	}
});