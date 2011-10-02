$(document).ready(function() {
			$('p.selText').text($('.selected').text());
			$("p.selText").click(function(){
				$('ul.selDrop').slideToggle();
			});
			
			$("ul.selDrop li").click(function(){
				$('p.selText').text($(this).text());
				$('ul.selDrop li').removeClass('selected');
				$(this).addClass('selected');
				
				var olo = $.cookie('PLAY_SESSION',{ raw: true });
				
				olo = olo.replace( new RegExp('(city%3A)[0-9]'), 'city%3A'+$(this).context.id );
				$.cookie('PLAY_SESSION', olo, { raw: true,expires: 7} );
				
				window.location.href = document.location;
				$('ul.selDrop').slideToggle();
			});
});
