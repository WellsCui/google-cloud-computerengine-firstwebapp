package com.wells.myfirstgooglewebapp.endpoints;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;





import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gson.Gson;

public class Signin {
	  /*
	   * Default HTTP transport to use to make HTTP requests.
	   */
	  private static final HttpTransport TRANSPORT = new NetHttpTransport();

	  /*
	   * Default JSON factory to use to deserialize JSON.
	   */
	  private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

	  /*
	   * Gson object to serialize JSON responses to requests to this servlet.
	   */
	  private static final Gson GSON = new Gson();

	  /*
	   * Creates a client secrets object from the client_secrets.json file.
	   */
	  private static GoogleClientSecrets clientSecrets;

	  static {
	    try {
	     // Reader reader = new FileReader("client_secrets.json"); 
	      Reader reader =  new InputStreamReader(Signin.class.getResourceAsStream("/client_secrets.json"));
	      
	      clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);
	    } catch (IOException e) {
	      throw new Error("No client_secrets.json found", e);
	    }
	  }

	  /*
	   * This is the Client ID that you generated in the API Console.
	   */
	  private static final String CLIENT_ID = clientSecrets.getWeb().getClientId();

	  /*
	   * This is the Client Secret that you generated in the API Console.
	   */
	  private static final String CLIENT_SECRET = clientSecrets.getWeb().getClientSecret();

	  /*
	   * Optionally replace this with your application's name.
	   */
	  private static final String APPLICATION_NAME = "Google+ Java Quickstart";
	  
	  public static Credential getCredential()
	  { 	  		   
		  return new GoogleCredential.Builder()
          .setJsonFactory(JSON_FACTORY)
          .setTransport(TRANSPORT)
          .setClientSecrets(CLIENT_ID, CLIENT_SECRET)
          .build();
	  }
	  
	  public void LoginApi(String tokenData) throws IOException
		{
			 // Build credential from stored token data.
	        GoogleCredential credential = new GoogleCredential.Builder()
	            .setJsonFactory(JSON_FACTORY)
	            .setTransport(TRANSPORT)
	            .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build()
	            .setFromTokenResponse(JSON_FACTORY.fromString(
	                tokenData, GoogleTokenResponse.class));
	        // Execute HTTP GET request to revoke current token.
	        HttpResponse revokeResponse = TRANSPORT.createRequestFactory()
	            .buildGetRequest(new GenericUrl(
	                String.format(
	                    "https://accounts.google.com/o/oauth2/revoke?token=%s",
	                    credential.getAccessToken()))).execute();
		
		}
}