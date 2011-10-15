Basket = {
    	renderContainer			:   	'#basket',
	init				: function (){
		this.$update();
	},
	update 				: function (resp){
		if (resp)$update(resp);else 
			$.ajax({
				type: "POST",
   				url: uu({}),
   				success: function(msg){
     					console.log( "Update data recieved: " + msg );
					update(msg);
   				}
			});
	},
	add				: function(i){
	//i is array
		this.update(resp);	
	},
	cng				: function(i, c){
	
		this.update(resp);
	},
	$update				: function(data){
		
		this.$reset();
	},
	$reset				: function(){
		$(this.renderContainer).children().each( function(i,v) { 
			$(v).remove();
		});
		this.$setDeliveryPrice(0);
		this.$setDiscount(0);
		this.$setTotalPrice(0);
		
	},
	$setDeliveryPrice	: function(c){

	},
	$setTotalPrice		: function(c){
	
	},
	$setDiscount		: function(c){
	
	}
};
var cmp = {kind:false, repr:100, di:"sin"};
var rndr = function(comps){
	var itmz = [];
	for (var i = 0; i < comps.length; i++){
		var ei = {id:comps[i].no,nm:comps[i]["name"],pr:comps[i].price};
		cmp[ei.id] = {en:false,pc:ei.pr}; 
		itmz.push(tmpl("dtmp",{d:ei}));		
	};
	var t = "";
	for (var i = 0; i < itmz.length/2 +(itmz.length%2?1:0); i=i+2){
		var le = itmz[i];
		var re = itmz[i+1];
		if (!re) re = tmpl("dtmp",{d:{}});
		var ei = {l:le,r:re};
		t=t+(tmpl("rotmp",{i:ei}));		
	}
	return tmpl("cmfrtmp",{i:t});
}
var add = function (i,c){
	if (c){cmp=[];		
		$.ajax({
			type: "POST",
			url: cu({}),
			data: "id="+i,
   			success: function(msg){
					$.fancybox(
						rndr(msg),
					{
						//'titlePosition'		: 'inside',
						'transitionIn'		: 'none',
						'transitionOut'		: 'none'
						}
					);
   				}
			});}else{
		Basket.add(i);		
	}
}
var toggle = function (self){
	var id = self.id.replace(/[^\d]/g, "");
	cmp[id].en = self.checked;
	var t = 0;
	$.each( cmp,  function(i, e){
		if (e.en){
			t= t+ e.pc;	
		}
	}
	$('').text();
	
	);
}
Basket.init();
