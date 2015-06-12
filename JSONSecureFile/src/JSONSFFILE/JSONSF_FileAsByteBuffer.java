package JSONSFFILE;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

import JSONSFGLOBAL.Constants ;

public class JSONSF_FileAsByteBuffer {

	// filename to open
	private String PathAndFileName;
	private byte[] tmpbuffer;
	private Path path;
	private FileSystem fileSystem;
	private BasicFileAttributes fileattr;
	private String inputname;
	private boolean EncryptedInputFileDetected ; 
	
	File file;
	FileInputStream fis;
	ByteArrayOutputStream bos;
	
	/**
	 * the map contains a link to a file
	 * such the targetted fiel shall be encrypted or decrypted
	 * file is first opened, buffer on file is created
	 * then buffer is either encrypted or decrypted
	 * then file is created based on the buffer
	* @author mbl
	* @brief JSONSF_FileAsByteBuffer class
	* @usage when a file content needs to be decrypted or encrypted, get a byte buffer on a file
	* it will not chnage the source file, generates a second file in clear or encrypted
	* @param String FileName          
	* @return na
	*/
	public JSONSF_FileAsByteBuffer(String pathFileName) {
	//
		PathAndFileName = pathFileName;
		file = null;
		fis = null;
		bos = null;
		tmpbuffer = new byte[1000000];
		inputname = null;
		path= null;
		fileSystem = null;
		EncryptedInputFileDetected = false;
		fileattr = null;
	}

    /**
    * @brief IsOpenedFileEncrypted
    * @usage 
    * @param           
    * @return EncryptedInputFileDetected
    */    
	public boolean IsOpenedFileEncrypted(){
		return EncryptedInputFileDetected;
	}
	
    /**
    * @brief OpenFile
    * @usage when a file shall be opened to get a stream made of byte array
    * @param none as provided in the constructor           
    * @return Constants.Success when ok or Constants.Fail when file doesn't exist
    */    
	public int  OpenFile() throws IOException{
    	int retval= Constants.Fail; 
 
        try {        
        	 file = new File("PathAndFileName");
        	 if (file.exists() == true){
        		 fis = new FileInputStream(file);
        		 bos = new ByteArrayOutputStream();
        		 // get filename extension if .jencson at the end
        		 // the file should be decrypted
        		 fileSystem = FileSystems.getDefault();
        		 path = fileSystem.getPath(PathAndFileName);
        		 if(path.isAbsolute()==false) {
        			 path = path.toAbsolutePath();
        		 }
        		 fileattr = Files.readAttributes(path, BasicFileAttributes.class);
        		 if(fileattr.isRegularFile()==true) {
        			 inputname = path.getFileName().toString();
        			 if (inputname.contains(Constants.EncFileExtension)==true){
        				 EncryptedInputFileDetected = true;
                		 retval = Constants.Success;
        			 }	 
        		 }// if(fileattr.isRegularFile()==true)     		         		 
        	 }// end         	 if (file.exists() == true){

        }// end try
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + PathAndFileName + "'"); 
            System.out.println( ex.getMessage() );
            ex.printStackTrace();
        }
        catch(IOException ex) {
            System.out.println("Unable to open file '" + PathAndFileName + "'"); 
            System.out.println( ex.getMessage() );
            ex.printStackTrace();
        }

   	 		return retval;
        
    }
	
    /**
    * @brief GetFileBuffer returns the content of the file in a buffer
    * @usage when operation shall performed of the whole file buffer
    * @param none as provided in the constructor           
    * @return bytes array from file
    */    
	public byte []  GetFileBuffer() {
    	int bytenb=0;
    	
        try {        
            while (  (bytenb = fis.read(tmpbuffer)) != -1) {
                bos.write(tmpbuffer, 0, bytenb); 
            }
        }// end try
        catch(IOException ex) {
            System.out.println("Unable to GetFileBuffer '" + PathAndFileName + "'"); 
            System.out.println( ex.getMessage() );
            ex.printStackTrace();
        }
   	 	return  bos.toByteArray();
    }
	
    /**
    * @brief CreateFile generates a file using the buffer bytestowrite
    * @usage when GetFileBuffer has been used and crypto operation has been done on a buffer
    * @param byte [] bytestowrite          
    * @return true when file created, false otherwise
    */    
	public boolean  CreateFile(byte [] bytestowrite) {

    	Path localpath=null;
    	String PathFileNameToCreate=null;
    	File outFile=null;
    	FileOutputStream fos=null;
    	boolean filecreated=false;
    	// get parent of input file
    	// add Constants.EncFileExtension  if clear file otherwise removed it
    	// create file using created path
    	
    	// TODO compare bytestowrite and bos.toByteArray() when equals nothing has been done of the buffer !
    	
        try {     
        	localpath = path.getParent();
        	if(EncryptedInputFileDetected==false){
        		PathFileNameToCreate = localpath.toString() + inputname + Constants.EncFileExtension;
        	}
        	else{
        		PathFileNameToCreate = localpath.toString() + inputname.replace(Constants.EncFileExtension,"");
        	}
            //create file
        	outFile = new File(PathFileNameToCreate);
            fos = new FileOutputStream(outFile);
            fos.write(bytestowrite);
            fos.flush();
            fos.close();
            filecreated=true;
            
        }// end try
        catch(IOException ex) {
            System.out.println("Unable to GetFileBuffer '" + PathAndFileName + "'"); 
            System.out.println( ex.getMessage() );
            ex.printStackTrace();
        }
   	 	return  filecreated;
    }

}
