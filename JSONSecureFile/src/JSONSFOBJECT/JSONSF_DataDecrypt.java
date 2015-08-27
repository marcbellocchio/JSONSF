package JSONSFOBJECT;


import java.util.Arrays;
import java.util.Map;

import JSONSFCRYPTO.JSONSF_Crypto;
import JSONSFCRYPTO.JSONSF_CryptoCipher_SerpentCBC;
import JSONSFCRYPTO.JSONSF_CryptoCipher_TFSPCBC;
import JSONSFCRYPTO.JSONSF_CryptoCipher_TwoFishCBC;
import JSONSFCRYPTO.JSONSF_CryptoDecipher_SerpentCBC;
import JSONSFCRYPTO.JSONSF_CryptoDecipher_TFSPCBC;
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
	private JSONSF_CryptoGenerateKeys GenKeys;
	
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
		GenKeys = new JSONSF_CryptoGenerateKeys();
		GenKeys.DoKeyGeneration(InputPassphrase);	
		ClearData = null;
		// wipe out input data
		JSONSF_Crypto.Wipe (InputPassphrase,Constants.WIPEMETHOD);
		
	}
	/**
	* @author mbl
	* @brief DoDecryption on data collected from json file, use the map of the super class
	* @usage when a file shall be decrypted for display
	* @param na        
	* @return na
	*/	
	public void DoDecryption(){
		
		JSONSF_CryptoDecipher_TwoFishCBC Decipher_TwoFishCBC;
		JSONSF_CryptoDecipher_SerpentCBC Decipher_SerpentCBC;
		JSONSF_CryptoDecipher_TFSPCBC Decipher_TFSPCBC;
		
		switch( GetEncryptionMethod () ){
		
		case 0: // none clear data
			ClearData = JSONSF_Crypto.Base64DecodeStrToByteBuffer(jsonfileheaderImport.get(Constants.DATA));
			break;
		case Constants.ENC_TWOFISHCBC: // twofishcbc	
			Decipher_TwoFishCBC = new JSONSF_CryptoDecipher_TwoFishCBC ();
			ClearData = Decipher_TwoFishCBC.TwoFishCBC(GenKeys.GetKey1(Decipher_TwoFishCBC.getKeyLength()), GenKeys.GetIV(Constants.DefaulIVLengthInBytes), JSONSF_Crypto.Base64DecodeStrToByteBuffer(jsonfileheaderImport.get(Constants.DATA)));		
			break;
		case Constants.ENC_SERPENTCBC: // serpentcbc	
			Decipher_SerpentCBC = new JSONSF_CryptoDecipher_SerpentCBC();
			ClearData = Decipher_SerpentCBC.SerpentCBC(GenKeys.GetKey1(Decipher_SerpentCBC.getKeyLength()), GenKeys.GetIV(Constants.DefaulIVLengthInBytes), JSONSF_Crypto.Base64DecodeStrToByteBuffer(jsonfileheaderImport.get(Constants.DATA)));
			break;
		case Constants.ENC_TWOFISHSERPENTCBC: // tfspcbc
			Decipher_TFSPCBC = new JSONSF_CryptoDecipher_TFSPCBC();
			ClearData = Decipher_TFSPCBC.SerpentCBC(GenKeys.GetKey1(Decipher_TFSPCBC.getKeyLength()), GenKeys.GetIV(Constants.DefaulIVLengthInBytes), JSONSF_Crypto.Base64DecodeStrToByteBuffer(jsonfileheaderImport.get(Constants.DATA)));			 		
			break;
		case Constants.ENC_TWOFISHCBC256: 
			Decipher_TwoFishCBC = new JSONSF_CryptoDecipher_TwoFishCBC ();
			Decipher_TwoFishCBC.setKeyLength(Constants.Bit256KeyLengthInBytes);
			ClearData = Decipher_TwoFishCBC.TwoFishCBC(GenKeys.GetKey1(Decipher_TwoFishCBC.getKeyLength()), GenKeys.GetIV(Constants.DefaulIVLengthInBytes), JSONSF_Crypto.Base64DecodeStrToByteBuffer(jsonfileheaderImport.get(Constants.DATA)));		
			break;
		case Constants.ENC_SERPENTCBC256: 	
			Decipher_SerpentCBC = new JSONSF_CryptoDecipher_SerpentCBC();
			Decipher_SerpentCBC.setKeyLength(Constants.Bit256KeyLengthInBytes);
			ClearData = Decipher_SerpentCBC.SerpentCBC(GenKeys.GetKey1(Decipher_SerpentCBC.getKeyLength()), GenKeys.GetIV(Constants.DefaulIVLengthInBytes), JSONSF_Crypto.Base64DecodeStrToByteBuffer(jsonfileheaderImport.get(Constants.DATA)));
			break;
		case Constants.ENC_TWOFISHSERPENTCBC256: 
			Decipher_TFSPCBC = new JSONSF_CryptoDecipher_TFSPCBC();
			Decipher_TFSPCBC.setKeyLength(Constants.Bit256KeyLengthInBytes);
			ClearData = Decipher_TFSPCBC.SerpentCBC(GenKeys.GetKey1(Decipher_TFSPCBC.getKeyLength()), GenKeys.GetIV(Constants.DefaulIVLengthInBytes), JSONSF_Crypto.Base64DecodeStrToByteBuffer(jsonfileheaderImport.get(Constants.DATA)));			 		
			break;			
			
		}

	}
	
	/**
	* @brief get clear buffer after decryption, return a stringbuffer that can be delete once used
	* @usage GetStrBufClearData after decryption
	* @param[in]    
	* @return StringBuffer clear data from byte buffer coming from the decryption of the data
	*/	
	public StringBuffer GetStrBufClearData (){

		//System.out.println("clear data from decryption is " + JSONSF_Crypto.encodeDec(ClearData) + "\n" );
		//return ClearData.toString();
		if (ClearData!=null)
			return new StringBuffer (ClearData.toString());
		else
			return new StringBuffer (Constants.Empty);
		
	}	
	
	/**
	* @brief Get data after decryption
	* @usage GetStrClearData after decryption , return a string may be not secure
	* @param[in]    
	* @return String clear data from byte buffer coming from the decryption of the data
	*/	
	public String GetStrClearData (){

		//System.out.println("clear data from decryption is " + JSONSF_Crypto.encodeDec(ClearData) + "\n" );
		//return ClearData.toString();
		if (ClearData!=null)
			return new String (ClearData);
		else
			return Constants.Empty;
		
	}	
	
	/**
	* @brief wipe clear data
	* @usage clean internal member variable ClearData
	* @param[in] na    
	* @return na
	*/	
	public void WipeClearData (){

		//System.out.println("clear data from decryption is " + JSONSF_Crypto.encodeDec(ClearData) + "\n" );
		//return ClearData.toString();
		if (ClearData!=null)
			JSONSF_Crypto.Wipe (ClearData,Constants.WIPEMETHOD);;
			ClearData=null;
	}	
	
	/**
	* @brief end decryption means wipe all keys
	* @usage wipe keys when no more usefull, call by default WipeClearData ()
	* @param[in] none   
	* @return none
	*/	
	public void EndDencryption (){
		GenKeys.WipeAll();
		WipeClearData ();
	}

}
