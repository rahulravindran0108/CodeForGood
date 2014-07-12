package com.example.yuvaparivarthan;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject; 
 
public class UserFunctions {
     
    private JSONParser jsonParser;
     
   private static String send_data_tag = "sendData";
   private static String server_url = "http://www.csivesit.org/yuva/";
     
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
    
    public JSONObject sendDataToServer(String date,String location, String attendance, String campCode,String feesCollected) {
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	System.out.println("came into");
    	params.add(new BasicNameValuePair("tag", send_data_tag));
    	params.add(new BasicNameValuePair("date", date));
    	params.add(new BasicNameValuePair("location", location));
    	params.add(new BasicNameValuePair("attendance", attendance));
    	params.add(new BasicNameValuePair("campCode", campCode));
    	params.add(new BasicNameValuePair("feesCollected",feesCollected));
    	JSONObject json = jsonParser.getJSONFromUrl(server_url, params);
    	return json;
    }                  
     
}