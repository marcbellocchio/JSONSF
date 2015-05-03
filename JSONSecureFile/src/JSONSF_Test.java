
import java.io.*;

import JSONSFGLOBAL.Constants ;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.json.simple.JSONValue;

import java.io.IOException; 
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.LinkedHashMap; 
import java.util.LinkedList; 
import java.util.List; 
import java.util.Map; 

 





import org.json.simple.parser.ContainerFactory; 
import org.json.simple.parser.JSONParser; 
import org.json.simple.parser.ParseException; 

import JSONSFCRYPTO.*;
import JSONSFFILE.*;
import JSONSFGLOBAL.*;
import JSONSFOBJECT.JSONSF_DataDecrypt;
import JSONSFOBJECT.JSONSF_DataEncrypt;
import JSONSFOBJECT.JSONSF_firstlevel;
/**
 * @author mbl
 * basic test

 */

public class JSONSF_Test {

	public JSONSF_Test() {
		// TODO Auto-generated constructor stub
	}

    /*
     * Case #1: Encrypting 16 bytes (1 block) using AES-CBC with 128-bit key 
     * Key :
     * 0x06a9214036b8a15b512e03d534120006 
     * IV :
     * 0x3dafba429d9eb430b422da802c9fac41 
     * Plaintext : "Single block msg"
     * Ciphertext: 0xe353779c1079aeb82708942dbe77181a
     *
     * Case #2: Encrypting 32 bytes (2 blocks) using AES-CBC with 128-bit key
     * Key : 0xc286696d887c9aa0611bbb3e2025a45a 
     * IV :
     * 0x562e17996d093d28ddb3ba695a2e6f58 
     * Plaintext :
     * 0x000102030405060708090a0b0c0d0e0f 101112131415161718191a1b1c1d1e1f
     * Ciphertext: 
     * 0xd296cd94c2cccf8a3a863028b5e1dc0a
     * 7586602d253cfff91b8266bea6d61ab1
     *
     * public static void main(String args[]) { try { 
     * byte[] key =
     * StringUtils.decodeHex("06a9214036b8a15b512e03d534120006"); 
     * byte[] iv =
     * StringUtils.decodeHex("3dafba429d9eb430b422da802c9fac41"); 
     * byte[] src =
     * "Single block ms".getBytes(); 
     * byte[]
     * desc=AESUtil.encryptString(src,key,iv);
     * System.out.println(StringUtils.encodeHex(desc)); 
     * System.out.println(new String(AESUtil.decryptString(desc,key,iv))); } catch (Exception e) {
     * e.printStackTrace(); } }
     */
	
	public int Test_TFSPCBC (){
		
		int testres = Constants.Fail;
		byte[] iv = null;
		byte[] key = null;
		byte[] result = null;
		byte[] firesult = null;
		String reference = "#~@@@ Un sot savant est sot plus qu’un sot ignorant Jean-Baptiste Poquelin, dit Molière Les Femmes savantes; le monde se divise en 2 categories ceux qui ont un revolvercharg� et ceux qui creusent; toi tu creuses !!!!!";
		byte[] plaindata = reference.getBytes();
		String referenceHex = JSONSF_CryptoCipher_TFSPCBC.encodeHex(plaindata);
		
		System.out.print("Test_TFSPCBC referenceHex 		is " + referenceHex + "\n");
		System.out.print("Test_TFSPCBC referenceHex lg 	is " + referenceHex.length() + "\n");
		
		JSONSF_CryptoCipher_TFSPCBC Cipher = new JSONSF_CryptoCipher_TFSPCBC();
		JSONSF_CryptoDecipher_TFSPCBC Decipher = new JSONSF_CryptoDecipher_TFSPCBC ();
		
		iv = JSONSF_CryptoCipher_TFSPCBC.decodeHex("3dafba429d9eb430b422da802c9fac41");
		key = JSONSF_CryptoCipher_TFSPCBC.decodeHex("c286696d887c9aa0611bbb3e2025a45a");
		
		result = Cipher.TwoFishSerpentCBC(key, iv, plaindata);
		if (result !=null){
			System.out.print("enc result 		is " + JSONSF_CryptoCipher_TFSPCBC.encodeHex(result) + "\n");
			System.out.print("enc result lg 	is " + JSONSF_CryptoCipher_TFSPCBC.encodeHex(result).length() + "\n");

			
			firesult = Decipher.TwoFishSerpentCBC(key, iv, result);
			if (firesult !=null){
				System.out.print("firesult 			is " + JSONSF_CryptoDecipher_TFSPCBC.encodeHex(firesult)+ "\n");
				System.out.print("firesult 	lg		is " + JSONSF_CryptoDecipher_TFSPCBC.encodeHex(firesult).length() + "\n");
		
			JSONSF_Crypto.Wipe(key, Constants.WIPEMETHOD);
			testres = JSONSF_CryptoDecipher_TFSPCBC.encodeHex(firesult).toString().compareTo(referenceHex);
			}
		}
		return testres; 
	}
	
	public int Test_Serpent (){
		
		int testres = Constants.Fail;
		byte[] iv = null;
		byte[] key = null;
		byte[] result = null;
		byte[] firesult = null;
		String reference = "Un sot savant est sot plus qu’un sot ignorant Jean-Baptiste Poquelin, dit Molière Les Femmes savantes";
		byte[] plaindata = reference.getBytes();
		String referenceHex = JSONSF_CryptoCipher_SerpentCBC.encodeHex(plaindata);
		
		System.out.print("Test_Serpent referenceHex 		is " + referenceHex + "\n");
		System.out.print("Test_Serpent referenceHex lg 	is " + referenceHex.length() + "\n");
		
		JSONSF_CryptoCipher_SerpentCBC Cipher = new JSONSF_CryptoCipher_SerpentCBC();
		JSONSF_CryptoDecipher_SerpentCBC Decipher = new JSONSF_CryptoDecipher_SerpentCBC ();
		
		iv = JSONSF_CryptoCipher_SerpentCBC.decodeHex("3dafba429d9eb430b422da802c9fac41");
		key = JSONSF_CryptoCipher_SerpentCBC.decodeHex("c286696d887c9aa0611bbb3e2025a45a");
		
		result = Cipher.SerpentCBC(key, iv, plaindata);
		
		System.out.print("enc result 		is " + JSONSF_CryptoCipher_SerpentCBC.encodeHex(result) + "\n");
		System.out.print("enc result lg 	is " + JSONSF_CryptoCipher_SerpentCBC.encodeHex(result).length() + "\n");
		
		firesult = Decipher.SerpentCBC(key, iv, result);
		
		System.out.print("firesult 			is " + JSONSF_CryptoCipher_SerpentCBC.encodeHex(firesult)+ "\n");
		System.out.print("firesult 	lg		is " + JSONSF_CryptoCipher_SerpentCBC.encodeHex(firesult).length() + "\n");
		
		
		testres = JSONSF_CryptoCipher_SerpentCBC.encodeHex(firesult).toString().compareTo(referenceHex);
		
		
		return testres; 
	}
	
	
	public int Test_Twofish (){
		
		int testres = Constants.Fail;
		byte[] iv = null;
		byte[] key = null;
		byte[] result = null;
		byte[] firesult = null;
		String reference = "Un sot savant est sot plus qu’un sot ignorant Jean-Baptiste Poquelin, dit Molière Les Femmes savantes";
		byte[] plaindata = reference.getBytes();
		String referenceHex = JSONSF_CryptoCipher_TwoFishCBC.encodeHex(plaindata);
		
		System.out.print("referenceHex 		is " + referenceHex + "\n");
		System.out.print("referenceHex lg 	is " + referenceHex.length() + "\n");
		
		JSONSF_CryptoCipher_TwoFishCBC Cipher = new JSONSF_CryptoCipher_TwoFishCBC();
		JSONSF_CryptoDecipher_TwoFishCBC Decipher = new JSONSF_CryptoDecipher_TwoFishCBC ();
		
		iv = JSONSF_CryptoCipher_TwoFishCBC.decodeHex("3dafba429d9eb430b422da802c9fac41");
		key = JSONSF_CryptoCipher_TwoFishCBC.decodeHex("c286696d887c9aa0611bbb3e2025a45a");
		
		result = Cipher.TwoFishCBC(key, iv, plaindata);
		
		System.out.print("enc result 		is " + JSONSF_CryptoCipher_TwoFishCBC.encodeHex(result) + "\n");
		System.out.print("enc result lg 	is " + JSONSF_CryptoCipher_TwoFishCBC.encodeHex(result).length() + "\n");
		
		firesult = Decipher.TwoFishCBC(key, iv, result);
		
		System.out.print("firesult 			is " + JSONSF_CryptoCipher_TwoFishCBC.encodeHex(firesult)+ "\n");
		System.out.print("firesult 	lg		is " + JSONSF_CryptoCipher_TwoFishCBC.encodeHex(firesult).length() + "\n");
		
		
		testres = JSONSF_CryptoCipher_TwoFishCBC.encodeHex(firesult).toString().compareTo(referenceHex);
		
		
		return testres; 
	}
		
		/**
referenceHex 		is 556e20736f7420736176616e742065737420736f7420706c7573207175e28099756e20736f742069676e6f72616e74204a65616e2d426170746973746520506f7175656c696e2c20646974204d6f6c69c3a87265204c65732046656d6d657320736176616e746573
referenceHex lg 	is 208
finalplain 			is 556e20736f7420736176616e742065737420736f7420706c7573207175e28099756e20736f742069676e6f72616e74204a65616e2d426170746973746520506f7175656c696e2c20646974204d6f6c69c3a87265204c65732046656d6d657320736176616e7465730808080808080808
finalplain lg			is 112 pad lg is 8
finalplain 			is 556e20736f7420736176616e742065737420736f7420706c7573207175e28099756e20736f742069676e6f72616e74204a65616e2d426170746973746520506f7175656c696e2c20646974204d6f6c69c3a87265204c65732046656d6d657320736176616e7465730808080808080808
finalplain lg			is 112
loop		is 0
loop		is 16
loop		is 32
loop		is 48
loop		is 64
loop		is 80
loop		is 96
enc result 		is         17e5abebe9266b38c7457d62edba37602a73eeef8e9f7c72bbd6576fadde62612b29fc6f05c51c883c7153b1e6972f0fc91535403b4ae0cf0e94de44cf2bbfd043e992204c94bb761405b80ddc31181a99cf2c5f35c26ec0e9e3e607fe7bffec0b20fac23916ec47f6bc7689dc860886
enc result lg 	is 224
finalplainOut 			is 556e20736f7420736176616e742065737420736f7420706c7573207175e28099756e20736f742069676e6f72616e74204a65616e2d426170746973746520506f7175656c696e2c20646974204d6f6c69c3a87265204c65732046656d6d657320736176616e746573
finalplainOut lg			is 104
unpad is			 8
firesult 			is     556e20736f7420736176616e742065737420736f7420706c7573207175e28099756e20736f742069676e6f72616e74204a65616e2d426170746973746520506f7175656c696e2c20646974204d6f6c69c3a87265204c65732046656d6d657320736176616e746573
firesult 	lg		is 208
        
		 */		
		
		
	
	public int Test_getWhirlpoolHash_lasydog(){
		
		int testres = Constants.Fail;
		String strReference = "The quick brown fox jumps over the lazy dog" ;
		/*
		 Whirlpool-T("The quick brown fox jumps over the lazy dog") =
				 3CCF8252D8BBB258460D9AA999C06EE38E67CB546CFFCF48E91F700F6FC7C183
				 AC8CC3D3096DD30A35B01F4620A1E3A20D79CD5168544D9E1B7CDF49970E87F1
				 */
		String strReferenceHASH = "kQXKqMepf9KYj8FAkr0s4Z43POCOQ7WM4CtVQAjCxD0Wyz2VK9wf5VjPsL6/qpfTCBheKFMRwlmxDC1RXogPjg==" ; 
		byte[]  result ;
		
		JSONSF_CryptoWhirlpoolHash hashclass = new JSONSF_CryptoWhirlpoolHash();
		
		result = hashclass.getWhirlpoolHash (strReference.getBytes());
		
		testres = result.toString().compareTo(strReferenceHASH);	
		return testres; 
		
	}
	

	public int Test_Hash_Sha512 (){
		
		int testres = Constants.Fail;
		String strReference = "zorroestarrive" ;
		// HEX: 000E1160CBB34D462BFBEAEADBFA5706A126C713BF33946D30D7222EEE6AA2D1EF0E2E87762917F4CBE7BFBB3C29893284C8EAB81C656468626BE30C0812E845
		String strReferenceHASH = "AA4RYMuzTUYr++rq2/pXBqEmxxO/M5RtMNciLu5qotHvDi6HdikX9Mvnv7s8KYkyhMjquBxlZGhia+MMCBLoRQ==" ; 
		String result ;
		
		JSONSF_CryptoSha512Hash hashclass = new JSONSF_CryptoSha512Hash();
		
		result = hashclass.getStringSha512Hash(strReference);
		
		testres = result.compareTo(strReferenceHASH);
		
		
		return testres; 
		
	}
	
	public void Test_JSONParser (){	
		  // test code to extract field from json file
		  String jsonText = "{\"first\": 123, \"second\": [4, 5, 6], \"third\": 789}";
		  JSONParser parser = new JSONParser();
		  
		  ContainerFactory containerFactory = new ContainerFactory(){
		    public List<?> creatArrayContainer() {
		      return  new LinkedList();
		    }

		    public Map<?,?> createObjectContainer() {
		      return new LinkedHashMap();
		    }
		                        
		  };
		                
		  try{
		    Map<?,?> json = (Map)parser.parse(jsonText, containerFactory);
		    Iterator<?> iter = json.entrySet().iterator();
		    System.out.println("==iterate result==");
		    while(iter.hasNext()){
		      Map.Entry<?,?> entry = (Map.Entry)iter.next();
		      System.out.println(entry.getKey() + "=>" + entry.getValue());
		    }
		                        
		    System.out.println("==toJSONString()==");
		    System.out.println(JSONValue.toJSONString(json));
		  }
		  catch(ParseException pe){
		    System.out.println(pe);
		  }
		  
	}

	public void Test_JSONFirstLevel(){
		
		JSONSF_firstlevel myobject = new JSONSF_firstlevel();
		if (myobject.ImportFromFile("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample.json") == Constants.Success )
			myobject.Validate();
		
		StringBuffer temp = new StringBuffer ("this is mw passphrase");
		
		JSONSF_DataDecrypt myDataDecryptObject = new JSONSF_DataDecrypt (temp);
		if (myDataDecryptObject.ImportFromFile("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample.json") == Constants.Success )
			myDataDecryptObject.Validate();
		
	}

	public int  Test_DataEncryptionDecryptionFromObject(){
		
		int result; 
		// quick test the encoding and decoding features of the package JSONSFOBJECT
		StringBuffer strbufpf = new StringBuffer("erikaship");
		StringBuffer strbufdata = new StringBuffer("{hellomyfriendthisisatestsample}");
		
		// encrypted data is in hex encoding 116c15a873086b81345139b54ecb3b3a6b80bb236f9064cec900e41a34ab8778
		// encrypted data is in base64 encoding EWwVqHMIa4E0UTm1Tss7OmuAuyNvkGTOyQDkGjSrh3g=
		
		
		//System.out.println("clear data is " + strbufdata + "\n" );
		//System.out.println("clear data is " + strbufdata.toString() + "\n" );
		//System.out.println("clear data is " + JSONSF_Crypto.encodeDec(strbufdata.toString().getBytes()) + "\n" );
		
		//JSONSF_Crypto.encodeDec()
		JSONSF_DataEncrypt myobjectforencryption = new JSONSF_DataEncrypt(strbufpf, strbufdata);
		myobjectforencryption.DoEncryption(1);
		
		//myobjectforencryption.GetEncryptedData();

		System.out.println("encrypted data is " + myobjectforencryption.GetEncryptedData() + "\n" );
		
		JSONSF_DataDecrypt myobjectfordecryption = new JSONSF_DataDecrypt(strbufpf);
		

		if (myobjectfordecryption.ImportFromFile("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample_erika.json") == Constants.Success ){
			myobjectfordecryption.Validate();
			myobjectfordecryption.DoDecryption();
			myobjectfordecryption.GetClearData();
			System.out.println("reference clear data is " + strbufdata + "\n" );
			System.out.println("decrypted clear data is " + myobjectfordecryption.GetClearData() + "\n" );
		}
			
		
		result = strbufdata.toString().compareTo(myobjectfordecryption.GetClearData());	
		JSONSF_Crypto.Wipe (strbufpf,Constants.WIPEMETHOD);
		JSONSF_Crypto.Wipe (strbufdata,Constants.WIPEMETHOD);	
		
		return result;
		
		
	}

	
	
	
	public void Test_JSONVersionFile (){
		
    	StringBuffer strtestbuffer ;
    	try{
    		JSONSF_ReadFile testreadfile = new JSONSF_ReadFile("/home/mbl/dev/workspace/JSONSecureFile/samplefile/sample.json");
    		strtestbuffer = testreadfile.OpenTextFileLineByLine();
    		System.out.println("string buffer is " + strtestbuffer.toString() );
    		
    	}
        catch(IOException ex) {
        	System.out.println("Error testing file " );   
            System.out.println( ex.getMessage() );
            ex.printStackTrace();
        } 
		
	}

}
