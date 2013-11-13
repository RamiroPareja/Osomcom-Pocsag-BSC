package org.t4f.bsc.thread;

import java.util.logging.Logger;

import org.t4f.bsc.Config;
import org.t4f.bsc.service.NodeServices;

public class NetworkProcess extends Thread {
	
	private final static Logger LOGGER = Logger.getLogger("POCSAG-BSC");

	
	private volatile boolean running = true;

	

	public NetworkProcess() {
		super("NetworkProcess");
	}

	@Override
	public synchronized void run() {

		NodeServices nodeServices = new NodeServices();
		
		while (running) {
			
			nodeServices.registerToMasterNode();
			
			try {
				Thread.sleep(Config.commUpdatePeriod * 1000);
			} catch (InterruptedException e) {				
				shutdown();
			}
			
			
		}
		
			
	}
	
	public void	shutdown() {
		running = false;
		LOGGER.fine("Stopping NetworkProcessor thread");
	}
	
	
	

}
