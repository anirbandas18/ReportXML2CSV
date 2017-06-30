package com.sss.report;

import java.io.IOException;
import java.util.List;

import com.sss.report.entity.Profile;
import com.sss.report.exception.ReportException;
import com.sss.report.service.XML2CSVService;

public class ReportGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xmlFileRepositoryPath = args[0];
		String csvReportRepositoryPath = args[1];
		try {
			//System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt")), true));
			XML2CSVService service = new XML2CSVService();
			List<Profile> profiles = service.parseXML(xmlFileRepositoryPath);
			service.persistCSV(profiles, csvReportRepositoryPath);
		} catch (ReportException | IOException e) {
			// TODO Auto-generated catch block
			// System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
