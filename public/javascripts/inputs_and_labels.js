$(document).ready(function() {
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
});
