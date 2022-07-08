# Reproducer for Redisson issue with env variables

### Steps to reproduce:

* set environment variable to override property set in application.properties:
  `export QUARKUS_REDISSON_SINGLESERVERCONFIG_PASSWORD=mbdev`
* set breakpoint to RedisURI.class, line 28
* run `quarkus dev --suspend`, attach debugger
* observe that variable URI is null, even though the address is set in the application.properties
* apparently overriding a property via an env variable overrides ALL Redisson properties, those
  not passes as env variable too are treated as null.