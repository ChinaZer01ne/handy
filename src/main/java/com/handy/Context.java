package com.handy;


import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
     * 类加载器
     * */
    private Loader loader;

    public Context() {
        loader = new Loader();
    }
    /**
     * 构建上下文
     */
    public void build() {
        if (scanner == null) {
            throw new IllegalArgumentException("scanner can not be null!");
        }
        scannedClassMap = scanner.scan(System.getProperty("user.dir"));
    }

    /**
     * 重新加载class
     */
    public void reloadClass() throws IOException, ClassNotFoundException {
        if (changeFilePathList == null || changeFilePathList.size() == 0) {
            return;
        }
        // 重新加载类文件
        loader.loadClass(changeFilePathList);
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
        //this.changeFileCompilePathList = loader.getCompilePath(changeFilePathList);
    }

    public void print(){
        System.out.println(213);
    }

}
