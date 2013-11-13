package org.t4f.bsc.thread;

import java.util.logging.Logger;

import org.t4f.bsc.pocsag.PocsagMessage;
import org.t4f.bsc.pocsag.PocsagQueue;
import org.t4f.bsc.pocsag.bts.BTSController;

public class PocsagProcess extends Thread {

	private final static Logger LOGGER = Logger.getLogger("POCSAG-BSC");

	private volatile boolean running = true;
	
	public PocsagProcess() {
		super("PocsagProcess");
	}

	@Override
	public void run() {
		
		while (running) {
			//try {
				PocsagMessage pocsagMessage = PocsagQueue.pollMessageQueue();
				if (pocsagMessage == null) {
					// Timeout. 
					// Generates a idle message
					// 
					// Esta logica habria que ver si meterla en el MSC o donde, por que hay que saber en que 
					// frecuencias operan nuestros subscribers y generar idles para todas ellas. 
				} else { 
					if (processPocsagMessage(pocsagMessage)) {
						// The message couldnt be proccesed. Send it back to queue to try again
						PocsagQueue.enqueuePocsagMessage(pocsagMessage);
					}
						
				}
//			} catch (InterruptedException e) {
//				shutdown();
//			}

		}

	}

	public void	shutdown() {
		running = false;
		LOGGER.fine("Stopping PocsagProcessor thread");
	}
	
	

	private boolean processPocsagMessage(PocsagMessage pocsagMessage) {

		LOGGER.fine("Processing POCSAG message: " + pocsagMessage);
		BTSController btsController = new BTSController();
		
		return btsController.sendPocsagMessage(pocsagMessage);		
		

	}

}
