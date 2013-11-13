package org.t4f.bsc.network.rest.consumer;

import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;

import org.t4f.bsc.Config;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class NodeRestConsumer {
	
	private final static Logger LOGGER = Logger.getLogger("POCSAG-BSC");
	
	public boolean registerToMasterNode() {
		
		String restURL = "http://" + Config.restMasterNodeHost + ":" + Config.restMasterNodePort + Config.restMasterNodePath;
		LOGGER.finer("Registering in Master node using REST petition to " + restURL);		
		
		Client client = Client.create();
		WebResource webResource = client.resource(restURL);
 
		Form form = new Form();
	    form.add("port", Config.restLocalNodePort.toString() );
	    form.add("name", Config.commNodeName );
	    form.add("time", "" + System.currentTimeMillis());
	    
	        
		
		ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_FORM_URLENCODED)
                   .post (ClientResponse.class,form);
 
		if (response.getStatus() != 200) {
		   LOGGER.warning("Unable to connect to master node. HTTP error code: " + response.getStatus());
		   return true;
		}
 
		String output = response.getEntity(String.class);
		if (output.equals("OK"))
			return false;
		else
			return true;
		
	}
	
}
