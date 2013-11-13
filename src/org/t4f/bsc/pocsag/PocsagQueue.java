package org.t4f.bsc.pocsag;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class PocsagQueue {

	private final static Logger LOGGER = Logger.getLogger("POCSAG-BSC");

	private static LinkedBlockingQueue<PocsagMessage> pocsagMessagesQueue = new LinkedBlockingQueue<PocsagMessage>();

	public static synchronized boolean enqueuePocsagMessage(PocsagMessage pocsagMessage) {

		try {
			pocsagMessagesQueue.put(pocsagMessage);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static synchronized boolean isMessageQueueEmpty() {

		return pocsagMessagesQueue.isEmpty();

	}

	public static synchronized PocsagMessage pollMessageQueue() { //long timeout) throws InterruptedException {
	
		//return pocsagMessagesQueue.poll(timeout, TimeUnit.SECONDS);	
		return pocsagMessagesQueue.poll();

	}
	
}
