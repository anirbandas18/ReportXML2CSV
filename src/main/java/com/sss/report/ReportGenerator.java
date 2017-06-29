package com.sss.report;

import java.util.List;

import com.sss.report.entity.Profile;
import com.sss.report.exception.ReportException;
import com.sss.report.service.XML2CSVService;

public class ReportGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xmlFileRepositoryPath = args[0];
		try {
			XML2CSVService service = new XML2CSVService();
			List<Profile> profiles = service.parseXML(xmlFileRepositoryPath);
			for(Profile profile : profiles) {
				System.out.println(profile);
			}
		} catch (ReportException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}
