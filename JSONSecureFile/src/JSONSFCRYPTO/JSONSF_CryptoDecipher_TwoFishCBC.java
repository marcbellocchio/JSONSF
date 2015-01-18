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
		
		
		return finalplainOut;

	}
	/**
	 * TwoFish version of a string
	 * return a string
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
    *            plain in is a string also hex encoded
    * @return encrypted byte buffer pad is PKCS7
    */
	public String TwoFishCBC( String key_hexencoded, String iv_hexencoded, String cipherInhexencoded ){
	
		byte [] cipherOut = null ;
		
        IPad padding = PadFactory.getInstance("PKCS7");
        padding.init(BitBlock128Bit);
        byte[] pt1 = decodeHex (cipherInhexencoded);
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
		attributes.put(IMode.KEY_MATERIAL, decodeHex(key_hexencoded));
		attributes.put(IMode.CIPHER_BLOCK_SIZE, new Integer(BitBlock128Bit));
		// These attributes are defined in IMode.
		attributes.put(IMode.STATE, new Integer(IMode.DECRYPTION));
		attributes.put(IMode.IV, decodeHex(iv_hexencoded));
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
		
		return new String (cipherOut);

	}	
	
}
