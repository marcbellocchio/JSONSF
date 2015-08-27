package JSONSTEST;
import JSONSFGLOBAL.Constants;
import JSONSFOBJECT.JSONSF_DataDecrypt;
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


public class JSONSF_TestJsonFirstLevel {

	public JSONSF_TestJsonFirstLevel() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void Test_JSONFirstLevel(){
		
		JSONSF_firstlevel myobject = new JSONSF_firstlevel();
		if (myobject.ImportFromFile("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample.json") == Constants.Success )
			myobject.ValidateImport();
		
		StringBuffer temp = new StringBuffer ("this is mw passphrase");
		
		JSONSF_DataDecrypt myDataDecryptObject = new JSONSF_DataDecrypt (temp);
		if (myDataDecryptObject.ImportFromFile("C:/MBL_DATA/dev/workspace/git/jsonsecurefile/JSONSecureFile/samplefile/sample.json") == Constants.Success )
			myDataDecryptObject.ValidateImport();
		
	}
	

		


}
