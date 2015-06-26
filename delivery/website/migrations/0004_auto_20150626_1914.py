# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('website', '0003_auto_20150626_1908'),
    ]

    operations = [
        migrations.AlterField(
            model_name='user',
            name='deleted',
            field=models.BooleanField(default=False),
        ),
    ]
