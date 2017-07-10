package com.sss.report.dao;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import com.sss.report.core.Utility;
import com.sss.report.model.PersistenceReport;
import com.sss.report.model.ReportModel;

public class CSVDAO implements Callable<PersistenceReport>{
	
	private ReportModel reportModel;
	
	public CSVDAO(ReportModel reportModel) {
		this.reportModel = reportModel;
	}	

	@Override
	public PersistenceReport call() throws Exception {
		StringWriter sw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(sw);
		long start = System.currentTimeMillis();
		bw.write(Utility.DELIMITTER + Utility.getChildDirName(reportModel.getCsvFileNameWithoutExt()));
		bw.newLine();
		for(String property : reportModel.getProperties()) {
			Object item = null;
			for(Object z : reportModel.getContent()) {
				if(Utility.isFieldPresent(z, reportModel.getPropertyKey(), property)) {
					item = z;
					break;
				}
			}
			String line = property + Utility.DELIMITTER;
			if(item != null) {
				line = line + item.toString();
			} 
			bw.write(line);
			bw.newLine();
		}
		long end = System.currentTimeMillis();
		bw.flush();
		byte[] bytes = sw.toString().getBytes();
		String csvFilePath = Utility.getEquivalentCSVFileName(reportModel.getCsvFileNameWithoutExt());
		Files.write(Paths.get(csvFilePath), bytes);
		long duration = end - start;
		PersistenceReport pr = new PersistenceReport();
		pr.setDuration(duration);
		pr.setSize(Utility.bytesToLong(bytes));
		return pr;
	}

}
