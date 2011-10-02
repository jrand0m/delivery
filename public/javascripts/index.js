$(document).ready(function() {
	//alert($('.selected').text());
	//alert($('p.selText').next().children('.selected').text());
	//var t = $('p.selText').next().children('li.selected').text();
	$('p#selText1').text($('ul#selDrop1').children('li.selected').text());
	$('p#selText2').text($('ul#selDrop2').children('li.selected').text());
	$('p#selText3').text($('ul#selDrop3').children('li.selected').text());

	$("p.selText").click(function(){
		$(this).next('ul.selDrop').slideToggle();
	});

	$("ul.selDrop li").click(function(){
		$(this).parent().prev('p.selText').text($(this).text());
		$(this).parent('li').removeClass('selected');
		$(this).addClass('selected');
		
		var olo = $.cookie('PLAY_SESSION',{ raw: true });
		
		olo = olo.replace( new RegExp('(city%3A)[0-9]'), 'city%3A'+$(this).context.id );
		$.cookie('PLAY_SESSION', olo, { raw: true,expires: 7} );
		
		window.location.href = document.location;
		$(this).parent().slideToggle();
	});
});
