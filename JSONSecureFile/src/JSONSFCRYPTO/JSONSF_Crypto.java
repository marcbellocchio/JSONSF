package JSONSFCRYPTO;

//import gnu.crypto.cipher.Blowfish;

//import java.security.Security;
//import java.util.Enumeration;

import gnu.crypto.util.Base64;

import java.io.*;
import java.util.Arrays;

/**
* @author mbl
* see manual for gnu crypto http://www.gnu.org/software/gnu-crypto/manual/index.html
* main site http://www.gnu.org/software/gnu-crypto/
* mother class for crypto operation
* provide base64 encoding decoding
*/


public class JSONSF_Crypto {
	
	// block size for cipher are in bytes 
	public static final int BitBlock128Bit = 16;
	public static final int BitBlock256Bit = 32;
	
    public static final String SIZE_ERROR = "size is 128 or 192 or 256 bit";
    public static final String UNKNOWNTYPE = "unknown type";
    public static final String NULL = "null";    
    
    public static final String IV = "IV";
    public static final String KEY = "KEY";
    public static final String DATA = "DATA";
	
	public JSONSF_Crypto(){
		
	}
	
	/**
	 * check if param is not null and if length is ok
	 * @param String type, bytes inparam
	 *         type can be IV, KEY, DATA
     *            
     * @return true when ok, 
     * false on error (shall never occur as exception is raised)
	 */
	public boolean IsParamValid ( String Type, byte [] inparam) {
		
		boolean valid=false; 

		
		switch (Type){
			case IV:
				if (inparam == null)
					throw new IllegalArgumentException(IV + "is" + NULL);
				if ( (inparam.length < BitBlock128Bit) || (inparam.length > BitBlock256Bit)){
					throw new IllegalArgumentException(IV + "shall have a" + SIZE_ERROR);
				}
				valid=true;				
				break;

			case KEY:
				if (inparam == null)
					throw new IllegalArgumentException(KEY + "is" + NULL);
				if ( (inparam.length < BitBlock128Bit) || (inparam.length > BitBlock256Bit)){
					throw new IllegalArgumentException(KEY + "shall have a" + SIZE_ERROR);
				}
				valid=true;				
				break;
				
			case DATA:
				if (inparam == null)
					throw new IllegalArgumentException(DATA + "is" + NULL);
				valid=true;				
				break;
				
			default:
				throw new IllegalArgumentException(UNKNOWNTYPE);				
		}

		return valid;
	}
	
	/**
	 * Base64Encode a string and return a string
	 */
	public static final String Base64Encode ( String strToEncode) {
		
		String encryptedString = Base64.encode(strToEncode.getBytes());
		return encryptedString;
		
	}
	/**
	 * Base64Encode a byte buffer and return a string
	 */	
	public static final String Base64Encode ( byte[] byteToEncode) {
		
		String encryptedString;
		encryptedString = Base64.encode(byteToEncode);
		return encryptedString;
		
	}
	/**
	 * Base64Encode a string and return a string
	 */
	public static final String Base64Decode ( String strToDecode) {
		
		byte[] decryptedByteArray= {0};
		try {
			decryptedByteArray = Base64.decode(strToDecode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decryptedByteArray.toString();
		
	}
	
	/**
	 * Base64Encode a string and return a string
	 */
	public static final byte[] Base64DecodeStrToByteBuffer ( String strToDecode) {
		
		byte[] decryptedByteArray= {0};
		try {
			decryptedByteArray = Base64.decode(strToDecode);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decryptedByteArray;
		
	}

  /**
   * Turns an array of bytes into a String representing each byte as an
   * unsigned hex number.
   * <p>
   * Method by Santeri Paavolainen, Helsinki Finland 1996<br>
   * (c) Santeri Paavolainen, Helsinki Finland 1996<br>
   * Distributed under LGPL.
   *
   * @param bytes
   *            an array of bytes to convert to a hex-string
   * @return generated hex string
   */
  public static final String encodeHex(byte[] bytes) {
      StringBuffer buf = new StringBuffer(bytes.length * 2);
      int i;

      for (i = 0; i < bytes.length; i++) {
          if (((int) bytes[i] & 0xff) < 0x10) {
              buf.append("0");
          }
          buf.append(Long.toString((int) bytes[i] & 0xff, 16));
      }
      return buf.toString();
  }
  
  /**
   * Turns an array of bytes into a String representing each byte as an
   * unsigned hex number.
   * <p>
   * Method by Santeri Paavolainen, Helsinki Finland 1996<br>
   * (c) Santeri Paavolainen, Helsinki Finland 1996<br>
   * Distributed under LGPL.
   *
   * @param bytes
   *            an array of bytes to convert to a hex-string
   * @return generated hex string
   */
  public static final String encodeDec(byte[] bytes) {
      StringBuffer buf = new StringBuffer(bytes.length * 2);
      int i;

      for (i = 0; i < bytes.length; i++) {
          if (((int) bytes[i] & 0xff) < 0x10) {
              buf.append("0");
          }
          buf.append(Long.toString((int) bytes[i] & 0xff, 10));
      }
      return buf.toString();
  }
  
  /**
   * Turns a hex encoded string into a byte array. It is specifically meant to
   * "reverse" the toHex(byte[]) method.
   *
   * @param hex
   *            a hex encoded String to transform into a byte array.
   * @return a byte array representing the hex String[
   */
  public  static final byte[] decodeHex(String hex) {
      char[] chars = hex.toCharArray();
      byte[] bytes = new byte[chars.length / 2];
      int byteCount = 0;
      for (int i = 0; i < chars.length; i += 2) {
          byte newByte = 0x00;
          newByte |= hexCharToByte(chars[i]);
          newByte <<= 4;
          newByte |= hexCharToByte(chars[i + 1]);
          bytes[byteCount] = newByte;
          byteCount++;
      }
      return bytes;
  }
  
  
  /**
   * Returns the the byte value of a hexadecimal char (0-f). It's assumed
   * that the hexadecimal chars are lower case as appropriate.
   *
   * @param ch
   *            a hexadecimal character (0-f)
   * @return the byte value of the character (0x00-0x0F)
   */
  private static final byte hexCharToByte(char ch) {
      switch (ch) {
      case '0':
          return 0x00;
      case '1':
          return 0x01;
      case '2':
          return 0x02;
      case '3':
          return 0x03;
      case '4':
          return 0x04;
      case '5':
          return 0x05;
      case '6':
          return 0x06;
      case '7':
          return 0x07;
      case '8':
          return 0x08;
      case '9':
          return 0x09;
      case 'a':
      case 'A':	
          return 0x0A;
      case 'b':
      case 'B':
          return 0x0B;
      case 'c':
      case 'C':
          return 0x0C;
      case 'd':
      case 'D':
          return 0x0D;
      case 'e':
      case 'E':
          return 0x0E;
      case 'f':
      case 'F':	
          return 0x0F;
      }
      return 0x00;
  }   

	/**
	* @brief wipe
	* @usage wipe sensitive data after use like key or input data field
	* @param[in] byte[] bytes the data to wipe
	* @param[in] String method (fast or secure)      
	* @return void
	*/
public  static final void Wipe(StringBuffer Strbuftowipe, String method) {
	  
		byte [] data = new byte [Strbuftowipe.length()];
		Arrays.fill( data, (byte) 0x45 );

		JSONSF_Crypto CryptoHelp = new JSONSF_Crypto();

		
	  switch (method){
	  	case "fast":  		
	  		Strbuftowipe.replace(0, Strbuftowipe.length(), CryptoHelp.Base64Encode(data));
	  		break;
	  	case "secure":
	  		// rc6
	  		break;
	  	default: // fast
	  		Strbuftowipe.replace(0, Strbuftowipe.length(), CryptoHelp.Base64Encode(data));
	  		break;
	  }

    
}
  
	/**
	* @brief wipe
	* @usage wipe sensitive data after use like key or input data field
	* @param[in] byte[] bytes the data to wipe
	* @param[in] String method (fast or secure)      
	* @return void
	*/
  public  static final void Wipe(byte[] bytestowipe, String method) {
	  
	  switch (method){
	  	case "fast":
	  		//bytestowipe.length
	  		//System.arraycopy(pt1, 0, finalplain, 0, pt1.length);
	  		Arrays.fill( bytestowipe, (byte) 0x0F );

	  		break;
	  	case "secure":
	  		// rc6
	  		
	  		break;
	  	default: // fast
		  	Arrays.fill( bytestowipe, (byte) 0x0F );
	  		break;
	  }

      
  }
  
}
