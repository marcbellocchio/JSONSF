/**
 * 
 */
package JSONSFFILE;

import JSONSFGLOBAL.Constants ;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



import org.json.simple.parser.*;

/**
 * JSON encoding decoding https://code.google.com/p/json-simple/wiki/DecodingExamples
 */

/**
* @author mbl
* @brief JSONSF_ReadFile class
* @usage when a file needs to be opened to check its format, and get data 
* @param na
*            
* @return na
*/
public class JSONSF_ReadJsonFile {
	
	
	// private data
	// filename to open
	private String PathAndFileName;
	// a line read from String PathAndFileName
	private String ReadLine ; 
	//
	private FileReader FileForGetLine; 
	
	private BufferedReader BufferedReaderForGetLine ;
	
	private Boolean SupportedVersionDetected;
	
	// constructor 
    public JSONSF_ReadJsonFile(String FileName)
    throws IOException
    {
    	PathAndFileName = FileName;
    	SupportedVersionDetected = false;
    	ReadLine= "";	
    }	
 
   
    
    /**
    * @brief OpenFile
    * @usage open a buffered reader of the file, to objective is to use getline after
    * @param none as provided in the constructor           
    * @return Constants.Success when ok or Constants.ErrFiletoBig when file contains more field than currently supported by the code 
    */
    public int  OpenFile() throws IOException{
    	int retval= Constants.Fail; 
        try {
            // FileReader reads text files in the default encoding.
        	 File fl = new File (PathAndFileName);
        	 if (fl.length() > Constants.Version_all_MAXSIZE) 
        		 retval = Constants.ErrFiletoBig;
        	 else{
	             FileForGetLine = new FileReader(PathAndFileName);
	            // Always wrap FileReader in BufferedReader.
	             BufferedReaderForGetLine = new BufferedReader(FileForGetLine);
	             retval = Constants.Success;
        	 }      
        }// end try
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + PathAndFileName + "'"); 
            System.out.println( ex.getMessage() );
            ex.printStackTrace();
        }
   	 	return retval;
    }
    
    /**
    * @brief CloseFile
    * @usage close a buffered reader of the file, to objective is to use getline after
    * @param none as provided in the constructor           
    * @return void but exception sent if error
    */
    public void  CloseFile() throws IOException{
        try {

             FileForGetLine.close();
             // Always close files.
             BufferedReaderForGetLine.close(); 
           	    
        }// end try
        catch(FileNotFoundException ex) {
            System.out.println("Unable to close file "); 
            System.out.println( ex.getMessage() );
            ex.printStackTrace();
        }
    }
    
    /**
    * @brief GetOneLine of a json file and store the value in Readline
    * @usage collect a line from the file, 
    * @param none           
    * @return true when a line
    */
    public boolean GetOneLine() throws IOException{
    	
    	boolean ret=false;
    	try{
    		while((ReadLine = BufferedReaderForGetLine.readLine()) != null) {
    			// remove useless line like { and  } that not needs to be interpreted
    			if ( (ReadLine.contentEquals(Constants.JSON_Marker_Begin)==false) 
            	&& (ReadLine.contentEquals(Constants.JSON_Marker_End)== false) ) {
                	// make each line of the json file a json file of one line
    				//JSONSF_LineAnalyser jsonanalyser = new JSONSF_LineAnalyser (ReadLine);
    				ret = true;
    				break;
                		
                }//	if ( (ReadLine.contentEquals(Constants.JSON_Marker_Begin)==false)
    			
    		}//end while
    		
    	}// end try
        catch(IOException ex) {
        	System.out.println("Error reading file '" + PathAndFileName + "'");   
            System.out.println( ex.getMessage() );
            ex.printStackTrace();
        }
    	return ret;

    }
    
    
    /**
    * @brief OpenTextFileLineByLine
    * @usage when a file shall be opened for decryption and reading
    * @param none as provided in the constructor           
    * @return StringBuffer containing each line of the opened file
    */
    public StringBuffer OpenTextFileLineByLine() throws IOException{
    	
    	StringBuffer strBuffer = new StringBuffer();
        
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(PathAndFileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((ReadLine = bufferedReader.readLine()) != null) {
                System.out.println(ReadLine);
                // skip markers of json file like { or  }
                //if ( (ReadLine.contentEquals(Constants.JSON_Marker_Begin)==true) 
                	//	|| (ReadLine.contentEquals(Constants.JSON_Marker_End)== true) ) {
                	//continue ;
                // file shall start by version	
            	if ( SupportedVersionDetected == false){
                    if ( (ReadLine.contentEquals(Constants.JSON_Marker_Begin)==false) 
                	&& (ReadLine.contentEquals(Constants.JSON_Marker_End)== false) ) {
                    	
                    	JSONSF_LineAnalyser jsonanalyser = new JSONSF_LineAnalyser (ReadLine);
                    	if (jsonanalyser.CheckVersion() == true ){
                    		// version detected, numberoflines known
                    		SupportedVersionDetected = true;
                    	}	// end if (jsonanalyser.CheckVersion() == true )
                        // add each read line in a buffer
                        strBuffer.append(ReadLine);
                    }//	
                	
            	}// end if ( VersionDetected == false){                	
                
            }// end while    

            // Always close files.
            bufferedReader.close();  
            // field version not present or version not supported
            if(!SupportedVersionDetected){
            	// delete all the string buffer
            	strBuffer.delete(0, strBuffer.length());
            }
            	    
        }// end try
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + PathAndFileName + "'"); 
            System.out.println( ex.getMessage() );
            ex.printStackTrace();
        }
        catch(IOException ex) {
        	System.out.println("Error reading file '" + PathAndFileName + "'");   
            System.out.println( ex.getMessage() );
            ex.printStackTrace();
        }
    	
    	return strBuffer;
    }
    
    /**
    * @brief GetReadLine
    * @usage to get one line
    * @param            
    * @return ReadLine
    */
    public String  GetReadLine() {
    	return ReadLine;
    }
    
	// ################ END OF JSONSF_ReadFile #############################
    
    /**
    * @brief JSONSF_Analyser class embedded as static class in JSONSF_ReadFile
    * will received the first string of the file and will return the number of line of the file
    * without reading the whole file. the number of lines is based on the version
    * version must be the first line of the file
    * @usage to analyse the JSON file already opened
    * @param none as provided in the constructor           
    * @return StringBuffer containing each line of the opened file
    */
	public static class JSONSF_LineAnalyser {
		// will received the first string of the file and will return the number of line of the file
		// without reading the whole file. the number of lines is based on the version
		// version must be the first line of the file
		
		// private data
		
		private String Analyzeline ; 
		private int NumberOfLines=0; 
		private int Version=0; 	
        private boolean VersionDetected = false;
 
	    /**

	    * @brief JSONSF_Analyser class embedded as static class in JSONSF_ReadFile
	    * transform a string from a json file in a single json file to be further interpreted by a json class
		* add { } to the string and remove the "," from the line to create a good s + Constants.JSON_Marker_End ;
	    * @usage to analyse the JSON file already opened
	    * @param none as provided in the constructor           
	    * @return StringBuffer containing each line of the opened file
	    */		
		public JSONSF_LineAnalyser( String Line) {
			Analyzeline = Constants.JSON_Marker_Begin + Line.replaceFirst(",", "") +  Constants.JSON_Marker_End;
		}// end JSONSF_Analyser
		
		public int GetNumberOfLines(){
			
			return NumberOfLines;
			
		}

		public int GetVersion(){
			
			return Version;
			
		}
		/**
	    * @brief check if version of the file is supported
	    * 
		* 
	    * @usage to check if the version in the json file is known and supported
	    * @param none           
	    * @return boolean, true when version is supported
	    */	
		public  Boolean CheckVersion () {
			// decode first line to get version if version is not in the string return 0
			JSONParser parser = new JSONParser();
			KeyFinder finder = new KeyFinder();
			// now configure keyfinder to find version in the string Firstline
			finder.setMatchKey(Constants.Version);

			try{
			    while(!finder.isEnd()){
			    	parser.parse(Analyzeline, finder, true);
			        if(finder.isFound()){
			          finder.setFound(false);
			          finder.getValue();
			          switch( Integer.valueOf( (String)finder.getValue()) ){
			          case 1 :
			        	  NumberOfLines = Constants.Version_1_NumLines;
			        	  Version = 1;
			        	  VersionDetected = true;
			          default :
		        	  
			        	  break;
			        	  
			          }// end switch	  
			         
			        }// end if
			    }// end while  
								
			}// end try
			catch(ParseException pe){
				System.out.println(pe);
			}
			
			return VersionDetected;
		
		}// end of CheckVersion	
		
	
    }// end of JSONSF_Analyser class
    
}    
