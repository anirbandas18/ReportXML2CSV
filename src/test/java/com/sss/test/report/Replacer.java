package com.sss.test.report;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Replacer {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Path dir = Paths.get(args[0]);
		String regex = "\",\"";
		DirectoryStream<Path> files = Files.newDirectoryStream(dir);
		for (Path f : files) {
			StringWriter sw = new StringWriter();
			long start = System.currentTimeMillis();
			BufferedReader br = new BufferedReader(new FileReader(f.toFile()));
			BufferedWriter bw = new BufferedWriter(sw);
			String line = "";
			while((line = br.readLine()) != null) {
				if(line.contains(regex)) {
					List<String> s = new ArrayList<>();
					line = line.substring(1, line.length() - 1);// remove first and last " character from each line
					List<String> tokens = new ArrayList<String>(Arrays.asList(line.split(regex))); // regex translates to ","
					for(String t : tokens) {
						String z = t;
						if(t.indexOf(",") != -1) {
							List<String> tkns = new ArrayList<String>(Arrays.asList(t.split(",")));
							z = String.join("\\,", tkns);
						} 
						s.add(z);
					}
					line = s.toString();
					line = line.substring(1, line.length() - 1);// removing [] from list's string representation
				}
				bw.write(line);
				bw.newLine();
			}
			br.close();
			bw.flush();
			long end = System.currentTimeMillis();
			long duration = end - start; 
			System.out.println(Files.write(f, sw.toString().getBytes()) + " " + duration + " ms");
		}
	}

}
