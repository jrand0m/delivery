$(document).ready(function() {

	$('p#selText1').text($('ul#selDrop1').children('li.selected').text());
	$('p#selText2').text($('ul#selDrop2').children('li.selected').text());
	$('p#selText3').text($('ul#selDrop3').children('li.selected').text());

	$('p.selText1').text($('p.selText1 .selected').text());
	
	$("p.selText").click(function(){
		$(this).next('ul.selDrop').slideToggle();
	});
	
	$("ul.selDrop li").click(function(){
		$(this).parent().prev('p.selText').text($(this).text());
		$(this).parent('li').removeClass('selected');
		$(this).addClass('selected');
		//window.location.href = '/application/changeCity?id=' + $(this).context.id;
		console.log($(this));
		$(this).parent().slideToggle();
	});
});
