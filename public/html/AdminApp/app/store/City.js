Ext.define('AV.store.City', {
    extend: 'Ext.data.Store',
    model: 'AV.model.City',
    autoLoad: true,

    proxy: {
        type: 'rest',
        url: '/power/edit/cities'
        /*reader: {
            type: 'json' / *,
            root: 'users',
            successProperty: 'success'* /
        }*/
    }
});