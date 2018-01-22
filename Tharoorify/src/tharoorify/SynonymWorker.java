package tharoorify;

import static io.restassured.RestAssured.given;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class SynonymWorker {

	public static void main(String[] args) throws IOException {
	
		RestAssured.baseURI="http://thesaurus.altervista.org";
		RestAssured.basePath="/thesaurus/v1";
		
		String synonymInput= inputWord();

		Response responseFromAPI =
		given()
			.param("word", synonymInput)
			.param("language", "en_US")
			.param("key", "API-KEY") //API key must be retrieved from the portal
			.param("output", "xml")
		.when()
		.get()
		.then()
		.statusCode(200).extract().response();
		
		int responseCode = responseValidator(responseFromAPI);
		if(responseCode==404) {
			System.out.println("Please verify the spelling and input the word again.");
			inputWord();
		}
		else if(responseCode!=200)
		{
			System.out.println("Error: Reponse code"+ responseCode);
			
		}
	
		String responseString = responseFromAPI.asString();
		System.out.println(responseString);


	}
	public static int responseValidator(Response re)
	{
		int j = re.getStatusCode();
		return j;
		
	}
	
	public static String inputWord() throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Input the word");
		String synonymInput = br.readLine();
		return synonymInput;
	}
	
	
}
