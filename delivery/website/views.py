from django.shortcuts import render
from django.http import HttpResponse


# Create your views here.

def index(request):
    return render(request, "index.html")


def yoda(request):
    return render(request, 'yoda.html')


def for_restaurants(request):
    return render(request, "Static/forRestaurants.html")


def google(request):
    return render(request, "Static/google9049961bc731c473.html")
