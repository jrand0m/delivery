$(document).ready(function() {
	$('p#selText1').text($('ul#selDrop1').children('li.selected').text());
	$('p#selText2').text($('ul#selDrop2').children('li.selected').text());
	$('p#selText3').text($('ul#selDrop3').children('li.selected').text());
	
	$("p.selText").click(function(){
		$(this).next('ul.selDrop').slideToggle();
	});
	
	$("ul#selDrop1 li").click(function(){
		$(this).parent().prev('p.selText').text($(this).text());
		$(this).parent('li').removeClass('selected');
		$(this).addClass('selected');
		window.location.href = cngcty({id:$(this).context.id});
		$(this).parent().slideToggle();
	});
	
	$("ul#selDrop2 li, ul#selDrop3 li").click(function(){
		$(this).parent().prev('p.selText').text($(this).text());
		$(this).parent('li').removeClass('selected');
		$(this).addClass('selected');
		$("#rest-sort-list div.all").hide();
		$("#rest-sort-list div." +$(this).context.id + "").show();
		$(this).parent().slideToggle();
	});

	
	
	
	
	/*$("ul.selDrop li").click(function(){
		$(this).parent().prev('p.selText').text($(this).text());
		$(this).parent('li').removeClass('selected');
		$(this).addClass('selected');
		$(this).parent().slideToggle();
	});*/
	
	
});
