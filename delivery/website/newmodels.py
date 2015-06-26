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


class PlayEvolutions(models.Model):
    id = models.IntegerField(primary_key=True)
    hash = models.CharField(max_length=255)
    applied_at = models.DateTimeField()
    apply_script = models.TextField(blank=True, null=True)
    revert_script = models.TextField(blank=True, null=True)
    state = models.CharField(max_length=255, blank=True, null=True)
    last_problem = models.TextField(blank=True, null=True)

    class Meta:
        managed = True
        db_table = 'play_evolutions'


class VdAddress(models.Model):
    address_id = models.BigIntegerField(primary_key=True)
    buildingnumber = models.CharField(max_length=30, blank=True, null=True)
    apartmentsnumber = models.CharField(max_length=20, blank=True, null=True)
    additionalinfo = models.CharField(max_length=255, blank=True, null=True)
    deleted = models.BooleanField()
    city = models.ForeignKey('VdCity')
    street = models.ForeignKey('VdStreet')

    class Meta:
        managed = True
        db_table = 'vd_address'


class VdAttachments(models.Model):
    id = models.CharField(primary_key=True, max_length=38)
    commenttext = models.CharField(max_length=255)
    createdat = models.DateTimeField()
    filetype = models.CharField(max_length=100)
    fileext = models.CharField(max_length=10)

    class Meta:
        managed = True
        db_table = 'vd_attachments'


class VdCity(models.Model):
    city_id = models.AutoField(primary_key=True)
    citynamekey = models.CharField(max_length=255)
    cityaliasname = models.CharField(max_length=255)
    display = models.BooleanField()

    class Meta:
        managed = True
        db_table = 'vd_city'


class VdComments(models.Model):
    id = models.BigIntegerField(primary_key=True)
    commenttext = models.CharField(max_length=255)
    commonrating = models.IntegerField()
    commentedat = models.DateTimeField()
    status = models.CharField(max_length=100)
    showasanonymous = models.NullBooleanField()
    order = models.ForeignKey('VdOrder')

    class Meta:
        managed = True
        db_table = 'vd_comments'


class VdMenuItemComponents(models.Model):
    id = models.BigIntegerField(primary_key=True)
    name = models.CharField(max_length=255)
    description = models.CharField(max_length=255)
    price = models.DecimalField(max_digits=16, decimal_places=0, blank=True, null=True)
    price_currency = models.CharField(max_length=3)
    deleted = models.BooleanField()
    requiredids = models.TextField(blank=True, null=True)  # This field type is a guess.
    notcompatibleids = models.TextField(blank=True, null=True)  # This field type is a guess.
    menu_item = models.ForeignKey('VdMenuItems')

    class Meta:
        managed = True
        db_table = 'vd_menu_item_components'


class VdMenuItems(models.Model):
    id = models.BigIntegerField(primary_key=True)
    name = models.CharField(max_length=255)
    description = models.CharField(max_length=255)
    menuitemcreated = models.DateTimeField()
    available = models.BooleanField()
    currency = models.CharField(max_length=3)
    price = models.DecimalField(max_digits=16, decimal_places=0, blank=True, null=True)
    deleted = models.BooleanField()
    restaurant = models.ForeignKey('VdRestaurant')
    menu_item_group = models.ForeignKey('VdMenuItemsGroups')

    class Meta:
        managed = True
        db_table = 'vd_menu_items'


class VdMenuItemsGroups(models.Model):
    name = models.CharField(max_length=255)
    description = models.CharField(max_length=255)
    deleted = models.BooleanField()
    restaurant = models.ForeignKey('VdRestaurant')

    class Meta:
        managed = True
        db_table = 'vd_menu_items_groups'


class VdOrder(models.Model):
    id = models.BigIntegerField(primary_key=True)
    declinemessage = models.CharField(max_length=255, blank=True, null=True)
    deleted = models.BooleanField()
    deliveryprice_currency = models.CharField(max_length=3)
    deliveryprice = models.DecimalField(max_digits=16, decimal_places=0, blank=True, null=True)
    totalmenuprice_currency = models.CharField(max_length=3)
    totalmenuprice = models.DecimalField(max_digits=16, decimal_places=0, blank=True, null=True)
    orderaccepted = models.DateTimeField(blank=True, null=True)
    orderclosed = models.DateTimeField(blank=True, null=True)
    ordercooked = models.DateTimeField(blank=True, null=True)
    orderconfirmed = models.DateTimeField(blank=True, null=True)
    ordercreated = models.DateTimeField(blank=True, null=True)
    orderdelivered = models.DateTimeField(blank=True, null=True)
    ordertaken = models.DateTimeField(blank=True, null=True)
    updatedat = models.DateTimeField()
    orderplanedcooked = models.TextField(blank=True, null=True)  # This field type is a guess.
    orderplaneddeliverytime = models.TextField(blank=True, null=True)  # This field type is a guess.
    orderstatus = models.CharField(max_length=100)
    paymentstatus = models.CharField(max_length=100)
    delivery_address = models.ForeignKey(VdAddress, blank=True, null=True)
    confirmed_courier = models.ForeignKey('VdUser', blank=True, null=True)
    order_owner = models.ForeignKey('VdUser')
    restaurant = models.ForeignKey('VdRestaurant')

    class Meta:
        managed = True
        db_table = 'vd_order'


class VdOrderItems(models.Model):
    id = models.BigIntegerField(primary_key=True)
    count = models.IntegerField()
    deleted = models.BooleanField()
    totalorderitemprice = models.DecimalField(max_digits=16, decimal_places=0, blank=True, null=True)
    totalorderitemprice_currency = models.CharField(max_length=3)
    menu_item = models.ForeignKey(VdMenuItems)
    order = models.ForeignKey(VdOrder)

    class Meta:
        managed = True
        db_table = 'vd_order_items'


class VdRestaurant(models.Model):
    title = models.CharField(max_length=255)
    deleted = models.BooleanField()
    showonindex = models.BooleanField()
    raiting = models.IntegerField(blank=True, null=True)
    devicelogin = models.CharField(max_length=255)
    devicepassword = models.CharField(max_length=100)
    lastping = models.DateTimeField(blank=True, null=True)
    discount = models.IntegerField()
    twoletters = models.CharField(max_length=2)
    city = models.ForeignKey(VdCity)
    address = models.ForeignKey(VdAddress)
    category = models.ForeignKey('VdRestaurantsCategories')
    workhours = models.ForeignKey('VdRestaurantWorkhours')
    user = models.ForeignKey('VdUser')
    logo = models.ForeignKey(VdAttachments, blank=True, null=True)

    class Meta:
        managed = True
        db_table = 'vd_restaurant'


class VdRestaurantDescriptions(models.Model):
    lang = models.CharField(max_length=5)
    description = models.CharField(max_length=255)
    restaurant = models.ForeignKey(VdRestaurant)

    class Meta:
        managed = True
        db_table = 'vd_restaurant_descriptions'


class VdRestaurantWorkhours(models.Model):
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
        db_table = 'vd_restaurant_workhours'


class VdRestaurantsCategories(models.Model):
    categorydisplaynameen = models.CharField(max_length=255)
    categorydisplaynameru = models.CharField(max_length=255, blank=True, null=True)
    categorydisplaynameua = models.CharField(max_length=255)

    class Meta:
        managed = True
        db_table = 'vd_restaurants_categories'


class VdStreet(models.Model):
    street_id = models.AutoField(primary_key=True)
    title_ua = models.CharField(max_length=255, blank=True, null=True)
    title_en = models.CharField(max_length=255, blank=True, null=True)
    title_ru = models.CharField(max_length=255, blank=True, null=True)
    display = models.BooleanField()
    city = models.ForeignKey(VdCity)

    class Meta:
        managed = True
        db_table = 'vd_street'


class VdSystemSettings(models.Model):
    field_stg_key = models.CharField(db_column='_stg_key', max_length=32)  # Field renamed because it started with '_'.
    field_stg_value = models.CharField(db_column='_stg_value', max_length=255)  # Field renamed because it started with '_'.
    isdefault = models.BooleanField()
    startdate = models.DateField(blank=True, null=True)
    enddate = models.DateField(blank=True, null=True)

    class Meta:
        managed = True
        db_table = 'vd_system_settings'


class VdUser(models.Model):
    id = models.CharField(primary_key=True, max_length=38)
    login = models.CharField(max_length=255)
    email = models.CharField(max_length=255, blank=True, null=True)
    phonenumber = models.CharField(max_length=255)
    password = models.CharField(max_length=255)
    name = models.CharField(max_length=255)
    usertype = models.CharField(max_length=100)
    lastlogindate = models.DateTimeField(blank=True, null=True)
    createddate = models.DateTimeField()
    updateddate = models.DateTimeField()
    deleted = models.BooleanField()

    class Meta:
        managed = True
        db_table = 'vd_user'
