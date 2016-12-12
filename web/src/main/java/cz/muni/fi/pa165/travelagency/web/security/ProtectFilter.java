
package cz.muni.fi.pa165.travelagency.web.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
@WebFilter(urlPatterns = {"/excursion/*", "/trip/*", "/user/*"})
public class ProtectFilter implements Filter {

    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;

        Object auth = request.getSession().getAttribute("authUser");
        if (auth != null) {
            chain.doFilter(request, response);
            return;
        }
       
        response401(response,request);
        
    }


   
    private void response401(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.sendRedirect(request.getContextPath()+"/auth/login");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}
