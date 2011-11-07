Ext.define('AV.controller.Cities', {
  extend: 'Ext.app.Controller',
  stores: ['City','Street'],
  models: ['City','Street'],
  views: ['city.List','city.Edit'],
  init: function() {
    this.control({
      'viewport > panel': {
	render: this.onPanelRendered
      },
      'viewport > panel citylist': {
	itemdblclick: this.editCity
      },
      'cityedit button[action=saveCityAct]': {
	click: this.updateCity
      },
      
      'viewport > panel button[action=addCityAction]':{
	click: this.addCity
      },
      
      'cityedit button[action=editStreetsAct]':{
	click: this.editStreets
      }
      
    });
  },
  
  onPanelRendered: function() {
    console.log('The panel was rendered');
  }, 
  editCity:function(grid, record){
    var view = Ext.widget('cityedit');
    view.setTitle("edit " + record.get('cityNameEN'));
    view.down('form').loadRecord(record);
    view.show();
  },
  updateCity: function(button){
      var win    = button.up('window'),
	  form   = win.down('form'),
	  record = form.getRecord(),
	  values = form.getValues();
	  if (!values.display){
		values.display=false;
	  }
			   if (record){
			     record.set(values);
			   }else{
			     this.getCityStore().add(values);
			   }
			   this.getCityStore().sync();
			   win.close();
  },
  addCity:function(){
    var view = this.getCityEditView();
    new view().show();
  },
  editStreets: function(){
    console.log("on edit streets");
  }
  
});