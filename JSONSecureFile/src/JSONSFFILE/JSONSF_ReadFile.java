/**
 * 
 */
package JSONSFFILE;

import JSONSFGLOBAL.Constants ;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import org.json.simple.parser.*;

import org.json.simple.parser.ContentHandler;
import org.json.simple.parser.ParseException;

/**
 * 
 */

/**
 * @author mbl
 * JSON encoding decoding https://code.google.com/p/json-simple/wiki/DecodingExamples
 */
public class JSONSF_ReadFile {
	

	
	// ################ JSONSF_ReadFile #############################
	// private data
	
	// filename to open
	private String PathAndFileName;
	// a line read from String PathAndFileName
	private String ReadLine ; 
	
	private Boolean SupportedVersionDetected;
	
	// constructor 
    public JSONSF_ReadFile(String FileName)
    throws IOException
    {
    	PathAndFileName = FileName;
    	SupportedVersionDetected = false;
    	ReadLine= "";
    	
    }	

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
                	
            	if ( SupportedVersionDetected == false){
                    if ( (ReadLine.contentEquals(Constants.JSON_Marker_Begin)==false) 
                	&& (ReadLine.contentEquals(Constants.JSON_Marker_End)== true) ) {
                    	
                    	JSONSF_Analyser jsonanalyser = new JSONSF_Analyser (ReadLine);
                    	if (jsonanalyser.CheckVersion() == true ){
                    		// version detected, numberoflines known
                    		SupportedVersionDetected = true;
                    	}	// end if (jsonanalyser.CheckVersion() == true )	
                    }//	if ( (ReadLine.contentEquals(Constants.JSON_Marker_Begin)==false)
                	
            	}// end if ( VersionDetected == false){
                	
                // add each read line in a buffer
                strBuffer.append(ReadLine);
                
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
    
	// ################ END OF JSONSF_ReadFile #############################
    
    // embedded class
	public static class JSONSF_Analyser {
		// short class to analyse the JSON file already opened
		// will received the first string of the file and will return the number of line of the file
		// without reading the whole file. the number of lines is based on the version
		// it must be the first line of the file
		
		// private data
		
		private String Firstline ; 
		private int NumberOfLines; 
		private int Version; 		
 
		
		public JSONSF_Analyser( String Line) {
			// transform a string from a json in a single json file
			// add { } to the string and remove , to the line to create a good s + Constants.JSON_Marker_End ;
			// version zero is not used in file, version start by 1
			Firstline = Constants.JSON_Marker_Begin + Line.replaceFirst(",", "") +  Constants.JSON_Marker_End;
            Version = 0;
            NumberOfLines = 0;;
		}// end JSONSF_Analyser
		
		public int GetNumberOfLines(){
			
			return NumberOfLines;
			
		}

		public int GetVersion(){
			
			return Version;
			
		}
		
		//public int GetNumberoflines () throws org.json.simple.parser.ParseException{
		// detect the version in the opened file
		// return true when version is detected and supported, otherwise it is false
		public  Boolean CheckVersion () {
	        Boolean VersionDetected = false;
			// decode first line to get version if version is not in the string return 0
			JSONParser parser = new JSONParser();
			KeyFinder finder = new KeyFinder();
			// now configure keyfinder to find version in the string Firstline
			finder.setMatchKey(Constants.Version);

			try{
			    while(!finder.isEnd()){
			    	parser.parse(Firstline, finder, true);
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
		
		}// end of GetNumberoflines
		
	
    }// end of JSONSF_Analyser class
    
}    
