/**
 * projectName: handy
 * fileName: FileOperator.java
 * packageName: com.handy
 * date: 2020-08-26 11:25
 * copyright(c) 2020 http://www.hydee.cn/ Inc. All rights reserved.
 */
package com.handy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Zer01ne
 * @since 2020/8/26 20:26
 */
public class FileOperator {


    private FileOperator(){}

    /**
     * 从class文件路径中获取类名
     * @param classFilePath : 类文件路径
     * @return java.lang.String
     */
    public static String getClassNameFromFullClassName(String classFilePath) {
        if (classFilePath == null || "".equals(classFilePath.trim())) {
            return null;
        }

        if (FileOperationCache.cache.get(classFilePath) != null) {
            return FileOperationCache.cache.get(classFilePath);
        }

        try {
            String fullClassName = classFilePath.substring(classFilePath.lastIndexOf("\\") + 1, classFilePath.lastIndexOf("."));
            FileOperationCache.cache.put(classFilePath, fullClassName);
            return fullClassName;
        }catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * 获取类的全限定类名
     * @param changeFilePathList : java文件路径
     * @return java.util.List<java.lang.String>
     */
    public static List<String> getFullClassNameList(List<String> changeFilePathList) {
        if (changeFilePathList == null || changeFilePathList.size() == 0) {
            throw new IllegalArgumentException("No file find!");
        }
        return changeFilePathList.stream().map(javaFilePath -> {
            String fullClassPath = getFullClassNameFromJavaSourceFile(javaFilePath);
            if (fullClassPath != null) return fullClassPath;
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 从java文件路径中获取类的全限定类名
     * @param javaFilePath : java文件路径
     * @return java.lang.String
     */
    public static String getFullClassNameFromJavaSourceFile(String javaFilePath) {
        String packagePath = javaFilePath.split("src\\\\main\\\\java")[1];
        //if (packagePath != null && packagePath.length() > 0 && !"\\".equals(packagePath)) {
        if (packagePath != null && packagePath.length() > 0) {
            String fullClassPath = packagePath.replaceAll("\\\\", ".");
            // 第一个0位置是 `.`
            return fullClassPath.substring(1,fullClassPath.lastIndexOf(".java"));
        }
        return null;
    }

    /**
     * 从类文件路径中获取类的全限定类名
     * @param javaFilePath : java文件路径
     * @return java.lang.String
     */
    public static String getFullClassNameFromClassFile(String javaFilePath) {
        String packagePath = javaFilePath.split("target\\\\classes")[1];
        //if (packagePath != null && packagePath.length() > 0 && !"\\".equals(packagePath)) {
        if (packagePath != null && packagePath.length() > 0) {
            String fullClassPath = packagePath.replaceAll("\\\\", ".");
            // 第一个0位置是 `.`
            return fullClassPath.substring(1,fullClassPath.lastIndexOf(".class"));
        }
        return null;
    }

    /**
     * 文件操作缓存
     * @author Zer01ne
     * @since 2020/8/26 21:22
     */
    private static class FileOperationCache {
        /** classFilePath -> fullClassName */
        protected static Map<String, String> cache = new HashMap<>();

    }

    public static void main(String[] args) {
        System.out.println(getClassNameFromFullClassName("C:\\a\\b\\c\\d.class"));
    }
}