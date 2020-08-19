package com.handy;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 加载类
 *
 * @author Zer01ne
 * @since 2020/8/19 21:15
 */
public class Loader {

    /**
     * 加载java文件
     *
     * @param changeFilePathList : 文件绝对路径集合
     */
    public void loadClass(List<String> changeFilePathList) throws IOException, ClassNotFoundException {
        // TODO 编译
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> javaFileObjectsFromPath = stdFileManager.getJavaFileObjectsFromStrings(changeFilePathList);
        //JavaFileObject a = stdFileManager.getJavaFileForOutput(StandardLocation.SOURCE_OUTPUT, "a/a", JavaFileObject.Kind.CLASS, null);
        //List<File> classFilePathList = changeFilePathList.stream()
        //        .map(javaFilePath -> javaFilePath.replace("src\\main\\java", "target\\classes"))
        //        .map(s -> s.substring(0,s.lastIndexOf("\\")))
        //        .map(s -> new File(s))
        //        .collect(Collectors.toList());
        List<String> optionList = changeFilePathList.stream()
                .map(javaFilePath -> javaFilePath.replace("src\\main\\java", "target\\classes"))
                .map(s -> s.substring(0,s.lastIndexOf("target\\classes") + 15))
                .map(s -> Arrays.asList("-d" ,s))
                .flatMap(Collection::stream).collect(Collectors.toList());
        //stdFileManager.setLocation(StandardLocation.SOURCE_OUTPUT,classFilePathList);
        JavaCompiler.CompilationTask task = compiler.getTask(null, stdFileManager, null, optionList, null, javaFileObjectsFromPath);
        task.call();
        stdFileManager.close();
        // TODO 加载
        //ClassLoader classLoader = this.getClass().getClassLoader();
        //classLoader.loadClass("Context.class");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Loader loader = new Loader();
        loader.loadClass(Arrays.asList("D:\\IdeaProjects\\handy\\src\\main\\java\\com\\handy\\Context.java"));
    }
}
