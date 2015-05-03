package JSONSFOBJECT;

import JSONSFGLOBAL.Constants ;

import java.util.Iterator;
import java.util.LinkedHashMap; 
import java.util.LinkedList; 
import java.util.List; 
import java.util.Map; 

import JSONSFFILE.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import java.util.HashMap;
import java.util.Hashtable;




import org.json.simple.parser.*;


/**
* @author mbl
* @brief firstlevel class
* @usage when a file is read, the first level holds all the fields of the json file 
* @param na        
* @return na
*/
public class JSONSF_firstlevel {

	//public String stread;
	public String stdelimiter;
	public String[] streadarray;
	// store all the fields from the json file header
	public Map <String, String> jsonfileheader;
	
	// stored encrypted method value read from file
	private int encMethod;
	
	public JSONSF_firstlevel() {
		
		jsonfileheader =  new HashMap <String, String> (); 
		stdelimiter = ":";
		encMethod=0;
	}
	
	/**
	* @brief ExportToFile
	* @usage create a json file from input data
	* @param[in] String FileName  
	* @param[class member]     
	* @return integer Constants.Success when ok or Constants.Fail or Constants.ErrFiletoBig
	*/
	public int ExportToFile(String FileName) {
		
		// to complete ...........
		
		
	}
	
	/**
	* @brief ImportFromFile
	* @usage from a json file, fill the class members with associated values 
	* @param[in] String FileName  
	* @param[class member] jsonfileheader is initiliazed with data from file    
	* @return integer Constants.Success when ok or Constants.Fail or Constants.ErrFiletoBig
	*/
	public int ImportFromFile(String FileName) {
		int retval= Constants.Fail ; 
		
		try{
			JSONSF_ReadFile filetoread  = new JSONSF_ReadFile (FileName);
			if ( (retval=filetoread.OpenFile()) == Constants.Success ){
				while (filetoread.GetOneLine()!=false){
					streadarray=filetoread.GetReadLine().split(stdelimiter);
					// remove each " white space and , from the string
					jsonfileheader.put(streadarray[0].replace('"', ' ').trim(), streadarray[1].replace('"', ' ').replace(',', ' ').trim());
					
				}// end while
				filetoread.CloseFile();
				retval = Constants.Success;
			}
				
		}
	    catch(IOException ex) {
	    	System.out.println("JSONSF_Populate " );   
	        System.out.println( ex.getMessage() );
	        ex.printStackTrace();
	    } 
		return retval;
	}

	/**
	* @brief GetFieldsFromVersion
	* @usage provide the number of fields of the json file according to version
	* @param[in] int version        
	* @return int number of fields or fail when not supported
	*/	
	public int GetNbFieldsFromVersion (int version){
		
		int retval=Constants.Fail;  
		switch (version){
			case 1:
				retval = Constants.Version_1_NumLines;
				break;
			case 2 :
				retval = Constants.Version_2_NumLines;
				break;
		
		}
		return retval;
		
	}
	
	/**
	* @brief GetEncryptionMethod
	* @usage provide encMethod
	* @param[in]        
	* @return encMethod 1:twofishcbc 2:serpentcbc 3:tfspcbc
	*/	
	public int GetEncryptionMethod (){
		return encMethod;	
	}
	
	/**
	* @brief CheckEncryptionAlgorithm
	* @usage check if the encryption algo is supported
	* @param[in] int value        
	* @return success or fail when not supported
	*/	
	public int CheckEncryptionAlgorithm (int value){
		
		int retval=Constants.Fail; 
		switch (value){
			case 1: // twofishcbc
			case 2: // serpentcbc
			case 3: // tfspcbc
				retval = Constants.Success;
				encMethod= value;
				break;
			default:
				break;
		}
		return retval;		
	}
	
	/**
	* @brief GetFieldsFromVersion
	* @usage provide the fields of the json file according to version
	* @param[in] int version        
	* @return String with fields or null when version is not supported
	*/	
	public String GetFieldsFromVersion (int version){
		
		String Field=null; 
		switch (version){
			case 1:
				Field = Constants.Version_1_Fields;
				break;
			case 2 :
				Field = Constants.Version_2_Fields;
				break;
		
		}
		return Field;
		
	}
	
	/**
	* @brief Validate
	* @usage check if the map contains valid data
	* @param[in] String FileName
	* @return  Constants.Success         
	* @return Constants.ErrToMuchLline when more lines than expected
	* @return Constants.ErrVersionNotSupported 
	* @return Constants.ErrVersionNotPresent 
	* @return Constants.ErrEncMethodNotSupported  
	*/
	public int Validate() {
		int retval= Constants.Fail ; 
		int version=0;
		int nbfields=0;
		int index = -1;
		boolean EncMethodSupported = false;
		String key=null;
		String value=null;
		String Fields=null;
		String EncryptionMethod = Constants.EncryptionMethod; 
		
		if(jsonfileheader.size() <= Constants.Version_all_MAXNumLines){
			// check if the version key is present
			value = jsonfileheader.get(Constants.Version);
			if ((value==null) )
				retval= Constants.ErrVersionNotPresent ;	
			else{
				version = Integer.valueOf(value);
				if (version > Constants.Version_MAX)
					retval= Constants.ErrVersionNotSupported ;
				else{
					// now can parse other keys
					Fields= GetFieldsFromVersion(version);
					if(Fields!=null){
						// count version now
						nbfields++;
						Iterator <String>iterator = jsonfileheader.keySet().iterator();
						while(iterator.hasNext()){
							// get remaining fields
							key   = (String)iterator.next();
							value = jsonfileheader.get(key);
							// check if key is part of Fields
							index = Fields.indexOf(key);
							if(index >= Constants.Zero){
								nbfields++;
							}	
							
							if( key.equals(EncryptionMethod) ){
								// check if it is supported

								if(CheckEncryptionAlgorithm(Integer.valueOf(value))==Constants.Success){
									EncMethodSupported = true;
								}
							}
								
						}// end while(iterator.hasNext()){ 	
						if ( nbfields == GetNbFieldsFromVersion(version) ){
							// correct number of fields detected, now check if the encrypted method is supported
							if(EncMethodSupported)
								retval = Constants.Success;
							else
								retval = Constants.ErrEncMethodNotSupported;
						}
																			
					}// end if(Fields!=null){
					else
						retval= Constants.ErrVersionNotSupported ;
				}//end else
			}// end else				
		}//  end if if(jsonfileheader.size() <= Constants.Version_all_MAXNumLines)
		else{
			retval= Constants.ErrToMuchLline ;
		}
			
		
		// key iterator
		//Iterator iterator = mapA.keySet().iterator();

		// value iterator
		//Iterator iterator = mapA.values();
		/*
		for(MyObject anObject : map.values()){
			   //do someting to anObject...
			}

			for(String key : map.keySet()){
			   MyObject value = map.get(key);
			   //do something to value
			}
			
			Iterator iterator = mapA.keySet().iterator();
			while(iterator.hasNext(){
			  Object key   = iterator.next();
			  Object value = mapA.get(key);
			}

			//access via new for-loop
			for(Object key : mapA.keySet()) {
			    Object value = mapA.get(key);
			}
			*/
		
		
		return retval;
	}	
	
	
	

}
