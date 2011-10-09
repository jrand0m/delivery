/* basket json example
{
	discount		:1233.12332, 	// float 1 = 100%, 0.3 = 30%
	total 			:12323,			// total of order excluding delivery 
	deliveryPrice	:123312, 		//value in coins
	events:			null 			//TBD
	items:[
		{	
			mi: 1212,    // menu item id
			ip: 1321,    // item price in coins
			cnt: 1,      // count
			comps:[      // array of components. if object has no components this field is null; if no components choosen it is empty array;
					{
						title:"", // name to display
						desc: "", //description 
						price: 123, price in coins
					},
					{..}], 
			tit: "",     // title text
			des: "",     //description text
		}, 
		{...}
	]
}
*/
/* components json example 
[
	{
		ci: 1233, 		  // component id
		cp: 123,  		  // component price in coins 
		tit: "",  		  // title
		des: "",  		  // description
		avail: true, 	  // is avaliable 
		req:[234,235,..], // array of component ids that are required for this component to be added 
	    nc:[456,7657],    // array of component ids that cannon be used simultaneously with tis component
	},
	{...}
]


*/


/*(url must be configured from main html(are subj to change), but parameters mostly will not change, so can be localy defined)
api URLs:
	- add menu item: "/application/addOrderItem?id=123&count=12&component=1&component=2&component=3&..."
	- del menu item: "/application/delOrderItem?id=123&count=12"
    - basketrefresh: "/application/basket"                      // not functional now
    - get components for menu item: "/application/comps?id=123" // not functional now
*/


Basket = {
	baseURL				:	'base',
    	renderContainer			:   	'basket',
	rows				:	[],
	init				: function (){
		this.$reset();
		if (!this.baseURL){
			return false;
		}
	},
	update 				: function (){
		this.$reset();
		
	},
	
	/*incr item to basket */
	inc				: function(item, count){
		this.update();
	},
	/*decr or rem item from basket */
	dec				: function(item, count){
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
var add = function (i,c){
	alert('add id = ' + i + "; has components = " + c);
	if (c){

		$.fancybox(
			c,
			{
				'titlePosition'		: 'inside',
				'transitionIn'		: 'none',
				'transitionOut'		: 'none'
			}
		);
	}else{
		
	}
}





