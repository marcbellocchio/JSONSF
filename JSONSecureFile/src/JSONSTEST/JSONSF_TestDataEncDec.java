package JSONSTEST;

import JSONSFCRYPTO.JSONSF_Crypto;
import JSONSFGLOBAL.Constants;
import JSONSFOBJECT.JSONSF_DataDecrypt;
import JSONSFOBJECT.JSONSF_DataEncrypt;
import JSONSFOBJECT.JSONSF_firstlevel;

import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.json.simple.JSONValue;

import java.io.IOException; 
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap; 
import java.util.LinkedList; 
import java.util.List; 
import java.util.Map;
import java.util.Formatter;

public class JSONSF_TestDataEncDec {

	public JSONSF_TestDataEncDec() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	* @brief test decryption on a txt line embedded in a json version 1 file using encryption method 1
	* @usage test decryption
	* @param[in] none   
	* @return 0 on success
	*/
	public int  Test_DataDecryptionFromObject(){
		int result = 12345; 
		// quick test the decoding features of the package JSONSFOBJECT
		StringBuffer strbufpf = new StringBuffer("erikaship");
		StringBuffer strbufdata = new StringBuffer("{hellomyfriendthisisatestsample}");
		
	
		JSONSF_DataDecrypt myobjectfordecryption = new JSONSF_DataDecrypt(strbufpf);
		
		if (myobjectfordecryption.ImportFromFile("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample_enc[pass=erikaship]_test_wholeinputmap.json") == Constants.Success ){
			myobjectfordecryption.ValidateImport();
			myobjectfordecryption.DoDecryption();
			System.out.println("reference clear data is " + strbufdata + "\n" );
			System.out.println("decrypted clear data is " + myobjectfordecryption.GetStrClearData() + "\n" );
			myobjectfordecryption.EndDencryption();
			//myobjectfordecryption.GetStrClearData();
		}
			
		
		result = strbufdata.toString().compareTo(myobjectfordecryption.GetStrClearData());	
		JSONSF_Crypto.Wipe (strbufpf,Constants.WIPEMETHOD);
		JSONSF_Crypto.Wipe (strbufdata,Constants.WIPEMETHOD);	
		
		return result;
		
	}
	
	/**
	* @brief test encryption on a txt line embedded in a json version 1 file using encryption method 1
	* @usage test encryption
	* @param[in] none   
	* @return 0 on success
	*/
	public int  Test_DataEncryptionFromObject(){
		
		int result= 123; 
		
		// use case a map is created will all the data inside, meaning that data value is already
		// the correct value to encrypt
		StringBuffer strbufpassphrase = new StringBuffer("erikaship");
		
		// create a fake data map as if it was coming from an UI
		HashMap<StringBuffer,StringBuffer> testbank = new HashMap<StringBuffer, StringBuffer>();

		testbank.put(new StringBuffer("version"), new StringBuffer("1"));
		testbank.put(new StringBuffer("category"), new StringBuffer("bank"));
		testbank.put(new StringBuffer("filetype"), new StringBuffer("txt"));
		testbank.put(new StringBuffer("desc"), new StringBuffer("line of text for test"));
		testbank.put(new StringBuffer("hash"), new StringBuffer("AE45B7"));
		testbank.put(new StringBuffer("encm"), new StringBuffer("1"));
		testbank.put(new StringBuffer("data"), new StringBuffer("{hellomyfriendthisisatestsample}"));
		
		//StringBuffer strbufdata = new StringBuffer("{hellomyfriendthisisatestsample}");
		
		// encrypted data is in hex encoding 116c15a873086b81345139b54ecb3b3a6b80bb236f9064cec900e41a34ab8778
		// encrypted data is in base64 encoding EWwVqHMIa4E0UTm1Tss7OmuAuyNvkGTOyQDkGjSrh3g=
		//System.out.println("clear data is " + strbufdata + "\n" );
		//System.out.println("clear data is " + strbufdata.toString() + "\n" );
		//System.out.println("clear data is " + JSONSF_Crypto.encodeDec(strbufdata.toString().getBytes()) + "\n" );
		
		//JSONSF_Crypto.encodeDec()
		// create object and keys from passphrase
		JSONSF_DataEncrypt myobjectforencryption = new JSONSF_DataEncrypt(strbufpassphrase);
		// set the map to be encrypted
		myobjectforencryption.SetMapForExport (testbank);
		// parse the whole map to check coherence
		if (myobjectforencryption.ValidateExport()== Constants.Success){
			// do encrypt data value
			myobjectforencryption.DoEncryption(myobjectforencryption.GetEncryptionMethod ());
			System.out.println("encrypted data is " + myobjectforencryption.GetEncryptedData() + "\n" );
			myobjectforencryption.EndEncryption();
			result= myobjectforencryption.ExportToFile("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample_enc[pass=erikaship]_test_wholeinputmap.json");
			
		}
		
		//myobjectforencryption.GetEncryptedData();
		
		return result;
		
		
	}

	/**
	* @brief test encryption on a txt line embedded in a json version 1 file using encryption method 1
	* value of data has been added after the creation of the map
	* @usage test encryption
	* @param[in] none   
	* @return 0 on success
	*/
	public int  Test_DataEncryptionValueSetAfterMapCreationFromObject(){
		
		int result= 123; 
		
		// use case a map is created will all the data inside, meaning that data value is already
		// the correct value to encrypt
		StringBuffer strbufpassphrase = new StringBuffer("tortuegeniale");
		StringBuffer strbufrealdata = new StringBuffer("[realdata what do you expect ?]");
		
		// create a fake data map as if it was coming from an UI
		HashMap<StringBuffer,StringBuffer> testbank = new HashMap<StringBuffer, StringBuffer>();

		testbank.put(new StringBuffer("version"), new StringBuffer("1"));
		testbank.put(new StringBuffer("category"), new StringBuffer("account"));
		testbank.put(new StringBuffer("filetype"), new StringBuffer("txt"));
		testbank.put(new StringBuffer("desc"), new StringBuffer("line of text for test"));
		testbank.put(new StringBuffer("hash"), new StringBuffer("1234567"));
		testbank.put(new StringBuffer("encm"), new StringBuffer("1"));
		testbank.put(new StringBuffer("data"), new StringBuffer("{to replace}"));
		
		//StringBuffer strbufdata = new StringBuffer("{hellomyfriendthisisatestsample}");
		
		// encrypted data is in hex encoding 116c15a873086b81345139b54ecb3b3a6b80bb236f9064cec900e41a34ab8778
		// encrypted data is in base64 encoding EWwVqHMIa4E0UTm1Tss7OmuAuyNvkGTOyQDkGjSrh3g=
		//System.out.println("clear data is " + strbufdata + "\n" );
		//System.out.println("clear data is " + strbufdata.toString() + "\n" );
		//System.out.println("clear data is " + JSONSF_Crypto.encodeDec(strbufdata.toString().getBytes()) + "\n" );
		
		//JSONSF_Crypto.encodeDec()
		// create object and keys from passphrase
		JSONSF_DataEncrypt myobjectforencryption = new JSONSF_DataEncrypt(strbufpassphrase);
		// set the map to be encrypted
		myobjectforencryption.SetMapForExport (testbank);
		myobjectforencryption.SetDataInMapForExport(strbufrealdata);
		// parse the whole map to check coherence
		if (myobjectforencryption.ValidateExport()== Constants.Success){
			// do encrypt data value
			myobjectforencryption.DoEncryption(myobjectforencryption.GetEncryptionMethod ());
			System.out.println("encrypted data is " + myobjectforencryption.GetEncryptedData() + "\n" );
			myobjectforencryption.EndEncryption();
			result= myobjectforencryption.ExportToFile("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample_enc[pass=tortuegeniale]_test_partialinputmap.json");
			
		}
		
		//myobjectforencryption.GetEncryptedData();
		
		return result;
		
		
	}

	/**
	* @brief test decryption on a txt line embedded in a json version 1 file using encryption method 1
	* value of data has been added after the creation of the map
	* @usage test decryption
	* @param[in] none   
	* @return 0 on success
	*/
	public int  Test_DataDecryptionValueSetAfterMapCreationFromObject(){
		int result = 12345; 
		// quick test the decoding features of the package JSONSFOBJECT
		StringBuffer strbufpf = new StringBuffer("tortuegeniale");
		StringBuffer strbufdata = new StringBuffer("[realdata what do you expect ?]");
		
	
		JSONSF_DataDecrypt myobjectfordecryption = new JSONSF_DataDecrypt(strbufpf);
		
		if (myobjectfordecryption.ImportFromFile("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample_enc[pass=tortuegeniale]_test_partialinputmap.json") == Constants.Success ){
			myobjectfordecryption.ValidateImport();
			myobjectfordecryption.DoDecryption();
			System.out.println("reference clear data is " + strbufdata + "\n" );
			System.out.println("decrypted clear data is " + myobjectfordecryption.GetStrClearData() + "\n" );
			myobjectfordecryption.EndDencryption();
			//myobjectfordecryption.GetStrClearData();

		}
			
		
		result = strbufdata.toString().compareTo(myobjectfordecryption.GetStrClearData());	
		JSONSF_Crypto.Wipe (strbufpf,Constants.WIPEMETHOD);
		JSONSF_Crypto.Wipe (strbufdata,Constants.WIPEMETHOD);	
		
		return result;
		
	}
	
	/**
	* @brief test encryption on a txt line embedded in a json version 4 file using encryption method 1
	* @usage test encryption
	* @param[in] none   
	* @return 0 on success
	*/
	public int  Test_DataEncryptionEncMethodTwoFishCBC256FromObject(){
		
		int result= 123; 
		
		// use case a map is created will all the data inside, meaning that data value is already
		// the correct value to encrypt
		StringBuffer strbufpassphrase = new StringBuffer("erikaship");
		StringBuffer strbufrealdata = new StringBuffer("[test padding ?#+]");
		
		// create a fake data map as if it was coming from an UI
		HashMap<StringBuffer,StringBuffer> testbank = new HashMap<StringBuffer, StringBuffer>();

		testbank.put(new StringBuffer("version"), new StringBuffer("1"));
		testbank.put(new StringBuffer("category"), new StringBuffer("bank"));
		testbank.put(new StringBuffer("filetype"), new StringBuffer("txt"));
		testbank.put(new StringBuffer("desc"), new StringBuffer("line of text for test twofishcbc 256"));
		testbank.put(new StringBuffer("hash"), new StringBuffer("AE45B7"));
		testbank.put(new StringBuffer("encm"), new StringBuffer("4"));
		testbank.put(new StringBuffer("data"), new StringBuffer("{hellomyfriendthisisatestsample}"));

		// create object and keys from passphrase
		JSONSF_DataEncrypt myobjectforencryption = new JSONSF_DataEncrypt(strbufpassphrase);
		// set the map to be encrypted
		myobjectforencryption.SetMapForExport (testbank);
		// parse the whole map to check coherence
		if (myobjectforencryption.ValidateExport()== Constants.Success){
			// do encrypt data value
			myobjectforencryption.DoEncryption(myobjectforencryption.GetEncryptionMethod ());
			//myobjectforencryption.EndEncryption();
			System.out.println("encrypted data is " + myobjectforencryption.GetEncryptedData() + "\n" );
			myobjectforencryption.WipeDataToEncrypt();
			result= myobjectforencryption.ExportToFile("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample_enc[pass=erikaship]TWoFishCBC256_test_wholeinputmap.json");
			
		}
		
		
		//myobjectforencryption.GetEncryptedData();
		// one more test
		myobjectforencryption.SetDataInMapForExport(strbufrealdata);
		if (myobjectforencryption.ValidateExport()== Constants.Success){
			// do encrypt data value
			myobjectforencryption.DoEncryption(myobjectforencryption.GetEncryptionMethod ());
			myobjectforencryption.EndEncryption();
			result= myobjectforencryption.ExportToFile("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample_enc_padding[pass=erikaship]TWoFishCBC256_test_wholeinputmap.json");
			
		}
		return result;
		
	}
	
	/**
	* @brief test decryption on a txt line embedded in a json version 1 file using encryption method 4
	* @usage test decryption
	* @param[in] none   
	* @return 0 on success
	*/
	public int  Test_DataDecryptionEncMethodTwoFishCBC256FromObject(){
		int result = 12345; 
		// quick test the decoding features of the package JSONSFOBJECT
		StringBuffer strbufpf = new StringBuffer("erikaship");
		StringBuffer strbufdata = new StringBuffer("{hellomyfriendthisisatestsample}");
		
	
		JSONSF_DataDecrypt myobjectfordecryption = new JSONSF_DataDecrypt(strbufpf);
		
		if (myobjectfordecryption.ImportFromFile("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample_enc[pass=erikaship]TWoFishCBC256_test_wholeinputmap.json") == Constants.Success ){
			myobjectfordecryption.ValidateImport();
			myobjectfordecryption.DoDecryption();
			System.out.println("reference clear data is " + strbufdata + "\n" );
			System.out.println("decrypted clear data is " + myobjectfordecryption.GetStrClearData() + "\n" );
			myobjectfordecryption.EndDencryption();
			//myobjectfordecryption.GetStrClearData();
		}
			
		
		result = strbufdata.toString().compareTo(myobjectfordecryption.GetStrClearData());	
		JSONSF_Crypto.Wipe (strbufpf,Constants.WIPEMETHOD);
		JSONSF_Crypto.Wipe (strbufdata,Constants.WIPEMETHOD);	
		
		return result;
		
	}
	


}
