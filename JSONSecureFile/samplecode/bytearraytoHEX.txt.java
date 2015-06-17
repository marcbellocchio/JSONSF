package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class ByteArrayToHexExample {

	public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException, IOException {

		String file = "httpd-2.2.6-win32-src-r2.zip";
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] digest = getDigest(new FileInputStream(file), md, 2048);

		// method #1
		// convert byte array to hex string with apache commons codec
		String result = new String(Hex.encodeHex(digest));
		System.out.println("MD5 Digest (via commons codec):" + result);

		// method #2
		// convert byte array to hex string using no external libraries
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(Integer.toHexString((int) (b & 0xff)));
		}
		System.out.println("MD5 Digest (without external libraries):" + sb.toString());

	}

	public static byte[] getDigest(InputStream is, MessageDigest md, int byteArraySize)
			throws NoSuchAlgorithmException, IOException {

		md.reset();
		byte[] bytes = new byte[byteArraySize];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			md.update(bytes, 0, numBytes);
		}
		byte[] digest = md.digest();
		return digest;
	}

}