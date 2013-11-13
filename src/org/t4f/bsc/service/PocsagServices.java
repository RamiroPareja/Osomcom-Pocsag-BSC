package org.t4f.bsc.service;

import java.util.logging.Logger;

import org.t4f.bsc.pocsag.PocsagMessage;
import org.t4f.bsc.pocsag.PocsagQueue;

public class PocsagServices {
	
	private final static Logger LOGGER = Logger.getLogger("POCSAG-BSC");
	
	public boolean send(PocsagMessage pocsagMsg) {
		
		LOGGER.info("Queing  POCSAG message:" + pocsagMsg);
		
		PocsagQueue.enqueuePocsagMessage(pocsagMsg);
		
		return false;
		
	} 

}
