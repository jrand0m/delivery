# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import django.contrib.auth.models
import django.utils.timezone
from django.conf import settings
import django.core.validators


class Migration(migrations.Migration):

    dependencies = [
        ('auth', '0006_require_contenttypes_0002'),
    ]

    operations = [
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('password', models.CharField(max_length=128, verbose_name='password')),
                ('last_login', models.DateTimeField(null=True, verbose_name='last login', blank=True)),
                ('is_superuser', models.BooleanField(default=False, help_text='Designates that this user has all permissions without explicitly assigning them.', verbose_name='superuser status')),
                ('username', models.CharField(error_messages={'unique': 'A user with that username already exists.'}, max_length=30, validators=[django.core.validators.RegexValidator('^[\\w.@+-]+$', 'Enter a valid username. This value may contain only letters, numbers and @/./+/-/_ characters.', 'invalid')], help_text='Required. 30 characters or fewer. Letters, digits and @/./+/-/_ only.', unique=True, verbose_name='username')),
                ('first_name', models.CharField(max_length=30, verbose_name='first name', blank=True)),
                ('last_name', models.CharField(max_length=30, verbose_name='last name', blank=True)),
                ('email', models.EmailField(max_length=254, verbose_name='email address', blank=True)),
                ('is_staff', models.BooleanField(default=False, help_text='Designates whether the user can log into this admin site.', verbose_name='staff status')),
                ('is_active', models.BooleanField(default=True, help_text='Designates whether this user should be treated as active. Unselect this instead of deleting accounts.', verbose_name='active')),
                ('date_joined', models.DateTimeField(default=django.utils.timezone.now, verbose_name='date joined')),
                ('phone_number', models.CharField(max_length=255)),
                ('name', models.CharField(max_length=255)),
                ('user_type', models.CharField(max_length=100)),
                ('last_login_date', models.DateTimeField(null=True, blank=True)),
                ('created_date', models.DateTimeField()),
                ('updated_date', models.DateTimeField()),
                ('deleted', models.BooleanField()),
                ('groups', models.ManyToManyField(related_query_name='user', related_name='user_set', to='auth.Group', blank=True, help_text='The groups this user belongs to. A user will get all permissions granted to each of their groups.', verbose_name='groups')),
                ('user_permissions', models.ManyToManyField(related_query_name='user', related_name='user_set', to='auth.Permission', blank=True, help_text='Specific permissions for this user.', verbose_name='user permissions')),
            ],
            options={
                'db_table': 'auth_user',
                'managed': True,
            },
            managers=[
                ('objects', django.contrib.auth.models.UserManager()),
            ],
        ),
        migrations.CreateModel(
            name='Address',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('building_number', models.CharField(max_length=30, null=True, blank=True)),
                ('apartments_number', models.CharField(max_length=20, null=True, blank=True)),
                ('additional_info', models.CharField(max_length=255, null=True, blank=True)),
                ('deleted', models.BooleanField()),
            ],
            options={
                'verbose_name': 'Address',
                'db_table': 'address',
                'managed': True,
                'verbose_name_plural': 'Addresses',
            },
        ),
        migrations.CreateModel(
            name='Attachment',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('comment_text', models.CharField(max_length=255)),
                ('create_date', models.DateTimeField()),
                ('file_type', models.CharField(max_length=100)),
                ('file_ext', models.CharField(max_length=10)),
            ],
            options={
                'verbose_name': 'Attachment',
                'db_table': 'attachments',
                'managed': True,
                'verbose_name_plural': 'Attachments',
            },
        ),
        migrations.CreateModel(
            name='City',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('city_name_key', models.CharField(max_length=255)),
                ('city_alias_name', models.CharField(max_length=255)),
                ('display', models.BooleanField()),
            ],
            options={
                'db_table': 'city',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='MenuItem',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('name', models.CharField(max_length=255)),
                ('description', models.CharField(max_length=255)),
                ('menu_item_created', models.DateTimeField()),
                ('available', models.BooleanField()),
                ('currency', models.CharField(max_length=3)),
                ('price', models.DecimalField(null=True, max_digits=16, decimal_places=0, blank=True)),
                ('deleted', models.BooleanField()),
            ],
            options={
                'db_table': 'menu_items',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='MenuItemComponent',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('name', models.CharField(max_length=255)),
                ('description', models.CharField(max_length=255)),
                ('price', models.DecimalField(null=True, max_digits=16, decimal_places=0, blank=True)),
                ('price_currency', models.CharField(max_length=3)),
                ('deleted', models.BooleanField()),
                ('required_ids', models.TextField(null=True, blank=True)),
                ('not_compatible_ids', models.TextField(null=True, blank=True)),
                ('menu_item', models.ForeignKey(related_name='menu_item_components', to='website.MenuItem')),
            ],
            options={
                'db_table': 'menu_item_components',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='MenuItemsGroup',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('name', models.CharField(max_length=255)),
                ('description', models.CharField(max_length=255)),
                ('deleted', models.BooleanField()),
            ],
            options={
                'db_table': 'menu_items_groups',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='Order',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('decline_message', models.CharField(max_length=255, null=True, blank=True)),
                ('deleted', models.BooleanField()),
                ('delivery_price_currency', models.CharField(max_length=3)),
                ('delivery_price', models.DecimalField(null=True, max_digits=16, decimal_places=0, blank=True)),
                ('total_menu_price_currency', models.CharField(max_length=3)),
                ('total_menu_price', models.DecimalField(null=True, max_digits=16, decimal_places=0, blank=True)),
                ('order_accepted', models.DateTimeField(null=True, blank=True)),
                ('order_closed', models.DateTimeField(null=True, blank=True)),
                ('order_cooked', models.DateTimeField(null=True, blank=True)),
                ('order_confirmed', models.DateTimeField(null=True, blank=True)),
                ('order_created', models.DateTimeField(null=True, blank=True)),
                ('order_delivered', models.DateTimeField(null=True, blank=True)),
                ('order_taken', models.DateTimeField(null=True, blank=True)),
                ('update_date', models.DateTimeField()),
                ('order_planed_cooked', models.TextField(null=True, blank=True)),
                ('order_planed_delivery_time', models.TextField(null=True, blank=True)),
                ('order_status', models.CharField(max_length=100)),
                ('payment_status', models.CharField(max_length=100)),
                ('confirmed_courier', models.ForeignKey(related_name='orders_as_courier', blank=True, to=settings.AUTH_USER_MODEL, null=True)),
                ('delivery_address', models.ForeignKey(related_name='orders', blank=True, to='website.Address', null=True)),
                ('order_owner', models.ForeignKey(related_name='orders_as_owner', to=settings.AUTH_USER_MODEL)),
            ],
            options={
                'db_table': 'order',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='OrderItem',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('count', models.IntegerField()),
                ('deleted', models.BooleanField()),
                ('total_order_item_price', models.DecimalField(null=True, max_digits=16, decimal_places=0, blank=True)),
                ('total_order_item_price_currency', models.CharField(max_length=3)),
                ('menu_item', models.ForeignKey(to='website.MenuItem')),
                ('order', models.ForeignKey(related_name='order_items', to='website.Order')),
            ],
            options={
                'db_table': 'order_items',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='Restaurant',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('title', models.CharField(max_length=255)),
                ('deleted', models.BooleanField()),
                ('show_on_index', models.BooleanField()),
                ('rating', models.IntegerField(null=True, blank=True)),
                ('device_login', models.CharField(max_length=255)),
                ('device_password', models.CharField(max_length=100)),
                ('last_ping', models.DateTimeField(null=True, blank=True)),
                ('discount', models.IntegerField()),
                ('two_letters', models.CharField(max_length=2)),
                ('address', models.ForeignKey(to='website.Address')),
            ],
            options={
                'db_table': 'restaurant',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='RestaurantDescription',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('lang', models.CharField(max_length=5)),
                ('description', models.CharField(max_length=255)),
                ('restaurant', models.ForeignKey(to='website.Restaurant')),
            ],
            options={
                'db_table': 'restaurant_descriptions',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='RestaurantsCategory',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('category_display_name_en', models.CharField(max_length=255)),
                ('category_display_name_ru', models.CharField(max_length=255, null=True, blank=True)),
                ('category_display_name_ua', models.CharField(max_length=255)),
            ],
            options={
                'db_table': 'restaurants_categories',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='RestaurantWorkhours',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('mon_start', models.TimeField()),
                ('mon_end', models.TimeField()),
                ('tue_start', models.TimeField()),
                ('tue_end', models.TimeField()),
                ('wed_start', models.TimeField()),
                ('wed_end', models.TimeField()),
                ('thu_start', models.TimeField()),
                ('thu_end', models.TimeField()),
                ('fri_start', models.TimeField()),
                ('fri_end', models.TimeField()),
                ('sat_start', models.TimeField()),
                ('sat_end', models.TimeField()),
                ('sun_start', models.TimeField()),
                ('sun_end', models.TimeField()),
            ],
            options={
                'db_table': 'restaurant_workhours',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='Street',
            fields=[
                ('street_id', models.AutoField(serialize=False, primary_key=True)),
                ('title_ua', models.CharField(max_length=255, null=True, blank=True)),
                ('title_en', models.CharField(max_length=255, null=True, blank=True)),
                ('title_ru', models.CharField(max_length=255, null=True, blank=True)),
                ('display', models.BooleanField()),
                ('city', models.ForeignKey(to='website.City')),
            ],
            options={
                'db_table': 'street',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='SystemSetting',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('field_stg_key', models.CharField(max_length=32, db_column='_stg_key')),
                ('field_stg_value', models.CharField(max_length=255, db_column='_stg_value')),
                ('is_default', models.BooleanField()),
                ('start_date', models.DateField(null=True, blank=True)),
                ('end_date', models.DateField(null=True, blank=True)),
            ],
            options={
                'db_table': 'system_settings',
                'managed': True,
            },
        ),
        migrations.AddField(
            model_name='restaurant',
            name='category',
            field=models.ForeignKey(to='website.RestaurantsCategory'),
        ),
        migrations.AddField(
            model_name='restaurant',
            name='city',
            field=models.ForeignKey(to='website.City'),
        ),
        migrations.AddField(
            model_name='restaurant',
            name='logo',
            field=models.ForeignKey(blank=True, to='website.Attachment', null=True),
        ),
        migrations.AddField(
            model_name='restaurant',
            name='user',
            field=models.ForeignKey(to=settings.AUTH_USER_MODEL),
        ),
        migrations.AddField(
            model_name='restaurant',
            name='work_hours',
            field=models.ForeignKey(to='website.RestaurantWorkhours'),
        ),
        migrations.AddField(
            model_name='order',
            name='restaurant',
            field=models.ForeignKey(related_name='orders', to='website.Restaurant'),
        ),
        migrations.AddField(
            model_name='menuitemsgroup',
            name='restaurant',
            field=models.ForeignKey(related_name='menu_items_groups', to='website.Restaurant'),
        ),
        migrations.AddField(
            model_name='menuitem',
            name='menu_item_group',
            field=models.ForeignKey(related_name='menu_items', to='website.MenuItemsGroup'),
        ),
        migrations.AddField(
            model_name='menuitem',
            name='restaurant',
            field=models.ForeignKey(related_name='menu_items', to='website.Restaurant'),
        ),
        migrations.AddField(
            model_name='address',
            name='city',
            field=models.ForeignKey(related_name='addresses', to='website.City'),
        ),
        migrations.AddField(
            model_name='address',
            name='street',
            field=models.ForeignKey(related_name='addresses', to='website.Street'),
        ),
    ]
