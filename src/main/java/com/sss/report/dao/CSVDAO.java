package com.sss.report.dao;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
		String columnName = Utility.getChildDirName(reportModel.getReportNameWithoutExt());
		bw.write(Utility.DELIMITTER + columnName);
		bw.newLine();
		for(Object property : reportModel.getProperties()) {
			Object item = null;
			int flag = 0;
			Object token = property;
			for(Object z : reportModel.getContent()) {
				if(Utility.isFieldPresent(z, reportModel.getPropertyKey(), property.toString())) {
					item = z;
					flag = 1;// profile
					break;
				} else {
					List<Object> content = Utility.getComplexFieldByName(property, reportModel.getPropertyKey());
					for(Object y : content) {
						if(Utility.isFieldPresent(y, reportModel.getPropertyKey(), z.toString())) {
							item = y;
							flag = 2;// property
							break;
						}
					}
					if(flag == 2) {
						token = Utility.getSimpleFieldByName(property, Utility.FILE_NAME);
						break;
					} else if (flag == 1) {
						token = z;
						break;
					}
				}
			}
			String line = token.toString() + Utility.DELIMITTER;
			if(item != null) {
				line = line + item.toString();
			} 
			bw.write(line);
			bw.newLine();
		}
		long end = System.currentTimeMillis();
		bw.flush();
		byte[] bytes = sw.toString().getBytes();
		String csvFilePath = Utility.getEquivalentCSVFileName(reportModel.getReportNameWithoutExt());
		Files.write(Paths.get(csvFilePath), bytes);
		long duration = end - start;
		PersistenceReport pr = new PersistenceReport();
		pr.setDuration(duration);
		pr.setSize(Utility.bytesToLong(bytes));
		return pr;
	}

}
