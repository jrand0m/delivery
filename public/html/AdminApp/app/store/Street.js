Ext.define('AV.store.Street', {
    extend: 'Ext.data.Store',
    model: 'AV.model.Street',
    autoLoad: true,

    proxy: {
        type: 'rest',
        url: '/power/edit/streets'
    }
});