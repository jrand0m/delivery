from common import *

DEBUG = False

RAVEN_CONFIG = dict(
    dsn='https://cacda38a101d43a5884b3570db7b04f9:795bafbfdd8b4d1280ae3d64caeb663a@app.getsentry.com/48668'
)

INSTALLED_APPS = INSTALLED_APPS + (
    'raven.contrib.django.raven_compat',
)
