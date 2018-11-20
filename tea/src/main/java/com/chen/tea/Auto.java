package com.chen.tea;

public class Auto {
    public static void main(String[] args) {
        System.out.println("args = " + args.length);
        new FileUtil().readFile(args);
//        String str = "aaskfkdskdhafjsfaejaskndc";
//        System.out.println(Character.toUpperCase(str.charAt(0)) + str.substring(1));
    }
}
