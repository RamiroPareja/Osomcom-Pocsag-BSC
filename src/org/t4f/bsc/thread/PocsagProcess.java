package org.t4f.bsc.thread;

import java.util.HashMap;
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
		
		HashMap <PocsagMessage, Integer> numTimesProccesed = new HashMap<PocsagMessage, Integer>();
		
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
						// The message couldnt be proccesed. 
						
						int numRetries = (numTimesProccesed.get(pocsagMessage)==null)?0:numTimesProccesed.get(pocsagMessage);
						if (numRetries<5) {
							// Send it back to queue to try again after waiting 10 seconds
							numTimesProccesed.put(pocsagMessage, ++numRetries);
							try {
								Thread.sleep(10000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							LOGGER.fine("Retrying (" + numRetries + ") message: " + pocsagMessage);
							
							PocsagQueue.enqueuePocsagMessage(pocsagMessage);

						} else {
							numTimesProccesed.remove(pocsagMessage);
							LOGGER.fine("Dismissing message after " + numRetries + " tries: " + pocsagMessage);
						}
						
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
