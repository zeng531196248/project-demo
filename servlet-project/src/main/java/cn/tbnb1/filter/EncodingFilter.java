package cn.tbnb1.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class EncodingFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain arg2)
			throws IOException, ServletException {
		 	request.setCharacterEncoding("utf-8");
	        response.setContentType("text/html;charset=utf-8");
	        //获取URL的路径和参数
	        HttpServletRequest req = (HttpServletRequest) request;
	        String path=req.getServletPath();
	        String param=req.getQueryString();
	        if(path!=null&&param!=null){
	            path=path+"?"+param;//全请求路径
	        }
	        System.out.println("Path:"+path);
	        //跳转或带参数跳转
	        request.getRequestDispatcher(path).forward(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
