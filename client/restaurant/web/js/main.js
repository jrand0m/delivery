$(document).ready(function() {
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

	init: function(){
		this.initVars();
		$('body').append($(document.createElement('table')).attr("id", "DCWMainOrderTable"));
		this.initAuthBox();
	},
	
	initVars: function(){
		this.newOrders = new Array();
		this.activeOrders = new Array();
	},
	
	showAuthorization: function(){
		
	},
	
	authorize: function(login, password){
		return true;
	},
	
	initAuthBox: function(){
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
			
		authForm.append(passWrapper.append(passAltText));
		authForm.append(passWrapper.append(passwordTextBox));
		
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
				authForm.hide();
				thisObj.showContent();
			}				
		});
		authForm.append($(document.createElement('div'))
			.addClass('DCWAuthBoxWrapper')
			.append(btnOk));
		
		$('body').append(authForm);
		btnOk.width(passwordTextBox.width());
	},
	
	showContent: function(){
		var row = $(document.createElement('tr'));
		
		var leftTableBorder = $(document.createElement('td'));
		leftTableBorder.addClass("DCWLeftTableBorder").addClass("DCWTableBorder");
		row.append(leftTableBorder);
		
		var newOrders = $(document.createElement('td'));
		newOrders.attr("id","DCWNewOrdersColumn");
		newOrders.addClass("DCWOrdersColumn");
		
		var activeOrders = $(document.createElement('td'));
		activeOrders.attr("id","DCWActiveOrdersColumn");
		activeOrders.addClass("DCWOrdersColumn");
		
		row.append(newOrders);
		
		var tableSeparator = $(document.createElement('td'));
		tableSeparator.addClass("DCWTableSeparator").addClass("DCWTableBorder");
		row.append(tableSeparator);
		
		row.append(activeOrders);
		
		var rightTableBorder = $(document.createElement('td'));
		rightTableBorder.addClass("DCWRightTableBorder").addClass("DCWTableBorder");
		row.append(rightTableBorder);
		
		$('#DCWMainOrderTable').html('');
		$('#DCWMainOrderTable').append(row);
		
		var thisObj = this;
		$(testVals).each(function(elem){
			var elemDom = thisObj.getNewOrderDiv(testVals[elem]);
			newOrders.append(elemDom);
		});
		
		$(testVals).each(function(elem){
			activeOrders.append(thisObj.getActiveOrderDiv(testVals2[elem]));
		});
	},
	
	getNewOrderDiv: function(orderElem) {
		var orderDiv = $(document.createElement('div'));
		orderDiv.addClass('DCWOrderDiv');
		var price = $(document.createElement('div'));
		price.text(orderElem.price);
		price.addClass('DCWOrderPriceDiv');
		orderDiv.append(price);
		var id = $(document.createElement('div'));
		id.text(orderElem.id);
		orderDiv.append(id);
		orderDiv.append(this.getDishesList(orderElem));
		orderElem.domElem = orderDiv;
		return orderDiv;
	},
	
	getActiveOrderDiv: function(orderElem) {
		var orderDiv = $(document.createElement('div'));
		orderDiv.addClass('DCWOrderDiv');
		var price = $(document.createElement('div'));
		price.text(orderElem.price);
		price.addClass('DCWOrderPriceDiv');
		orderDiv.append(price);
		var id = $(document.createElement('div'));
		id.text(orderElem.id);
		orderDiv.append(id);
		orderDiv.append(this.getDishesList(orderElem));
		var that = this;
		orderDiv.click(function(){
			orderElem.domElem.remove();
		});
		
		orderElem.domElem = orderDiv;
		return orderDiv;
	},
	
	getDishesList: function(orderElem) {
		var parentDishesDiv = $(document.createElement('div'));
		$(orderElem.dishes).each(function(elem){
			var dishDom = $(document.createElement('div'));
			dishDom.append($(document.createElement('div')).text(orderElem.dishes[elem].name));
			parentDishesDiv.append(dishDom);
		});
		return parentDishesDiv;
	}
};