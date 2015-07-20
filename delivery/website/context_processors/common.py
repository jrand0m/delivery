from django.conf import settings


def general(request):
    return dict(
        static_prefix=settings.STATIC_URL,
        media_prefix=settings.MEDIA_URL,
        debug=settings.DEBUG
    )
