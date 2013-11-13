package org.t4f.bsc.network.rest.service;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.t4f.bsc.pocsag.PocsagMessage;
import org.t4f.bsc.service.PocsagServices;


@Path("/pocsag")
public class PocsagRestServices {
	
	private final static Logger LOGGER = Logger.getLogger("POCSAG-BSC");

	@POST
	@Path("/send/{RIC}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String send(@PathParam("RIC") String RIC,					    
					   @FormParam("frequency") String frequency,
					   @FormParam("bauds") String bauds,
					   @FormParam("msgType") String msgType,
					   @FormParam("message") String message) {
		
		PocsagServices pocsagServices = new PocsagServices();
		PocsagMessage pocsagMsg;
		
		LOGGER.finer("REST POST - RIC: " + RIC + " - freq: " + frequency + 
				     " - bauds: " + bauds + " type: " + msgType + " - msg: " + message);
		
		try {
			pocsagMsg = new PocsagMessage(Integer.parseInt(RIC), 
					Integer.parseInt(frequency), Integer.parseInt(bauds), 
					PocsagMessage.MessageType.valueOf(msgType), message);
		} catch (NumberFormatException e) {
			LOGGER.info("Error parsing POCSAG message from REST POST\n" +
					"RIC: " + RIC + " - freq: " + frequency + 
				     " - bauds: " + bauds + " type: " + msgType + " - msg: " + message);
			return "Error";
		} catch (IllegalArgumentException e) {
			LOGGER.info("Error parsing POCSAG message from REST POST\n" +
					"RIC: " + RIC + " - freq: " + frequency + 
				     " - bauds: " + bauds + " type: " + msgType + " - msg: " + message);
			return "Error";
		}
		
		
		pocsagServices.send(pocsagMsg);
		
		
		return "Sent";
	}
	
}
