package org.t4f.bsc.service;

import java.util.logging.Logger;

import org.t4f.bsc.Config;
import org.t4f.bsc.network.mqtt.MqttService;
import org.t4f.bsc.network.rest.consumer.NodeRestConsumer;

public class NodeServices {

	private final static Logger LOGGER = Logger.getLogger("POCSAG-BSC");
	
	public boolean registerToMasterNode () {
		
		if (Config.commMethod.equals("MQTT")) {
			MqttService mqttService = new MqttService();
			return mqttService.registerToMasterNode();
		} else {
			NodeRestConsumer nodeRestConsumer = new NodeRestConsumer();
			return nodeRestConsumer.registerToMasterNode();
		}
		
	}
	
}
