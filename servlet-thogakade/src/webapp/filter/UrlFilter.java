package webapp.filter;

import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/api/*")
public class UrlFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        addCorsHeader(httpServletResponse);
        if(httpServletRequest.getSession().getAttribute("isLogged")==null)
            httpServletRequest.getSession().setAttribute("isLogged", true);

        if (httpServletRequest.getSession().getAttribute("isLogged")!=null){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            Gson gson = new Gson();
            PrintWriter writer = servletResponse.getWriter();
            String s = gson.toJson(false);
            writer.write(s);
        }
    }

    private void addCorsHeader(HttpServletResponse response){
        System.out.println("{URLFILTER}[addCorHeader]");
        response.setContentType("application/json;charset=UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
    }

    @Override
    public void destroy() {

    }
}
