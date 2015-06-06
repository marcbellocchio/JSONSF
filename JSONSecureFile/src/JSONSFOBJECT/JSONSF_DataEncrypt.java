package JSONSFOBJECT;

import java.util.Arrays;
import java.util.Map;

import JSONSFCRYPTO.JSONSF_Crypto;
import JSONSFCRYPTO.JSONSF_CryptoCipher_TwoFishCBC;
import JSONSFCRYPTO.JSONSF_CryptoDecipher_TwoFishCBC;
import JSONSFCRYPTO.JSONSF_CryptoGenerateKeys;
import JSONSFGLOBAL.Constants ;

public class JSONSF_DataEncrypt extends JSONSF_firstlevel {

	// used to hold the encrypted data
	private byte [] EncData;

	
	private StringBuffer Passphrase; 
	
	private StringBuffer DataToEncrypt; 
	
	/**
	* @brief JSONSF_DataEncrypt constructor
	* @usage when data shall be encrypted to be inserted in a json file or just be encrypted
	* @param StringBuffer InputPassphrase, StringBuffer InputData      
	* @return na
	*/
	public JSONSF_DataEncrypt(StringBuffer InputPassphrase, StringBuffer InputData ) {
		// TODO Auto-generated constructor stub
		super();
		Passphrase = new StringBuffer (InputPassphrase);
		DataToEncrypt = new StringBuffer (InputData);
		EncData = null;	
	}

	/**
	* @brief JSONSF_DataEncrypt constructor
	* @usage when data shall be encrypted to be inserted in a json file; data inside the map will be encrypted
	* @param StringBuffer InputPassphrase
	* @     
	* @return na
	*/
	public JSONSF_DataEncrypt(StringBuffer InputPassphrase ) {
		// TODO Auto-generated constructor stub
		super();
		Passphrase = new StringBuffer (InputPassphrase);
		//
		//DataToEncrypt = new StringBuffer (InputData);
		// get DataToEncrypt for jsonfileheaderExport	
		DataToEncrypt= null;
		EncData = null;	
	}
	
	/**
	* @author mbl
	* @brief DoEncryption on data to be added to json file, DataToEncrypt is the data in clear to be encrypted; clear data shall be wiped after usage
	* @usage when data shall be encrypted to be inserted in a json file
	* @param int encmethod      
	* @return na
	*/	
	public void DoEncryption(int encmethod){
		// based on encryption info decryption will occur
		// first generate keys based on passphrase		
		JSONSF_CryptoGenerateKeys GenKeys = new JSONSF_CryptoGenerateKeys();
		GenKeys.DoKeyGeneration(Passphrase);
		// test if DataToEncrypt is already allocated
		if(DataToEncrypt==null){
			DataToEncrypt = GetDataFromMapForExport();
		}// end if(DataToEncrypt==null)
		
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

	}// end 

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
