package io.mattnelson.resources;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RedirectFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String originalURI = ((HttpServletRequest)request).getRequestURI();
        final Matcher matcher = Pattern.compile("^(?<context>/alias)/?.*").matcher(originalURI);
        if (matcher.matches()) {
            final String newURI = originalURI.replace(matcher.group("context"), "");
            final RequestDispatcher dispatch = request.getRequestDispatcher(newURI);
            dispatch.forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
