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
	    * twofish
	    * @param bytes buffer
	    *            key, IV and plain data are byte buffer
	    * @return encrypted byte buffer pad is PKCS7
	    */
		public byte [] TwoFishSerpentCBC( byte [] key_bytes, byte [] iv_bytes, byte [] plainIn ){
		
			byte [] cipherOutTwoFish = null ;
			byte [] cipherOutFinal = null ;
			byte [] key_bytes_serpent = null ;
			
	        IPad padding = PadFactory.getInstance("PKCS7");
	        padding.init(BitBlock128Bit);
	        byte[] pt1 = plainIn;
	        byte[] pad = padding.pad(pt1, 0, pt1.length);
	        byte[] finalplain = null;
	        // 
	        if (pad.length == BitBlock128Bit) {
	        	// one block no pad
	        	finalplain = new byte[pt1.length];
	            System.arraycopy(pt1, 0, finalplain, 0, pt1.length);
	        } else { 
	        	// pad input buffer final buffer to encrypt is 
	        	finalplain = new byte[pt1.length + pad.length];
	            System.arraycopy(pt1, 0, finalplain, 0, pt1.length);
	            System.arraycopy(pad, 0, finalplain, pt1.length, pad.length);
	        }
	        
			IMode mode = ModeFactory.getInstance("CBC","Twofish", BitBlock128Bit);
			Map<String, Object> attributes = new HashMap<String, Object>();
			// These attributes are defined in gnu.crypto.cipher.IBlockCipher.
			attributes.put(IMode.KEY_MATERIAL, key_bytes);
			attributes.put(IMode.CIPHER_BLOCK_SIZE, new Integer(BitBlock128Bit));
			// These attributes are defined in IMode.
			attributes.put(IMode.STATE, new Integer(IMode.ENCRYPTION));
			attributes.put(IMode.IV, iv_bytes);
			try {
				mode.init(attributes);
			} catch (InvalidKeyException | IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int bs = mode.currentBlockSize();
			cipherOutTwoFish = new byte[finalplain.length ];
			// note that the doc from gnu crypto is wrong for the loop count
			for (int i = 0; i +bs  <= finalplain.length; i += bs){
				mode.update(finalplain, i, cipherOutTwoFish, i);
			}
			
			
			
			
			
			
			return cipherOutFinal;

		}	
	

}
