# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('website', '0002_remove_user_created_date'),
    ]

    operations = [
        migrations.AlterField(
            model_name='user',
            name='updated_date',
            field=models.DateTimeField(null=True),
        ),
    ]
