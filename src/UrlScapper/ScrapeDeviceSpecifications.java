package UrlScapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ScrapeDeviceSpecifications {

	//public static final String GOOGLE_SEARCH_URL = "https://www.devicespecifications.com/en/model/";

	public static void main(String[] args) {

		String file = "input_links.txt";

		File fout = new File("output_features.txt");

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

				URL url = new URL(line);
				BufferedReader response = null;
				HttpURLConnection conn = null;

				try{
					HttpURLConnection httpcon = (HttpURLConnection) url.openConnection(); 
					httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
					httpcon.setRequestMethod("GET");
					//					response = new BufferedReader(new InputStreamReader(
					//							(httpcon.getInputStream())));
					//					System.out.println(response);
					Document doc = parseXML(httpcon.getInputStream());
					parseResponse(doc);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}

		}catch (Exception e) {
			e.printStackTrace();
		}

	}


	private static Document parseXML(InputStream stream)
			throws Exception
	{
		DocumentBuilderFactory objDocumentBuilderFactory = null;
		DocumentBuilder objDocumentBuilder = null;
		Document doc = null;
		try
		{
			objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

			doc = objDocumentBuilder.parse(stream);
		}
		catch(Exception ex)
		{
			throw ex;
		}       

		return doc;
	}

	private static void parseResponse(Document doc) {
		Element root = doc.getElementById("model-brief-specifications");
		System.out.println(root.toString());	
	}

}
