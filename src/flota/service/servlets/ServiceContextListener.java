package flota.service.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import flota.service.beans.GoogleContext;

public class ServiceContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		GoogleContext.getContext();

	}

}
