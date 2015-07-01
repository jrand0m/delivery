# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('website', '0005_auto_20150626_1915'),
    ]

    operations = [
        migrations.RenameModel(
            old_name='RestaurantsCategory',
            new_name='RestaurantCategory',
        ),
        migrations.AlterModelOptions(
            name='city',
            options={'managed': True, 'verbose_name': 'City', 'verbose_name_plural': 'Cities'},
        ),
        migrations.AlterModelOptions(
            name='menuitem',
            options={'managed': True, 'verbose_name': 'Menu item', 'verbose_name_plural': 'Menu items'},
        ),
        migrations.AlterModelOptions(
            name='menuitemcomponent',
            options={'managed': True, 'verbose_name': 'Menu item component', 'verbose_name_plural': 'Menu item components'},
        ),
        migrations.AlterModelOptions(
            name='menuitemsgroup',
            options={'managed': True, 'verbose_name': 'Menu item group', 'verbose_name_plural': 'Menu item groups'},
        ),
        migrations.AlterModelOptions(
            name='order',
            options={'managed': True, 'verbose_name': 'Order', 'verbose_name_plural': 'Orders'},
        ),
        migrations.AlterModelOptions(
            name='orderitem',
            options={'managed': True, 'verbose_name': 'Order item', 'verbose_name_plural': 'Order items'},
        ),
        migrations.AlterModelOptions(
            name='restaurant',
            options={'managed': True, 'verbose_name': 'Restaurant', 'verbose_name_plural': 'Restaurants'},
        ),
        migrations.AlterModelOptions(
            name='restaurantcategory',
            options={'managed': True, 'verbose_name': 'Restaurant category', 'verbose_name_plural': 'Restaurant categories'},
        ),
        migrations.AlterModelOptions(
            name='restaurantdescription',
            options={'managed': True, 'verbose_name': 'Restaurant description', 'verbose_name_plural': 'Restaurant descriptions'},
        ),
        migrations.AlterModelOptions(
            name='restaurantworkhours',
            options={'managed': True, 'verbose_name': 'Restaurant workhours', 'verbose_name_plural': 'Restaurant workhours'},
        ),
        migrations.AlterModelOptions(
            name='street',
            options={'managed': True, 'verbose_name': 'Street', 'verbose_name_plural': 'Streets'},
        ),
        migrations.AlterModelOptions(
            name='systemsetting',
            options={'managed': True, 'verbose_name': 'System setting', 'verbose_name_plural': 'System settings'},
        ),
        migrations.AlterModelOptions(
            name='user',
            options={'managed': True, 'verbose_name': 'User', 'verbose_name_plural': 'Users'},
        ),
        migrations.AlterModelTable(
            name='restaurantcategory',
            table='restaurant_categories',
        ),
    ]
