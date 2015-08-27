package JSONSFCRYPTO;

import gnu.crypto.mode.IMode;
import gnu.crypto.mode.ModeFactory;
import gnu.crypto.pad.IPad;
import gnu.crypto.pad.PadFactory;

import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

import JSONSFGLOBAL.Constants ;

public class JSONSF_CryptoCipher_TwoFishCBC extends JSONSF_Crypto {

	public JSONSF_CryptoCipher_TwoFishCBC() {
		// TODO Auto-generated constructor stub
	}


    /**
    * TwoFish CBC 
    * <p>
    * gnu crypto java lib used
    * block size 128 bit, key size 128, 256
    * @param bytes buffer
    *            key, IV and plain data are byte buffer
    * @return encrypted byte buffer pad is PKCS7
    * 		  null when param are not in range	
    */
	public byte [] TwoFishCBC( byte [] key_bytes, byte [] iv_bytes, byte [] plainIn ){
	
		byte [] cipherOut = null ;
		boolean IsAllParamValid=false;
		
		// check inputs, exception throws
		IsAllParamValid = ( IsParamValid(KEY, key_bytes) && IsParamValid(IV, iv_bytes) && IsParamValid(DATA, plainIn) );
		
		if (IsAllParamValid == true){			
		
	        IPad padding = PadFactory.getInstance("PKCS7");
	        padding.init(Constants.DefaulBlockDataSizeInBytes);
	        byte[] pt1 = plainIn;
	        byte[] pad = padding.pad(pt1, 0, pt1.length);
	        byte[] finalplain = null;
	        // 
	        if (pad.length == Constants.DefaulBlockDataSizeInBytes) {
	        	// one block no pad
	        	finalplain = new byte[pt1.length];
	            System.arraycopy(pt1, 0, finalplain, 0, pt1.length);
	        } else { 
	        	// pad input buffer final buffer to encrypt is 
	        	finalplain = new byte[pt1.length + pad.length];
	            System.arraycopy(pt1, 0, finalplain, 0, pt1.length);
	            System.arraycopy(pad, 0, finalplain, pt1.length, pad.length);
	        }
	        
			IMode mode = ModeFactory.getInstance("CBC","Twofish", Constants.DefaulBlockDataSizeInBytes);
			Map<String, Object> attributes = new HashMap<String, Object>();
			// These attributes are defined in gnu.crypto.cipher.IBlockCipher.
			attributes.put(IMode.KEY_MATERIAL, key_bytes);
			attributes.put(IMode.CIPHER_BLOCK_SIZE, new Integer(Constants.DefaulBlockDataSizeInBytes));
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
			cipherOut = new byte[finalplain.length ];
			// note that the doc from gnu crypto is wrong for the loop count
			for (int i = 0; i +bs  <= finalplain.length; i += bs){
				mode.update(finalplain, i, cipherOut, i);
			}
		}// end if
		
		return cipherOut;

	}
	
	/**
	 * TwoFish version of string
	 * return a string hex encoded
	 */
    /**
    * TwoFish CBC 
    * <p>
    * gnu crypto java lib used
    * block size 128 bit, key size 128, 192, 256
    * @param bytes
    *            key, IV and plain data are string
    *            key and IV shall be hex encoded i.e
    *            3dafba429d9eb430b422da802c9fac41
    *            plain in is a string like "this is my bottle of wine"
    * @return encrypted string or "" when error
    */
	public String TwoFishCBC( String key_hexencoded, String iv_hexencoded, String plainIn ){
	    
		byte [] Cipheredbuf=null; 
		Cipheredbuf = TwoFishCBC(decodeHex(key_hexencoded), decodeHex(iv_hexencoded), plainIn.getBytes());
		if(Cipheredbuf!=null)
			return encodeHex(Cipheredbuf );
		else
			return "";	
	}
	
}// end class 
