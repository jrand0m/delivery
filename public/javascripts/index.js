$(document).ready(function() {
			$('p.selText').text($('.selected').text());
			$("p.selText").click(function(){
				$('ul.selDrop').slideToggle();
			});
			
			$("ul.selDrop li").click(function(){
				$('p.selText').text($(this).text());
				$('ul.selDrop li').removeClass('selected');
				$(this).addClass('selected');
				
			//	var olo = $.cookie('city',{ raw: true });
				//$.cookie('city', $(this).context.id, { raw: true,expires: 7} );
				
				window.location.href = '/changeCity?id=' + $(this).context.id ;
				$('ul.selDrop').slideToggle();
			});
});
