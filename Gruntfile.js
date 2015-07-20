// Configuration for static

LESS_DIR = 'delivery/website/static/less';
CSS_DIR = 'delivery/static/css';

module.exports = function(grunt) {
    grunt.initConfig({
        less: {
            v1: {
                files: LESS_DIR + '/*.less',
                options:  {
                    paths: ['bower_components/'],
                    compress: false,
                    cleancss: true
                }
            }
        },
        watch: {
            v1: {
                files: [LESS_DIR + "*.less"],
                tasks: ["less:v2"]
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
