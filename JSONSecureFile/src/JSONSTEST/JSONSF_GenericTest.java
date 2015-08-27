package JSONSTEST;

import java.io.*;

import JSONSFGLOBAL.Constants ;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.json.simple.JSONValue;

import java.io.IOException; 
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap; 
import java.util.LinkedList; 
import java.util.List; 
import java.util.Map;
import java.util.Formatter;

 









import org.json.simple.parser.ContainerFactory; 
import org.json.simple.parser.JSONParser; 
import org.json.simple.parser.ParseException; 

import JSONSFCRYPTO.*;
import JSONSFFILE.*;
import JSONSFGLOBAL.*;
import JSONSFOBJECT.JSONSF_DataDecrypt;
import JSONSFOBJECT.JSONSF_DataEncrypt;
import JSONSFOBJECT.JSONSF_firstlevel;
/**
 * @author mbl
 * basic test

 */

public class JSONSF_GenericTest {

	public JSONSF_GenericTest() {
		
	}
	
	@SuppressWarnings("rawtypes")
	public void Test_JSONParser (){	
		  // test code to extract field from json file
		  String jsonText = "{\"first\": 123, \"second\": [4, 5, 6], \"third\": 789}";
		  JSONParser parser = new JSONParser();
		  
		  ContainerFactory containerFactory = new ContainerFactory(){
		    //@SuppressWarnings("rawtypes")
			public List<?> creatArrayContainer() {
		      return  new LinkedList();
		    }
		    //@SuppressWarnings("rawtypes")
		    public Map<?,?> createObjectContainer() {
		      return new LinkedHashMap();
		    }
		                        
		  };
		                
		  try{
		    Map<?,?> json = (Map)parser.parse(jsonText, containerFactory);
		    Iterator<?> iter = json.entrySet().iterator();
		    System.out.println("==iterate result==");
		    while(iter.hasNext()){
		      Map.Entry<?,?> entry = (Map.Entry)iter.next();
		      System.out.println(entry.getKey() + "=>" + entry.getValue());
		    }
		                        
		    System.out.println("==toJSONString()==");
		    System.out.println(JSONValue.toJSONString(json));
		  }
		  catch(ParseException pe){
		    System.out.println(pe);
		  }
		  
	}

	public int Test_StringByteChar (){
		
		int testres = Constants.Fail;
		StringBuffer TestStr = new StringBuffer();
		java.util.Formatter formatter = new java.util.Formatter(TestStr);
		
	     //char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
	       //     + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	 
	    //char[] numbers = ("0123456789").toCharArray();
		
		//String reference = "123456789abcdef";
		String reference = "{\"data\": \"value\", "
				+ "\"hash\": \"1234567\" }";
		byte[] plaindataref = reference.getBytes();

		// string is 123456789abcdef
		// bytes are [49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102]
		
		for (int itr = 0; itr <plaindataref.length;itr++){

			formatter.format("%c",plaindataref[itr]);
			//TestStr.append(plaindataref[itr]);
			
		}
		
		System.out.print("reference StringBuffer is  " + TestStr.toString() + "\n");
		
		System.out.print("reference string is  " + reference + "\n");
		
		System.out.print("reference string from bytes using tostring (wrong method) is  " + plaindataref.toString() + "\n");
		System.out.print("reference string from bytes is  " + new String (plaindataref) + "\n");
		

		
		formatter.close();
		testres = Constants.Success;
		return testres; 
		
		
	}
	
	
	
	public int Test_ExportObjectToJSON (){
		int result = Constants.Fail; 
		// tested the 07 june @23.30 ok
			
		
		// create a fake data map as if it was coming from an UI
		HashMap<StringBuffer,StringBuffer> testbank = new HashMap<StringBuffer, StringBuffer>();

		testbank.put(new StringBuffer("version"), new StringBuffer("1"));
		testbank.put(new StringBuffer("category"), new StringBuffer("bank"));
		testbank.put(new StringBuffer("filetype"), new StringBuffer("json"));
		testbank.put(new StringBuffer("desc"), new StringBuffer("account HSBC"));
		testbank.put(new StringBuffer("hash"), new StringBuffer("AE45B7"));
		testbank.put(new StringBuffer("encm"), new StringBuffer("1"));
		testbank.put(new StringBuffer("data"), new StringBuffer("{\"login\":\"zorro\",\"pass\":\"rambo89\"}"));
		
		StringBuffer strbufpf = new StringBuffer("MyPasswordIs");
		// create object with passphrase
		JSONSF_DataEncrypt myobjectforencryption = new JSONSF_DataEncrypt(strbufpf);
		// provide the map to encrypt the data field
		myobjectforencryption.SetMapForExport (testbank);
		// parse the whole map to check coherence
		if (myobjectforencryption.ValidateExport()== Constants.Success){
			// do encrypt data value
			myobjectforencryption.DoEncryption(myobjectforencryption.GetEncryptionMethod ());
			result= myobjectforencryption.ExportToFile("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample_enc_HSBC.json");
			
		}
		//	
		return result;
		
	}
	
	

	
	public int  Test_JSONSF_FileAsByteBuffer(){
		// here we suppose that we have a file in input, it can be clear or encrypted
		// for clear use case, it means that the UI in charge of collecting the input of the user detects a link in the data
		// such link points to a file to be encrypted instead of having data encapsulated in a JSON object {}
		
		int result = Constants.Fail; 

		try{
	
			// testing JSONSF_FileAsByteBuffer
			//JSONSF_FileAsByteBuffer fortest = new JSONSF_FileAsByteBuffer("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample_clear_MyBank.json");
			//JSONSF_FileAsByteBuffer fortest = new JSONSF_FileAsByteBuffer("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample_clear_MyBank.jsonjencson");
			JSONSF_FileAsByteBuffer fortest = new JSONSF_FileAsByteBuffer("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample_clear_MyFile.json");
			
				result = fortest.OpenFile();
				if(result == Constants.Success){							
					if (fortest.IsOpenedFileEncrypted()==false){
						System.out.println("opened file is in clear " );
						// now we suppose that we encrypt the buffer, but we don't as we only want to test the class
						// encryption shall be done by the caller of such class
						// shall call encryption here
						
						fortest.CreateFile(fortest.GetFileBuffer(), false);
						
					}
					else{
						System.out.println("opened file is in encrypted " );
						//shall call decryption here
						// now we suppose that we decrypt the buffer, but we don't as we only want to test the class
						// decryption shall be done by the caller of such class
						fortest.CreateFile(fortest.GetFileBuffer(), false);
						
					}
				}// end if(result == Constants.Success){

		}
        catch(IOException ex) {
        	System.out.println("Error testing Test_JSONSF_FileAsByteBuffer " );   
            System.out.println( ex.getMessage() );
            ex.printStackTrace();

        }
		return result;
	}
	
	public void Test_JSONVersionFile (){
		
    	StringBuffer strtestbuffer ;
    	try{
    		JSONSF_ReadJsonFile testreadfile = new JSONSF_ReadJsonFile("/home/mbl/dev/workspace/JSONSecureFile/samplefile/sample.json");
    		strtestbuffer = testreadfile.OpenTextFileLineByLine();
    		System.out.println("string buffer is " + strtestbuffer.toString() );
    		
    	}
        catch(IOException ex) {
        	System.out.println("Error testing file " );   
            System.out.println( ex.getMessage() );
            ex.printStackTrace();
        } 
		
	}
	
	/* to complete as use case not clear
	 * 	public int  Test_JSONSF_FileAsByteBuffer(){
		// here we suppose that we have a fiel in input, it can be clear or encrypted
		// for clear use case, it means that the UI in charge of collecting the input of the user is not used
		// the user generates the file using a text editor
		
		int result = Constants.Fail; 
		// test if the file is a JSONSF file
		JSONSF_firstlevel myobject = new JSONSF_firstlevel();
		try{
			if (myobject.ImportFromFile("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample.json") == Constants.Success ){
				result= myobject.ValidateImport();
				if(result == Constants.Success){
					// testing JSONSF_FileAsByteBuffer
					JSONSF_FileAsByteBuffer fortest = new JSONSF_FileAsByteBuffer("/home/mbl/dev/workspace/JSONSecureFile/samplefile/sample_clear_MyBank.json");
						result = fortest.OpenFile();
						if(result == Constants.Success){							
							if (fortest.IsOpenedFileEncrypted()==false){
								System.out.println("opened file is in clear " );
								// now shall detect if we have to encrypt it
							}
							else{
								System.out.println("opened file is in encrypted " );
								// now shall detect method for decryption
							}
						}// end if(result == Constants.Success){
				}	// end if(result == Constants.Success){
			}// end if (myobject.ImportFromFile("C:/MBL
		}
        catch(IOException ex) {
        	System.out.println("Error testing Test_JSONSF_FileAsByteBuffer " );   
            System.out.println( ex.getMessage() );
            ex.printStackTrace();

        }
		return result;
	}
	 * 
	 */
	

}
