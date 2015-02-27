/**
 * 
 */
package JSONSFCRYPTO;


/**
 * @author bellocch
 * class that will cascade twofishcbc, then serpentcbc
 */
public class JSONSF_CryptoCipher_TFSPCBC extends  JSONSF_CryptoCipher_SerpentCBC {

	/**
	 * 
	 */
	public JSONSF_CryptoCipher_TFSPCBC() {
		// TODO Auto-generated constructor stub
	}
	
	   /**
	    * TwoFishSerpent  CBC 
	    * <p>
	    * gnu crypto java lib used
	    * block size 128 bit, key size 128, 192, 256
	    * 
	    * @param bytes buffer
	    *            key, IV and plain data are byte buffer
	    * @return encrypted byte buffer pad is PKCS7 or null on error
	    */
		public byte [] TwoFishSerpentCBC( byte [] key_bytes, byte [] iv_bytes, byte [] plainIn ){
		
			byte [] cipherOutTwoFish = null ;
			byte [] cipherOutFinal = null ;
			
			cipherOutTwoFish = TwoFishCBC (key_bytes, iv_bytes, plainIn );
			if (cipherOutTwoFish!=null){
				cipherOutFinal = SerpentCBC (key_bytes, iv_bytes, plainIn );
			}
						
			return cipherOutFinal;

		}	
	

}
