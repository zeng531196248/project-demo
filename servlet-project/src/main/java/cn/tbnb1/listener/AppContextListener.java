package cn.tbnb1.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 
 * @author zengrong
 *
 */
@WebListener
public class AppContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("--------容器已经销毁------");

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("--------容器已经创建------");
		
	}

}
