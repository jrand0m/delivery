# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
from django.contrib.sites.models import Site


def update_sites(apps, schema):
    site = Site.objects.first()
    site.name = 'vdo.ma'
    site.domain = '*'
    site.save()


class Migration(migrations.Migration):

    dependencies = [
        ('website', '0004_auto_20150626_1914'),
    ]

    operations = [
        migrations.RunPython(update_sites)
    ]
