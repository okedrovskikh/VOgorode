#!/bin/sh
set -e
psql -v ON_ERROR_STOP=1 --username "postgres" <<-EOSQL
  alter system set shared_preload_libraries='pg_stat_statements';
  create extension pg_stat_statements;
EOSQL
