var lop = function(m){
	if ($('.zaraz .zaraz_item').size()>9){
		var target = $('.zaraz .zaraz_item:last');
		target.hide('fast', function(){ target.remove(); });
	}
	
	$('.zaraz h1').after(tmpl("lotmpl",m));
	$('.zaraz .zaraz_item:first').slideDown('fast');
	
}

var lofn= function(f){
     $.ajax({
        url: loupd({top:f}),
        success: function(m) {
            $(m).each(function() {
                lop(this);
            });
        },
        complete: function() {
            lofn(false);
        },
        dataType: 'json'
    });	
}
lofn(true);
$(document).ready(function() {
	$('p.selText').text($('.selected').text());
	
	$("p.selText").click(function(){
		$(this).next('ul.selDrop').slideToggle();
	});
	
	$("ul.selDrop li").click(function(){
		$(this).parent().prev('p.selText').text($(this).text());
		$(this).parent('li').removeClass('selected');
		$(this).addClass('selected');
		window.location.href = cngcty({id:$(this).context.id});
		$(this).parent().slideToggle();
	});
});
