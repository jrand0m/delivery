$(document).ready(function() {
	DCWMain.init();
});

var DCWMain = {
	newOrders: Object,
	pendingOrders: Object,
	activeOrders: Object,
	newOrdersContent: '',
	pendingOrdersContent: '',
	activeOrdersContent: '',
	dialogFrame: '',
	lastOrderTime: 0,

	init: function() {
		var thisObj = this;
		thisObj.initVars();
		var fake = $(document.createElement("a"))
			.addClass('DCWFake').text('.');
		
		fake.keydown(function(event){
			_.noTab(event);
		});
		
		fake.click(function(){
			_.showDialog(thisObj.getCV());
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
		this.newOrders = new Array();
		this.pendingOrders = new Array();
		this.activeOrders = new Array();
	},
		
	showContent: function(){
		var thisObj = this;
		var tabButtonContainer = _.createDiv('DCWTabButtons')
			.append(_.createButton('New', 'DCWTabBtn')
				.click(function() {
					thisObj.pendingOrdersContent.hide();
					thisObj.activeOrdersContent.hide();
					thisObj.newOrdersContent.show();
				}))
			.append(_.createButton('Pending', 'DCWTabBtn')
				.click(function() {
					thisObj.newOrdersContent.hide();
					thisObj.activeOrdersContent.hide();
					thisObj.pendingOrdersContent.show();
				}))
			.append(_.createButton('Active', 'DCWTabBtn')
				.click(function() {
					thisObj.newOrdersContent.hide();
					thisObj.pendingOrdersContent.hide();
					thisObj.activeOrdersContent.show();
				}));
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