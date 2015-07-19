var cnt=0, bg;
var $wmh = $('.wrapper-main header');
var arr = [1, 2, 3, 4, 5, 6, 7];

var bgrotater = setInterval(function() {
    if (cnt==7) cnt=0;
    //bg = 'url("' + arr[cnt] + '")';
    bg = 'url("static/images/0' + arr[cnt] + '.jpg")';
    cnt++;
    $wmh.css({'background-image': bg});
}, 4000);