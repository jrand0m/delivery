cmp=[];
Basket = {
    	renderContainer			:   	'div.rb_cont tbody',
	init				: function (){
		this.update(null);
	},
	update 				: function (resp){
		if (resp)Basket.$update(resp);else 
			$.ajax({
				type: "POST",
   				url: uu({chart:rid}),
   				success: function(msg){
					Basket.update(msg);
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
				console.log("add sucessful!");
				Basket.update(msg);	
			}
			
		});
		$.fancybox.close();	
			
	},
	cng				: function(i, c){
	
		this.update(resp);
	},
	$update				: function(data){
		this.$reset();
		console.log("on $update items ->" + data.items );
		console.log("render to ->"+ $(Basket.renderContainer));
		$.each(data.items, function(i,e){
			var x ;
			try{
			console.log("on $update rendering item -> " + e );
			x = tmpl("ittmp",{cnt:e["count"],nm:e["name"],de:e.desc,pc:e.price})
			}catch(r){
			console.log("on $update rendering item err " + r );
			}
			$(Basket.renderContainer).append(x);
		});
		Basket.$setDeliveryPrice(data.delivery);
		Basket.$setDiscount(data.discount);
		Basket.$setTotalPrice(data.total);
	},
	$reset				: function(){
		$(Basket.renderContainer).children().each( function(i,v) { 
			$(v).remove();
		});
	},
	$setDeliveryPrice	: function(c){
		$(Basket.renderContainer).append(tmpl("dptmp",{pc:c}));
	},
	$setTotalPrice		: function(c){
		$(Basket.renderContainer).append(tmpl("tptmp",{pc:c}));
	},
	$setDiscount		: function(c){
		if(c&&c>0) 
		$(Basket.renderContainer).append(tmpl("dctmp",{pc:c}));
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
