MANAGE_PY = ./manage.py

init: | createuser update migrate

createuser:
	$(MANAGE_PY) createsuperuser --user admin --email admin@localhost

update:
	pip install -r requirements.txt

run:
	$(MANAGE_PY) runserver 0.0.0.0:8000

migrate:
	$(MANAGE_PY) migrate

shell:
	$(MANAGE_PY) shell

test:
#	DJANGO_SETTINGS_MODULE=westrampage.settings.test
	$(MANAGE_PY) test -v 3 --keepdb

newtest:
	DJANGO_SETTINGS_MODULE=westrampage.settings.test $(MANAGE_PY) test -v 3

killcache:
	find . -name *.py[co] -exec rm {} \;

wtf: | update migrate killcache
