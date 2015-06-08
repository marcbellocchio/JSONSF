/**
 * 
 */
package JSONSFFILE;

import JSONSFGLOBAL.Constants ;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;

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
	        	FileForWriteLine = new FileWriter(fl);
	            // Always wrap FileWriter in BufferedWriter.
        		 BufferedWriterLine = new BufferedWriter(FileForWriteLine);
	             retval = Constants.Success;
	        	     
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
	     * @return void
	     */
	     public void WriteMap(Map <StringBuffer, StringBuffer> MapToWrite) throws IOException{
	     	
    		StringBuffer key=null;
    		StringBuffer value=null;
    		String LineToWrite = null;
    		int nbmapkeyvalue=0 ; 
    		
	     	try{
	     		// write {
	     		BufferedWriterLine.write(Constants.JSON_Marker_Begin + "\r\n");
	     		MapToWrite.size();
	     		// loop all map file and write to file	    		
	    		Iterator <StringBuffer>iterator = MapToWrite.keySet().iterator();
	    		while(iterator.hasNext()){
	    			// get all fields
	    			key   = (StringBuffer)iterator.next();
	    			value = MapToWrite.get(key);
	    			LineToWrite = "\"" + key.toString() + "\" " + ":" + "\"" + value.toString() + "\"" ;
	    			if(++nbmapkeyvalue == MapToWrite.size() )
	    				LineToWrite = LineToWrite + "\r\n";
	    			else
	    				LineToWrite = LineToWrite + "," + "\r\n";

	    			BufferedWriterLine.write(LineToWrite);	     		
	     		
	    		}// end while
	    		BufferedWriterLine.write(Constants.JSON_Marker_End);
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
	              BufferedWriterLine.flush();
	              BufferedWriterLine.close(); 
	              
	        	 //FileForWriteLine.flush();
	        	 //FileForWriteLine.close();
	              // Always close files.

	            	    
	         }// end try
	         catch(FileNotFoundException ex) {
	             System.out.println("Unable to close file "); 
	             System.out.println( ex.getMessage() );
	             ex.printStackTrace();
	         }
	     }	

}// end class

