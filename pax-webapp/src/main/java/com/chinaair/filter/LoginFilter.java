package com.chinaair.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chinaair.entity.Employee;
import com.chinaair.webBean.LoginBean;

public class LoginFilter implements Filter {

	FilterConfig filterConfig;
	@Inject
	private LoginBean loginBean;
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(true);
		
		Employee loginUser = (Employee) session.getAttribute("loginUser");
	    String contextPath = req.getContextPath();
		 if (loginUser == null || loginBean == null || !loginBean.isLoggedIn() || loginBean.getLoginUser() == null) {
			 resp.sendRedirect(contextPath + "/login.xhtml?sessionended=true");
	     }else{
	    	 chain.doFilter(request, response); 
	     }
	        
	}

	public void destroy() {

	}

}
