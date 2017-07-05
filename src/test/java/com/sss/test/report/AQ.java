package com.sss.test.report;

import java.io.*;

public class AQ {

  public static void main(String args[]) {
    try {
        FileInputStream fis = new FileInputStream("input/Account_CCP__c.xml");
        BufferedReader r = new BufferedReader(new InputStreamReader(fis,
                "UTF8"));
        for (String s = ""; (s = r.readLine()) != null;) {
            System.out.println(s);
        }
        r.close();
        System.exit(0);
    }

    catch (Exception e) {
        e.printStackTrace();
        System.exit(1);
    }
  }
}