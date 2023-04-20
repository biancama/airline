#!/bin/sh
set -o allexport && source ./.env && set +o allexport && sbt core/run