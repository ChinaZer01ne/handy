package com.handy;
/**
 * 自定义ClassLoader来加载热更新的文件
 * @author Zer01ne
 * @since 2020/8/20 22:32
 */
public class HandyClassLoader extends ClassLoader {
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return findClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

}
