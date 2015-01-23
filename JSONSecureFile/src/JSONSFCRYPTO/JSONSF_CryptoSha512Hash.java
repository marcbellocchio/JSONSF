/**
 * 
 */
package JSONSFCRYPTO;

import gnu.crypto.hash.HashFactory;
import gnu.crypto.hash.IMessageDigest;
import gnu.crypto.hash.Sha512;

/**
 * @author bellocch
 *
 */
public class JSONSF_CryptoSha512Hash extends JSONSF_Crypto{

	/**
	 * 
	 */
	public JSONSF_CryptoSha512Hash() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Sha512 version of a string
	 * return a string encoded in base 64
	 */	
    /**
    * Sha512 version of a string
    * @param String strToHash         
    * @return String based64 encoded
    */
	public String getBase64EncStringSha512Hash (String strToHash){
		
	     byte[] byteHash = null;
		
	     IMessageDigest md_Sha512 = HashFactory.getInstance("Sha512");
	     byteHash = strToHash.getBytes();
	     md_Sha512.update(byteHash, 0, byteHash.length);
	     
	     byteHash = md_Sha512.digest();
		
		return Base64Encode (byteHash);
		
	}
	
	/**
	 * Sha512 version of a string
	 * return a string 
	 */	
    /**
    * Sha512 version of a string
    * @param String strToHash         
    * @return String 
    */
	public  String getStringSha512Hash(String strToHash){
	    byte[] byteHash = null;
	    
	    byteHash = strToHash.getBytes();
		Sha512 sha512=new Sha512();
		sha512.update(byteHash, 0, byteHash.length);
		return new String(sha512.digest());
	}

	/**
	 * Sha512 version of a byte buffer
	 * return a byte buffer encoded in base 64
	 */	
    /**
    * Sha512 version of a buffer
    * @param byte[] input         
    * @return byte[]  encoded in base 64
    */
	public  byte[] getBase64EncBufSha512Hash(byte[] input){
		Sha512 sha512=new Sha512();
		sha512.update(input, 0, input.length);
		return (Base64Encode(sha512.digest())).getBytes();
	}
	
	/**
	 * Sha512 version of a byte buffer
	 * return a byte buffer 
	 */	
    /**
    * Sha512 version of a buffer
    * @param byte[] input         
    * @return byte[]  
    */
	public  byte[] getBufSha512Hash(byte[] input){
		Sha512 sha512=new Sha512();
		sha512.update(input, 0, input.length);
		return sha512.digest();
	}
	
}
