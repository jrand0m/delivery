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
	sudo apt-get install postgresql libpq-dev python-dev npm python-pip python-virtualenv
	@echo -e '$(YELLOW)init database$(RESET)'
	echo "$(BOOTSTRAP_SQL)" > tmp.sql
	sudo -u postgres psql -f tmp.sql
	rm -rfv tmp.sql
	@echo -e '$(YELLOW)create simple local properties file$(RESET)'
	echo "$(SAMPLE_LOCAL_CONFIG)" > delivery/settings/local_settings.py

initOsx:
	@echo -e '$(YELLOW)Installing OSX dependencies$(RESET)'
	brew install npm wget python postgres pyenv-virtualenv
	ln -sfv /usr/local/opt/postgresql/*.plist ~/Library/LaunchAgents
	launchctl load ~/Library/LaunchAgents/homebrew.mxcl.postgresql.plist
	@echo -e '$(YELLOW)Create virtual env (:trollface:) $(RESET)'
	#pyenv virtualenv .env
	@echo -e '$(YELLOW)init database$(RESET)'
	echo "$(BOOTSTRAP_SQL)" > tmp.sql
	psql -f tmp.sql
	rm -rfv tmp.sql
	@echo -e '$(YELLOW)create simple local properties file$(RESET)'
	echo "$(SAMPLE_LOCAL_CONFIG)" > delivery/settings/local_settings.py

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
pipupgrade:|pipdistupdate
	pip freeze --local | grep -v '^\-e' | cut -d = -f 1  | xargs -n1 pip install -U
	pip freeze > requirements.txt

pipdistupdate:
	pip install --upgrade distribute
wtf: | update migrate killcache

less:
	$(GRUNT) less

watch:
	$(GRUNT) less
	$(GRUNT) watch

static: | less
