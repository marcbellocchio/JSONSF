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
    
    /** the first field of the json file */
	public static final  String Version = "version";
   
	/** ### JSON Version 1 ### */
    /** number of line of the json file */
    public static final int Version_1_NumLines = 5;
   
    /**  field of the json file */
	public static final  String Version_1_Fields = "version" + " category"  + " filetype" + " hash" + " data";

	/** ### JSON Version 2 ### */
    /** number of line of the json file */
    public static final int Version_2_NumLines = 5;
   
    /**  field of the json file */
	public static final  String Version_2_Fields = "version" + " category"  + " filetype" + " hash" + " data";
	
    /** need to comment ? */
    public static final int Zero = 0;
 
    /** need to comment ? */
    public static final int Success = Zero;
    
    /** need to comment ? */
    public static final int Fail = 12345;
    
	/** ### CRYPTO Constants ### */	    
    /**  */
    public static final String TWOFISHCBC = "twofishcbc";
    
    public static final String SERPENTCBC = "serpentcbc";
    
    public static final String TWOFISHSERPENTCBC = "tfspcbc";

}

