# JettyContextPath

Minimal example of multiple context paths with jetty/dropwizard

There a few approaches implemented.
* contextPath
* servletMapping
* contextHandler
* handlerWrapper
* redirect
---

Each modes can be enabled in the `config.yml` or supplied via a command line arg `-Ddw.redirect=true`

1. Run `mvn clean package` to build your application
1. Start application with `java -jar target/jetty-context-path-1.0-SNAPSHOT.jar server config.yml`
1. Check that `http://localhost:8080/alias/link` return the same URL
1. Check that `http://localhost:8080/link` return the same URL
