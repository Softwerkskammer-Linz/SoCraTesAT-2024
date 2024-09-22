.DEFAULT_GOAL := all
SHELL := bash
.ONESHELL:
.SHELLFLAGS := -eu -o pipefail -c
.DELETE_ON_ERROR:
MAKEFLAGS += --warn-undefined-variables
MAKEFLAGS += --no-builtin-rules

EXCLUDES ?=
JSON:= $(filter-out $(EXCLUDES),$(shell find -type f -name '*.json' | sed 's: :\\ :g'))
MP4 := $(patsubst %.json,%.mp4,$(JSON))
PNG := $(patsubst %.json,%.png,$(JSON))

.PHONY: help
help: # see https://diamantidis.github.io/tips/2020/07/01/list-makefile-targets
	@( grep -E '^[\a-zA-Z0-9_%%.-]+:.*?## .*$$' $(MAKEFILE_LIST) || true ) \
	| sed -n 's/^\(.*\): \(.*\)##\(.*\)/\1|\3/p' \
	| column -t  -s '|'

.PHONY: clean
clean: ## Clean up generated artifacts
	#rm -f *.json *.mp4

%.mp4: %.json
	echo ~/.bin/record_screencast -t /bin/bash -n $(patsubst %.mp4,%,$<)

%.png: %.mp4 ## Generates stills from videos
	ffmpeg -i $< -ss 00:00:05 -vframes 1 $@

all: $(MP4) $(PNG)
