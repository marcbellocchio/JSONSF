/**
 * 
 */
package JSONSFCRYPTO;

import gnu.crypto.mode.IMode;
import gnu.crypto.mode.ModeFactory;
import gnu.crypto.pad.IPad;
import gnu.crypto.pad.PadFactory;

import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

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
	    * @return encrypted byte buffer pad is PKCS7
	    */
		public byte [] TwoFishSerpentCBC( byte [] key_bytes, byte [] iv_bytes, byte [] plainIn ){
		
			byte [] cipherOutTwoFish = null ;
			byte [] cipherOutFinal = null ;
			byte [] key_bytes_serpent = null ;
			boolean IsAllParamValid=false;
			
			cipherOutTwoFish = TwoFishCBC (key_bytes, iv_bytes, plainIn );
			if (cipherOutTwoFish!=null){
				cipherOutFinal = SerpentCBC (key_bytes, iv_bytes, plainIn );
			}
						
			return cipherOutFinal;

		}	
	

}
