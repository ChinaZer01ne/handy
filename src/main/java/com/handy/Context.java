package com.handy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 上下文类，记录了整体结构
 * @author Zer01ne
 * @since 2020/8/16 16:48
 */
public class Context {

    private Map<String, String> scannedClassMap = new HashMap<>();
    /**
     * 扫描器
     * */
    private Scanner scanner;

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void build() {
        if (scanner == null) {
            throw new IllegalArgumentException("scanner can not be null");
        }
        try {
            scannedClassMap = scanner.scan(System.getProperty("user.dir"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
