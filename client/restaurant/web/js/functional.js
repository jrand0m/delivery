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
			dishDom.append(_.createDiv('DCWDishName').text(dishes[elem].name));
			dishDom.append(_.createDiv('DCWDishCount').text(dishes[elem].count));
			dishDom.append(_.createDiv('DCWDishPrice').text(dishes[elem].pricePerItem));
			parentDishesDiv.append(dishDom);
		});
		return parentDishesDiv;
	},

	getNewOrderDiv: function(orderElem, parent) {
		
		var orderDiv = _.createDiv("DCWOrderDiv");
		var id = _.createDiv('DCWOrderIdDiv');
		id.text(_.lang.orderID + orderElem.id);
		orderDiv.append(id);
		orderDiv.append(_.getDishesList(orderElem.list));
		orderElem.domElem = orderDiv;
		parent.newOrders.push(orderElem);
		var buttonsDiv = _.createDiv("DCWOrderButtonsDiv");
		
		buttonsDiv.append(_.getTimeBtn(parent, 15, orderElem));
		buttonsDiv.append(_.getTimeBtn(parent, 30, orderElem));
		buttonsDiv.append(_.getTimeBtn(parent, 45, orderElem));
		buttonsDiv.append(_.getTimeBtn(parent, 60, orderElem));
		buttonsDiv.append(_.getRejectBtn(parent.newOrders, orderElem));
		
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
		var btnRject = _.createButton(_.lang.reject, 'DCWOrderButton');
		btnRject.prepend('<img src="img/cross.png" style="vertical-align: middle">');
		$(btnRject).click(function(el){
			_.newDialog(_.getRejectDialog(parrentArray, orderElem));
		});
	},
	
	getActiveOrderDiv: function(orderElem, parent, isCoocked) {
		var orderDiv = _.createDiv('DCWOrderDiv');
		var orderHeaderWrapper = _.createDiv('DCWOrderHeaderWrapper');
		var id = _.createDiv('DCWOrderIdDiv').text(_.lang.orderID + orderElem.id);
		var timeToFinish = _.createDiv('DCWTimeToFinish').html(_.lang.timeConfirmed 
			+ Math.ceil((orderElem.timeFinished - new Date().getTime())  / 60000) + _.lang.time);
		
		orderDiv.append(orderHeaderWrapper);
		parent.activeOrders.push(orderElem);
		_.removeElement(parent.newOrders, orderElem);
		orderDiv.append(_.getDishesList(orderElem.list));
		
		
		orderHeaderWrapper.append(id);
		orderHeaderWrapper.append(timeToFinish);
		
		orderDiv.append(_.getActiveOrderDivBtns(parent.activeOrders, orderElem, isCoocked));
		
		orderElem.domElem = orderDiv;
		return orderDiv;
	},
	
	getActiveOrderDivBtns: function(parrentArray, orderElem, isCoocked) {
		var btnDiv = this.createDiv('DCWActiveOrdersBtnsWrapper');
		var btnReject = _.createButton(_.lang.reject, 'DCWOrderButton reject');
		btnReject.prepend('<img src="img/cross.png" style="vertical-align: middle">');
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
		
		btnReject.click(function() {
			_.newDialog(_.getRejectDialog(parrentArray, orderElem));
		});
		if(!isCoocked) {
			btnTaken.hide();
		} else {
			btnMade.hide();
		}
		
		btnDiv.append(btnMade);
		btnDiv.append(btnTaken);
		btnDiv.append(btnReject);
		
		return btnDiv;
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
		alert(new Date().getTime());
		alert(time);
		alert(time * 60000);
		alert();
		element.timeFinished = new Date().getTime()
			+ time * 60000;
		alert(element.timeFinished);
		parent.activeOrdersContent.append(_.getActiveOrderDiv(element, parent));
		_.sendOrderConfirmed(element, time);
	},
	
	updateTimes: function(parent) {
		$(parent.activeOrders).each(function(){
			var timeToFinish = this.timeFinished 
				- new Date().getTime();
			var isPositive = timeToFinish > 0;
			if(isPositive) {
				$('.DCWTimeToFinish', this.domElem).html(_.lang.timeConfirmed 
					+ Math.ceil( timeToFinish / 60000 ) + _.lang.time);
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
