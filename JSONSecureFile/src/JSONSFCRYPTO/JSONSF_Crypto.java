package JSONSFCRYPTO;

//import gnu.crypto.cipher.Blowfish;

//import java.security.Security;
//import java.util.Enumeration;

import gnu.crypto.util.Base64;

import java.io.*;

/**
* @author mbl
* see manual for gnu crypto http://www.gnu.org/software/gnu-crypto/manual/index.html
* main site http://www.gnu.org/software/gnu-crypto/
* mother class for crypto operation
* provide base64 encoding decoding
*/


public class JSONSF_Crypto {
	
	
	public JSONSF_Crypto(){
		
	}
	/**
	 * Base64Encode a string and return a string
	 */
	public String Base64Encode ( String strToEncode) {
		
		String encryptedString = Base64.encode(strToEncode.getBytes());
		return encryptedString;
		
	}
	/**
	 * Base64Encode a byte buffer and return a string
	 */	
	public String Base64Encode ( byte[] byteToEncode) {
		
		String encryptedString;
		encryptedString = Base64.encode(byteToEncode);
		return encryptedString;
		
	}
	/**
	 * Base64Encode a string and return a string
	 */
	public String Base64Decode ( String strToDecode) {
		
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

}
