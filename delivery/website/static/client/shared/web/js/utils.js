$.extend(_, {
	defLang: 'ua',
	ajaxOk: true,
	dialogChain: [],
	
	newDialog: function(dialog) {
		_.dialogChain.push(dialog);
		if(!_.dialogFrame.is(':visible')) {
			_.showDialog(_.dialogChain[0]);
		}
	},
	
	urgentDialog: function(dialog) {
		_.dialogChain.splice(0,0,dialog);
		_.showDialog(_.dialogChain[0]);
	},
	
	nextDialog: function() {
		_.dialogChain.splice(0, 1);
		if(_.dialogChain.length) {
			_.showDialog(_.dialogChain[0]);
		} else {
			_.dialogFrame.hide();
		}
	},
		
	getAuthBox: function(parent){
		var authForm = _.createDiv("DCWAuthForm",  "DCWAuthForm");
			
		var loginTextBox = $(document.createElement('input'));
		loginTextBox.attr("id","DCWLoginTextBox");
		loginTextBox.attr("value", _.lang.login);
		loginTextBox.addClass('DCWInput');
		loginTextBox.addClass('DCWAuthAltInput');
		
		loginTextBox.focus(function() {
			that = $(this);
			if(that.hasClass('DCWAuthAltInput')) {
				that.attr("value","");
				that.removeClass('DCWAuthAltInput');
				that.addClass('DCWAuthInput');
			}
		});
		
		loginTextBox.blur(function() {
			that = $(this);
			if(that.attr('value')=='') {
				that.attr("value", "login");
				that.removeClass('DCWAuthInput');
				that.addClass('DCWAuthAltInput');
			}
		});
		
		authForm.append(_.createDiv("DCWAuthBoxWrapper")
			.append(loginTextBox));
		
		var passAltText = $(document.createElement('input'));
		passAltText.attr("id","DCWPasswordAltText");
		passAltText.addClass('DCWAuthAltInput');
		passAltText.attr("value", _.lang.password);
		
		var passwordTextBox = $(document.createElement('input'));
		passwordTextBox.attr("id","DCWPassTextBox");
		passwordTextBox.attr("type","password");
		passwordTextBox.addClass('DCWInput');
		passwordTextBox.addClass('DCWAuthInput');
		
		var passWrapper = _.createDiv("DCWAuthBoxWrapper");
		
		passWrapper.append(passAltText);
		passWrapper.append(passwordTextBox);
		authForm.append(passWrapper);
		
		passwordTextBox.hide();
		
		passAltText.focus(function() {
			that = $(this);
			that.hide();
			passwordTextBox.show();
			passwordTextBox.focus();
		});
		
		passwordTextBox.blur(function() {
			that = $(this);
			if(that.attr('value')=='') {
				that.hide();
				passAltText.show();
			}
		});
		
		var btnOk = _.createButton(_.lang.ok, 'DCWOrderButton');
		var thisObj = this;
		btnOk.click(function() {
			that = $(this);
			
			_.authorize(loginTextBox.attr('value'), passwordTextBox.attr('value'), function(){_.getAllOrders(parent); _.nextDialog();});
		});
		
		authForm.append(this.createDiv("DCWAuthBoxWrapper")
			.append(btnOk));
			
		return authForm;
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
	
	createSpan: function(className, id) {
		var button = $(document.createElement('span'));
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
			_.urgentDialog(_.createDiv().html(_.lang.connectionError));


			if (typeof External !== 'undefined') {
				External.refresh();
			} else {
				setTimeout("location.reload(true);", 10000);
			}
		});
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
			.addClass('DCWDialogFrame');
			
		var dialogFrame = _.dialogFrame
			.html('')
			.append(_.createDiv("DCWDialogBackground", "DCWDialogBackground"))
			.append(dialogContent)
			.show();
			
		dialogContent.waitForImages(function() {
				dialogContent.css('margin-left',-dialogContent.width()/2);
				dialogContent.css('margin-top',-dialogContent.height()/2);
			});
	},
	
	formatCurrencyString: function(string) {
		var index = string.length - 2;
		return string.slice(0,index) + "." + string.slice(index);
	},
	
	getDishesList: function(dishes) {
		var parentDishesDiv = this.createDiv();
		$(dishes).each(function(elem){
			var dishDom = _.createDiv('DCWDishContent');
			dishDom.append(_.createDiv('DCWDishPrice').text(
				_.formatCurrencyString(this.pricePerItem+"")));
			dishDom.append(_.createSpan('DCWDishNumber').text((elem + 1) + '. '));
			//dishDom.append(_.createSpan('DCWDishCount').text(dishes[elem].count));
			dishDom.append(_.createSpan('DCWDishName').text(dishes[elem].name));
			if(this.components && this.components.length != 0) {
				var dishIngredients = _.createDiv('DCWDishIngredient');
				//$(dishes[elem].list).each(function(dish) {
					dishIngredients.append(_.getDishesList(this.components));
				//});
				
				dishDom.append(dishIngredients);
			};
			parentDishesDiv.append(dishDom);
		});
		return parentDishesDiv;
	},
	
	vibrateDevice: function() {
		if (typeof External !== 'undefined') {
			External.vibrate();
			External.playNotificationSound();
		} else {
			_.evalSound('audio1');
		}
	},
	
	evalSound: function(soundobj) {
		var thissound=document.getElementById(soundobj);
		thissound.play();
	}
});