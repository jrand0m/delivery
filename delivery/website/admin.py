from django.contrib import admin
from django.contrib.auth.admin import UserAdmin as NativeUserAdmin
from django.utils.translation import ugettext_lazy as _
from .models import *

# Register your models here.

class AddressAdmin(admin.ModelAdmin):
    pass


class AttachmentAdmin(admin.ModelAdmin):
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

admin.site.register(Address, AddressAdmin)
admin.site.register(Attachment, AttachmentAdmin)
admin.site.register(User, UserAdmin)
