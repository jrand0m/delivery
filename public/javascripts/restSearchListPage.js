$(document).ready(function() {
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
		$(this).parent().slideToggle();
	});
});
