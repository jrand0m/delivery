$(document).ready(function() {
	$(function() {
	var offset = $("#basket").offset();
	var topPadding = 15;
	$(window).scroll(function() {
		
			
		if ($(window).scrollTop()> offset.top) {
			$("#basket").stop().animate({marginTop: $(window).scrollTop() - offset.top + topPadding});
		}
		else {$("#basket").stop().animate({marginTop: 0});};});
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
	
	//$("a.tync").toogle(function () {
	//	$(this).next("div").animate({"width": "+=120px"}, "slow");
	//	$(this).toggleClass("active"); 
//	});
	$("a.tync").toggle(  
        function() {
			$("a.tync").parent().parent("tr").removeClass("act");
			$(this).parent().parent("tr").addClass("act");
			$("a.tync").next("div").animate({"width": "-=326px"}, "slow");
			$(this).next("div").animate({"width": "+=326px"}, "slow");
			//$(this).parent().parent("tr").toggleClass("act");
        },  
        function() {
			$(this).parent().parent("tr").removeClass("act");
            $(this).next("div").animate({"width": "-=326px"}, "slow");
			//$(this).parent().parent("tr").toggleClass("act");
        }  
    );

});
