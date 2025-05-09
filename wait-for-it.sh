#!/bin/bash
# wait-for-it.sh script to wait for the database to be ready

set -e

host="$1"
port="$2"
shift 2
cmd="$@"

until mysqladmin ping -h"$host" -P"$port" -u"root" -p"Mike2000" --silent; do
  >&2 echo "MySQL is unavailable - sleeping"
  sleep 1
done

>&2 echo "MySQL is up - executing command"
exec $cmd