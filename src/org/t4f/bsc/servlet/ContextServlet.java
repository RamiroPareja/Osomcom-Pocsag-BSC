package org.t4f.bsc.servlet;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.t4f.bsc.Config;
import org.t4f.bsc.network.mqtt.MqttService;
import org.t4f.bsc.thread.NetworkProcess;
import org.t4f.bsc.thread.PocsagProcess;

public class ContextServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static Logger LOGGER = Logger.getLogger("POCSAG-BSC");
       
	private NetworkProcess networkProcess;
	private PocsagProcess pocsagProcess;



	@Override
	public void init() throws ServletException {
		super.init();


		Level logLevel; 
		String configurationFile = getServletContext().getRealPath("/WEB-INF/");		
		
		

		String tmp = getServletConfig().getInitParameter("configFile");
		
		if (tmp == null || tmp.equals(""))
			configurationFile = getServletContext().getRealPath("/WEB-INF/pocsag-bsc.config");
		else 
			configurationFile = getServletContext().getRealPath("/WEB-INF/" + tmp);		
		
		Config.loadFromFile(configurationFile); 
		
		
		logLevel = Level.parse(Config.logLevel);
		 
		 
		ConsoleHandler ch = new ConsoleHandler(); 
		ch.setLevel(logLevel);		
		LOGGER.addHandler(ch);		
		LOGGER.setLevel(logLevel);
		
		LOGGER.finest("Logging system initialized"); 
		
		
		networkProcess = new NetworkProcess();
		networkProcess.start();
		LOGGER.info("Network process thread started");
		
		pocsagProcess = new PocsagProcess();
		pocsagProcess.start();
		LOGGER.info("Pocsag process thread started");
		
		if (Config.commMethod.equals("MQTT"))
			MqttService.init();
				
		
		LOGGER.finest("Context Servlet initialized");
		
		
	}
	
	
    
	@Override
	public void destroy() {
		super.destroy();

		pocsagProcess.shutdown();
		networkProcess.interrupt();
		networkProcess.shutdown();
		MqttService.disconnect();

		

				
	}
	
    

}
