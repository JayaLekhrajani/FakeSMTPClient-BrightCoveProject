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
    	StringBuilder sb=new StringBuilder();

    	//Checking if length of arguments is less than 2 or if the second parameter is "", both of which mean no body
    	if(args.length < 2 || args[1].equals(""))
    	{	
    		sb.append("Cannot send an email with no body.\n");
    		try {
				console.write(sb.toString());
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
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
    		sb.append("Invalid email address: ");
    	}
    	//Printing a slightly different message if more than one email addresses are Invalid
    	else if(count>1)
    	{
    		sb.append("Invalid email addresses: ");
    	}
    	for(int i=0;i<count;i++)
		{
			sb.append(" "+invalidEmails[i]);
		}
		sb.append("");
		try {
			console.write(sb.toString());
		} catch (IOException e2) {
			e2.printStackTrace();
		}
    	
    	
    	/*
    	 * Writing to Network since no error
    	 */
    	
    	//If Protocol is SMTP
    	if("SMTP".equals(protocol))
    	{
	    	try {
	    		sb.append("connect smtp\n");
	    		//looping through all emails
	    		for(int i=0;i<emails.length;i++)
	    		{
	    			sb.append("To: "+emails[i]+"\n");
	    		}
	    		sb.append("\n"+message+"\n\ndisconnect\n");
				network.write(sb.toString());
			} catch (IOException e) {
				try {
					console.write("Connection error. Please try again.\n");
				} catch (IOException e1) {
					throw new RuntimeException(e1.getMessage());
				}
			}
    	}
    	//If Protocol is IM
    	else if("-im".equals(protocol))
    	{
    		try {
    			sb.append("connect chat\n");
    			for(int i=0;i<emails.length;i++)
    			{
    				sb.append("<"+emails[i]+">("+message+")\n");
    			}
    			sb.append("disconnect\n");
    			network.write(sb.toString());
    		}
    		catch(IOException e) {
    			try {
					console.write("Connection error. Please try again.\n");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    		}
    	}
    }
    
}
