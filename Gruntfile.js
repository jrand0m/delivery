// Do not edit below this line

module.exports = function(grunt) {
    grunt.initConfig({
        less: {
            v1: {
                files: {
                    './delivery/website/static/css/app.css': './delivery/website/static/less/app.less'
                },
                options:  {
                    paths: ['bower_components/'],
                    compress: false,
                    cleancss: true
                }
            }
        },
        watch: {
            v1: {
                files: ['./delivery/website/static/less/*.less'],
                tasks: ['less:v1']
            }
        },
        cssmin: {
            options: {
                keepSpecialComments: 0
            },
            v1: {
                files: []
            }
        }
    });
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-less');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.registerTask('default', ['less', 'cssmin']);
    //grunt.registerTask('bundle', 'Compile LESS & perform maximum clean-up.', function() {
    //    grunt.task.run('less', 'cssmin');
    //});
};
