from django.contrib import admin
from django.contrib.auth.admin import UserAdmin as NativeUserAdmin
from django.utils.translation import ugettext_lazy as _
from .models import *

# Register your models here.

class AddressAdmin(admin.ModelAdmin):
    pass


class AttachmentAdmin(admin.ModelAdmin):
    pass


class CityAdmin(admin.ModelAdmin):
    pass


class MenuItemComponentAdmin(admin.ModelAdmin):
    pass


class MenuItemAdmin(admin.ModelAdmin):
    pass


class MenuItemsGroupAdmin(admin.ModelAdmin):
    pass


class OrderAdmin(admin.ModelAdmin):
    pass


class OrderItemAdmin(admin.ModelAdmin):
    pass


class RestaurantAdmin(admin.ModelAdmin):
    pass


class RestaurantsDescriptionAdmin(admin.ModelAdmin):
    pass


class RestaurantWorkhoursAdmin(admin.ModelAdmin):
    pass


class RestaurantsCategoryAdmin(admin.ModelAdmin):
    pass


class StreetAdmin(admin.ModelAdmin):
    pass


class SytemSettingAdmin(admin.ModelAdmin):
    pass


class UserAdmin(NativeUserAdmin):
    fieldsets = (
        (None, {'fields': ('username', 'password')}),
        # (_('Project team info'),
        #  {'fields': ('in_project_team', 'title', 'description', 'photo', 'team_order', 'steam_account_id')}),
        (_('Personal info'), {'fields': ('first_name', 'last_name', 'email')}),
        (_('Permissions'), {'fields': ('is_active', 'is_staff', 'is_superuser',
                                       'groups', 'user_permissions')}),
        (_('Important dates'), {'fields': ('last_login', 'date_joined')})
    )
class TeamMateAdmin(admin.ModelAdmin):
    pass
admin.site.register(Address, AddressAdmin)
admin.site.register(Attachment, AttachmentAdmin)
admin.site.register(City, CityAdmin)
admin.site.register(MenuItemComponent, MenuItemComponentAdmin)
admin.site.register(MenuItem, MenuItemAdmin)
admin.site.register(MenuItemsGroup, MenuItemsGroupAdmin)
admin.site.register(Order, OrderAdmin)
admin.site.register(OrderItem, OrderItemAdmin)
admin.site.register(Restaurant, RestaurantAdmin)
admin.site.register(RestaurantDescription, RestaurantsDescriptionAdmin)
admin.site.register(RestaurantWorkhours, RestaurantWorkhoursAdmin)
admin.site.register(RestaurantCategory, RestaurantsCategoryAdmin)
admin.site.register(Street, StreetAdmin)
admin.site.register(SystemSetting, SytemSettingAdmin)
admin.site.register(User, UserAdmin)
admin.site.register(TeamMate, TeamMateAdmin)
