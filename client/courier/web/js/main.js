$(document).ready(function() {
	DCWMain.init();
});

var DCWMain = {
	orders: Object,
	newOrdersContent: '',
	pendingOrdersContent: '',
	activeOrdersContent: '',
	dialogFrame: '',
	lastOrderTime: 0,
	updatedNewOrders: 0,
	updatedPendingOrders: 0,
	updatedActiveOrders: 0,
	tabOpened: 0,

	init: function() {
		var thisObj = this;
		thisObj.initVars();
		var fake = $(document.createElement("a"))
			.addClass('DCWFake').text('.');
		
		fake.keydown(function(event){
			_.noTab(event);
		});
		
		fake.click(function(){
			_.newDialog(thisObj.getCV());
		});
		
		fake.attr("href", "#");
		$('body').prepend(fake);
		$('body').append(
			_.createDiv('', "DCWMainOrdersContent"));
			
		_.initDialogFrame();
		this.showContent();
	},
	
	getCV: function() {
		var imgName = 'img/cv' + Math.floor(Math.random()*3) + '.jpg';
		return _.createDiv().append($(document.createElement('img')).attr('src', imgName));
	},
	
	initVars: function(){
		var lang = _.getUrlVar('lang');
		_.initLang(lang);
		this.orders = new Array();
	},
		
	showContent: function() {
		var thisObj = this;
		var newOrdersButtonsDiv = _.createDiv('DCWOrdersButton')
			.append(_.createDiv().text(_.lang.newOrders))
			.append(_.createDiv()
				.append(_.createSpan().text(_.lang.orderCount))
				.append(_.createSpan('DCWOrdersCount', 'DCWNewOrdersCount')))
			.append(_.createDiv()
				.append(_.createSpan().text(_.lang.updatedCount))
				.append(_.createSpan('DCWUpdatedOrdersCount', 'DCWUpdatedNewOrdersCount')))
			.click(function() {
				thisObj.pendingOrdersContent.hide();
				thisObj.activeOrdersContent.hide();
				thisObj.newOrdersContent.show();
				thisObj.tabOpened = 0;
				thisObj.updatedNewOrders = 0;
				_.updateUpdatedOrdersCount(thisObj);
			});
		
		var pendingOrdersButtonsDiv = _.createDiv('DCWOrdersButton')
			.append(_.createDiv().text(_.lang.pendingOrders))
			.append(_.createDiv()
				.append(_.createSpan().text(_.lang.orderCount))
				.append(_.createSpan('DCWOrdersCount', 'DCWPendingOrdersCount')))
			.append(_.createDiv()
				.append(_.createSpan().text(_.lang.updatedCount))
				.append(_.createSpan('DCWUpdatedOrdersCount', 'DCWUpdatedPendingOrdersCount')))
			.click(function() {
				thisObj.newOrdersContent.hide();
				thisObj.activeOrdersContent.hide();
				thisObj.pendingOrdersContent.show();
				thisObj.tabOpened = 1;
				thisObj.updatedPendingOrders = 0;
				_.updateUpdatedOrdersCount(thisObj);
			});
			
		var activeOrdersButtonsDiv = _.createDiv('DCWOrdersButton')
			.append(_.createDiv().text(_.lang.activeOrders))
			.append(_.createDiv()
				.append(_.createSpan().text(_.lang.orderCount))
				.append(_.createSpan('DCWOrdersCount', 'DCWActiveOrdersCount')))
			.append(_.createDiv()
				.append(_.createSpan().text(_.lang.updatedCount))
				.append(_.createSpan('DCWUpdatedOrdersCount', 'DCWUpdatedActiveOrdersCount')))
			.click(function() {
				thisObj.newOrdersContent.hide();
				thisObj.pendingOrdersContent.hide();
				thisObj.activeOrdersContent.show();
				thisObj.tabOpened = 2;
				thisObj.updatedActiveOrders = 0;
				_.updateUpdatedOrdersCount(thisObj);
			});
			
		var tabButtonContainer = _.createDiv('DCWTabButtons').append(
			_.createDiv('SCWButtonsWrapper')
				.append(newOrdersButtonsDiv)
				.append(pendingOrdersButtonsDiv)
				.append(activeOrdersButtonsDiv));
			
		var ordersContent = _.createDiv('DCWOrders');
		
		this.newOrdersContent = _.createDiv("DCWOrdersContent", "DCWNewOrdersContent");
		ordersContent.append(this.newOrdersContent);
		
		this.pendingOrdersContent = _.createDiv("DCWOrdersContent", "DCWPendingOrdersContent");
		ordersContent.append(this.pendingOrdersContent.hide());
		
		this.activeOrdersContent = _.createDiv("DCWOrdersColumnContent", "DCWActiveOrdersColumnContent");
		ordersContent.append(this.activeOrdersContent.hide());
		
		
		$('body').append(tabButtonContainer);
		$('body').append(ordersContent);
		
		_.getAllOrders(this);	
	}
};