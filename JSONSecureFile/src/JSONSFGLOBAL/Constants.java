package JSONSFGLOBAL;

/**
 * code from package org.xbill.mDNS;
 */

/**
 * @author not mbl
 *
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

    /** need to comment ? */
    public static final int Zero = 0;
    
	/** ### CRYPTO Constants ### */	    
    /**  */
    public static final String TWOFISHCBC = "twofishcbc";
    
    public static final String SERPENTCBC = "serpentcbc";

}

