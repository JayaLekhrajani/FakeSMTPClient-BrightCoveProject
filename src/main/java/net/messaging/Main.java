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
    	//Checking if length of arguments is less than 2 or if the second parameter is "", both of which mean no body
    	if(args.length < 2 || args[1].equals(""))
    	{
    		try {
				console.write("Cannot send an email with no body.\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
    		return;
    	}
    	String email;
    	String message;
    	String protocol;
    	
    	if(args[0].charAt(0)!='-')
    	{
    	protocol = "SMTP";
    	email = args[0];
    	message = args[1];
    	}
    	else {
    		protocol = args[0];
    		email = args[1];
    		message=args[2];
    	}
    	String[] emails;
    	//Splitting the email if it contains ','
    	if(email.contains(","))
    	{
    		emails = email.split(",");
    	}
    	else {
    		emails = new String[1];
    		emails[0] = email;
    	}
    	String[] invalidEmails = new String[emails.length];
    	int count=0;
    	//Storing invalid emails in a separate array;
    	for(int i=0;i<emails.length;i++) {
    		if(!emails[i].contains("@")){
    			invalidEmails[count++]=emails[i];
    		}
    	}
    	//If exactly one email address is invalid
    	if(count==1)
    	{
    		try {
    			console.write("Invalid email address: "+invalidEmails[0]+"\n");
    		}
    		catch(IOException e)
    		{
    			e.printStackTrace();
    		}
    		
    		return; //Returning to prevent further processing.
    	}
    	//Printing a slightly different message if more than one email addresses are Invalid
    	else if(count>1)
    	{
    		
    		try {
    			String result="Invalid email addresses:";

    			for(int i=0;i<count;i++)
    			{
    				result+=" "+invalidEmails[i];
    			}
    			result+="\n";
    			console.write(result);
    		}
    		catch(IOException e)
    		{
    			e.printStackTrace();
    		}
    		
    		return; //Returning to prevent further processing.
    	}
    	
    	
    	/*
    	 * Writing to Network since no error
    	 */
    	
    	//If Protocol is SMTP
    	if("SMTP".equals(protocol))
    	{
	    	try {
	    		String result="connect smtp\n";
	    		//looping through all emails
	    		for(int i=0;i<emails.length;i++)
	    		{
	    			result+="To: "+emails[i]+"\n";
	    		}
	    		result+="\n"+message+"\n\ndisconnect\n";
				network.write(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	else if("-im".equals(protocol))
    	{
    		try {
    			String result="connect chat\n";
    			//looping through all emails
    			for(int i=0;i<emails.length;i++)
    			{
    				result+="<"+emails[i]+">("+message+")\n";
    			}
    			result+="disconnect\n";
    			network.write(result);
    		}
    		catch(IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
}