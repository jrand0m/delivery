Basket = {
	baseURL				:	"http://vdoma.com.ua/application/",
	rows				:	[],
	init				: function (){
		this.reset();
		
	}
	update 				: function (){
		this.reset();
		
	},
	
	/*add item to basket */
	addItem				: function(item, count){
		this.update();
	},
	/*rem item rom basket */
	remItem				: function(item, count){
		this.update();
	},
	/*builds rows with data passed. data is array of ordItems*/
	$update				: function(data){
	
	},
    $makeCall			: function(url, data, callback){
	
	},
	/* insert row to gui */
	$insertRow			: function(/*?*/){
	
	},
	/* remove row to gui */
	$removeRow			: function(/*? id of menu item*/){
	
	},
	/* delete rows from gui */
	$reset				: function(){
		$.each(this.rows, function(i,v) { 
			$(v).remove();
		});
		this.$setDeliveryPrice(0);
		this.$setTotalPrice(0);
		this.$setDiscount(0);
	},
	$setDeliveryPrice	: function(c){

	},
	$setTotalPrice		: function(c){
	
	},
	$setDiscount		: function(c){
	
	}
};
Basket.init();

