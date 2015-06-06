/**
 * 
 */
package JSONSFFILE;

import JSONSFGLOBAL.Constants ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.parser.*;

/**
 * JSON encoding decoding https://code.google.com/p/json-simple/wiki/DecodingExamples
 */

/**
 * @author bellocch
 * @usage write in a json file the data entered by a user in a graphical interface
 */
public class JSONSF_WriteFile {
	// private data
	
	// filename to open
	private String PathAndFileName;
	// a line read from String PathAndFileName
	private String WriteLine ; 
	//
	private FileWriter FileForWriteLine; 
	
	private BufferedWriter BufferedWriterLine ;
	
	private Boolean SupportedVersionDetected;
	/**
	 * 
	 */
	public JSONSF_WriteFile(String FileName) {
		// TODO Auto-generated constructor stub
		PathAndFileName = FileName;
    	SupportedVersionDetected = false;
    	WriteLine= "";	
	}
	
	   /**
	    * @brief OpenFile
	    * @usage open a buffered writer for the file, 
	    * @param none as provided in the constructor           
	    * @return void but exception sent if error
	    */
	    public int  OpenFile() throws IOException{
	    	int retval= Constants.Fail; 
	        try {
	            // FileWriter writes text files in the default encoding.
	        	 File fl = new File (PathAndFileName);
	        	 if (fl.length() > Constants.Version_all_MAXSIZE) 
	        		 retval = Constants.ErrFiletoBig;
	        	 else{
	        		 FileForWriteLine = new FileWriter(PathAndFileName);
		            // Always wrap FileWriter in BufferedWriter.
	        		 BufferedWriterLine = new BufferedWriter(FileForWriteLine);
		             retval = Constants.Success;
	        	 }      
	        }// end try
	        catch(FileNotFoundException ex) {
	            System.out.println("Unable to write file '" + PathAndFileName + "'"); 
	            System.out.println( ex.getMessage() );
	            ex.printStackTrace();
	        }
	   	 	return retval;
	    }	
	    
	    /**
	     * @brief Write a MAP in a file in json format
	     * @usage when the map shall be written to file in a json format
	     * @param Map <StringBuffer, StringBuffer> MapToWrite           
	     * @return true when a line
	     */
	     public void WriteMap(Map <StringBuffer, StringBuffer> MapToWrite) throws IOException{
	     	
    		StringBuffer key=null;
    		StringBuffer value=null;
    		String LineToWrite = null;
    		
	     	try{
	     		// write {
	     		BufferedWriterLine.write(Constants.JSON_Marker_Begin);
	     		// loop all map file and write to file	    		
	    		Iterator <StringBuffer>iterator = MapToWrite.keySet().iterator();
	    		while(iterator.hasNext()){
	    			// get all fields
	    			key   = (StringBuffer)iterator.next();
	    			value = MapToWrite.get(key.toString());
	    			LineToWrite = "\" " + key.toString() + " \" " + ":" + "\" " + value.toString() + "\" " + "\r\n " ;
	    			BufferedWriterLine.write(LineToWrite);	     		
	     		BufferedWriterLine.write(Constants.JSON_Marker_End);
	    		}// end while
	     		
	     	}// end try
	         catch(IOException ex) {
	         	System.out.println("Error reading file '" + PathAndFileName + "'");   
	             System.out.println( ex.getMessage() );
	             ex.printStackTrace();
	         }
	     }

	    
	    
	    /**
	     * @brief CloseFile
	     * @usage close a buffered writer of the file, 
	     * @param none as provided in the constructor           
	     * @return void but exception sent if error
	     */
	     public void  CloseFile() throws IOException{
	         try {

	              FileForWriteLine.close();
	              // Always close files.
	              BufferedWriterLine.close(); 
	            	    
	         }// end try
	         catch(FileNotFoundException ex) {
	             System.out.println("Unable to close file "); 
	             System.out.println( ex.getMessage() );
	             ex.printStackTrace();
	         }
	     }	

}// end class

