package com.sss.report;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import com.sss.report.entity.Profile;
import com.sss.report.exception.ReportException;
import com.sss.report.model.ProfileMetadataModel;
import com.sss.report.service.XML2CSVService;

public class ReportGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xmlFileRepositoryPath = args[0];
		try {
			System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt")), true));
			XML2CSVService service = new XML2CSVService();
			List<Profile> profiles = service.parseXML(xmlFileRepositoryPath);
			System.out.println(profiles.size());
			ProfileMetadataModel pmdm = service.getProfileMetadata(profiles);
			String md = pmdm.toString();
			System.out.println(md);
		} catch (ReportException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			// System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
