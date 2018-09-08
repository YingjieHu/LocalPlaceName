package edu.buffalo.gsda;

import java.util.Vector;

import org.json.JSONObject;

public class CalaisExtractor
{	
		private CalaisHttpClient calaisHttpClient = null;
		
		public CalaisExtractor()
		{
				calaisHttpClient = new CalaisHttpClient();
		}
		
		public Vector<String> extractEntities(String originString)
		{
				try
				{
						String output = calaisHttpClient.getAnnotatedResult(originString);//new CalaisLocator().getcalaisSoap().enlighten(licenseID, content, paramsXML);
						
						//System.out.println(output);
						
						JSONObject resultJsonObject = new JSONObject(output);
						String docID = resultJsonObject.getJSONObject("doc").getJSONObject("info").getString("docId");
						
						Vector<String> entitiesVector = new Vector<>();
						
						int  socialTagIndex = 0;
						while(true)
						{
							socialTagIndex++;
							JSONObject socialTagObject = null;
							
							try 
							{
								socialTagObject = resultJsonObject.getJSONObject(docID+"/SocialTag/"+socialTagIndex);
							} 
							catch (Exception e) 
							{
								// TODO: handle exception
							}
							
							if(socialTagObject ==  null)
								break;
							
							String entityString = socialTagObject.getString("name");
							entityString = entityString.toLowerCase();
							
							int commaIndex = entityString.indexOf(",");
							if(commaIndex != -1)
								entityString = entityString.substring(0, commaIndex).trim();
							
							entitiesVector.add(entityString);
						}
						
						return entitiesVector;
								
				} 
				catch (Exception e)
				{
						e.printStackTrace();
				} 

				return null;	
		}
		

}
