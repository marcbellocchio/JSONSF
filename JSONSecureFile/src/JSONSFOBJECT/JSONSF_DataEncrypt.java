package JSONSFOBJECT;

import java.util.Arrays;
import java.util.Map;

import JSONSFCRYPTO.JSONSF_Crypto;
import JSONSFCRYPTO.JSONSF_CryptoCipher_TwoFishCBC;
import JSONSFCRYPTO.JSONSF_CryptoDecipher_TwoFishCBC;
import JSONSFCRYPTO.JSONSF_CryptoGenerateKeys;
import JSONSFGLOBAL.Constants ;

public class JSONSF_DataEncrypt {

	// used to hold the encrypted data
	private byte [] EncData;

	
	private StringBuffer Passphrase; 
	
	private StringBuffer DataToEncrypt; 
	
	public JSONSF_DataEncrypt(StringBuffer InputPassphrase, StringBuffer InputData ) {
		// TODO Auto-generated constructor stub
		super();
		Passphrase = new StringBuffer (InputPassphrase);
		DataToEncrypt = new StringBuffer (InputData);
		EncData = null;	
		
	}
	
	/**
	* @author mbl
	* @brief DoEncryption on data to be added to json file
	* @usage when data shall be encrypted to be inserted in a json file
	* @param na        
	* @return na
	*/	
	public void DoEncryption(int encmethod){
		// based on encryption info decryption will occur
		// first generate keys based on passphrase
		
		JSONSF_CryptoGenerateKeys GenKeys = new JSONSF_CryptoGenerateKeys();
		GenKeys.DoKeyGeneration(Passphrase);
		
		switch( encmethod ){
		case 1: // twofishcbc	
			JSONSF_CryptoCipher_TwoFishCBC Cipher = new JSONSF_CryptoCipher_TwoFishCBC ();
			EncData = Cipher.TwoFishCBC(GenKeys.GetKey1(Constants.DefaultKeyLengthInBytes), GenKeys.GetIV(Constants.DefaulIVLengthInBytes), DataToEncrypt.toString().getBytes());
			break;
		case 2: // serpentcbc
			
			break;
		case 3: // tfspcbc
			
			break;
			
		default:
			
			break;
			
		}
		GenKeys.WipeAll();

	}

	/**
	* @brief GetEncryptedData
	* @usage GetEncryptedData
	* @param[in]    
	* @return string of clear data encoded in base 64
	*/	
	public String GetEncryptedData (){
        if(EncData!=null)
        	return JSONSF_Crypto.Base64Encode(EncData);
		else
			return Constants.Empty;
		
	}	

}
