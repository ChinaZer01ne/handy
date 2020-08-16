package com.handy;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
    public Map<String, String> scan(String path) throws IOException {
        File file = new File(path);
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("File path is error!");
        }

        List<File> javaFiles = new ArrayList<>();
        Arrays.asList(Objects.requireNonNull(file.listFiles(File::isDirectory))).forEach(f->{
            
        });
        return new HashMap<>();
    }
}
