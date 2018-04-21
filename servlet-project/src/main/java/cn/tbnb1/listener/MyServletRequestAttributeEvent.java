package cn.tbnb1.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class MyServletRequestAttributeEvent implements ServletRequestAttributeListener {

	@Override
	public void attributeAdded(ServletRequestAttributeEvent request) {
		request.getServletRequest().setAttribute("name", "测试对请求增加参数");
		System.err.println("对请求增加参数");
	}

	@Override
	public void attributeRemoved(ServletRequestAttributeEvent request) {
		System.err.println("对请求移除参数");

	}

	@Override
	public void attributeReplaced(ServletRequestAttributeEvent request) {
		System.err.println("对请求替换参数");

	}

}
