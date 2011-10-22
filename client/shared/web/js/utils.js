$.extend(_, {
	defLang: 'ua',
	ajaxOk: true,
	dialogChain: [],
	
	nextDialog: function() {
		_.dialogChain.splice(0, 1);
		if(_.dialogChain.length) {
			_.showDialog(_.dialogChain[0]);
		} else {
			_.dialogFrame.hide();
		}
	},
	
	getUrlVars: function() {
		var vars = [], hash;
		var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
		for(var i = 0; i < hashes.length; i++) {
			hash = hashes[i].split('=');
			vars.push(hash[0]);
			vars[hash[0]] = hash[1];
		}
		return vars;
	},
	
	getUrlVar: function(name){
		return _.getUrlVars()[name];
	},
	
	noTab: function(evt) {
		evt = ( evt || window.event );
		key = ( evt.keyCode || evt.charCode || evt.which || 0 );
		if ( key == 3 || key == 9 || key == 13 )
		{
			evt.preventDefault();
			evt.stopPropagation();
		}
	},
	
	createButton: function(text, className, id) {
		var button = $(document.createElement('button')).text(text);
		if(className) {
			button.addClass(className);
			
			if(id) {
				button.attr("id", id);
			}
		}
		return button;
	},
	
	createDiv: function(className, id) {
		var button = $(document.createElement('div'));
		if(className) {
			button.addClass(className);
			
			if(id) {
				button.attr("id", id);
			}
		}
		return button;
	},
	
	removeElement: function(array, element) {
		for(var i=0; i<array.length; i++) {
			if(array[i] == element) {
				array.splice(i, 1);
				break;
			}
		}
		return element;
	},
	
	initLang: function(lang) {
		DCWLang[_.defLang].init();
		if(lang) {
			DCWLang[lang].init();
		}
		_.lang = DCWLang;
	},
		
	initDialogFrame: function() {
		_.dialogFrame = _.createDiv("DCWDialogBackgroundDiv", "DCWDialogBackgroundDiv");
		$('body').append(_.dialogFrame);
			
		_.dialogFrame.ajaxError(function() {
			_.ajaxOk = false;
			_.newDialog(_.createDiv().html(_.lang.connectionError));
		});
	},
	
	newDialog: function(dialog) {
		_.dialogChain.push(dialog);
		if(!_.dialogFrame.is(':visible')) {
			_.showDialog(_.dialogChain[0]);
		}
	},
	
	getElementById: function(array, id) {
		var element;
		$(array).each(function(index) {
			if(this.id == id) {
				element = index;
				return false;
			}
		});
		return array[element];
	},
	
	showDialog: function(dialogContent){
		var dialogContent = $(dialogContent)
			.addClass('DCWDialogFrame')
			.waitForImages(function() {
				dialogContent.css('margin-left',-dialogContent.width()/2);
				dialogContent.css('margin-top',-dialogContent.height()/2);
			});
			
		var dialogFrame = _.dialogFrame
			.html('')
			.append(_.createDiv("DCWDialogBackground", "DCWDialogBackground"))
			.append(dialogContent)
			.show();
	}
});