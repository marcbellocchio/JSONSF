
import java.io.*;
import JSONSFCRYPTO.*;
import JSONSFFILE.*;
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
	public int Test_Twofish (){
		
		int testres = 123;
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
		
		
	}
	public int Test_getWhirlpoolHash_lasydog(){
		
		int testres = 123;
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
		
		int testres = 123;
		String strReference = "zorroestarrive" ;
		// HEX: 000E1160CBB34D462BFBEAEADBFA5706A126C713BF33946D30D7222EEE6AA2D1EF0E2E87762917F4CBE7BFBB3C29893284C8EAB81C656468626BE30C0812E845
		String strReferenceHASH = "AA4RYMuzTUYr++rq2/pXBqEmxxO/M5RtMNciLu5qotHvDi6HdikX9Mvnv7s8KYkyhMjquBxlZGhia+MMCBLoRQ==" ; 
		String result ;
		
		JSONSF_CryptoSha512Hash hashclass = new JSONSF_CryptoSha512Hash();
		
		result = hashclass.Hash_Sha512 (strReference);
		
		testres = result.compareTo(strReferenceHASH);
		
		
		return testres; 
		
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
