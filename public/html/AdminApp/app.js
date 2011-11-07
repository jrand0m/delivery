Ext.application({
    name: 'AV',
    controllers: [
        'Cities'
    ],

    appFolder: 'app',

    launch: function() {
        Ext.create('AV.view.Viewport', {
            layout: 'fit'
        });
    }
});