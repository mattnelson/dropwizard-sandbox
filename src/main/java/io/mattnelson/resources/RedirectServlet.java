package io.mattnelson.resources;

import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

/** A servlet which redirects requests to underlying container */
public class RedirectServlet extends ServletContainer {

    private final ServletContainer container;

    public RedirectServlet(final ServletContainer container) {
        this.container = container;
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        container.service(request, response);
    }

    @Override
    public void service(final ServletRequest request, final ServletResponse response)
            throws ServletException, IOException {
        container.service(request, response);
    }

    @Override
    public Value<Integer> service(
            final URI baseUri,
            final URI requestUri,
            final HttpServletRequest request,
            final HttpServletResponse response)
            throws ServletException, IOException {
        return container.service(baseUri, requestUri, request, response);
    }

    @Override
    public void doFilter(
            final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        container.doFilter(request, response, chain);
    }

    @Override
    public void doFilter(
            final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        container.doFilter(request, response, chain);
    }
}
