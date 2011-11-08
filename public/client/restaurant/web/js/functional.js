$.extend(_, {
	role: 'RestaurantBarman',
	
	parseAllOrders: function(data, parent) {
		var doVibrate = 0;
		$(data).each(function(index){
			var elem = this;
			var doVibrate = 0;
			if(elem.status == "CONFIRMED") {
				var elemDom = _.getNewOrderDiv(elem, parent);
				parent.newOrdersContent.append(elemDom);
				doVibrate = 1;
			} else if(elem.status == "ACCEPTED") {
				elem.timeFinished = new Date().getTime()
					+ elem.timeToFinish;
				parent.activeOrdersContent.append(_.getActiveOrderDiv(elem, parent));
				doVibrate = 1;
			} else if(elem.status == "COOKED") {
				parent.activeOrdersContent.append(_.getActiveOrderDiv(elem, parent, true));
				doVibrate = 1;
			}
			if(elem.time > parent.lastOrderTime)parent.lastOrderTime = elem.time;
			
//			$(parent.myScroll).each(function(el){
//				this.refresh();
//			});
		});
		
		if(doVibrate) {
			_.vibrateDevice();
		}
	},
	
	parseNewOrders: function(data, parent) {
		var doVibrate = 0;
		$(data).each(function(index){
			var elem = this;
			if(elem.status == "CONFIRMED") {
				var elemDom = _.getNewOrderDiv(elem, parent);
				parent.newOrdersContent.append(elemDom);
				doVibrate = 1;
				if(elem.time > parent.lastOrderTime)parent.lastOrderTime = elem.time;
			}
			
//			$(parent.myScroll).each(function(el){
//				this.refresh();
//			});
		});
		
		if(doVibrate) {
			_.vibrateDevice();
		}
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
		
		rejectDialog.append(_.createButton(_.lang.cancel).click(function(){
			_.nextDialog();
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
	},
	
	initScroll: function(elementName) {
		return new aUX.web.scroller(elementName, {
			scrollBars : true,
			verticalScroll : true,
			horizontalScroll : false,
			//vScrollCSS : "scrollBarV",
			//hScrollCSS : "scrollBarH"
		});
	},
	
	preventDefaultScroll: function(event) {
		event.preventDefault();
		window.scroll(0, 0);
		return false;
	}
});
