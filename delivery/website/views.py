from django.shortcuts import render_to_response
from django.http import HttpResponse


# Create your views here.

def index(request):
    return HttpResponse('Hello world!')

def indexM(request):
    return render_to_response('main.html', {'test':':)'})
