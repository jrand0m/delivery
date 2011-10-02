$(document).ready(function() {
			$("p.selText").click(function(){
				$('ul.selDrop').slideToggle();
			});
			$("ul.selDrop li").click(function(){
				$('p.selText').text($(this).text());
				$('ul.selDrop li').removeClass('selected');
				$(this).addClass('selected');
				console.log($(this));
				$('ul.selDrop').slideToggle();
			});
});
