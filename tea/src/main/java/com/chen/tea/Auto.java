package com.chen.tea;

public class Auto {
    private static Object ob = new Object();
    public static void main(String[] args) {
        System.out.println("args = " + args.length);
//        new FileUtil().readFile(args);
        testTimer();
//        String str = "aaskfkdskdhafjsfaejaskndc";
//        System.out.println(Character.toUpperCase(str.charAt(0)) + str.substring(1));
    }

    private static void testTimer() {
        long per = 3000;
        while (true) {
            System.out.println("just for test time period " + System.currentTimeMillis());
            synchronized (ob) {
                per = per + 3000;
                try {
                    ob.wait(per);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
