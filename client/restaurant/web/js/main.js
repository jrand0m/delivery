$(document).ready(function() {
	DCWMain.init();
});

var DCWMain = {
	newOrders: [],
	activeOrders: [],
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
			_.noTab(event);
		});
		fake.click(function(){
			_.newDialog(thisObj.getCV());
		});
		
		fake.attr("href", "#");
		$('body').prepend(fake);
		$('body').append(
			$(document.createElement('table'))
			.attr("id", "DCWMainOrderTable"));
		
		_.initDialogFrame();
		this.showContent();
	},
	
	getCV: function() {
		var imgName = 'img/cv' + Math.floor(Math.random()*3) + '.jpg';
		return this.createDiv().append($(document.createElement('img')).attr('src', imgName));
	
	},
	
	initVars: function(){
		var lang = _.getUrlVar('lang');
		_.initLang(lang);

		this.newOrders = new Array();
		this.activeOrders = new Array();
	},

	showContent: function(){
		var row = $(document.createElement('tr'));
		
		var leftTableBorder = $(document.createElement('td'))
			.append(_.createDiv("DCW10px").text(' '));
		leftTableBorder.addClass("DCWLeftTableBorder").addClass("DCWTableBorder");
		row.append(leftTableBorder);
		
		var newOrders = $(document.createElement('td'));
		newOrders.attr("id","DCWNewOrdersColumn");
		newOrders.addClass("DCWOrdersColumn");
		
		this.newOrdersContent = _.createDiv("DCWOrdersColumnContent", "DCWNewOrdersColumnContent");
		
		newOrders.append(
			_.createDiv('DCWContentWrapper')
				.append(this.newOrdersContent
					.append(_.createDiv("DCW10px").text(' '))));
		row.append(newOrders);
		
		this.activeOrdersContent = _.createDiv("DCWOrdersColumnContent", "DCWActiveOrdersColumnContent");
		
		var activeOrders = $(document.createElement('td'));
		activeOrders.attr("id","DCWActiveOrdersColumn");
		activeOrders.addClass("DCWOrdersColumn");
				
		var tableSeparator = $(document.createElement('td'))
			.append(_.createDiv("DCW10px").text(' '));
		tableSeparator.addClass("DCWTableSeparator").addClass("DCWTableBorder");
		row.append(tableSeparator);
		
		activeOrders.append(
			_.createDiv('DCWContentWrapper')
				.append(this.activeOrdersContent
					.append(_.createDiv("DCW10px").text(' '))));
		row.append(activeOrders);
		
		var rightTableBorder = $(document.createElement('td'))
			.append(_.createDiv("DCW10px").text(' '));
		rightTableBorder.addClass("DCWRightTableBorder").addClass("DCWTableBorder");
		row.append(rightTableBorder);
		
		$('#DCWMainOrderTable').html('');
		$('#DCWMainOrderTable').append(row);
		
		var thisObj = this;
		
		$('.DCWContentWrapper').each(function(el){
			thisObj.myScroll.push(new iScroll(this, { snap: false, bounce: false,  momentum:false, hScrollbar:false, vScrollbar:false }));
		});

		$(window).resize(function() {
			_.initColunHeight();
		});

		_.getAllOrders(this);
	}
};
