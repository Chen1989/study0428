package com.chen.security.serializer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by PengChen on 2018/9/29.
 */

public class StartEntry {
    public static void main(String[] args) {
        Person person = new Person(28, "chen", "男");
        try {
            ObjectOutputStream outputStream =
//                    new ObjectOutputStream(new FileOutputStream("sdcard/test/test.txt"));
                    new ObjectOutputStream(new FileOutputStream("D:\\app\\Person.txt"));
            outputStream.writeObject(person);
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("D:\\app\\Person.txt"));
            Person p = (Person) inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
