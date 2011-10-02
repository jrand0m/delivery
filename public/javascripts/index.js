$(document).ready(function() {
	$('p.selText').text($('.selected').text());
	
	$("p.selText").click(function(){
		$(this).next('ul.selDrop').slideToggle();
	});
	
	$("ul.selDrop li").click(function(){
		$(this).parent().prev('p.selText').text($(this).text());
		$(this).parent('li').removeClass('selected');
		$(this).addClass('selected');
		window.location.href = '/application/changeCity?id=' + $(this).context.id;
		$(this).parent().slideToggle();
	});
});
