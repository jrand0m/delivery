from django.shortcuts import render_to_response
from models import User
from rest_framework import viewsets
from serializers import UserSerializer


# Create your views here.

def yoda(request):
    return render_to_response('yoda.html')

def for_restaurants(request):
    return render_to_response("Static/forRestaurants.html")

def google(request):
    return render_to_response("Static/google9049961bc731c473.html")



class UserViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = User.objects.all()
    serializer_class = UserSerializer
