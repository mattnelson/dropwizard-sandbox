package io.mattnelson;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.mattnelson.resources.LinkFilter;
import io.mattnelson.resources.LinkResource;
import io.mattnelson.resources.RedirectFilter;
import io.mattnelson.resources.RedirectServlet;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RewriteRegexRule;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletMapping;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;

public class JettyContextPathApplication extends Application<JettyContextPathConfiguration> {

    public static void main(final String[] args) throws Exception {
        new JettyContextPathApplication().run(args);
    }

    @Override
    public String getName() {
        return "JettyContextPath";
    }

    @Override
    public void initialize(final Bootstrap<JettyContextPathConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final JettyContextPathConfiguration configuration,
                    final Environment environment) {
        if (configuration.contextPath) {
            System.out.println("Running in context path mode");
            environment.getApplicationContext().setContextPath("/alias");
        } else if (configuration.servletMapping) {
            System.out.println("Running in servlet mapping mode");
            final ServletMapping servletMapping = new ServletMapping();
            servletMapping.setServletName("jersey");
            servletMapping.setPathSpec("/alias/*");
            environment.getApplicationContext().getServletHandler().addServletMapping(servletMapping);

            final FilterMapping filterMapping = new FilterMapping();
            filterMapping.setServletName("jersey");
            filterMapping.setPathSpec("/alias/*");
            environment.getApplicationContext().getServletHandler().addFilterMapping(filterMapping);
        } else if (configuration.contextHandler) {
            System.out.println("Running in context handler mode");
            ContextHandler context = new ContextHandler("/alias");
            context.setHandler(environment.getApplicationContext().getServletHandler());

            final ContextHandlerCollection contexts =
                    new ContextHandlerCollection(environment.getApplicationContext(), context);
            environment.getApplicationContext().setHandler(contexts);
        } else if (configuration.handlerWrapper) {
            System.out.println("Running in handler wrapper mode");
            environment.getApplicationContext().insertHandler(new ContextServletHandler());
        } else if (configuration.redirectFilter) {
            System.out.println("Running in redirect filter mode");
            environment.servlets().addFilter("redirect", RedirectFilter.class)
                    .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*");
        } else if (configuration.redirectServlet) {
            System.out.println("Running in redirect servlet mode");
            environment.servlets().addServlet("redirect", new RedirectServlet((ServletContainer) environment.getJerseyServletContainer()))
                    .addMapping("/alias/*");
        } else if (configuration.rewrite) {
            RewriteHandler rewriteHandler = new RewriteHandler();
            rewriteHandler.addRule(new RewriteRegexRule("/alias/link", "/link"));
            environment.getApplicationContext().insertHandler(rewriteHandler);
        }

        environment.servlets().addFilter("link", LinkFilter.class)
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/link");
        environment.jersey().register(LinkResource.class);
    }

    static class ContextServletHandler extends HandlerWrapper {
        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            System.out.println("executed ContextServletHandler");
            baseRequest.setContextPath("beans");
            baseRequest.setContext(null);
            super.handle(target, baseRequest, request, response);
        }
    }
}
