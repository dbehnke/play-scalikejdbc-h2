play-scalikejdbc-h2
===================

This is a demo app to of Play 2.2, Scalikejdbc and H2 all working together

How it works
------------

The demo app has a simple Remote controller that serves the H2 help via JSON - it uses basic authentication


Running the Demo
----------------

1. Launch like any other play app (install Play first and include it in your path)

```sh
play run
```
  
2. Use curl to test json output

```sh
curl http://localhost:9000/test -v -u admin:cheesefinger
```
