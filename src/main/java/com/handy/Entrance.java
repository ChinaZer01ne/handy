package com.handy;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;

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

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread already start!");
        });

    }

    public static void premain(String agentArgs) {

    }
}
