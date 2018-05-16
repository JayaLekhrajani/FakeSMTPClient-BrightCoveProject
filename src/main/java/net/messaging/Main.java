package net.messaging;

import java.io.*;

public class Main {
    private static Writer network;
    private static Writer console;

    public static void setNetwork(Writer network) {
        Main.network = network;
    }

    public static void setConsole(Writer console) {
        Main.console = console;
    }

    public static void main(String... args) {
    	//Checking if length of arguments is less than 2 or if the second parameter is "", both of which signify no body
    	if(args.length < 2 || args[1].equals(""))
    	{
    		try {
				console.write("Cannot send an email with no body.\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
    		return;
    	}
    	
    	String email = args[0];
    	String message = args[1];
    	
    	//Checking if the email contains "@". Not checking if it contains ".com" since it was not specified
    	if(!email.contains("@"))
    	{
    		try {
    			console.write("Invalid email address: "+email+"\n");
    		}
    		catch(IOException e)
    		{
    			e.printStackTrace();
    		}
    		
    		return; //Returning to prevent further processing.
    	}
    	
    	//Writing to Network since no error
    	try {
			network.write("connect smtp\nTo: "+email+"\n\n"+message+"\n\ndisconnect\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}