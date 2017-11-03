package UrlScapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class ReadFile {

	public static void main(String[] args){

		String file = "source.txt";

		//for writing into new file required for processing
		File fout = new File("formatted_source.txt");
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
				line = line.substring(1, line.length()-2);	
				String[] parts = line.split(",");
				StringBuilder mBuilder = new StringBuilder();
				int i = 0;
				for(String part : parts){
					if(i == parts.length - 1){
						mBuilder.append(part.trim());
					}else{
						mBuilder.append(part.trim()).append("+");
					} 
					i++;
				}
				
				bw.write(mBuilder.toString());
				bw.newLine();
				//System.out.println(mBuilder.toString());
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
