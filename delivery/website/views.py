from django.shortcuts import render_to_response
from django.http import HttpResponse


# Create your views here.

def index(request):
    return render_to_response("index.html")

def yoda(request):
    return render_to_response('yoda.html')


def for_restaurants(request):
    return render_to_response("Static/forRestaurants.html")
def google(request):
    return render_to_response("Static/google9049961bc731c473.html")
