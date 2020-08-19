package com.handy;

import java.lang.instrument.Instrumentation;

/**
 * 程序入口
 * @author Zer01ne
 * @since 2020/8/16 16:57
 */
public class Entrance {

    public static void premain(String agentArgs, Instrumentation instrumentation){
        Scanner scanner = new Scanner();
        Context context = new Context();
        context.setScanner(scanner);
        context.build();

        Thread thread = new Thread(new FileCompareTask(context));
        System.out.println(thread.getThreadGroup().getParent());
        thread.setDaemon(true);
        thread.start();

        System.out.println("Thread already start!");
    }

    public static void premain(String agentArgs) {

    }

}
