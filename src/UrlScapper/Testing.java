package UrlScapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Testing {

	public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
	public static void main(String[] args) throws IOException {
		String file = "formatted_source.txt";
		String testing = "testing.txt";
		String key="AIzaSyCFOPoY0YQEvftk2QmSdpqHC9BHuyU3WJY";
		String unique = "008884397582340606990:ee4l3zm2yyo";
		//writing to output file
		//for writing into new file required for processing
		File fout = new File("links.txt");

		FileOutputStream fos;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(fout);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String qry = "site:carfolio.com+intitle:" + line;
				System.out.println(qry); 
				URL url = new URL(
						"https://www.googleapis.com/customsearch/v1?key="+key+"&cx="+ unique + "&q="+ qry + "&alt=json");
				JSONObject results = null;
				BufferedReader response = null;
				HttpURLConnection conn = null;

				try{
					conn = (HttpURLConnection) url.openConnection();
			        conn.setRequestMethod("GET");
			        conn.setRequestProperty("Accept", "application/json");
			        response = new BufferedReader(new InputStreamReader(
			                (conn.getInputStream())));
			        System.out.println(response);
					Object obj = new JSONParser().parse(response);
					results = (JSONObject) obj;
					if(results!=null){
						System.out.println(results);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}

				if(results!=null) {
					JSONArray mArray = (JSONArray) results.get("items");
					for(Object item : mArray){
						JSONObject itemCurr = (JSONObject)item;
						String title = (String) itemCurr.get("title");
						String[] parts = line.split("\\+");
						boolean linkFound = true;
						for(String part : parts){
							if(!title.contains(part)){
								linkFound = false;
							}
						}
						if(linkFound){
							String link = (String)itemCurr.get("link");
							bw.write(line + " " +link);
							bw.newLine();
							break;
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}	

		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}