$(document).ready(function() {
	DCWMain.init();
});

var DCWMain = {
	newOrders: Object,
	activeOrders: Object,
	newOrdersContent: '',
	activeOrdersContent: '',
	dialogFrame: '',

	init: function(){	
		var thisObj = this;
		this.initVars();
		var fake = $(document.createElement("a")).addClass('DCWFake').text('.');
		
		fake.keydown(function(event){
			thisObj.disableTab(event);
		});
		
		fake.attr("href", "#");
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
		var authForm = this.createDiv("DCWAuthForm",  "DCWAuthForm");
			
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
		
		authForm.append(this.createDiv("DCWAuthBoxWrapper")
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
		
		var passWrapper = this.createDiv("DCWAuthBoxWrapper");
		
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
		
		var btnOk = this.createButton('ok', 'DCWOrderButton');
		var thisObj = this;
		btnOk.click(function() {
			that = $(this);
			
			if(thisObj.authorize(loginTextBox.attr('value'), passwordTextBox.attr('value'))) {
				thisObj.dialogFrame.hide();
				thisObj.showContent();
			}				
		});
		authForm.append(this.createDiv("DCWAuthBoxWrapper")
			.append(btnOk));
			
		return authForm;
	},
	
	initDialogFrame: function() {
		this.dialogFrame = this.createDiv("DCWDialogBackgroundDiv", "DCWDialogBackgroundDiv");
		$('body').append(this.dialogFrame);
			
		this.dialogFrame.ajaxError(function() {
			this.showDialog(this.createDiv().html("ERROR, blyet'!<br>shmu f5!"));
		});
	},
	
	disableTab: function(evt) {
		evt = ( evt || window.event );
		key = ( evt.keyCode || evt.charCode || evt.which || 0 );
		if ( key == 3 || key == 9 || key == 13 )
		{
			evt.preventDefault();
			evt.stopPropagation();
		}
	},
	
	showDialog: function(dialogContent){
		var dialogFr = this.createDiv("DCWDialogFrame", "DCWDialogFrame");
		
		dialogFr.append(dialogContent);
		this.dialogFrame.html('');
		this.dialogFrame.append(dialogFr);
		this.dialogFrame.show();
		dialogFr.css('margin-left',-dialogFr.width()/2);
		dialogFr.css('margin-top',-dialogFr.height()/2);
	},
	
	getRejectDialog: function(element) {
		var thisObj = this;
		var rejectDialog = this.createDiv();
		var inp = $(document.createElement('input')).attr('id','DCWRejectComment');
		var reasonTexts = ['No ingredients for some dish.', 'Will not be finished while restaurant is open.'];
		$(reasonTexts).each(function(el){
			var id = 'DCWRejectRadio' + el;
			var rejectRadio = $(document.createElement('input')).attr('type','radio')
				.attr('value',reasonTexts[el]).attr('name','rejectBtn').attr('id',id);
			var rejectLabel = $(document.createElement('label')).text(reasonTexts[el]).attr('for', id).css('display', 'block');
			rejectLabel.prepend(rejectRadio);
			rejectDialog.append(rejectLabel);
		});
		rejectDialog.append($(document.createElement('label')).text('comment: ')
			.attr('for', 'DCWRejectComment').css('display', 'block').append(inp));
		rejectDialog.append(this.createButton('reject').click(function(){
			var text = $(":radio[name=rejectBtn]").filter(":checked").val() + ' ' + inp.attr('value');
			
			thisObj.sendOrderRejected(element, text);
			element.domElem.remove();
			$(element).remove();
			thisObj.dialogFrame.hide();
		}));
		
		return rejectDialog;
	},
	
	showContent: function(){
		var row = $(document.createElement('tr'));
		
		var leftTableBorder = $(document.createElement('td'))
			.append(this.createDiv("DCW10px").text(' '));
		leftTableBorder.addClass("DCWLeftTableBorder").addClass("DCWTableBorder");
		row.append(leftTableBorder);
		
		var newOrders = $(document.createElement('td'));
		newOrders.attr("id","DCWNewOrdersColumn");
		newOrders.addClass("DCWOrdersColumn");
		
		this.newOrdersContent = this.createDiv("DCWOrdersColumnContent", "DCWNewOrdersColumnContent");
		
		newOrders.append(this.newOrdersContent);
		row.append(newOrders);
		
		this.activeOrdersContent = this.createDiv("DCWOrdersColumnContent", "DCWActiveOrdersColumnContent");
		
		var activeOrders = $(document.createElement('td'));
		activeOrders.attr("id","DCWActiveOrdersColumn");
		activeOrders.addClass("DCWOrdersColumn");
				
		var tableSeparator = $(document.createElement('td'))
			.append(this.createDiv("DCW10px").text(' '));
		tableSeparator.addClass("DCWTableSeparator").addClass("DCWTableBorder");
		row.append(tableSeparator);
		
		activeOrders.append(this.activeOrdersContent);
		row.append(activeOrders);
		
		var rightTableBorder = $(document.createElement('td'))
			.append(this.createDiv("DCW10px").text(' '));
		rightTableBorder.addClass("DCWRightTableBorder").addClass("DCWTableBorder");
		row.append(rightTableBorder);
		
		$('#DCWMainOrderTable').html('');
		$('#DCWMainOrderTable').append(row);
		
		var thisObj = this;
		
		this.getNewOrders();
		
	},
	
	getNewOrderDiv: function(orderElem) {
		
		var orderDiv = this.createDiv("DCWOrderDiv");
		var id = this.createDiv();
		id.text(orderElem.id);
		orderDiv.append(id);
		orderDiv.append(this.getDishesList(orderElem.list));
		orderElem.domElem = orderDiv;
		this.newOrders[this.newOrders.length] = orderElem;
		var buttonsDiv = this.createDiv("DCWOrderButtonsDiv");
		var thisObj = this;
		
		var btn15 = this.createButton('15', 'DCWOrderButton');
		$(btn15).click(function(el){
			thisObj.timeButtonPressed(orderElem, 15);
		});
		buttonsDiv.append(btn15);
		
		var btn30 = this.createButton('30', 'DCWOrderButton');
		$(btn30).click(function(el){
			thisObj.timeButtonPressed(orderElem, 30);
		});
		buttonsDiv.append(btn30);
		
		var btn45 = this.createButton('45', 'DCWOrderButton');
		$(btn45).click(function(el){
			thisObj.timeButtonPressed(orderElem, 45);
		});
		buttonsDiv.append(btn45);
		
		var btn60 = this.createButton('60', 'DCWOrderButton');
		$(btn60).click(function(el){
			thisObj.timeButtonPressed(orderElem, 60);
		});
		buttonsDiv.append(btn60);
		
		buttonsDiv.append(this.createButton('more', 'DCWOrderButton'));
		
		var btnRject = this.createButton('reject', 'DCWOrderButton');
		$(btnRject).click(function(el){
			thisObj.showDialog(thisObj.getRejectDialog(orderElem));
		});
		buttonsDiv.append(btnRject);
		
		orderDiv.append(buttonsDiv);
		return orderDiv;
	},
	
	getActiveOrderDiv: function(orderElem) {
		var thisObj = this;		
		var orderDiv = this.createDiv('DCWOrderDiv');
		/*var price = $(document.createElement('div'));
		price.text(orderElem.price);
		price.addClass('DCWOrderPriceDiv');
		orderDiv.append(price);*/
		var orderHeaderWrapper = this.createDiv('DCWOrderHeaderWrapper');
		var id = this.createDiv().text(orderElem.id);
		var btnReject = this.createButton('reject', 'DCWOrderButton');
		var btnMade = this.createButton('made', 'DCWOrderButton');
		var btnTaken = this.createButton('taken', 'DCWOrderButton');
		
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
		
		var btnDiv = this.createDiv('DCWActiveOrdersBtnsWrapper');
		btnDiv.append(btnMade);
		btnDiv.append(btnTaken);
		btnDiv.append(btnReject);
		orderHeaderWrapper.append(btnDiv);
		
		btnTaken.hide();
		orderHeaderWrapper.append(id);
		orderDiv.append(orderHeaderWrapper);
		this.activeOrders[this.activeOrders.length] = orderElem;
		orderElem.domElem = orderDiv;
		orderDiv.append(this.getDishesList(orderElem.list));
		orderElem.domElem = orderDiv;
		return orderDiv;
	},
	
	getDishesList: function(dishes) {
		var thisObj = this;
		var parentDishesDiv = this.createDiv();
		$(dishes).each(function(elem){
			var dishDom = thisObj.createDiv('DCWDishContent');
			dishDom.append(thisObj.createDiv('DCWDishName').text(dishes[elem].name));
			dishDom.append(thisObj.createDiv('DCWDishCount').text(dishes[elem].count));
			dishDom.append(thisObj.createDiv('DCWDishPrice').text(dishes[elem].pricePerItem));
			parentDishesDiv.append(dishDom);
		});
		return parentDishesDiv;
	},
	
	getNewOrders: function() {
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
		
	createButton: function(text, className, id) {
		var button = $(document.createElement('button')).text(text);
		if(className) {
			button.addClass(className);
			
			if(id) {
				button.attr("id", id);
			}
		}
		return button;
	},
	
	createDiv: function(className, id) {
		var button = $(document.createElement('div'));
		if(className) {
			button.addClass(className);
			
			if(id) {
				button.attr("id", id);
			}
		}
		return button;
	}
};