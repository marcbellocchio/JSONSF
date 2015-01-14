/**
 * 
 */
package JSONSFCRYPTO;

import gnu.crypto.hash.HashFactory;
import gnu.crypto.hash.IMessageDigest;
import gnu.crypto.hash.Sha512;
import gnu.crypto.hash.Whirlpool;

/**
 * @author bellocch
 *
 */
public class JSONSF_CryptoWhirlpoolHash extends JSONSF_Crypto{

	/**
	 * 
	 */
	public JSONSF_CryptoWhirlpoolHash() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Whirlpool-T version of a byte buffer
	 * return a byte buffer encoded in base 64
	 */
	public  byte[] getWhirlpoolHash(byte[] input){
		Whirlpool whirlpool=new Whirlpool();
		whirlpool.update(input, 0, input.length);
		return (Base64Encode(whirlpool.digest())).getBytes();
	}
 
	/**
	 * Whirlpool-T version of a string
	 * return a ingstr encoded in base 64
	 */
	public String Hash_Whirlpool (String strToHash){
		
	     byte[] byteHash = {0};
		
	     IMessageDigest md_Whirlpool = HashFactory.getInstance("Whirlpool");
	     //MessageDigest md;
	     if (md_Whirlpool.selfTest()){
	    	 System.out.println("self test ok");
	    	 
	     }
	     byteHash = strToHash.getBytes();
	     md_Whirlpool.reset();
	     md_Whirlpool.update(byteHash, 0, byteHash.length);
	     
	     byteHash = md_Whirlpool.digest();
		
		return Base64Encode (byteHash);
		
	}
		
}
