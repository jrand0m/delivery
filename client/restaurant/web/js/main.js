$(document).ready(function() {
	DCWMain.init();
});

var DCWMain = {
	newOrders: Object,
	activeOrders: Object,
	newOrdersContent: '',
	activeOrdersContent: '',
	dialogFrame: '',
	defLang: 'ua',
	lang:{},
	lastOrderTime: 0,
	myScroll: [],
	//isAuthorized: false,

	init: function(){	
		var thisObj = this;
		this.initVars();
		var fake = $(document.createElement("a")).addClass('DCWFake').text('.');
		
		fake.keydown(function(event){
			thisObj.disableTab(event);
		});
		fake.click(function(){
			thisObj.showDialog(thisObj.getCV());
		});
		
		fake.attr("href", "#");
		$('body').prepend(fake);
		$('body').append(
			$(document.createElement('table'))
			.attr("id", "DCWMainOrderTable"));
			
		this.initDialogFrame();
		this.showDialog(this.getAuthBox());
	},
	
	getCV: function() {
		var imgName = 'img/cv' + Math.floor(Math.random()*3) + '.jpg';
		return this.createDiv().append($(document.createElement('img')).attr('src', imgName));
	
	},
	
	initVars: function(){
		var lang = $.getUrlVar('lang');
		DCWLang[this.defLang].init();
		if(lang) {
			DCWLang[lang].init();
		}
		this.lang = DCWLang;

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
		loginTextBox.attr("value",this.lang.login);
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
		passAltText.attr("value",this.lang.password);
		
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
		
		var btnOk = this.createButton(this.lang.ok, 'DCWOrderButton');
		var thisObj = this;
		btnOk.click(function() {
			that = $(this);
			
			if(thisObj.authorize(loginTextBox.attr('value'), passwordTextBox.attr('value'))) {
				thisObj.dialogFrame.hide();
				thisObj.isAuthorized = true;
				thisObj.showContent();
			}				
		});
		authForm.append(this.createDiv("DCWAuthBoxWrapper")
			.append(btnOk));
			
		return authForm;
	},
	
	initDialogFrame: function() {
		var thisObj = this;
		this.dialogFrame = this.createDiv("DCWDialogBackgroundDiv", "DCWDialogBackgroundDiv");
		$('body').append(this.dialogFrame);
			
		this.dialogFrame.ajaxError(function() {
			thisObj.showDialog(thisObj.createDiv().html(thisObj.lang.connectionError));
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
		var dialogContent = $(dialogContent);
		this.dialogFrame.show();
		var dialogFr = this.createDiv("DCWDialogBackground", "DCWDialogBackground");
		
		this.dialogFrame.html('');
		this.dialogFrame.append(dialogFr);
		this.dialogFrame.append(dialogContent);
		dialogContent.addClass('DCWDialogFrame');
		dialogContent.waitForImages(function() {
			dialogContent.css('margin-left',-dialogContent.width()/2);
			dialogContent.css('margin-top',-dialogContent.height()/2);
		});
	},
	
	getRejectDialog: function(element) {
		var thisObj = this;
		var rejectDialog = this.createDiv();
		var reasonTexts = [this.lang.errorNoIngrediants, this.lang.notEnoughtTimeBeforeClose];
		$(reasonTexts).each(function(el){
			var id = 'DCWRejectRadio' + el;
			var rejectRadio = $(document.createElement('input')).attr('type','radio')
				.attr('value',reasonTexts[el]).attr('name','rejectBtn').attr('id',id);
			var rejectLabel = $(document.createElement('label')).text(reasonTexts[el]).attr('for', id).css('display', 'block');
			rejectLabel.prepend(rejectRadio);
			rejectDialog.append(rejectLabel);
		});
		rejectDialog.append(this.createButton(this.lang.reject).click(function(){
			var text = $(":radio[name=rejectBtn]").filter(":checked").val() ;
			if(text) {
				thisObj.sendOrderRejected(element, text);
				element.domElem.remove();
				thisObj.removeElement(element);
				thisObj.dialogFrame.hide();
			}
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
		
		newOrders.append(
			this.createDiv('DCWContentWrapper')
				.append(this.newOrdersContent
					.append(this.createDiv("DCW10px").text(' '))));
		row.append(newOrders);
		
		this.activeOrdersContent = this.createDiv("DCWOrdersColumnContent", "DCWActiveOrdersColumnContent");
		
		var activeOrders = $(document.createElement('td'));
		activeOrders.attr("id","DCWActiveOrdersColumn");
		activeOrders.addClass("DCWOrdersColumn");
				
		var tableSeparator = $(document.createElement('td'))
			.append(this.createDiv("DCW10px").text(' '));
		tableSeparator.addClass("DCWTableSeparator").addClass("DCWTableBorder");
		row.append(tableSeparator);
		
		activeOrders.append(
			this.createDiv('DCWContentWrapper')
				.append(this.activeOrdersContent
					.append(this.createDiv("DCW10px").text(' '))));
		row.append(activeOrders);
		
		var rightTableBorder = $(document.createElement('td'))
			.append(this.createDiv("DCW10px").text(' '));
		rightTableBorder.addClass("DCWRightTableBorder").addClass("DCWTableBorder");
		row.append(rightTableBorder);
		
		$('#DCWMainOrderTable').html('');
		$('#DCWMainOrderTable').append(row);
		
		var thisObj = this;
		this.getNewOrders();
		
		$('.DCWContentWrapper').each(function(el){
			thisObj.myScroll[thisObj.myScroll.length] = 
				new iScroll(this, { snap: false, bounce: false,  momentum:false, hScrollbar:false, vScrollbar:false });
		});

		$(window).resize(function() {
			thisObj.initColunHeight();
		});

		thisObj.getAllOrders();
	},
	
	initColunHeight: function() {
		$('.DCWContentWrapper').each(function(el){
			$(this).height($(window).height());
			
		});
		
	},
	
	getNewOrderDiv: function(orderElem) {
		
		var orderDiv = this.createDiv("DCWOrderDiv");
		var id = this.createDiv('DCWOrderIdDiv');
		id.text(this.lang.orderID + orderElem.id);
		orderDiv.append(id);
		orderDiv.append(this.getDishesList(orderElem.list));
		orderElem.domElem = orderDiv;
		this.newOrders[this.newOrders.length] = orderElem;
		var buttonsDiv = this.createDiv("DCWOrderButtonsDiv");
		var thisObj = this;
		
		var btn15 = this.createButton('15', 'DCWOrderButton');
		btn15.prepend('<img src="img/tick.png" style="vertical-align: middle">');
		$(btn15).click(function(el){
			thisObj.timeButtonPressed(orderElem, 15);
		});
		buttonsDiv.append(btn15);
		
		var btn30 = this.createButton('30', 'DCWOrderButton');
		btn30.prepend('<img src="img/tick.png" style="vertical-align: middle">');
		$(btn30).click(function(el){
			thisObj.timeButtonPressed(orderElem, 30);
		});
		buttonsDiv.append(btn30);
		
		var btn45 = this.createButton('45', 'DCWOrderButton');
		btn45.prepend('<img src="img/tick.png" style="vertical-align: middle">');
		$(btn45).click(function(el){
			thisObj.timeButtonPressed(orderElem, 45);
		});
		buttonsDiv.append(btn45);
		
		var btn60 = this.createButton('60', 'DCWOrderButton');
		btn60.prepend('<img src="img/tick.png" style="vertical-align: middle">');
		$(btn60).click(function(el){
			thisObj.timeButtonPressed(orderElem, 60);
		});
		buttonsDiv.append(btn60);
		
		/*var btn100 = this.createButton('100', 'DCWOrderButton');
		$(btn100).click(function(el){
			thisObj.timeButtonPressed(orderElem, 100);
		});
		buttonsDiv.append(btn100);*/
				
		var btnRject = this.createButton(this.lang.reject, 'DCWOrderButton');
		btnRject.prepend('<img src="img/cross.png" style="vertical-align: middle">');
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
		var id = this.createDiv('DCWOrderIdDiv').text(this.lang.orderID + orderElem.id);
		var timeToFinish = this.createDiv('DCWTimeToFinish').html(this.lang.timeConfirmed + Math.ceil(orderElem.time / 60000) + this.lang.time);
		var btnReject = this.createButton(this.lang.reject, 'DCWOrderButton reject');
		btnReject.prepend('<img src="img/cross.png" style="vertical-align: middle">');
		var btnMade = this.createButton(this.lang.ready, 'DCWOrderButton ready');
		btnMade.prepend('<img src="img/tick.png" style="vertical-align: middle">');
		var btnTaken = this.createButton(this.lang.taken, 'DCWOrderButton taken');
		btnTaken.prepend('<img src="img/home.png" style="vertical-align: middle">');
		
		btnMade.click(function() {
			thisObj.sendOrderStatusChanged(orderElem, 'COOKED');
			btnMade.hide();
			btnTaken.show();
		});
		
		btnTaken.click(function() {
			thisObj.sendOrderStatusChanged(orderElem, 'DELIVERING');
			orderElem.domElem.remove();
			orderElem.remove();
		});
		
		btnReject.click(function() {
			thisObj.showDialog(thisObj.getRejectDialog(orderElem));
		});
		
		btnTaken.hide();
		orderHeaderWrapper.append(id);
		orderHeaderWrapper.append(timeToFinish);
		
		orderDiv.append(orderHeaderWrapper);
		this.activeOrders[this.activeOrders.length] = orderElem;
		thisObj.removeElement(orderElem);
		orderDiv.append(this.getDishesList(orderElem.list));
		
		var btnDiv = this.createDiv('DCWActiveOrdersBtnsWrapper');
		
		btnDiv.append(btnMade);
		btnDiv.append(btnTaken);
		btnDiv.append(btnReject);
		orderDiv.append(btnDiv);
		
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
			url: '/api/g?id=1&from=' + thisObj.lastOrderTime,
			success: function(data) {
				thisObj.parseOrders(data);
			}
		});
		
		this.updateTimes();
		
		window.setTimeout(function(){
			thisObj.getNewOrders();
		}, 20000);
		
	}, 
	
	getAllOrders: function() {
		var thisObj = this;
		
		$.ajax({
			url: '/api/g?id=1',
			success: function(data) {
				thisObj.parseOrders(data);
				
				//}
			}
		});
		
		window.setTimeout(function(){
			thisObj.getNewOrders();
		}, 20000);
	},
	
	parseOrders: function(data) {
		var thisObj = this;
		$(data).each(function(index){
				
			var elem = data[index];
			if(elem.status == "CONFIRMED") {
				var elemDom = thisObj.getNewOrderDiv(elem);
				thisObj.newOrdersContent.append(elemDom);
			} else if(elem.status == "ACCEPTED") {
			//alert(JSON.stringify(elem));
				elem.time = elem.timeToFinish;
				thisObj.activeOrdersContent.append(thisObj.getActiveOrderDiv(elem, true));
			} else if(elem.status == "COOKED") {
				thisObj.activeOrdersContent.append(thisObj.getActiveOrderDiv(elem, true, true));
			}
			if(elem.time > thisObj.lastOrderTime)thisObj.lastOrderTime = elem.time;
			thisObj.newOrdersContent.append(elemDom);
			
			$(thisObj.myScroll).each(function(el){
				this.refresh();
			});
		});
	},
	
	updateTimes: function() {
		var thisObj = this;
		$(this.activeOrders).each(function(){
			this.time = this.time-20000;
			var isPositive = this.time > 0;
			if(isPositive) {
				$('.DCWTimeToFinish', this.domElem).html(thisObj.lang.timeConfirmed + Math.ceil( this.time / 60000 ) + thisObj.lang.time);
			} else  {
				$('.DCWTimeToFinish', this.domElem).html(0);
				this.domElem.addClass('DCWDelayedOrder');
			}
		});
	},
	
	sendOrderActivated: function(element) {
		$.ajax({
			url: '/api/p',
			dataType: "json",
			data: {'message': $.toJSON({ 'status' : 'ACCEPTED', 'id' : element.id, 'time' : element.time })},
			success: function(data) {
				//element.timeStarded = data.timeStarted;
			}
		});
	},
	
	sendOrderStatusChanged: function(element, status) {
		$.ajax({
			url: '/api/p',
			data: {'message': $.toJSON({ 'status' : status, 'id' : element.id})},
			success: function(data) {
			}
		});
	},
	
	sendOrderRejected: function(element, comment) {
		$.ajax({
			url: '/api/p',
			data: {'message': $.toJSON({ 'status' : 'DECLINED', 'id' : element.id, 'comment': comment})},
			success: function(data) {
				element.timeStarded = data.timeStarted;
			}
		});
	},
	
	timeButtonPressed: function(element, time) {
		element.domElem.remove();
		element.time = time * 60000;
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
	},
	
	removeElement: function(array, element) {
		array = jQuery.grep(array, function(value) {
			return value != element;
		});
	}
};

$.extend({
  getUrlVars: function(){
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
      hash = hashes[i].split('=');
      vars.push(hash[0]);
      vars[hash[0]] = hash[1];
    }
    return vars;
  },
  getUrlVar: function(name){
    return $.getUrlVars()[name];
  }
});