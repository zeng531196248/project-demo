package cn.tbnb1.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class MyServletRequestListener implements ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		System.err.println("--------请求销毁--------");

	}

	@Override
	public void requestInitialized(ServletRequestEvent request) {
		System.err.println("--------请求创建--------");

	}

}
