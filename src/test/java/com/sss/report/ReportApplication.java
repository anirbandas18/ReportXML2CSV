package com.sss.report;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.ExecutionException;

import com.sss.report.core.HibernateUtil;
import com.sss.report.service.XMLServices;

public class ReportApplication {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		System.setProperty("derby.system.home", System.getProperty("user.dir"));
		System.out.println(System.getProperty("derby.system.home"));
		HibernateUtil.create();
		Long currentTime = 10l;
		String stdoutName = String.valueOf(currentTime) + "_STDOUT.txt";
		String stderrName = String.valueOf(currentTime) + "_STDERR.txt";
		//System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(stdoutName)), true));
		//System.setErr(new PrintStream(new BufferedOutputStream(new FileOutputStream(stderrName)), true));
		XMLServices xmlService = new XMLServices();
		xmlService.parseProfiles(args[0]);
		//System.setOut(System.out);
		//System.setErr(System.err);
		System.out.println("Parsing finished");
		HibernateUtil.shutdown();
	}
	
}