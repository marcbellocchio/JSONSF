/**
 * main class to start code from command line
 */

/**
 * @author mbl
 *
 */


import java.io.IOException;
//import java.net.InetAddress;
import java.net.UnknownHostException;
//import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.json.simple.parser.ParseException;


public class JSONSF_main {

	public static int type ;
	public static int dclass;
	//private static final int WAITING_TIME_RESPONSE = 2000;
	//private static final String SDTVWCAM_TCP_LOCAL = "_sdtvwcam._tcp.local.";
	private static final String COMMAND_LINE =
    "------------------------------------------------------------------------------\n" +
    "| Command Line: dnssd <option> [parameters]                                  |\n" +
    "------------------------------------------------------------------------------\n" +
    "dnssd -E <algo> <key> <IV> <plain>											   \n" +
    "dnssd -D <algo> <key> <IV> <ciphered>											   \n" +
    "dnssd -B        <Type> [<Domain>]             (Browse for services instances)\n" +
    "dnssd -L <Name> <Type> [<Domain>]                (Look up a service instance)\n" +
    "dnssd -R <Name> <Type> <Domain> <Port> <Host> [<TXT>...] (Register a service)\n" +
    "dnssd -Q <FQDN> <rrtype> <rrclass>        (Generic query for any record type)\n" +
    "dnssd -G v4/v6/v4v6 <Hostname>         (Get address information for hostname)\n" +
    "dnssd -V           (Get version of currently running daemon / system service)\n" +
    "dnssd -W  <timeout>    (Specific query for _sdtvwcam._tcp:local. record PTR class in)\n" +    
    "------------------------------------------------------------------------------";
    protected JSONSF_main()
    throws UnknownHostException
    {
    }
    
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	       StringBuilder timingBuilder = new StringBuilder();
	        try
	        {
	            if (args.length > 0)
	            {
	                timingBuilder.append("Execution of \"JSONSecureFile ");
	                for (String arg : args)
	                {
	                    timingBuilder.append(arg).append(" ");
	                }
	                timingBuilder.setLength(timingBuilder.length() - 1);
	                timingBuilder.append("\"");
	                
	                String temp = args[0];
	                
	                
	                
	                
	                if ((temp != null) && ((temp.length() == 2) || (temp.length() == 3)) &&
	                (temp.startsWith("-") || temp.startsWith("--")))
	                {

	                    //char option = temp.charAt(temp.length() - 1);
	            
	                    ExecutionTimer._start();
	                    
	                    
	                    switch (temp)
	                    {
	                        // encrypt
	                        case "-E":
	                        case "--E":	                        	
	                            // start timer
	                            ExecutionTimer._start();

	                            System.out.println("Encryption is starting");
	                            // args[1] contains algo 
	                            if ((args.length < 5) || (args[1] == null) || (args[1].length() == 0))
	                            {
	                                throw new IllegalArgumentException("jsonsecurefile Too few arguments for -E option");
	                            }
	                            else{
	                            	// i.e twofish 3dafba429d9eb430b422da802c9fac41 c286696d887c9aa0611bbb3e2025a45a "Calmer un visage qui pleure n'est pas plus facile que d'y redonner le sourire."
	                            	switch (args[1].toLowerCase()){
	                            	
	                            		case Constants.TWOFISH:
	    	                        		byte[] result = null;
	                            			
	    	                        		JSONSF_CryptoCipher Cipher = new JSONSF_CryptoCipher();
	    	                        		
	    	                        		result = Cipher.TwoFishCBC(args[2].toLowerCase().getBytes(), args[3].toLowerCase().getBytes(), args[4].toLowerCase().getBytes());
	    	                        		System.out.println("\n" + " Encryption result is " + result.toString());
	    	                        		break;
	                            		default:
	                            			throw new IllegalArgumentException("jsonsecurefile algo not supported for -E option" + args[1] );
	                          	
	                            	
	                            	}// end switch (args[1].toLowerCase()){                            	
	                            }// end else
	                            System.out.println("\n" + timingBuilder.toString() + " - took " + ExecutionTimer._took(TimeUnit.SECONDS) + " seconds.");                            
	                            break;	
	                            
		                        // decrypt
		                        case "-D":
		                        case "--D":	                        	
		                            // start timer
		                            ExecutionTimer._start();

		                            System.out.println("Decryption is starting");
		                            // args[1] contains algo 
		                            if ((args.length < 5) || (args[1] == null) || (args[1].length() == 0))
		                            {
		                                throw new IllegalArgumentException("jsonsecurefile Too few arguments for -D option");
		                            }
		                            else{
		                            	// i.e twofish 3dafba429d9eb430b422da802c9fac41 c286696d887c9aa0611bbb3e2025a45a "Calmer un visage qui pleure n'est pas plus facile que d'y redonner le sourire."
		                            	switch (args[1].toLowerCase()){
		                            	
		                            		case Constants.TWOFISH:
		    	                        		byte[] result = null;
		                            			
		    	                        		JSONSF_CryptoDecipher Decipher = new JSONSF_CryptoDecipher ();
		    	                        		
		    	                        		result = Decipher.TwoFishCBC(args[2].toLowerCase().getBytes(), args[3].toLowerCase().getBytes(), args[4].toLowerCase().getBytes());
		    	                        		System.out.println("\n" + " Decryption result is " + result.toString());
		    	                        		break;
		                            		default:
		                            			throw new IllegalArgumentException("jsonsecurefile algo not supported for -D option" + args[1] );
		                          	
		                            	
		                            	}// end switch (args[1].toLowerCase()){                            	
		                            }// end else
		                            System.out.println("\n" + timingBuilder.toString() + " - took " + ExecutionTimer._took(TimeUnit.SECONDS) + " seconds.");                            
		                            break;
						case "-B":
	                            // Browse for service instances
	                            if ((args.length < 2) || (args[1] == null) || (args[1].length() == 0))
	                            {
	                                throw new IllegalArgumentException("jsonsecurefile Too few arguments for -B option");
	                            }
	                            

	                            System.out.println("\n" + timingBuilder.toString() + " - took " + ExecutionTimer._took(TimeUnit.SECONDS) + " seconds.");
	                            break;
                           
	                        case "-T":
	                        case "--T":	
	                        	
	                        	// hash test 
	                        	JSONSF_Test testclass = new JSONSF_Test(); 
	                        	
	                        	//testclass.Test_getWhirlpoolHash_lasydog(); 
	                        	
	                        	if (testclass.Test_Twofish () == 0)
		                            System.out.println("Test_Twofish is ok");
	                        	else
	                        		System.out.println("Test_Twofish is ko, please review the code");
	                        		
	                        	 
	                        	 //testclass.Test_JSONVersionFile ();
	                        	
                      	
	                            break;
	                        default:
	                            throw new IllegalArgumentException("Invalid option \"" + args[0] + "\" specified!");
	                    }
	                } else
	                {
	                    throw new IllegalArgumentException("Invalid option \"" + args[0] + "\" specified!");
	                }
	            } else
	            {
	                throw new IllegalArgumentException((String) null);
	            }
	        } catch (IllegalArgumentException e)
	        {
	            printHelp(e.getMessage());
	        } finally
	        {
	            System.out.println("\nTotal " + timingBuilder.toString() + " - took " + ExecutionTimer._took(TimeUnit.SECONDS) + " seconds.");
	        }
	        
	        System.exit(0);
	

	}// end main
	
    private static void printHelp(final String message)
    {
        if ((message != null) && (message.length() > 0))
        {
            System.out.println("\n==>>" + message + "<<==\n");
        }
        System.out.println(COMMAND_LINE);
    }// end printHelp

}






