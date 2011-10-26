$.extend(_, {
	
	parseAllOrders: function(data, parent) {
		$(data).each(function(index){
			var elem = this;
			if(elem.status == "CONFIRMED") {
				var elemDom = _.getNewOrderDiv(elem, parent);
				parent.newOrdersContent.append(elemDom);
			} else if(elem.status == "ACCEPTED") {
				elem.timeFinished = new Date().getTime()
					+ elem.timeToFinish;
				parent.activeOrdersContent.append(_.getActiveOrderDiv(elem, parent));
			} else if(elem.status == "COOKED") {
				parent.activeOrdersContent.append(_.getActiveOrderDiv(elem, parent, true));
			}
			if(elem.time > parent.lastOrderTime)parent.lastOrderTime = elem.time;
			
			$(parent.myScroll).each(function(el){
				this.refresh();
			});
		});
	},
	
	parseNewOrders: function(data, parent) {
		$(data).each(function(index){
			var elem = this;
			if(elem.status == "CONFIRMED") {
				var elemDom = _.getNewOrderDiv(elem, parent);
				parent.newOrdersContent.append(elemDom);
				if(elem.time > parent.lastOrderTime)parent.lastOrderTime = elem.time;
			}
			
			$(parent.myScroll).each(function(el){
				this.refresh();
			});
		});
	},
	
	getDishesList: function(dishes) {
		var parentDishesDiv = this.createDiv();
		$(dishes).each(function(elem){
			var dishDom = _.createDiv('DCWDishContent');
			dishDom.append(_.createDiv('DCWDishPrice').text(
				_.formatCurrencyString(dishes[elem].pricePerItem+"")));
			dishDom.append(_.createSpan('DCWDishNumber').text(elem + '. '));
			dishDom.append(_.createSpan('DCWDishCount').text(dishes[elem].count));
			dishDom.append(_.createSpan('DCWDishName').text(dishes[elem].name));
			parentDishesDiv.append(dishDom);
		});
		return parentDishesDiv;
	},

	getNewOrderDiv: function(orderElem, parent) {
		
		var orderDiv = _.createDiv("DCWOrderDiv");
		
		orderDiv.append(_.getOrderheader(orderElem));
		orderDiv.append(_.getDishesList(orderElem.list));
		orderElem.domElem = orderDiv;
		parent.newOrders.push(orderElem);
		var buttonsDiv = _.createDiv("DCWOrderButtonsDiv");
		
		buttonsDiv.append(_.getRejectBtn(parent.newOrders, orderElem));
		buttonsDiv.append(_.getTimeBtn(parent, 15, orderElem));
		buttonsDiv.append(_.getTimeBtn(parent, 30, orderElem));
		buttonsDiv.append(_.getTimeBtn(parent, 45, orderElem));
		buttonsDiv.append(_.getTimeBtn(parent, 60, orderElem));
		
		orderDiv.append(buttonsDiv);
		return orderDiv;
	},
	
	getTimeBtn: function(parent, time, orderElem) {
		var btn = this.createButton(time+'', 'DCWOrderButton');
		btn.prepend('<img src="img/tick.png" style="vertical-align: middle">');
		$(btn).click(function(el){
			_.timeButtonPressed(parent, orderElem, time);
		});
		return btn;
	},
	
	getRejectBtn: function(parrentArray, orderElem) {
		var btnRject = _.createButton(_.lang.reject, 'DCWOrderButton DCWRejectOrderButton');
		btnRject.prepend('<img src="img/cross.png" style="vertical-align: middle">');
		$(btnRject).click(function(el){
			_.newDialog(_.getRejectDialog(parrentArray, orderElem));
		});
		return btnRject;
	},
	
	getActiveOrderDiv: function(orderElem, parent, isCoocked) {
		var orderDiv = _.createDiv('DCWOrderDiv');
		parent.activeOrders.push(orderElem);
		_.removeElement(parent.newOrders, orderElem);
		orderDiv.append(_.getOrderheader(orderElem));
		orderDiv.append(_.getDishesList(orderElem.list));
		
		orderDiv.append(_.getActiveOrderDivBtns(parent.activeOrders, orderElem, isCoocked));
		
		orderElem.domElem = orderDiv;
		_.updateTimes(parent);
		return orderDiv;
	},
	
	getActiveOrderDivBtns: function(parrentArray, orderElem, isCoocked) {
		var btnDiv = this.createDiv('DCWActiveOrdersBtnsWrapper');
		var btnMade = _.createButton(_.lang.ready, 'DCWOrderButton ready');
		btnMade.prepend('<img src="img/tick.png" style="vertical-align: middle">');
		var btnTaken = _.createButton(_.lang.taken, 'DCWOrderButton taken');
		btnTaken.prepend('<img src="img/home.png" style="vertical-align: middle">');
		
		btnMade.click(function() {
			_.sendOrderStatusChanged(orderElem, 'COOKED');
			btnMade.hide();
			btnTaken.show();
		});
		
		btnTaken.click(function() {
			_.sendOrderStatusChanged(orderElem, 'DELIVERING');
			orderElem.domElem.remove();
			_.removeElement(parrentArray, orderElem);
		});
		
		var timeToFinish = _.createDiv('DCWTimeToFinishContainer');
		timeToFinish
			.append(_.createDiv('DCWOrderTimeSufix DCWOrderTimeDiv').append(_.lang.time))
			.append(_.createDiv('DCWTimeToFinish DCWOrderTimeDiv'))
			.append(_.createDiv('DCWOrderTimePrefix DCWOrderTimeDiv').append(_.lang.timeConfirmed));
		
		if(!isCoocked) {
			btnTaken.hide();
		} else {
			btnMade.hide();
		}
		
		btnDiv.append(_.getRejectBtn(parrentArray, orderElem));
		btnDiv.append(timeToFinish);
		btnDiv.append(btnMade);
		btnDiv.append(btnTaken);
		
		return btnDiv;
	},
	
	getOrderheader: function(orderElem) {
		var orderHeaderWrapper = _.createDiv('DCWOrderHeaderWrapper');
		var id = _.createDiv('DCWOrderIdDiv').text(_.lang.orderID + orderElem.id);
		var priceContent = _.createDiv('DCWOrderPriceContainer').append(_.createSpan('DCWOrderPricePrefix').text(_.lang.price));
		priceContent.append(_.createSpan('DCWOrderPriceDiv').text(_.formatCurrencyString(orderElem.price+'')));
		orderHeaderWrapper.append(id);
		orderHeaderWrapper.append(priceContent);
		return orderHeaderWrapper;
	},
	
	getRejectDialog: function(parentArray, element) {
		var thisObj = this;
		var rejectDialog = _.createDiv();
		var reasonTexts = [_.lang.errorNoIngrediants, _.lang.notEnoughtTimeBeforeClose];
		$(reasonTexts).each(function(el){
			var id = 'DCWRejectRadio' + el;
			var rejectRadio = $(document.createElement('input')).attr('type','radio')
				.attr('value',reasonTexts[el]).attr('name','rejectBtn').attr('id',id);
			var rejectLabel = $(document.createElement('label')).text(reasonTexts[el]).attr('for', id).css('display', 'block');
			rejectLabel.prepend(rejectRadio);
			rejectDialog.append(rejectLabel);
		});
		
		rejectDialog.append(_.createButton(_.lang.reject).click(function(){
			var text = $(":radio[name=rejectBtn]").filter(":checked").val() ;
			if(text) {
				_.sendOrderRejected(element, text);
				element.domElem.remove();
				_.removeElement(parentArray, element);
				_.nextDialog();
			}
		}));
		
		return rejectDialog;
	},
	
	timeButtonPressed: function(parent, element, time) {
		element.domElem.remove();
		element.timeFinished = new Date().getTime()
			+ time * 60000;
		parent.activeOrdersContent.append(_.getActiveOrderDiv(element, parent));
		_.sendOrderConfirmed(element, time);
	},
	
	updateTimes: function(parent) {
		$(parent.activeOrders).each(function(){
			var timeToFinish = this.timeFinished 
				- new Date().getTime();
			var isPositive = timeToFinish > 0;
			if(isPositive) {
				$('.DCWTimeToFinish', this.domElem).html(Math.ceil( timeToFinish / 60000 ));
			} else  {
				$('.DCWTimeToFinish', this.domElem).html(0);
				this.domElem.addClass('DCWDelayedOrder');
			}
		});
	},
	
	initColunHeight: function() {
		$('.DCWContentWrapper').each(function(el){
			$(this).height($(window).height());
		});
	}
});
