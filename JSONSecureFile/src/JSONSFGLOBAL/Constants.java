package JSONSFGLOBAL;

/**
 * code from package org.xbill.mDNS;
 */

/**
 * @author not mbl
 * but mbl is used to have global class in C++
 */

public interface Constants
{
	/** ### JSON Constants ### */	
	
	
    /**  */
    public static final String JSON_Marker_Begin = "{";
    
    /**  */
    public static final String JSON_Marker_End = "}"; 
    
    public static final String Empty = "EmptyField";
    
    /** the mandatory field of the json file */
	public static final  String Version = "version";

    /** MAX size of the json file for all version */
    public static final int Version_all_MAXSIZE = 1000000;	
	
	
    /** MAX number of line of the json file for all version */
    public static final int Version_all_MAXNumLines = 5;	
    
    /** current supported version */
    public static final int Version_MAX = 1;	
        
	
	/** ### JSON Version 1 ### */
    /** number of line of the json file */
    public static final int Version_1_NumLines = 5;
   
    /**  field of the json file */
	public static final  String Version_1_Fields = " category"  + " filetype" + " hash" + " data";

	/** ### JSON Version 2 ### */
    /** number of line of the json file */
    public static final int Version_2_NumLines = 5;
   
    /**  field of the json file */
	public static final  String Version_2_Fields =  " category"  + " filetype" + " hash" + " data";
	
    
	/** ### CRYPTO Constants ### */	    
    /**  */
    public static final String TWOFISHCBC = "twofishcbc";
    
    public static final String SERPENTCBC = "serpentcbc";
    
    public static final String TWOFISHSERPENTCBC = "tfspcbc";
 
	/** ### ERROR management Constants ### */	    
    /**  */
    
    /** need to comment ? */
    public static final int Zero = 0;
    
    /** need to comment ? */
    public static final int Un = 1;
 
    /** need to comment ? */
    public static final int Success = Zero;
    
    /** need to comment ? */
    public static final int Fail = 12345678;
    
    /** file to big */
    public static final int ErrFiletoBig = 20;
    
    /** too many line in json file  */
    public static final int ErrToMuchLline = 21;
    
    /** version not supported  */
    public static final int ErrVersionNotPresent = 22; 
    
    /** version not supported  */
    public static final int ErrVersionNotSupported = 23;  
    
    
    
}

