cmp=[];
var format = function(num){
	num = isNaN(num) || num === '' || num === null ? 0.00 : num;
    return (parseFloat(num)/100).toFixed(2);
}
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
				Basket.update(msg);	
			}
			
		});
		$.fancybox.close();	
		$('#a'+i).removeClass('current');	
	},
	cng				: function(i, c){
		var data = "id="+i+"&count="+c;
		$.ajax({
			type: "POST",
			url: du({}),
			data: data,
			success: function(msg){
				Basket.update(msg);	
			}
			
		});
	},
	$update				: function(data){
		this.$reset();
		$.each(data.items, function(i,e){
			$(Basket.renderContainer).append(tmpl("ittmp",{id:e["id"],cnt:e["count"],nm:e["name"],de:e.desc,pc:format(e.price)}));
		});
		
		$(Basket.renderContainer).append("<input type=\"hidden\" name=\"id\" value=\""+data["no"]+"\"/>");
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
		$(Basket.renderContainer).append(tmpl("dptmp",{pc:format(c)}));
	},
	$setTotalPrice		: function(c){
		$(Basket.renderContainer).append(tmpl("tptmp",{pc:format(c)}));
	},
	$setDiscount		: function(c){
		if(c&&c>0) 
		$(Basket.renderContainer).append(tmpl("dctmp",{pc:format(c)}));
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
		ei.pr = format(ei.pr);
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
	obj.pcd = format(obj.pc);
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
	el.text(el.text().replace(/[\d.]+/g, format(t)));
}

Basket.init();
