Ext.define('AV.model.Street', {
    extend: 'Ext.data.Model',
    fields: ['id','city','title_ua','title_en','use'],
    associations: [
        { type: 'belongsTo', model: 'City' }
    ]
});