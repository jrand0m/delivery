from django.shortcuts import render_to_response
from django.http import HttpResponse


# Create your views here.

def index(request):
    return render_to_response("index.html")

def indexM(request):
    return render_to_response('main.html', {'test':':)'})

def yoda(request):
    return render_to_response('yoda.html')
def forRestaurants():
    return render_to_response("Static/forRestaurants.html")

