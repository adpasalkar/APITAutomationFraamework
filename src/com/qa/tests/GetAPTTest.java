package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

public class GetAPTTest extends TestBase{
	TestBase testBase;
	String ServiceURL;
	String apiURL;
	String URL;
	RestClient restclient;
	CloseableHttpResponse R;
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException{
		testBase = new TestBase();
		ServiceURL = prop.getProperty("URL");
		apiURL = prop.getProperty("serviceURL");
		//Target URL https://reqres.in/api/user
		
		URL = ServiceURL + apiURL;
	}
	
	
	@Test
	public void getAPItest() throws ClientProtocolException, IOException{
		restclient = new RestClient();
		R = restclient.get(URL);
				

		//a. To print Status code
		int StatusCode = R.getStatusLine().getStatusCode();
		System.out.println("Status Code" +StatusCode); 
		Assert.assertEquals(StatusCode,RESPONSE_STATUS_CODE_200, "Wrong status code");
		
		
		//b. JSON string. 
		String responseString = EntityUtils.toString(R.getEntity(),"UTF-8");
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("REsponse JSON from API ---> " + responseJson );
		
			
		//single value assertion:
				//per_page:
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("value of per page is-->"+ perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);

		//c. To print all header
		Header[] headeraray = R.getAllHeaders();
		HashMap<String,String> allHeader = new HashMap<String, String>();
		
		for(Header header : headeraray)
		{
			allHeader.put(header.getName(),header.getValue());
		}
		System.out.println("Headers Array--->" +allHeader);
		
	}

}
