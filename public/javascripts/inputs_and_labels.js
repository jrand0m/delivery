$(document).ready(function() {
	$(".ingredient_popup_but").fancybox({
		'titlePosition'		: 'inside',
		'transitionIn'		: 'none',
		'transitionOut'		: 'none'
	});
	
	var $inp=$("#login input");
	$inp.each(function(){
		if($(this).val()!='')
		{
			$(this).prev('label').hide();
		}
	});
	$("#login input").focus(function() {
		$(this).prev('label').css('display','none');
	});
	$("#login input").blur(function() {
		if (this.value==''){
			$(this).prev('label').css('display','inline');
		};
	});
	
	$(".minus").click(function () {
		var value=parseInt($(this).next("input").val());
		if (value>1) {
			$(this).next("input").val(value-1) 
		};
		if (value==2) {
			$(this).css('background-position', '0 -24px');
		};
		if (value==1) {
			$(this).parent().parent().slideToggle('fast');
		};
	});
	
	$(".plus").click(function () {
		var value=parseInt($(this).prev("input").val())
		$(this).prev("input").val(value+1);
		if (value=2){
			$(this).prev("input").prev('span').css('background-position', '0 -12px');
		};
	});
	$("#basket").makeFloat({x:"current",y:"current"});
});
