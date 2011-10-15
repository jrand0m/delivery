cmp=[];
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
		var data = "id="+i;
		if (cmp.length!=0){
			$.each(cmp,function(i,e){
				if (e&&e.en){
					data=data+"&component="+i;
				}	
			});	
		}
		$.ajax({
			type: "POST",
			url: au({}),
			data: data,
			success: function(msg){
				this.update(msg);	
			}
		});
			
			
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

var rndr = function(c){
	var obj = {};obj["nm"]= c.nm;obj["dc"]= c.dc;
	obj["no"]= c.no;obj["pc"]= c.pc;
	var comps = c.items;
	var itmz = [];
	for (var i = 0; i < comps.length; i++){
		var ei = {id:comps[i].no,nm:comps[i]["name"],pr:comps[i].price};
		cmp[ei.id] = {en:false,pc:ei.pr}; 
		itmz.push(tmpl("dtmp",{d:ei}));		
	};
	obj["tb"] = "";
	for (var i = 0; i < itmz.length/2 +(itmz.length%2?1:0); i=i+2){
		var le = itmz[i];
		var re = itmz[i+1];
		if (!re) re = tmpl("dtmp",{d:{}});
		var ei = {l:le,r:re};
		obj["tb"]=obj["tb"]+(tmpl("rotmp",{i:ei}));		
	}
	return tmpl("cmfrtmp",{i:obj});
}
var add = function (i,c){
	$('#a'+i).addClass('current');
	if (c){cmp=[];		
		$.ajax({
			type: "POST",
			url: cu({}),
			data: "id="+i,
   			success: function(msg){
   					$('#a'+i).removeClass('current');
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
	var t = parseInt($('#basePc').val());
	$.each( cmp,  function(i, e){
		if (e&&e.en){
			t= t+ e.pc;	
		}
	});
	var el = $('div#fancybox-wrap tr.total td.price');
	el.text(el.text().replace(/[\d]+/g, t));
}
Basket.init();
