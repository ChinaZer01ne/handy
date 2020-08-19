package com.handy;

import java.util.*;

/**
 * 上下文类，记录了整体结构
 * @author Zer01ne
 * @since 2020/8/16 16:48
 */
public class Context {

    /**
     * 发生变化的文件的路径
     */
    List<String> changeFilePathList = new ArrayList<>();
    /**
     * java文件路径和最后修改时间的映射
     */
    private Map<String, Long> scannedClassMap = new HashMap<>();
    /**
     * 扫描器
     * */
    private Scanner scanner;

    /**
     * 构建上下文
     */
    public void build() {
        if (scanner == null) {
            throw new IllegalArgumentException("scanner can not be null");
        }
        scannedClassMap = scanner.scan(System.getProperty("user.dir"));
    }

    /**
     * 重新加载class
     */
    public void reloadClass() {
        if (changeFilePathList == null || changeFilePathList.size() == 0) {
            return;
        }
        // TODO 重新加载类文件
        System.out.println("class reload finished!");
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Map<String, Long> getScannedClassMap() {
        return Collections.unmodifiableMap(scannedClassMap);
    }

    public void setScannedClassMap(Map<String, Long> scannedClassMap) {
        this.scannedClassMap = scannedClassMap;
    }

    public List<String> getChangeFilePathList() {
        return Collections.unmodifiableList(changeFilePathList);
    }

    public void setChangeFilePathList(List<String> changeFilePathList) {
        this.changeFilePathList = changeFilePathList;
    }

}
