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
 *
 */
public class JSONSF_CryptoDecipher_TwoFishCBC extends JSONSF_Crypto{

	/**
	 * 
	 */
	public JSONSF_CryptoDecipher_TwoFishCBC() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * TwoFish version of a byte buffer
	 * return a byte buffer 
	 */
    /**
    * TwoFish CBC 
    * <p>
    * gnu crypto java lib used
    *
    * @param bytes
    *            key, IV and cipherIn are byte buffer
    * @return decrypted byte buffer unpad pkcs7
    */
	public byte [] TwoFishCBC( byte [] key_bytes, byte [] iv_bytes, byte [] cipherIn){
	
		byte [] plainOut = null ; 
		byte [] finalplainOut = null ;
		
		boolean IsAllParamValid=false;
		
		// check inputs, exception throws
		IsAllParamValid = ( IsParamValid(KEY, key_bytes) && IsParamValid(IV, iv_bytes) && IsParamValid(DATA, cipherIn) );
		
		if (IsAllParamValid == true){
		
	        IPad padding = PadFactory.getInstance("PKCS7");
	        padding.init(BitBlock128Bit);
			IMode mode = ModeFactory.getInstance("CBC","Twofish", BitBlock128Bit);
			Map<String, Object> attributes = new HashMap<String, Object>();
			// These attributes are defined in gnu.crypto.cipher.IBlockCipher.
			attributes.put(IMode.KEY_MATERIAL, key_bytes);
			attributes.put(IMode.CIPHER_BLOCK_SIZE, new Integer(BitBlock128Bit));
			// These attributes are defined in IMode.
			attributes.put(IMode.STATE, new Integer(IMode.DECRYPTION));
			attributes.put(IMode.IV, iv_bytes);
			try {
				mode.init(attributes);
			} catch (InvalidKeyException | IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int bs = mode.currentBlockSize();
			plainOut = new byte[cipherIn.length];
			// note that the doc from gnu crypto is wrong for the loop count
			for (int i = 0; i +bs <= cipherIn.length; i += bs){
				mode.update(cipherIn, i, plainOut, i);
			}
			// now is time to unpad
	        try {
	            int unpad = padding.unpad(plainOut, 0, plainOut.length);
	            finalplainOut = new byte[plainOut.length - unpad];
	            System.arraycopy(plainOut, 0, finalplainOut, 0, finalplainOut.length);
	    		
	        } catch (Exception e) {
	        	finalplainOut = new byte[plainOut.length];
	            System.arraycopy(plainOut, 0, finalplainOut, 0, finalplainOut.length);
	        }
		}// end if
		
		return finalplainOut;

	}

    /**
    * TwoFish CBC 
    * <p>
    * gnu crypto java lib used
    * block size 128 bit, key size 128, 192, 256
    * @param bytes
    *            key, IV and plain data are string
    *            key and IV shall be hex encoded i.e
    *            3dafba429d9eb430b422da802c9fac41
    *            cipher in is a string also hex encoded
    * @return plain string
    */
	public String TwoFishCBC( String key_hexencoded, String iv_hexencoded, String cipherInhexencoded ){
	
		byte [] Cipheredbuf=null; 
		Cipheredbuf = TwoFishCBC(decodeHex(key_hexencoded), decodeHex(iv_hexencoded), decodeHex(cipherInhexencoded));
		if(Cipheredbuf!=null)
			return new String(Cipheredbuf);
		else
			return "";	

	}	
	
}
