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

    	String email = args[0];
    	String message = args[1];
    	try {
			network.write("connect smtp\nTo: "+email+"\n\n"+message+"\n\ndisconnect\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}