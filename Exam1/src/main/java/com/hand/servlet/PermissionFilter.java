package com.hand.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PermissionFilter implements Filter {

    public PermissionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;
		
		String servletPath=req.getServletPath();
		
		HttpSession session=req.getSession();
		String flag=(String) session.getAttribute("flag");
		
		if(servletPath!=null&&(servletPath.equals("/login.jsp")||servletPath.equals("/LoginServlet"))){
			chain.doFilter(request, response);
		}else{
			if(flag!=null&&flag.equals("login_success")){
				chain.doFilter(request, response);
			}else if(flag!=null&&flag.equals("login_error")){
				req.setAttribute("msg", "登录失败，请重新登录！<br/>");
				req.setAttribute("return_uri", servletPath);
				RequestDispatcher rd=req.getRequestDispatcher("/login.jsp");
				rd.forward(req, resp);
			}else{
				req.setAttribute("msg", "您尚未登录！<br/>");
				req.setAttribute("return_uri", servletPath);
				RequestDispatcher rd=req.getRequestDispatcher("/login.jsp");
				rd.forward(req, resp);
			}
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
