MANAGE_PY = ./manage.py
NPM_REQS = $(shell cat npm-requirements.txt)
NPM_OPTS = --prefix .
GRUNT = node ./node_modules/.bin/grunt
export NPM_CONFIG_PREFIX

init: | createuser update migrate

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

static:
	$(GRUNT) less

watch:
	$(GRUNT) less
	$(GRUNT) watch
