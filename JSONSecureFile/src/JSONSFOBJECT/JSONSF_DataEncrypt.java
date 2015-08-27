package JSONSFOBJECT;

import java.util.Arrays;
import java.util.Map;

import JSONSFCRYPTO.JSONSF_Crypto;
import JSONSFCRYPTO.JSONSF_CryptoCipher_SerpentCBC;
import JSONSFCRYPTO.JSONSF_CryptoCipher_TFSPCBC;
import JSONSFCRYPTO.JSONSF_CryptoCipher_TwoFishCBC;
import JSONSFCRYPTO.JSONSF_CryptoDecipher_TwoFishCBC;
import JSONSFCRYPTO.JSONSF_CryptoGenerateKeys;
import JSONSFCRYPTO.JSONSF_CryptoWhirlpoolHash;
import JSONSFGLOBAL.Constants ;

public class JSONSF_DataEncrypt extends JSONSF_firstlevel {

	// used to hold the encrypted data
	private byte [] EncData;
	
	private StringBuffer DataToEncrypt; 
	
	private JSONSF_CryptoGenerateKeys GenKeys;
	
	/**
	* @brief JSONSF_DataEncrypt constructor
	* @usage when data shall be encrypted to be inserted in a json file or just be encrypted
	* @param [in] StringBuffer InputPassphrase  
	* @param [in] StringBuffer InputData  
	* @return na
	*/
	public JSONSF_DataEncrypt(StringBuffer InputPassphrase, StringBuffer InputData ) {
		// TODO Auto-generated constructor stub
		super();
		
		GenKeys = new JSONSF_CryptoGenerateKeys();
		GenKeys.DoKeyGeneration(InputPassphrase);	
		DataToEncrypt = new StringBuffer (InputData);
		EncData = null;	
		// wipe out input data
		JSONSF_Crypto.Wipe (InputPassphrase,Constants.WIPEMETHOD);
		
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
		GenKeys = new JSONSF_CryptoGenerateKeys();
		GenKeys.DoKeyGeneration(InputPassphrase);	
		DataToEncrypt= null;
		EncData = null;	
		JSONSF_Crypto.Wipe (InputPassphrase,Constants.WIPEMETHOD);
	}
	
	/**
	* @author mbl
	* @brief DoEncryption on data to be added to json file, DataToEncrypt is the data in clear to be encrypted; clear data shall be wiped after usage
	* @usage when data shall be encrypted to be inserted in a json file
	* @param int encmethod      
	* @return na
	*/	
	public void DoEncryption(int encmethod){

		// test if DataToEncrypt is already allocated
		if(DataToEncrypt==null){
			DataToEncrypt = GetDataFromMapForExport();
		}// end if(DataToEncrypt==null)
		
		JSONSF_CryptoCipher_TwoFishCBC Cipher_TwoFishCBC;
		JSONSF_CryptoCipher_SerpentCBC Cipher_SerpentCBC;
		JSONSF_CryptoCipher_TFSPCBC Cipher_TFSPCBC;
		
		switch( encmethod ){
		
		case 0: // clear data, none encryption
			SetDataInMapForExport(new StringBuffer (GetEncryptedData()) );
			break;

		case Constants.ENC_TWOFISHCBC: // twofishcbc	
			Cipher_TwoFishCBC = new JSONSF_CryptoCipher_TwoFishCBC ();
			EncData = Cipher_TwoFishCBC.TwoFishCBC(GenKeys.GetKey1(Cipher_TwoFishCBC.getKeyLength()), GenKeys.GetIV(Constants.DefaulIVLengthInBytes), DataToEncrypt.toString().getBytes());
			SetDataInMapForExport(new StringBuffer (GetEncryptedData()) );
			break;
		case Constants.ENC_SERPENTCBC: // serpentcbc
			Cipher_SerpentCBC = new JSONSF_CryptoCipher_SerpentCBC ();
			EncData = Cipher_SerpentCBC.SerpentCBC(GenKeys.GetKey1(Cipher_SerpentCBC.getKeyLength()), GenKeys.GetIV(Constants.DefaulIVLengthInBytes), DataToEncrypt.toString().getBytes());
			SetDataInMapForExport(new StringBuffer (GetEncryptedData()) );
			
			break;
		case Constants.ENC_TWOFISHSERPENTCBC: // tfspcbc
			Cipher_TFSPCBC = new JSONSF_CryptoCipher_TFSPCBC ();
			EncData = Cipher_TFSPCBC.TwoFishSerpentCBC(GenKeys.GetKey1(Cipher_TFSPCBC.getKeyLength()), GenKeys.GetIV(Constants.DefaulIVLengthInBytes), DataToEncrypt.toString().getBytes());
			SetDataInMapForExport(new StringBuffer (GetEncryptedData()) );
			break;
			
		case Constants.ENC_TWOFISHCBC256: 
			Cipher_TwoFishCBC = new JSONSF_CryptoCipher_TwoFishCBC ();
			Cipher_TwoFishCBC.setKeyLength(Constants.Bit256KeyLengthInBytes);
			EncData = Cipher_TwoFishCBC.TwoFishCBC(GenKeys.GetKey1(Cipher_TwoFishCBC.getKeyLength()), GenKeys.GetIV(Constants.DefaulIVLengthInBytes), DataToEncrypt.toString().getBytes());
			SetDataInMapForExport(new StringBuffer (GetEncryptedData()) );
			break;
		case Constants.ENC_SERPENTCBC256: 
			Cipher_SerpentCBC = new JSONSF_CryptoCipher_SerpentCBC ();
			Cipher_SerpentCBC.setKeyLength(Constants.Bit256KeyLengthInBytes);
			EncData = Cipher_SerpentCBC.SerpentCBC(GenKeys.GetKey1(Cipher_SerpentCBC.getKeyLength()), GenKeys.GetIV(Constants.DefaulIVLengthInBytes), DataToEncrypt.toString().getBytes());
			SetDataInMapForExport(new StringBuffer (GetEncryptedData()) );
			
			break;
		case Constants.ENC_TWOFISHSERPENTCBC256: 
			Cipher_TFSPCBC = new JSONSF_CryptoCipher_TFSPCBC ();
			Cipher_TFSPCBC.setKeyLength(Constants.Bit256KeyLengthInBytes);
			EncData = Cipher_TFSPCBC.TwoFishSerpentCBC(GenKeys.GetKey1(Cipher_TFSPCBC.getKeyLength()), GenKeys.GetIV(Constants.DefaulIVLengthInBytes), DataToEncrypt.toString().getBytes());
			SetDataInMapForExport(new StringBuffer (GetEncryptedData()) );
			break;
			
		default:
			
			break;
			
		}

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
	
	/**
	* @brief wipe DataToEncrypt
	* @usage clean internal member variable DataToEncrypt
	* @param[in] na    
	* @return na
	*/	
	public void WipeDataToEncrypt (){

		//System.out.println("clear data from decryption is " + JSONSF_Crypto.encodeDec(ClearData) + "\n" );
		//return ClearData.toString();
		if (DataToEncrypt!=null)
			JSONSF_Crypto.Wipe (DataToEncrypt,Constants.WIPEMETHOD);;
			DataToEncrypt=null;
	}	
	
	/**
	* @brief end encryption means wipe all keys
	* @usage wipe keys when no more usefull
	* @param[in] none   
	* @return none
	*/	
	public void EndEncryption (){
		GenKeys.WipeAll();
		WipeDataToEncrypt ();
		
	}

}
