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

	// stored encrypted method value read from file
	private int encMethod;
	// store all the fields to be inserted into the json file header
	private Map <StringBuffer, StringBuffer> jsonfileheaderExport;
	
	//public String stread;
	public String stdelimiter;
	public String[] streadarray;
	// store all the fields from the json file header
	public Map <String, String> jsonfileheaderImport;

	public JSONSF_firstlevel() {
		
		jsonfileheaderImport =  new HashMap <String, String> (); 
		jsonfileheaderExport =  new HashMap <StringBuffer, StringBuffer> (); 
		stdelimiter = ":";
		encMethod=0;
	}
	
	/**
	* @brief GetFieldsFromVersion
	* @usage provide the number of fields of the json file according to version
	* @param[in] int version        
	* @return int number of fields or fail when not supported
	*/	
	@SuppressWarnings("unchecked")
	public void SetMapForExport (HashMap ToExport){
		jsonfileheaderExport.putAll(ToExport);	
	}
	
	/**
	* @brief GetDataFromMapForExport
	* @usage return the clear data for encryption
	* @param[in]         
	* @return data when ok; null when data not found
	*/	
	public StringBuffer GetDataFromMapForExport (){
		StringBuffer key=null;
		StringBuffer value=null;
		StringBuffer ret = null;
		
		Iterator <StringBuffer>iterator = jsonfileheaderExport.keySet().iterator();
		while(iterator.hasNext()){
			// get remaining fields
			key   = (StringBuffer)iterator.next();
			value = jsonfileheaderExport.get(key.toString());
			if( key.equals(Constants.DATA) ){
				// exit from while and return value
				ret = value;
				break;
			}		
		}// end while(iterator.hasNext()){ 	
		return ret;
	}

	/**
	* @brief SetDataInMapForExport
	* @usage all encrypted data in the jsonfileheaderExport
	* @param[in]         
	* @return data when ok; null when data not found
	*/	
	public int SetDataInMapForExport (StringBuffer EncryptedData){
		int retval= Constants.Fail ;
		StringBuffer key=null;
		
		Iterator <StringBuffer>iterator = jsonfileheaderExport.keySet().iterator();
		while(iterator.hasNext()){
			// get remaining fields
			key   = (StringBuffer)iterator.next();
			if( key.equals(Constants.DATA) ){
				// replace clear value with encrypted data
				jsonfileheaderExport.replace(key, EncryptedData);
				retval = Constants.Success;
				break;
			}		
		}// end while(iterator.hasNext()){ 	
		return retval;
	}
	
	/**
	* @brief ExportToFile
	* @usage create a json file from input data
	* @param[in] String FileName  
	* @param[class member]     
	* @return integer Constants.Success when ok or Constants.Fail or Constants.ErrFiletoBig
	*/
	public int ExportToFile(String FileName) {
		int retval= Constants.Fail ; 
		// to complete ...........
		// jsonfileheader contains all the TAG value to be inserted in the file
		// then shall be converted in json
		// finally shall be written in the file
		
		return retval;
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
					// each line is a TAG : VALUE
					streadarray=filetoread.GetReadLine().split(stdelimiter);
					// remove each " white space and , from the string
					// streadarray[0] is TAG, streadarray[1] is value
					jsonfileheaderImport.put(streadarray[0].replace('"', ' ').trim(), streadarray[1].replace('"', ' ').replace(',', ' ').trim());
					
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
	* @return encMethod 0: none 1:twofishcbc 2:serpentcbc 3:tfspcbc
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
		    case 0: // none, clear data
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
	* @brief  Validate the input json file, please note that version (key) must be the first field of the file to recover all the others
	* @usage  check if the map contains valid data
	* @param  [in] String FileName
	* @return Constants.Success when ok       
	*  Constants.ErrToMuchLline when more lines than expected
	*  Constants.ErrVersionNotSupported 
	*  Constants.ErrVersionNotPresent 
	*  Constants.ErrEncMethodNotSupported  
	*/
	public int ValidateImport() {
		int retval= Constants.Fail ; 
		int version=0;
		int nbfields=0;
		int index = -1;
		boolean EncMethodSupported = false;
		String key=null;
		String value=null;
		String Fields=null;
		String EncryptionMethod = Constants.EncryptionMethod; 
		
		if(jsonfileheaderImport.size() <= Constants.Version_all_MAXNumLines){
			// check if the version key is present in first place
			value = jsonfileheaderImport.get(Constants.Version);
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
						Iterator <String>iterator = jsonfileheaderImport.keySet().iterator();
						while(iterator.hasNext()){
							// get remaining fields
							key   = (String)iterator.next();
							value = jsonfileheaderImport.get(key);
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
			
		return retval;
	}	
	
	/**
	* @brief  Validate the output data to be written in the json file, please note that version (key) must be the first field of the file to recover all the others
	* @usage  check if data to export are supported
	* @param  [in] 
	* @return Constants.Success when ok       
	*  Constants.ErrToMuchLline when more lines than expected
	*  Constants.ErrVersionNotSupported 
	*  Constants.ErrVersionNotPresent 
	*  Constants.ErrEncMethodNotSupported  
	*/
	public int ValidateExport() {
		int retval= Constants.Fail ; 
		int version=0;
		int nbfields=0;
		int index = -1;
		boolean EncMethodSupported = false;
		StringBuffer key=null;
		StringBuffer value=null;
		String Fields=null;
		String EncryptionMethod = Constants.EncryptionMethod; 
		
		if(jsonfileheaderExport.size() <= Constants.Version_all_MAXNumLines){
			// check if the version key is present in first place
			value = jsonfileheaderExport.get(Constants.Version);
			if ((value==null) )
				retval= Constants.ErrVersionNotPresent ;	
			else{
				version = Integer.valueOf(value.toString());
				if (version > Constants.Version_MAX)
					retval= Constants.ErrVersionNotSupported ;
				else{
					// now can parse other keys
					Fields= GetFieldsFromVersion(version);
					if(Fields!=null){
						// count version now
						nbfields++;
						Iterator <StringBuffer>iterator = jsonfileheaderExport.keySet().iterator();
						while(iterator.hasNext()){
							// get remaining fields
							key   = (StringBuffer)iterator.next();
							value = jsonfileheaderExport.get(key.toString());
							// check if key is part of Fields
							index = Fields.indexOf(key.toString());
							if(index >= Constants.Zero){
								nbfields++;
							}	
							if( key.equals(EncryptionMethod) ){
								// check if it is supported
								if(CheckEncryptionAlgorithm(Integer.valueOf(value.toString()))==Constants.Success){
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
		return retval;
	}	
	
}
