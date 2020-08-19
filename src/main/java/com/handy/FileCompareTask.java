package com.handy;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Zer01ne
 * @since 2020/8/19 23:50
 */
public class FileCompareTask implements Runnable {

    private Context context;

    public FileCompareTask(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Map<String, Long> newScanFileMap = context.getScanner().scan(System.getProperty("user.dir"));
            Map<String, Long> scannedClassMap = context.getScannedClassMap();
            List<String> changeFilePathList = compareChange(newScanFileMap, scannedClassMap);

            context.setScannedClassMap(newScanFileMap);
            context.setChangeFilePathList(changeFilePathList);

            System.out.println("already changed file : "+ changeFilePathList);

            try {
                context.reloadClass();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 比较扫描到的文件是否发生了变化
     * @param newScanFileMap : 新扫描到的文件
     * @param oldScanFileMap : 旧文件
     * @return java.util.List<java.lang.String> 发生变化的文件
     */
    private List<String> compareChange(Map<String, Long> newScanFileMap, Map<String, Long> oldScanFileMap) {
        List<String> changFilePathList = new LinkedList<>();
        for (Map.Entry<String, Long> pathLastModifiedEntry : newScanFileMap.entrySet()) {
            Long lastModified = oldScanFileMap.get(pathLastModifiedEntry.getKey());
            if (lastModified == null || !Objects.equals(lastModified, pathLastModifiedEntry.getValue())) {
                changFilePathList.add(pathLastModifiedEntry.getKey());
            }
        }
        return changFilePathList;
    }

}