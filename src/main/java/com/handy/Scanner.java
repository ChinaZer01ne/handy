package com.handy;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 扫描项目变更文件
 * @author Zer01ne
 * @since 2020/8/16 16:40
 */
public class Scanner {

    /**
     * 扫描指定目录下的所有.java文件和资源文件
     * @param path : 路径
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String, Long> scan(String path) {
        List<File> files = doScan(path);
        return fileRemark(files);
    }

    /**
     * 记录文件的最后修改事件，保存变化
     * @param files : 所有项目文件
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    private Map<String, Long> fileRemark(List<File> files) {
        if (files == null || files.size() == 0) {
            return new HashMap<>();
        }
        int capacity = (int) Math.ceil(files.size() / 0.75);
        Map<String, Long> fileLastModifiedMap = new HashMap<>(capacity);
        for (File file : files) {
            long lastModified = file.lastModified();
            fileLastModifiedMap.put(file.getPath(), lastModified);
        }

        return fileLastModifiedMap;
    }

    /**
     * 扫描所有资源文件
     * @param path : 项目路径
     * @return java.util.List<java.io.File>
     */
    private List<File> doScan(String path) {
        File file = new File(path);
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("File path is error!");
        }

        List<File> sourceFiles = findSource(file);

        return getAllJavaFiles(sourceFiles);
    }

    /**
     * 获取项目所有的java文件
     * @param sourceFiles :
     * @return java.util.List<java.io.File>
     */
    private List<File> getAllJavaFiles(List<File> sourceFiles) {
        if (sourceFiles == null || sourceFiles.size() == 0) {
            return new ArrayList<>();
        }

        List<File> allJavaFiles = new LinkedList<>();
        for (File file : sourceFiles) {
            allJavaFiles.addAll(recursiveJavaFiles(file));
        }
        return allJavaFiles;
    }

    /**
     * dfs java文件
     * @param file :
     * @return java.util.Collection<? extends java.io.File>
     */
    private Collection<? extends File> recursiveJavaFiles(File file) {
        List<File> fileList = new LinkedList<>();
        if (!file.isDirectory()) {
            if (file.getPath().endsWith(".java")) {
                fileList.add(file);
                return fileList;
            }
            return new ArrayList<>();
        }

        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            return new ArrayList<>();
        }

        for (File fileItem : files) {
            fileList.addAll(recursiveJavaFiles(fileItem));
        }

        return fileList;
    }

    /**
     * 查找src/java/main文件
     * @param file : root目录
     * @return java.util.List<java.io.File>
     */
    private List<File> findSource(File file) {
        List<File> allSourceFiles = new ArrayList<>();
        Queue<File> queue = new LinkedList<>();
        queue.add(file);
        List<File> currentPhaseFiles = new LinkedList<>();
        while (!queue.isEmpty()) {
            while (!queue.isEmpty()) {
                File curFile = queue.poll();
                if (curFile.isDirectory()) {
                    currentPhaseFiles.add(curFile);
                }
            }
            allSourceFiles.addAll(checkCurrentPhaseFiles(currentPhaseFiles));
            int size = currentPhaseFiles.size();
            while (size != 0) {
                File removeFile = currentPhaseFiles.remove(--size);
                File[] files = removeFile.listFiles();
                if (files != null && files.length > 0) {
                    queue.addAll(Arrays.asList(files));
                }
            }
        }

        return allSourceFiles;
    }

    /**
     * 校验当前文件是否是资源文件
     * @param currentPhaseFiles :
     * @return java.util.List<java.io.File>
     */
    private Set<File> checkCurrentPhaseFiles(List<File> currentPhaseFiles) {
        Set<File> sourceFileSet = new HashSet<>();
        for (File currentPhaseFile : currentPhaseFiles) {
            if (currentPhaseFile.getPath().endsWith("src\\main\\java")) {
                sourceFileSet.add(currentPhaseFile);
            }
        }
        return sourceFileSet;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner();
        Map<String, Long> scan = scanner.scan(System.getProperty("user.dir"));
        System.out.println(scan);
    }
}
