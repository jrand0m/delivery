from django.conf.urls import patterns, include, url
from django.contrib import admin

from rest_framework import routers
from website import views

router = routers.DefaultRouter()
router.register(r'users', views.UserViewSet)

urlpatterns = patterns(
    '',
    # Examples:

    url(r'^api-v1/', include(router.urls)),

    url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework')),

    url(r'^admin/', include(admin.site.urls)),

    url(r'^pages/', include('django.contrib.flatpages.urls')),

    url(r'^$', 'delivery.website.views.yoda', name='yoda'),

    url(r'^for/restaurants', 'delivery.website.views.for_restaurants', name='for_restaurants'),
    
    url(r'^google9049961bc731c473.html', 'delivery.website.views.google', name='google'),
)
