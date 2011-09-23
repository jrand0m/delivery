$(document).ready(function() {
	//DCWMain.init();
	DCWMain.init();
});

var testVals = new Array();
var testVals2 = new Array();
	
var dishes1 = [{
	name: 'asdfas',
	price: '10',
	count: '25'
},{
	name: 'asdfas',
	price: '10',
	count: '25'
}];
for(var i = 0; i< 10; i++) {
	testVals[testVals.length] = {
		id:i,
		price:"25",
		dishes:dishes1
	};
}
for(var i = 0; i< 10; i++) {
	testVals2[testVals2.length] = {
		id:i,
		price:"25",
		dishes:dishes1
	};
}

var DCWMain = {
	newOrders: Object,
	activeOrders: Object,
	newOrdersContent: '',
	activeOrdersContent: '',
	dialogFrame: '',

	init: function(){	
		this.initVars();
		var fake = $(document.createElement("a"));
		fake.keypress(this.cancel);
		fake.attr("href", "#");
		fake
		$('body').prepend(fake);
		$('body').append(
			$(document.createElement('table'))
			.attr("id", "DCWMainOrderTable"));
			
		this.initDialogFrame();
		this.showDialog(this.getAuthBox());
	},
	
	initVars: function(){
		this.newOrders = new Array();
		this.activeOrders = new Array();
	},
		
	authorize: function(login, password){
		return true;
	},
	
	getAuthBox: function(){
		var authForm = $(document.createElement('div'));
		authForm.attr("id", "DCWAuthForm");
			
		var loginTextBox = $(document.createElement('input'));
		loginTextBox.attr("id","DCWLoginTextBox");
		loginTextBox.attr("value","login");
		loginTextBox.addClass('DCWInput');
		loginTextBox.addClass('DCWAuthAltInput');
		
		loginTextBox.focus(function() {
			that = $(this);
			if(that.hasClass('DCWAuthAltInput')) {
				that.attr("value","");
				that.removeClass('DCWAuthAltInput');
				that.addClass('DCWAuthInput');
			}
		});
		
		loginTextBox.blur(function() {
			that = $(this);
			if(that.attr('value')=='') {
				that.attr("value", "login");
				that.removeClass('DCWAuthInput');
				that.addClass('DCWAuthAltInput');
			}
		});
		
		authForm.append($(document.createElement('div'))
			.addClass('DCWAuthBoxWrapper')
			.append(loginTextBox));
		
		var passAltText = $(document.createElement('input'));
		passAltText.attr("id","DCWPasswordAltText");
		passAltText.addClass('DCWAuthAltInput');
		passAltText.attr("value","password");
		
		var passwordTextBox = $(document.createElement('input'));
		passwordTextBox.attr("id","DCWPassTextBox");
		passwordTextBox.attr("type","password");
		passwordTextBox.addClass('DCWInput');
		passwordTextBox.addClass('DCWAuthInput');
		
		var passWrapper = $(document.createElement('div')).addClass('DCWAuthBoxWrapper');
		
		passWrapper.append(passAltText);
		passWrapper.append(passwordTextBox);
		authForm.append(passWrapper);
		
		passwordTextBox.hide();
		
		passAltText.focus(function() {
			that = $(this);
			that.hide();
			passwordTextBox.show();
			passwordTextBox.focus();
		});
		
		passwordTextBox.blur(function() {
			that = $(this);
			if(that.attr('value')=='') {
				that.hide();
				passAltText.show();
			}
		});
		
		var btnOk = $(document.createElement('button'));
		btnOk.text('ok');
		var thisObj = this;
		btnOk.click(function() {
			that = $(this);
			
			if(thisObj.authorize(loginTextBox.attr('value'), passwordTextBox.attr('value'))) {
				//authForm.hide();
				thisObj.dialogFrame.hide();
				thisObj.showContent();
			}				
		});
		authForm.append($(document.createElement('div'))
			.addClass('DCWAuthBoxWrapper')
			.append(btnOk));
			
		return authForm;

		//btnOk.width(passwordTextBox.width());
	},
	
	initDialogFrame: function() {	
		this.dialogFrame = $(document.createElement('div')).attr("id", "DCWDialogBackgroundDiv");
		$('body').append(this.dialogFrame);
			
		this.dialogFrame.ajaxError(function() {
			this.showDialog($(document.createElement("div")).html("ERROR, blyet'!<br>shmu f5!"));
		});
	},
	cancel: function(evt)
	{
		evt = ( evt || window.event );
		key = ( evt.keyCode || evt.charCode || evt.which || 0 );
		if ( key == 3 || key == 9 || key == 13 )
		{
			evt.preventDefault();
			evt.stopPropagation();
		}
	},
	
	showDialog: function(dialogContent){
		var dialogFr = $(document.createElement('div')).attr("id", "DCWDialogFrame");
		dialogFr.keypress(this.cancel);
		/*dialogContent.focusout(function(){
		//alert(111);
			/*var firstElem = $(":input:first",dialogContent);
			alert(JSON.stringify(firstElem));
			$(firstElem).focus();*/
		/*		
			var fake = $(document.createElement("a"));
			fake.attr("href", "#");
			
			dialogContent.prepend(fake);
			//node.insertBefore(fake, node.firstChild);
			fake.focus();
			fake.blur(function(e) { fake.remove(); });

		});*/
		dialogFr.append(dialogContent);
		this.dialogFrame.html('');
		this.dialogFrame.append(dialogFr);
		this.dialogFrame.show();
		dialogFr.css('margin-left',-dialogFr.width()/2);
		dialogFr.css('margin-top',-dialogFr.height()/2);
	},
	
	getRejectDialog: function(element) {
		var rejectDialog = $(document.createElement('div'));
		var inp = $(document.createElement('input'));
		/*inp.blur(function(){
			alert('asdfdfs');
		});*/
		rejectDialog.append(inp);
		return rejectDialog;
	},
	
	showContent: function(){
		var row = $(document.createElement('tr'));
		
		var leftTableBorder = $(document.createElement('td'))
			.append($(document.createElement('div')).addClass("DCW10px").text(' '));
		leftTableBorder.addClass("DCWLeftTableBorder").addClass("DCWTableBorder");
		row.append(leftTableBorder);
		
		var newOrders = $(document.createElement('td'));
		newOrders.attr("id","DCWNewOrdersColumn");
		newOrders.addClass("DCWOrdersColumn");
		
		this.newOrdersContent = $(document.createElement('div'));
		this.newOrdersContent.attr("id","DCWNewOrdersColumnContent");
		this.newOrdersContent.addClass("DCWOrdersColumnContent");
		
		newOrders.append(this.newOrdersContent);
		row.append(newOrders);
		
		this.activeOrdersContent = $(document.createElement('div'));
		this.activeOrdersContent.attr("id","DCWActiveOrdersColumnContent");
		this.activeOrdersContent.addClass("DCWOrdersColumnContent");
		
		var activeOrders = $(document.createElement('td'));
		activeOrders.attr("id","DCWActiveOrdersColumn");
		activeOrders.addClass("DCWOrdersColumn");
				
		var tableSeparator = $(document.createElement('td'))
			.append($(document.createElement('div')).addClass("DCW10px").text(' '));
		tableSeparator.addClass("DCWTableSeparator").addClass("DCWTableBorder");
		row.append(tableSeparator);
		
		activeOrders.append(this.activeOrdersContent);
		row.append(activeOrders);
		
		var rightTableBorder = $(document.createElement('td'))
			.append($(document.createElement('div')).addClass("DCW10px").text(' '));
		rightTableBorder.addClass("DCWRightTableBorder").addClass("DCWTableBorder");
		row.append(rightTableBorder);
		
		$('#DCWMainOrderTable').html('');
		$('#DCWMainOrderTable').append(row);
		
		var thisObj = this;
		
		this.getNewOrders();
		
	},
	
	getNewOrderDiv: function(orderElem) {
		
		var orderDiv = $(document.createElement('div'));
		orderDiv.addClass('DCWOrderDiv');
		/*var price = $(document.createElement('div'));
		price.text(orderElem.price);
		price.addClass('DCWOrderPriceDiv');
		orderDiv.append(price);*/
		var id = $(document.createElement('div'));
		id.text(orderElem.id);
		orderDiv.append(id);
		orderDiv.append(this.getDishesList(orderElem.list));
		orderElem.domElem = orderDiv;
		this.newOrders[this.newOrders.length] = orderElem;
		var buttonsDiv = $(document.createElement('div')).addClass('DCWOrderButtonsDiv');
		var thisObj = this;
		
		var btn15 = $(document.createElement('button')).addClass('DCWOrderButton').text('15');
		$(btn15).click(function(el){
			thisObj.timeButtonPressed(orderElem, 15);
		});
		buttonsDiv.append(btn15);
		
		var btn30 = $(document.createElement('button')).addClass('DCWOrderButton').text('30');
		$(btn30).click(function(el){
			thisObj.timeButtonPressed(orderElem, 30);
		});
		buttonsDiv.append(btn30);
		
		var btn45 = $(document.createElement('button')).addClass('DCWOrderButton').text('45');
		$(btn45).click(function(el){
			thisObj.timeButtonPressed(orderElem, 45);
		});
		buttonsDiv.append(btn45);
		
		var btn60 = $(document.createElement('button')).addClass('DCWOrderButton').text('60');
		$(btn60).click(function(el){
			thisObj.timeButtonPressed(orderElem, 60);
		});
		buttonsDiv.append(btn60);
		
		buttonsDiv.append($(document.createElement('button')).addClass('DCWOrderButton').text('more'));
		
		var btnRject = $(document.createElement('button')).addClass('DCWOrderButton').text('reject');
		$(btnRject).click(function(el){
			thisObj.showRejectDialog(orderElem);
		});
		buttonsDiv.append(btnRject);
		
		orderDiv.append(buttonsDiv);
		return orderDiv;
	},
	
	getActiveOrderDiv: function(orderElem) {
		var thisObj = this;		
		var orderDiv = $(document.createElement('div'));
		orderDiv.addClass('DCWOrderDiv');
		/*var price = $(document.createElement('div'));
		price.text(orderElem.price);
		price.addClass('DCWOrderPriceDiv');
		orderDiv.append(price);*/
		var orderHeaderWrapper = $(document.createElement('div')).addClass('DCWOrderHeaderWrapper');
		var id = $(document.createElement('div')).text(orderElem.id);
		var btnReject = $(document.createElement('button')).text('reject');
		var btnMade = $(document.createElement('button')).text('made');
		var btnTaken = $(document.createElement('button')).text('taken').hide();
		
		btnMade.click(function() {
			thisObj.sendOrderStatusChanged(orderElem, 'made');
			btnMade.hide();
			btnTaken.show();
		});
		
		btnTaken.click(function() {
			thisObj.sendOrderStatusChanged(orderElem, 'taken');
			orderElem.domElem.remove();
			orderElem.remove();
		});
		
		btnReject.click(function() {
			thisObj.showDialog(thisObj.getRejectDialog(orderElem));
		});
		orderHeaderWrapper.append(btnMade);
		orderHeaderWrapper.append(btnTaken);
		orderHeaderWrapper.append(btnReject);
		orderHeaderWrapper.append(id);
		orderDiv.append(orderHeaderWrapper);
		this.activeOrders[this.activeOrders.length] = orderElem;
		orderElem.domElem = orderDiv;
		orderDiv.append(this.getDishesList(orderElem.list));
		orderElem.domElem = orderDiv;
		return orderDiv;
	},
	
	getDishesList: function(dishes) {
		var parentDishesDiv = $(document.createElement('div'));
		$(dishes).each(function(elem){
			var dishDom = $(document.createElement('div')).addClass('DCWDishContent');
			dishDom.append($(document.createElement('div')).addClass('DCWDishName').text(dishes[elem].name));
			dishDom.append($(document.createElement('div')).addClass('DCWDishCount').text(dishes[elem].count));
			dishDom.append($(document.createElement('div')).addClass('DCWDishPrice').text(dishes[elem].pricePerItem));
			parentDishesDiv.append(dishDom);
		});
		return parentDishesDiv;
	},
	
	getNewOrders: function() {
		//newOrders
		var thisObj = this;
		
		$.ajax({
			url: '/api/g?id=1',
			success: function(data) {
				
				$(data).each(function(elem){
					var elemDom = thisObj.getNewOrderDiv(data[elem]);
					thisObj.newOrdersContent.append(elemDom);
				});
			}
		});
	}, 
	
	sendOrderActivated: function(element) {
		/*$.ajax({
			url: '/api/g?id=1',
			data: { 'status' : 'activated', 'id' : element.id, 'time' : element.time },
			success: function(data) {
				element.timeStarded = data.timeStarted;
			}
		});*/
	},
	
	sendOrderStatusChanged: function(element, status) {
		/*$.ajax({
			url: '/api/g?id=1',
			data: { 'status' : status, 'id' : element.id},
			success: function(data) {
			}
		});*/
	},
	
	sendOrderRejected: function(element, comment) {
		/*$.ajax({
			url: '/api/g?id=1',
			data: { 'status' : 'rejected', 'id' : element.id, 'comment': 'comment'},
			success: function(data) {
				element.timeStarded = data.timeStarted;
			}
		});*/
	},
	
	timeButtonPressed: function(element, time) {
		element.domElem.remove();
		element.time = time;
		this.activeOrdersContent.append(this.getActiveOrderDiv(element));
		this.sendOrderActivated(element, time);
	},
	
	showRejectDialog: function(orderElem) {
		
		sendOrderRejected(orderElem, '');
		orderElem.domElem.remove();
		orderElem.remove();
	}
};