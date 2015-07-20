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
    },

    // запустить приложение
    deps: ['./bootstrap']
});
//requirejs(['app/main']);
