from django.conf.urls import patterns, include, url
from django.contrib import admin

urlpatterns = patterns(
    '',
    # Examples:
    # url(r'^$', 'delivery.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^admin/', include(admin.site.urls)),

    url(r'^pages/', include('django.contrib.flatpages.urls')),
    url(r'^test-index$', 'delivery.website.views.index', name='index'),
    url(r'^$', 'delivery.website.views.yoda', name='yoda'),
    
    url(r'^google9049961bc731c473.html', 'delivery.website.views.google', name='google'),
)
