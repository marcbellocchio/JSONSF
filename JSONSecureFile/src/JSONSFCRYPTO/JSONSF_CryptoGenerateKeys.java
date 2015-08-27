package JSONSFCRYPTO;


import java.util.Arrays;

import JSONSFGLOBAL.Constants;
import gnu.crypto.hash.HashFactory;


public class JSONSF_CryptoGenerateKeys  {

	private byte [] Key1 ;
	private byte [] Key2 ;
	private byte [] IV ;
	
	public JSONSF_CryptoGenerateKeys() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	* @brief GetKey1
	* @usage getkey1 based on length parameter
	* @param[in] int length, requested key length in bytes   
	* @return byte buffer of requested length
	*/	
	public byte [] GetKey1 (int  length){
		//Arrays.copyOf(Key1, length);
		if ( (Constants.Zero  < length) && (length <= Constants.MaxKeyLengthInBytes))
			return Arrays.copyOf(Key1, length);
		else
			return Arrays.copyOf(Key1, Constants.DefaultKeyLengthInBytes);			
		
	}
	
	/**
	* @brief GetKey2
	* @usage getkey based on length parameter
	* @param[in] int length, requested key length in bytes   
	* @return byte buffer of requested length or default key length is provided length bigger than Constants.MaxKeyLengthInBytes
	*/	
	public byte [] GetKey2 (int  length){
		//Arrays.copyOf(Key1, length);
		if ( (Constants.Zero  < length) && (length <= Constants.MaxKeyLengthInBytes))
			return Arrays.copyOf(Key2, length);
		else
			return Arrays.copyOf(Key2, Constants.DefaultKeyLengthInBytes);			
		
	}	

	/**
	* @brief GetIV
	* @usage GetIV
	* @param[in] int length, requested IV length in bytes   
	* @return byte buffer of requested length or default key length is provided length bigger than Constants.MaxKeyLengthInBytes
	*/	
	public byte [] GetIV (int  length){

		if ( (Constants.Zero  < length) && (length <= Constants.MaxKeyLengthInBytes))
			return Arrays.copyOf(IV, length);
		else{
			return Arrays.copyOf(IV, Constants.DefaultKeyLengthInBytes);	
		}	
		
	}
	
	/**
	* @brief WipeAll
	* @usage WipeAll key and IV
	* @param[in]    
	* @return 
	*/	
	public void WipeAll (){

		JSONSF_Crypto.Wipe (Key1,Constants.WIPEMETHOD);	
		JSONSF_Crypto.Wipe (Key2,Constants.WIPEMETHOD);	
		JSONSF_Crypto.Wipe (IV,Constants.WIPEMETHOD);	
		
	}
	
	
	/**
	* @brief DoKeyGeneration
	* @usage create keys and IV
	* @param[in]     StringBuffer Input represents the passphrase used as seed for generation   
	* @return 
	*/	
	public void DoKeyGeneration (StringBuffer Input){
		
		JSONSF_CryptoWhirlpoolHash hashclassW = new JSONSF_CryptoWhirlpoolHash();
		
		Key1 = hashclassW.getWhirlpoolHash (Input.toString().getBytes());
		
		JSONSF_CryptoSha512Hash hashclassS = new JSONSF_CryptoSha512Hash();
		
		Key2 = hashclassS.getBufSha512Hash(Input.toString().getBytes());
		
		JSONSF_Crypto.Wipe (Input,Constants.WIPEMETHOD);
		
        IV = hashclassS.getBufSha512Hash(Key2);
		
			
	}	

}
