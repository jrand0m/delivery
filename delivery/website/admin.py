from django.contrib import admin
from .models import *

# Register your models here.

class AddressAdmin(admin.ModelAdmin):
    pass


class AttachmentAdmin(admin.ModelAdmin):
    pass


admin.site.register(Address, AddressAdmin)
admin.site.register(Attachment, AttachmentAdmin)
