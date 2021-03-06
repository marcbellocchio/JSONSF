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
    
    /** the version field of the json file */
	public static final  String Version = "version";

    /** the encryption method  field of the json file */
	public static final  String EncryptionMethod = "encm";
	
    /** the data field of the json file */
	public static final  String DATA = "data";	
	
	public static final  String FileTypeList =  " json"  + " txt" + "misc" ;
	
    /** MAX size of the json file for all version in bytes*/
    public static final int Version_all_MAXSIZE = 1000000;	
    
    /** current supported version */
    public static final int Version_MAX = 1;	
        
	
	/** ### JSON Version 1 ### */
    /** number of line of the json file (version + Version_1_Fields)*/
    public static final int Version_1_NumLines = 7;
   
    /**  field of the json file */
	public static final  String Version_1_Fields = " category"  + " filetype" + " desc" + " hash" + " encm" + " data";

	/** ### JSON Version 2 ### */
    /** number of line of the json file (version + Version_2_Fields) */
    public static final int Version_2_NumLines = 7;
   
    /**  field of the json file */
	public static final  String Version_2_Fields =  " category"  + " filetype" + " desc" + " hash" + " encm" + " data";
    
	/** MAX number of line of the json file for all version */
    public static final int Version_all_MAXNumLines = 7;	
	
	
	/** ### CRYPTO Constants ### */	    
    /**  */
    public static final int ENC_NONE = 0;
    
    public static final String TWOFISHCBC = "twofishcbc";
    
    public static final int ENC_TWOFISHCBC = 1;
    public static final int ENC_TWOFISHCBC256 = 4;
        
    public static final String SERPENTCBC = "serpentcbc";
    
    public static final int ENC_SERPENTCBC = 2;
    public static final int ENC_SERPENTCBC256 = 5;
    
    public static final String TWOFISHSERPENTCBC = "tfspcbc";
    
    public static final int ENC_TWOFISHSERPENTCBC = 3;    
    public static final int ENC_TWOFISHSERPENTCBC256 = 6;  
    
    public static final  String EncryptionMethodList =  " twofishcbc"  + " serpentcbc" + " tfspcbc" + " twofishcbc256"  + " serpentcbc256" + " tfspcbc256" ;
    
    // can be fast or secure
    public static final String WIPEMETHOD = "fast";
    
	/** MAX key length in bytes */
    public static final int MaxKeyLengthInBytes = 64;
 
	/** default key length in bytes */
    public static final int DefaultKeyLengthInBytes = 16;
    
	/** default key length in bytes */
    public static final int Bit256KeyLengthInBytes = 32;
    
	/** default IV length in bytes */
    public static final int DefaulBlockDataSizeInBytes = 16;
   
	/** default IV length in bytes */
    public static final int DefaulIVLengthInBytes = 16;
    
    public static String EncFileExtension = "jencson";
    
    
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
    public static final int ErrFiletoBig = 10020;
    
    /** too many line in json file  */
    public static final int ErrToMuchLline = 10021;
    
    /** version not supported  */
    public static final int ErrVersionNotPresent = 10022; 
    
    /** version not supported  */
    public static final int ErrVersionNotSupported = 10023;  
    
    /** encryption method not supported  */
    public static final int ErrEncMethodNotSupported = 10024;   
    
}

