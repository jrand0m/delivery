MANAGE_PY = ./manage.py
NPM_REQS = $(shell cat npm-requirements.txt)
NPM_OPTS = --prefix .
GRUNT = node ./node_modules/.bin/grunt
export NPM_CONFIG_PREFIX

define BOOTSTRAP_SQL
CREATE DATABASE vdoma;\nCREATE USER vdoma WITH password 'vdoma';\nGRANT ALL privileges ON DATABASE vdoma TO vdoma;\n
endef
define SAMPLE_LOCAL_CONFIG
#!/bin/python2\nDEBUG = True\n\nDATABASES ={\n'default':{\n'ENGINE': 'django.db.backends.postgresql_psycopg2',\n'NAME': 'vdoma',\n'USER': 'vdoma',\n'PASSWORD': 'vdoma',\n'HOST': '127.0.0.1',\n'PORT': '5432',\n}\n}
endef

initUbuntu: 
	@echo -e '$(YELLOW)Installing ubuntu dependencies$(RESET)'	
	sudo apt-get install postgresql libpq-dev python-dev npm python-pip
	@echo -e '$(YELLOW)init database$(RESET)'
	echo -e "$(BOOTSTRAP_SQL)" > tmp.sql
	sudo -u postgres psql -f tmp.sql
	rm -rfv tmp.sql
	@echo -e '$(YELLOW)create simple local properties file$(RESET)'
	echo -e "$(SAMPLE_LOCAL_CONFIG)" > delivery/settings/local_settings.py

init: | update migrate createuser 

createuser:
	$(MANAGE_PY) createsuperuser --user admin --email admin@localhost

update:
	@echo -e '$(YELLOW)Installing PIP requirements$(RESET)'
	pip install -r requirements.txt
	@echo -e '$(YELLOW)Installing NPM requirements$(RESET)'
	@for FNAME in $(NPM_REQS) ; do \
	npm list $$FNAME > /dev/null ; \
		if [  "$$?" -ne "0" ] ; then \
			echo -e "$(YELLOW) * npm install $$FNAME$(RESET)" ; \
			npm install $$FNAME ; \
		else \
			echo -e "$(YELLOW) * Package $$FNAME already installed.$(RESET)" ; \
		fi; \
	done
	@echo -e '$(GREEN)Full update complete.$(RESET)'

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

less:
	$(GRUNT) less

watch:
	$(GRUNT) less
	$(GRUNT) watch

static: | less
