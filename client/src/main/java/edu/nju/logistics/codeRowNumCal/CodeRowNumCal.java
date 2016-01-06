package edu.nju.logistics.codeRowNumCal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * 统计代码行数
 * 
 * @author 董轶波
 *
 */
public class CodeRowNumCal {

	public static final String PATH = "E:\\workspace\\logistics";

	public static int COUNT = 0;

	public static int NO_ANNOTATION_COUNT = 0;

	public static void main(String[] args) {

		mainMethod(PATH);

		System.out.println("当前代码行数为(包含注释) " + COUNT);
		System.out.println("当前代码行数为(不包含注释) " + NO_ANNOTATION_COUNT);
		System.out.println("当前代码的注释行数为 " + (COUNT - NO_ANNOTATION_COUNT));
	}

	private static void mainMethod(String path) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				// 递归调用
				mainMethod(file.getPath());
			} else {
				// 只统计.java文件
				String temp = file.getPath();
				if (temp.substring(temp.length() - 4, temp.length()).equals("java")) {
					codeCal(temp);
				}
			}
		}
	}

	private static void codeCal(String path) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(path));
			String line = "";
			while ((line = reader.readLine()) != null) {
				// 空行不计入统计
				if (!line.trim().equals("")) {
					COUNT++;
					if (noAnnotation(line)) {
						NO_ANNOTATION_COUNT++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean noAnnotation(String line) {
		if (line.contains("//")) {
			return false;
		} else if (line.contains("*")) {
			return false;
		}
		return true;

	}

}
