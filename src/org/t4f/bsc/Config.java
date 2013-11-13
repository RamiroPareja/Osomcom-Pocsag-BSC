package org.t4f.bsc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class Config {

	//private final static Logger LOGGER = Logger.getLogger("POCSAG-BSC");

	
	public static String logLevel = "FINEST";
	public static String commMethod = "REST" ;
	public static String restMasterNodeHost = "localhost";
	public static String restMasterNodePort = "8080";
	public static String restMasterNodePath = "/OsomcomPOCSAG-MSC/rest/node";
	public static String restLocalNodePort = "8080";
	public static String mqttBroker= "localhost";
	public static int mqttPort= 1883;
	public static String mqttSubscribingTopic= "t4f.org/msc";
	public static String mqttPostingTopic= "t4f.org/bsc";
	public static int commUpdatePeriod = 45;
	public static String commNodeName= "Node";
	public static String btsPort = "/dev/ttyUSB0";
	public static int btsBauds = 9600;
	
	
	
	public static boolean loadFromFile(String file) {

		
		Properties prop = new Properties();

		try {
			//
			prop.load(new FileInputStream(file));
			
			//LOGGER.info("Loading config file " + file);
			
			logLevel = prop.containsKey("log.level") ? prop.getProperty("log.level") : logLevel;
			restMasterNodeHost = prop.containsKey("rest.masterNodeHost") ? prop.getProperty("rest.masterNodeHost") : restMasterNodeHost;			
			restMasterNodePort = prop.containsKey("rest.masterNodePort") ? prop.getProperty("rest.masterNodePort") : restMasterNodePort;
			restMasterNodePath = prop.containsKey("rest.masterNodePath") ? prop.getProperty("rest.masterNodePath") : restMasterNodePath;
			restLocalNodePort = prop.containsKey("rest.localNodePort") ? prop.getProperty("rest.localNodePort") : restLocalNodePort;
			mqttBroker = prop.containsKey("mqtt.broker") ? prop.getProperty("mqtt.broker") : mqttBroker;
			try {
				mqttPort = prop.containsKey("mqtt.port") ? new Integer(prop.getProperty("mqtt.port")) : mqttPort;
			} catch (NumberFormatException ne) {
				System.err.println("Error parsing config file. 'mqtt.port' value '" +  prop.getProperty("mqtt.port") + "' is incorrect");
			}
			mqttSubscribingTopic = prop.containsKey("mqtt.subscribingTopic") ? prop.getProperty("mqtt.subscribingTopic") : mqttSubscribingTopic;
			mqttPostingTopic = prop.containsKey("mqtt.postingTopic") ? prop.getProperty("mqtt.postingTopic") : mqttPostingTopic;
			
			commMethod = prop.containsKey("comm.method") ? prop.getProperty("comm.method") : commMethod;
			btsPort = prop.containsKey("bts.port") ? prop.getProperty("bts.port") : btsPort;
			
			try {
				btsBauds = prop.containsKey("bts.bauds") ? new Integer(prop.getProperty("bts.bauds")) : btsBauds;
			} catch (NumberFormatException ne) {
				//LOGGER.warning("Error parsing config file. 'bts.bauds' value '" +  prop.getProperty("bts.bauds") + "' is incorrect");
				System.err.println("Error parsing config file. 'bts.bauds' value '" +  prop.getProperty("bts.bauds") + "' is incorrect");
			}
			
			
			try {
				commUpdatePeriod = prop.containsKey("comm.updatePeriod") ? new Integer(prop.getProperty("comm.updatePeriod")) : commUpdatePeriod;				
			} catch (NumberFormatException ne) {
				//LOGGER.warning("Error parsing config file. 'masterUpdatePeriod' value '" +  prop.getProperty("masterUpdatePeriod") + "' is incorrect");
				System.err.println("Error parsing config file. 'masterUpdatePeriod' value '" +  prop.getProperty("masterUpdatePeriod") + "' is incorrect");
			}
			commNodeName = prop.containsKey("comm.nodeName") ? prop.getProperty("comm.nodeName") : commNodeName;

		} catch (IOException ex) {
			//LOGGER.severe("Error Loading config file:\n " + ex);
			System.err.println("Error Loading config file:\n " + ex);
			return true;
		}
		
		return false;

	}

}
