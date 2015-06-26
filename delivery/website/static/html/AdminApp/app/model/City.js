Ext.define('AV.model.City', {
    extend: 'Ext.data.Model',
    fields: [
    {
      name:'cityNameUA',
      type:'string'
    },
    {
      name:'cityNameEN',
      type:'string'
      
    },
    {
      name:'cityNameRU',
      type:'string'
    },
    {
      name:'display'
    },
    {
      name:'id',
      type:'int'
    }],
    associations: [{
        type: 'hasMany',
        model: 'Street',
        primaryKey: 'id',
        autoLoad: true
    }]
});