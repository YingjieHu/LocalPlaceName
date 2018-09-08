package edu.buffalo.gsda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


// An annotator wrapper based on DBpedia Spotlight
public class SpotlightEntityExtractor
{
		private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
		{
		    StringBuilder result = new StringBuilder();
		    boolean first = true;

		    for (NameValuePair pair : params)
		    {
		        if (first)
		            first = false;
		        else
		            result.append("&");

		        result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
		        result.append("=");
		        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
		    }

		    return result.toString();
		}
		
		
		// The parameter "isGeography" specifies if we want to focus on place names only 
		public JSONObject extractEntityFromString(String originalString, boolean isGeography)
		{
				try
				{
						URL url = new URL("http://stko-lod.geog.ucsb.edu:2222/rest/annotate");
						HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Accept", "application/json");
						conn.setDoInput(true);
						conn.setDoOutput(true);

						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("text", originalString));
					    params.add(new BasicNameValuePair("confidence", "0.2"));
						params.add(new BasicNameValuePair("support", "2"));
						
						if(isGeography) params.add(new BasicNameValuePair("types", "Place"));

						OutputStream os = conn.getOutputStream();
						BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
						//System.out.println(getQuery(params));
						writer.write( getQuery(params));
						writer.flush();
						writer.close();
						os.close();

						conn.connect();
						
						InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
						BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
						String line = bufferedReader.readLine();
						
						StringBuffer stringBuffer = new StringBuffer();
						while(line != null)
						{
								stringBuffer.append(line);
								
								line = bufferedReader.readLine();
						}
						
						bufferedReader.close();
						
						JSONObject jsonObject = new JSONObject(stringBuffer.toString());
						
						return jsonObject;
				} 
				catch (Exception e)
				{
						e.printStackTrace();
				}
				
				return null;
		}
		

}
