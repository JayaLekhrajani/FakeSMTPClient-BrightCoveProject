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
    	
    	String email = args[0];
    	String message = args[1];
    	
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
    	for(int i=0;i<emails.length;i++) {
    		if(!emails[i].contains("@")){
    			invalidEmails[count++]=emails[i];
    		}
    	}
    	
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
    	
    	//Writing to Network since no error
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
    
}