package JSONSFCRYPTO;

import JSONSFGLOBAL.Constants;

public class JSONSF_CryptoDecipher_TFSPCBC extends  JSONSF_CryptoDecipher_SerpentCBC{

	public JSONSF_CryptoDecipher_TFSPCBC() {
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
	    * @return decrypted byte buffer or null on error
	    */
		public byte [] TwoFishSerpentCBC( byte [] key_bytes, byte [] iv_bytes, byte [] plainIn ){
		
			byte [] DecipherOutTwoFish = null ;
			byte [] DecipherOutFinal = null ;
			
			DecipherOutTwoFish = SerpentCBC (key_bytes, iv_bytes, plainIn );
			if (DecipherOutTwoFish!=null){
				DecipherOutFinal = TwoFishCBC (key_bytes, iv_bytes, DecipherOutTwoFish );
				  
			}
			//Wipe(key_bytes, Constants.WIPEMETHOD);				
			return DecipherOutFinal;

		}	
	
	
	
}
