$(document).ready(function() {
			$("p").click(function(){
				$('ul').slideToggle();
			});
			$("li").click(function(){
				$('p').text($(this).text());
				$('li').removeClass('selected');
				$(this).addClass('selected');
				$('ul').slideToggle();
				
			});
});
