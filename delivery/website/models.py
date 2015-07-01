# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Remove `managed = True` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
#
# Also note: You'll have to insert the output of 'django-admin sqlcustom [app_label]'
# into your database.
from __future__ import unicode_literals
from django.db import models
from django.utils.translation import ugettext_lazy as _
from django.contrib.auth.models import AbstractUser

class Address(models.Model):
    building_number = models.CharField(max_length=30, blank=True, null=True)
    apartments_number = models.CharField(max_length=20, blank=True, null=True)
    additional_info = models.CharField(max_length=255, blank=True, null=True)
    deleted = models.BooleanField()
    city = models.ForeignKey('City', related_name='addresses')
    street = models.ForeignKey('Street', related_name='addresses')

    class Meta:
        managed = True
        db_table = 'address'
        verbose_name = _('Address')
        verbose_name_plural = _('Addresses')

    def __unicode__(self):
        return u'[Address: {}, {}/{} {}]'.format(self.city, self.building_number, self.apartments_number, self.street)


class Attachment(models.Model):
    comment_text = models.CharField(max_length=255)
    create_date = models.DateTimeField()
    file_type = models.CharField(max_length=100)
    file_ext = models.CharField(max_length=10)

    class Meta:
        managed = True
        db_table = 'attachments'
        verbose_name = _('Attachment')
        verbose_name_plural = _('Attachments')

    def __unicode__(self):
        return u'[Attachment: {} / {}]'.format(self.file_type, self.file_ext)


class City(models.Model):
    city_name_key = models.CharField(max_length=255)
    city_alias_name = models.CharField(max_length=255)
    display = models.BooleanField()

    class Meta:
        managed = True
        db_table = 'city'
        verbose_name = _('City')
        verbose_name_plural = _('Cities')

    def __unicode__(self):
        return u'[City: {}]'.format(self.city_alias_name)


class MenuItemComponent(models.Model):
    name = models.CharField(max_length=255)
    description = models.CharField(max_length=255)
    price = models.DecimalField(max_digits=16, decimal_places=0, blank=True, null=True)
    price_currency = models.CharField(max_length=3)
    deleted = models.BooleanField()
    required_ids = models.TextField(blank=True, null=True)  # This field type is a guess.
    not_compatible_ids = models.TextField(blank=True, null=True)  # This field type is a guess.
    menu_item = models.ForeignKey('MenuItem', related_name='menu_item_components')

    class Meta:
        managed = True
        db_table = 'menu_item_components'
        verbose_name = _('Menu item component')
        verbose_name_plural = _('Menu item components')

    def __unicode__(self):
        return u'[MenuItemComponent: {} for {} {}]'.format(self.name, self.price, self.price_currency)


class MenuItem(models.Model):
    name = models.CharField(max_length=255)
    description = models.CharField(max_length=255)
    menu_item_created = models.DateTimeField()
    available = models.BooleanField()
    currency = models.CharField(max_length=3)
    price = models.DecimalField(max_digits=16, decimal_places=0, blank=True, null=True)
    deleted = models.BooleanField()
    restaurant = models.ForeignKey('Restaurant', related_name='menu_items')
    menu_item_group = models.ForeignKey('MenuItemsGroup', related_name='menu_items')

    class Meta:
        managed = True
        db_table = 'menu_items'
        verbose_name = _('Menu item')
        verbose_name_plural = _('Menu items')

    def __unicode__(self):
        return u'[MenuItem: {} for {} {}]'.format(self.name, self.price, self.currency)


class MenuItemsGroup(models.Model):
    name = models.CharField(max_length=255)
    description = models.CharField(max_length=255)
    deleted = models.BooleanField()
    restaurant = models.ForeignKey('Restaurant', related_name='menu_items_groups')

    class Meta:
        managed = True
        db_table = 'menu_items_groups'
        verbose_name = _('Menu item group')
        verbose_name_plural = _('Menu item groups')

    def __unicode__(self):
        return u'[MenuItemsGroup: {}]'.format(self.name)


class Order(models.Model):
    decline_message = models.CharField(max_length=255, blank=True, null=True)
    deleted = models.BooleanField()
    delivery_price_currency = models.CharField(max_length=3)
    delivery_price = models.DecimalField(max_digits=16, decimal_places=0, blank=True, null=True)
    total_menu_price_currency = models.CharField(max_length=3)
    total_menu_price = models.DecimalField(max_digits=16, decimal_places=0, blank=True, null=True)
    order_accepted = models.DateTimeField(blank=True, null=True)
    order_closed = models.DateTimeField(blank=True, null=True)
    order_cooked = models.DateTimeField(blank=True, null=True)
    order_confirmed = models.DateTimeField(blank=True, null=True)
    order_created = models.DateTimeField(blank=True, null=True)
    order_delivered = models.DateTimeField(blank=True, null=True)
    order_taken = models.DateTimeField(blank=True, null=True)
    update_date = models.DateTimeField()
    order_planed_cooked = models.TextField(blank=True, null=True)  # This field type is a guess.
    order_planed_delivery_time = models.TextField(blank=True, null=True)  # This field type is a guess.
    order_status = models.CharField(max_length=100)
    payment_status = models.CharField(max_length=100)
    delivery_address = models.ForeignKey(Address, blank=True, null=True, related_name='orders')
    confirmed_courier = models.ForeignKey('User', blank=True, null=True, related_name='orders_as_courier')
    order_owner = models.ForeignKey('User', related_name='orders_as_owner')
    restaurant = models.ForeignKey('Restaurant', related_name='orders')

    class Meta:
        managed = True
        db_table = 'order'
        verbose_name = _('Order')
        verbose_name_plural = _('Orders')

    def __unicode__(self):
        return u'[Order: {} @ {}]'.format(self.order_status, self.restaurant)


class OrderItem(models.Model):
    count = models.IntegerField()
    deleted = models.BooleanField()
    total_order_item_price = models.DecimalField(max_digits=16, decimal_places=0, blank=True, null=True)
    total_order_item_price_currency = models.CharField(max_length=3)
    menu_item = models.ForeignKey('MenuItem')
    order = models.ForeignKey(Order, related_name='order_items')

    class Meta:
        managed = True
        db_table = 'order_items'
        verbose_name = _('Order item')
        verbose_name_plural = _('Order items')

    def __unicode__(self):
        return u'[OrderItem: {} {} for {} {}]'.format(self.count, self.menu_item, self.total_order_item_price, self.total_order_item_price_currency)


class Restaurant(models.Model):
    title = models.CharField(max_length=255)
    deleted = models.BooleanField()
    show_on_index = models.BooleanField()
    rating = models.IntegerField(blank=True, null=True)
    device_login = models.CharField(max_length=255)
    device_password = models.CharField(max_length=100)
    last_ping = models.DateTimeField(blank=True, null=True)
    discount = models.IntegerField()
    two_letters = models.CharField(max_length=2)
    city = models.ForeignKey(City)
    address = models.ForeignKey(Address)
    category = models.ForeignKey('RestaurantCategory')
    work_hours = models.ForeignKey('RestaurantWorkhours')
    user = models.ForeignKey('User')
    logo = models.ForeignKey('Attachment', blank=True, null=True)

    class Meta:
        managed = True
        db_table = 'restaurant'
        verbose_name = _('Restaurant')
        verbose_name_plural = _('Restaurants')

    def __unicode__(self):
        return u'[Restaurant: {} @ {}]'.format(self.title, self.address)


class RestaurantDescription(models.Model):
    lang = models.CharField(max_length=5)
    description = models.CharField(max_length=255)
    restaurant = models.ForeignKey(Restaurant)

    class Meta:
        managed = True
        db_table = 'restaurant_descriptions'
        verbose_name = _('Restaurant description')
        verbose_name_plural = _('Restaurant descriptions')

    def __unicode__(self):
        return u'[RestaurantDescription: {} for {}]'.format(self.description, self.restaurant)


class RestaurantWorkhours(models.Model):
    mon_start = models.TimeField()
    mon_end = models.TimeField()
    tue_start = models.TimeField()
    tue_end = models.TimeField()
    wed_start = models.TimeField()
    wed_end = models.TimeField()
    thu_start = models.TimeField()
    thu_end = models.TimeField()
    fri_start = models.TimeField()
    fri_end = models.TimeField()
    sat_start = models.TimeField()
    sat_end = models.TimeField()
    sun_start = models.TimeField()
    sun_end = models.TimeField()

    class Meta:
        managed = True
        db_table = 'restaurant_workhours'
        verbose_name = _('Restaurant workhours')
        verbose_name_plural = _('Restaurant workhours')

    def __unicode__(self):
        keys = ('mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun')
        pairs = [
            (getattr(self, '{}_start'.format(key)), getattr(self, '{}_end'.format(key))) for key in keys
        ]
        return u'[RestaurantWorkhours: {}]'.format(
            ', '.join(['-'.join([d.strftime('%H:%M') for d in pair]) for pair in pairs])
        )


class RestaurantCategory(models.Model):
    category_display_name_en = models.CharField(max_length=255)
    category_display_name_ru = models.CharField(max_length=255, blank=True, null=True)
    category_display_name_ua = models.CharField(max_length=255)

    class Meta:
        managed = True
        db_table = 'restaurant_categories'
        verbose_name = _('Restaurant category')
        verbose_name_plural = _('Restaurant categories')

    def __unicode__(self):
        return u'[RestaurantCategory: {}]'.format(self.category_display_name_ua)


class Street(models.Model):
    street_id = models.AutoField(primary_key=True)
    title_ua = models.CharField(max_length=255, blank=True, null=True)
    title_en = models.CharField(max_length=255, blank=True, null=True)
    title_ru = models.CharField(max_length=255, blank=True, null=True)
    display = models.BooleanField()
    city = models.ForeignKey(City)

    class Meta:
        managed = True
        db_table = 'street'
        verbose_name = _('Street')
        verbose_name_plural = _('Streets')

    def __unicode__(self):
        return u'[Street: {}]'.format(self.title_ua)


class SystemSetting(models.Model):
    field_stg_key = models.CharField(db_column='_stg_key', max_length=32)  # Field renamed because it started with '_'.
    field_stg_value = models.CharField(db_column='_stg_value', max_length=255)  # Field renamed because it started with '_'.
    is_default = models.BooleanField()
    start_date = models.DateField(blank=True, null=True)
    end_date = models.DateField(blank=True, null=True)

    class Meta:
        managed = True
        db_table = 'system_settings'
        verbose_name = _('System setting')
        verbose_name_plural = _('System settings')

    def __unicode__(self):
        return u'[SystemSetting: {} = {}]'.format(self.field_stg_key, self.field_stg_value)


class User(AbstractUser):
    phone_number = models.CharField(max_length=255)
    name = models.CharField(max_length=255)
    user_type = models.CharField(max_length=100)
    last_login_date = models.DateTimeField(blank=True, null=True)
    updated_date = models.DateTimeField(null=True)
    deleted = models.BooleanField(default=False)

    USERNAME_FIELD = 'username'

    class Meta:
        managed = True
        db_table = 'auth_user'
        verbose_name = _('User')
        verbose_name_plural = _('Users')

    def __unicode__(self):
        return u'[User: {} ({} {})]'.format(self.username, self.first_name, self.last_name)
