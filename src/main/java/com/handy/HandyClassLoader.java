package com.handy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义ClassLoader来加载热更新的文件
 * @author Zer01ne
 * @since 2020/8/20 22:32
 */
public class HandyClassLoader extends ClassLoader {

    public HandyClassLoader() {
        super(null);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (isFullClassName(name)) {
            return getSystemClassLoader().loadClass(name);
        }
        return findClass(name);
    }

    /**
     * 是否是全限定类名
     * @param name : 类路径或者全限定类名
     * @return boolean
     */
    private boolean isFullClassName(String name) throws ClassNotFoundException {
        if (name == null || "".equals(name.trim())) {
            throw new ClassNotFoundException();
        }
        return !name.contains("\\");
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] classBytes = loadClassData(name);
        String classNameFromFullClassName = FileOperator.getFullClassNameFromClassFile(name);
        return defineClass(classNameFromFullClassName, classBytes, 0, classBytes.length);
    }

    /**
     * 加载类数据
     * @param name : class路径名
     * @return byte[] 类字节数据
     */
    private byte[] loadClassData(String name) {
        try (InputStream fileInputStream = new FileInputStream(new File(name));
             ByteArrayOutputStream  byteOutputStream = new ByteArrayOutputStream ();) {
            int read;
            while ((read = fileInputStream.read()) != -1) {
                byteOutputStream.write(read);
            }
            return byteOutputStream.toByteArray();
        } catch (FileNotFoundException e) {
            System.out.println(name + " is not find!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * 批量加载类
     * @param changeFileCompilePathList : 要加载的类文件
     * @return java.util.List<java.lang.Class<?>>
     * @throws ClassNotFoundException if can not find class
     */
    public List<Class<?>> loadClass(List<String> changeFileCompilePathList) throws ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        for (String classFilePath : changeFileCompilePathList) {
            Class<?> clazz = loadClass(classFilePath);
            System.out.println(clazz + " reload finished!");
            classList.add(clazz);
        }
        return classList;
    }
}
