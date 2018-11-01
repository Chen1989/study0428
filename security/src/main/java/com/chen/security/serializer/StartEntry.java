package com.chen.security.serializer;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.Random;

/**
 * Created by PengChen on 2018/9/29.
 */

public class StartEntry {
    public static void main(String[] args) {
//        Person person = new Person(28, "chen", "男");
//        try {
//            ObjectOutputStream outputStream =
////                    new ObjectOutputStream(new FileOutputStream("sdcard/test/test.txt"));
//                    new ObjectOutputStream(new FileOutputStream("D:\\app\\Person.txt"));
//            outputStream.writeObject(person);
//            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("D:\\app\\Person.txt"));
//            Person p = (Person) inputStream.readObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        writeFile4();
//        deleteDirectory(new File("D:\\app\\ca"));
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            System.out.println("getAbsolutePath nextInt = " + random.nextInt());
        }
    }

    private static void writeFile1() {
        try {
            DataOutputStream stream = new DataOutputStream(new FileOutputStream("D:\\app\\1027.txt"));
            stream.write("你大爷的啊".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFile2() {
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream("D:\\app\\1027.txt"));
            stream.write("aaa".getBytes());
            stream.close();//写完之后这一句必须调用
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFile3() {
        String mes = "你好,world!你好,world!你好,world!你好,world!" +
                "你好,world!你好,world!你好,world!你好,world!你好,world!" +
                "你好,world!你好,world!你好,world!你好,world!你好,world!你好,world!" +
                "你好,world!你好,world!你好,world!你好,world!你好,world!你好,world!" ;
        byte[] b = mes.getBytes() ;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream() ;
        try {
            byteArrayOutputStream.write( b );

            FileOutputStream fileOutputStream = new FileOutputStream( new File( "D:\\app\\1027.txt") ) ;

            byteArrayOutputStream.writeTo( fileOutputStream ) ;

            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeFile4() {
        try {
            File file = new File("D:\\app\\ca\\ca\\1027.txt");
            System.out.println("getAbsolutePath = " + file.getAbsolutePath());
            System.out.println("getAbsolutePath = " +
                    file.getAbsolutePath().lastIndexOf(File.separator));
            String parent = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(File.separator));
            System.out.println("parent = " + parent);

            new File(parent).mkdirs();
            System.out.println("parent list = " + new File(parent).getParentFile().listFiles());
            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
//            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream("D:\\app\\1027.txt"));
            accessFile.write("aaa".getBytes());
            accessFile.write("你好,world!你好,world!你好,world!".getBytes());
            accessFile.close();//写完之后这一句必须调用

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteDirectory(File file) {
        if (file.isFile()) {// 表示该文件不是文件夹
            file.delete();
        } else {
            // 首先得到当前的路径
//            String[] childFilePaths = file.list();
            File[] childFiles = file.listFiles();
            for (File childFile : childFiles) {
                deleteDirectory(childFile);
            }
            file.delete();
        }
    }

    private static void writeEnum() {
        try {
            ObjectOutputStream outputStream =
//                    new ObjectOutputStream(new FileOutputStream("sdcard/test/test.txt"));
                    new ObjectOutputStream(new FileOutputStream("D:\\app\\enum.txt"));
            ChenEnum p0 = ChenEnum.INSTANCE1;
            outputStream.writeObject(p0);
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("D:\\app\\enum.txt"));
            ChenEnum p = (ChenEnum) inputStream.readObject();
            System.out.println("result = " + (p == p0));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
