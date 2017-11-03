package UrlScapper;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UrlScrapper {

	public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		String searchTerm = "site:carfolio.com+intitle:'Volvo'+'XC90'+2013";
		int num = 1;
		scanner.close();
		String searchURL = GOOGLE_SEARCH_URL + "?q="+searchTerm+"&num="+num;
		//without proper User-Agent, we will get 403 error
		Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
		
		//below will print HTML data, save it to a file and open in browser to compare
		//System.out.println(doc.html());
		
		//If google search results HTML change the <h3 class="r" to <h3 class="r1"
		//we need to change below accordingly
		
		
		Elements results = doc.select("h3.r > a");

		for (Element result : results) {
			String linkHref = result.attr("href");
			String linkText = result.text();
			System.out.println(linkText);
			//System.out.println("Text::" + linkText + ", URL::" + linkHref.substring(6, linkHref.indexOf("&")));
			String link = linkHref.substring(6, linkHref.indexOf("&"));
			link = URLDecoder.decode(link, "UTF-8");
			
			//Before putting into the file, check if th linkText contains the search query terms
			System.out.println(link);
		}
	}

}