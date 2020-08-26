package com.handy;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 加载类
 *
 * @author Zer01ne
 * @since 2020/8/19 21:15
 */
public class Loader {

    /**
     * 自定义类加载器，用来加载类
     */
    private HandyClassLoader handyClassLoader = new HandyClassLoader();
    /**
     * 发生变化的文件编译到的目标路径
     */
    private List<String> changeFileCompilePathList = new ArrayList<>();
    /**
     * 发生变化的文件编译成类后的全限定类名
     */
    private List<String> fullClassNameList = new ArrayList<>();

    /**
     * 加载java文件
     *
     * @param changeFilePathList : 文件绝对路径集合
     */
    public void loadClass(List<String> changeFilePathList) throws IOException, ClassNotFoundException {
        changeFileCompilePathList = getCompilePath(changeFilePathList);
        fullClassNameList = FileOperator.getFullClassNameList(changeFilePathList);
        //// 编译
        compileJavaFile(changeFilePathList);
        //// 加载
        //doLoadClass(changeFileCompilePathList);
        doLoadClass(changeFileCompilePathList);
    }



    /**
     * 加载类文件
     * @param changeFileCompilePathList : 需要加载的class文件路径
     */
    private void doLoadClass(List<String> changeFileCompilePathList) throws ClassNotFoundException {
        if (changeFileCompilePathList == null || changeFileCompilePathList.size() == 0) {
            throw new ClassNotFoundException("No class file can file!");
        }

        List<Class<?>> classList = handyClassLoader.loadClass(changeFileCompilePathList);
        for (Class<?> aClass : classList) {
            Object o = null;
            try {
                o = aClass.getConstructor().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            //Context context = (Context) o;
            System.out.println(Context.class.getClassLoader());
            System.out.println(o.getClass().getClassLoader());
            System.out.println(handyClassLoader.getParent());
        }
    }

    /**
     * 编译java文件
     * @param changeFilePathList : 待编译的java文件的路径
     */
    private void compileJavaFile(List<String> changeFilePathList) throws IOException {
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
    }

    /**
     * 获取文件编译后的路径
     * @param changeFilePathList :
     * @return java.util.List<java.lang.String>
     */
    public List<String> getCompilePath(List<String> changeFilePathList) {
        if (changeFilePathList == null || changeFilePathList.size() == 0) {
            return new ArrayList<>();
        }
        return changeFilePathList.stream()
                .map(javaSourceFilePath -> javaSourceFilePath.replace("src\\main\\java", "target\\classes"))
                .map(javaClassFilePath -> javaClassFilePath.replace(".java", ".class"))
                .collect(Collectors.toList());
    }

    public List<String> getChangeFileCompilePathList() {
        return Collections.unmodifiableList(changeFileCompilePathList);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Loader loader = new Loader();
        //loader.loadClass(Arrays.asList("D:\\IdeaProjects\\handy\\src\\main\\java\\com\\handy\\Context.java"));
        //List<String> compilePath = loader.getCompilePath(Arrays.asList("D:\\IdeaProjects\\handy\\src\\main\\java\\com\\handy\\Context.java"));
        //loader.doLoadClass(compilePath);
        loader.loadClass(Arrays.asList("C:\\Project\\handy\\src\\main\\java\\com\\handy\\Context.java"));
    }

}
