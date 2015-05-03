package JSONSFOBJECT;


import java.util.Arrays;
import java.util.Map;

import JSONSFCRYPTO.JSONSF_Crypto;
import JSONSFCRYPTO.JSONSF_CryptoCipher_TwoFishCBC;
import JSONSFCRYPTO.JSONSF_CryptoDecipher_TwoFishCBC;
import JSONSFCRYPTO.JSONSF_CryptoGenerateKeys;
import JSONSFGLOBAL.Constants ;

/**
* @author mbl
* @brief  class that will decrypt the data collected and checked by firstlevel
* @usage when a file is read, the first level holds all the fields of the json file 
* @param na        
* @return na
*/
public class JSONSF_DataDecrypt extends JSONSF_firstlevel {

	// used to hold the decrypted data
	private byte [] ClearData;
	private StringBuffer Passphrase;  
	/**
	* @author mbl
	* @brief JSONSF_DataDecrypt constructor
	* @usage create object for data decryption 
	* @param StringBuffer InputPassphrase, keys will be generates from it      
	* @return na
	*/	
	public JSONSF_DataDecrypt(StringBuffer InputPassphrase) {
		// calling base class constructor
		super();
		Passphrase = new StringBuffer (InputPassphrase);
		ClearData = null;
		// wipe out input data
		//JSONSF_Crypto.Wipe (InputPassphrase,Constants.WIPEMETHOD);
		
	}
	/**
	* @author mbl
	* @brief DoDecryption on data collected from json file, use the map of the super class
	* @usage when a file shall be decrypted for display
	* @param na        
	* @return na
	*/	
	public void DoDecryption(){
		// first generate keys based on passphrase
		
		JSONSF_CryptoGenerateKeys GenKeys = new JSONSF_CryptoGenerateKeys();
		GenKeys.DoKeyGeneration(Passphrase);
		// get key shall be 16 bytes long
		
		switch( GetEncryptionMethod () ){
		case 1: // twofishcbc	
			
			JSONSF_CryptoDecipher_TwoFishCBC Decipher = new JSONSF_CryptoDecipher_TwoFishCBC ();

            // data in jsonfile has been base64 encoded
			//System.out.println("enc data base 64 is " + jsonfileheader.get(Constants.DATA) + "\n" );
			// use keys previously generated, decode base 64 the data collected from the json file
			ClearData = Decipher.TwoFishCBC(GenKeys.GetKey1(Constants.DefaultKeyLengthInBytes), GenKeys.GetIV(Constants.DefaulIVLengthInBytes), JSONSF_Crypto.Base64DecodeStrToByteBuffer(jsonfileheader.get(Constants.DATA)));		

			break;
		case 2: // serpentcbc
			
			break;
		case 3: // tfspcbc
			
			break;			
			
		}
		GenKeys.WipeAll();

	}
	
	/**
	* @brief GetClearData
	* @usage GetClearData after decryption
	* @param[in]    
	* @return String clear data from byte buffer coming from the decryption of the data
	*/	
	public String GetClearData (){

		//System.out.println("clear data from decryption is " + JSONSF_Crypto.encodeDec(ClearData) + "\n" );
		//return ClearData.toString();
		if (ClearData!=null)
			return new String (ClearData);
		else
			return Constants.Empty;
		
	}	
	

}
