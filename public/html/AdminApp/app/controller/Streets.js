Ext.define('AV.controller.Street', {
  extend: 'Ext.app.Controller',
  stores: ['City','Street'],
  models: ['City','Street'],
  views: ['street.List','street.Edit'],
  init: function() {
    this.control({
      
      
    });
  },
});