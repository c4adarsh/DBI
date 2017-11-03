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

public class UrlScrapper {

	public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
	public static void main(String[] args) throws IOException {
		String file = "formatted_source.txt";
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
				//line = line.substring(1, line.length()-2);	
				String qry = "site:carfolio.com+intitle:" + line;
				//int num = 1;
				URL url = new URL(
		                "https://www.googleapis.com/customsearch/v1?key="+key+"&cx="+ unique + "&q="+ qry + "&alt=json");
				//without proper User-Agent, we will get 403 error

				//boolean exception = false;

				BufferedReader results = null;
				
				HttpURLConnection conn = null;

				try{
					conn = (HttpURLConnection) url.openConnection();
			        conn.setRequestMethod("GET");
			        conn.setRequestProperty("Accept", "application/json");
			        results = new BufferedReader(new InputStreamReader(
			                (conn.getInputStream())));
				}catch (Exception e) {
					//exception = true;
					e.printStackTrace();
				}
				
				String output;
				
				while (results!=null && (output = results.readLine()) != null) {
					//System.out.println(output);
					bw.write(output);
		            if(output.contains("\"link\": \"")){                
		                String link=output.substring(output.indexOf("\"link\": \"")+
		                       ("\"link\": \"").length(), output.indexOf("\","));
		                System.out.println(link);
		                //bw.write(line + " : " +link);
						//bw.newLine();
						//break;
		            }     
		        }
				
				if(conn!=null){
					 conn.disconnect();  
				}

//				if(results!=null){
//					for (Element result : results) {
//						String linkHref = result.attr("href");
//						String linkText = result.text();
//						System.out.println(linkText);
//						//System.out.println("Text::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&")));
//						String link = linkHref.substring(6, linkHref.indexOf("&"));
//						link = URLDecoder.decode(link, "UTF-8");
//						//Before putting into the file, check if th linkText contains the search query terms
//						//System.out.println(line + " " + link);
//						//line = line.replaceAll("\'","");
//						String[] parts = line.split("\\+");
//						boolean linkFound = true;
//						for(String part : parts){
//							if(!line.contains(part)){
//								linkFound = false;
//							}
//						}
//						if(linkFound){
//							bw.write(line + " " +link);
//							bw.newLine();
//						}
//
//					}
//				}
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