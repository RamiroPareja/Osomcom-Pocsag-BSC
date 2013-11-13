package org.t4f.bsc.pocsag.bts;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.logging.Logger;

import org.t4f.bsc.Config;
import org.t4f.bsc.pocsag.PocsagMessage;

public class BTSController {

	private final static Logger LOGGER = Logger.getLogger("POCSAG-BSC");
	
	private SerialPort serialPort = null;
	
	public boolean sendPocsagMessage(PocsagMessage message) {
		
		String tmp;
		
		
		if (serialPort == null ) {
			serialPort = openConnection(Config.btsPort, Config.btsBauds);
		}
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			PrintStream out = new PrintStream(serialPort.getOutputStream());
			
			out.println("CONFIG FREQ " + message.getFrequency());
			System.out.println("CONFIG FREQ " + message.getFrequency());
			tmp = in.readLine();
			if ("OK".equals(tmp) == false) { 
				throw new IOException("Imposible to set the frequency " + message.getFrequency());
			}
			
			out.println("CONFIG BAUDS " + message.getBauds());
			System.out.println("CONFIG BAUDS " + message.getBauds());
			tmp = in.readLine();
			if ("OK".equals(tmp) == false) { 
				throw new IOException("Imposible to set the baud rate " + message.getBauds());
			}
			
			String s = "SEND " + message.getMsgType().toString().charAt(0) + " " + message.getRIC() + " " + message.getMessage();
			out.println(s);
			System.out.println(s);
			
			
			// Skip the debug info with the data sent through the radio interface and search for the OK
			while ((tmp = in.readLine()) != null)
			if ("OK".equals(tmp) == true) { 
				break;
			}
			if (tmp == null)
				throw new IOException("Imposible to send command " + s);
			
			
			
		} catch (IOException e) {
			LOGGER.severe("Error sending data to BTS: " + e);
		} finally {
			closeConnection();
		}
	
		return false;
		
	}
	
	private SerialPort openConnection(String portName, int bauds) {
		
		LOGGER.fine("Openning BTS connection in port " + portName);
		
		try {
			CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
	        if ( portIdentifier.isCurrentlyOwned() )
	        {
	        	LOGGER.severe("Error openning connection: Port currently in use");
	            return null;
	        }
	        
	        CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
	            
	        if ( commPort == null || !(commPort instanceof SerialPort) )
	        {
	        	LOGGER.severe("Error openning port");
	        	return null;
	        }
	        
	        SerialPort serialPort = (SerialPort) commPort;
	        serialPort.setSerialPortParams(bauds,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
	        
	        return serialPort;
		} catch (Exception e) {
			LOGGER.severe("Error openning connection: " + e);
			return null;
		} 
		
	}

	public void closeConnection() {
		if (serialPort != null ) {
			LOGGER.fine("Clossing BTS connection");
			serialPort.close();
			serialPort = null;
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		closeConnection();
		super.finalize();
	}
	
	
	
}
