package io.mattnelson.resources;

import javax.servlet.*;
import java.io.IOException;

public class LinkFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("executed LinkFilter");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
