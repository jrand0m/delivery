requirejs.config({
    baseUrl: 'static/js/libs',
    paths: {
        app: '../app',
        jquery: 'jquery-2.1.4.min'
    },

    shim: {
        'angular': {
            exports: 'angular'
        }
    }
});
requirejs(['app/main']);
