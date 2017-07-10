package com.sss.report.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utility {
	
	public static final String DELIMITTER = ",";
	public static final String PROPERTIES = "properties";
	public static final String PROFILE = "profile";

	public static String getChildDirName(String childDir) {
		int pos = childDir.lastIndexOf(File.separator);
		String childDirName = childDir.substring(pos + 1);
		return childDirName;
	}
	
	public static Path createDir(String dirPath) throws IOException {
		System.out.println(dirPath);
		Path csvRepositoryDir = Paths.get(dirPath);
		if(!Files.exists(csvRepositoryDir)) {
			csvRepositoryDir = Files.createDirectory(csvRepositoryDir);
		}
		return csvRepositoryDir;
	}
	
	public static boolean isFieldPresent(Object bean, String fieldName, String fieldValue) {
		Class<?> clazz = bean.getClass();
		boolean flag = true;
		try {
			Field f = clazz.getDeclaredField(fieldName);
			f.setAccessible(true);
			Object value = f.get(bean);
			flag = value.equals(fieldValue);
			f.setAccessible(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public static List<Object> getFieldByName(Object bean, String fieldName) throws IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = bean.getClass();
		List<Field> f = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));
		Field item = f.stream().filter(z -> z.getName().equals(fieldName)).findFirst().orElse(null);
		List<Object> content = new ArrayList<>();
		if(item != null) {
			item.setAccessible(true);
			content = (List<Object>) item.get(bean);
			item.setAccessible(false);
		}
		return content;
	}

}
