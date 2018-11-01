package com.chen.security;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by PengChen on 2017/1/9.
 */
public class SecurityManager {

    public static void main(String[] args) {

        if(args != null && args.length >= 1) {
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("enckey")) {
                    encryptKey(args[1], args[2]);
                } else if (args[0].equalsIgnoreCase("enc_num")){
                    encryptNum(args[1], args[2]);
                }
            } else if (args.length == 4 && args[0].equalsIgnoreCase("encrypt")) {
                try {
                    encrypt(args[1], args[2], Integer.parseInt(args[3]));
                } catch (IOException var3) {
                    var3.printStackTrace();
                }
            } else if (args.length == 5 && args[0].equalsIgnoreCase("enc")
                    && args[1].equalsIgnoreCase("smix")) {
                try {
                    encryptSimx(args[2], args[3], args[4]);
                } catch (IOException var2) {
                    var2.printStackTrace();
                }
            }

        } else {
            System.out.println("no parameter");
        }

//        String input = "D:\\app\\js\\chen.txt";
//        String output = "D:\\app\\js\\chen1.txt";
//        String output2 = "D:\\app\\js\\chen2.txt";
//        encryptKey(input, output);
//        testDecKey(output, output2);
    }

    private static void testDecKey(String inStr, String outStr) {
        try {
            KeySecurityInputStream4 in = new KeySecurityInputStream4(new FileInputStream(inStr));
            FileOutputStream outputStream2 = new FileOutputStream(outStr);
            write(in, outputStream2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void encryptNum(String inStr, String outStr) {
        try {
            FileInputStream in = new FileInputStream(inStr);
            KeySecurityOutputStream5 outputStream2 = new KeySecurityOutputStream5(new FileOutputStream(outStr));
            write(in, outputStream2);
            in.close();
            outputStream2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void encryptKey(String inStr, String outStr) {
        try {
            FileInputStream in = new FileInputStream(inStr);
            KeySecurityOutputStream4 outputStream2 = new KeySecurityOutputStream4(new FileOutputStream(outStr));
            write(in, outputStream2);
            in.close();
            outputStream2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void encryptSimx(String src, String tar, String value) throws IOException {
        FileInputStream in = new FileInputStream(src);
        SMixOutputStream out = new SMixOutputStream(new FileOutputStream(tar), Integer.parseInt(value));

        write(in, out);
        in.close();
        out.close();
    }

    private static void encrypt(String src, String tar, int value) throws IOException {
        FileInputStream in = new FileInputStream(src);

        SecurityOutputStream out = new SecurityOutputStream(new FileOutputStream(tar), value);

        write(in, out);
        in.close();
        out.close();
    }

    public static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buff = new byte[4096];
        int buffLen;
        while ((buffLen = in.read(buff, 0, buff.length)) != -1) {
            out.write(buff, 0, buffLen);
        }
    }

    private static int mapping(Map<Integer, Integer> map, int value) {
        if (map.containsKey(Integer.valueOf(value))) {
            int mapValue = map.get(value);
            return mapValue;
        }
        return value;
    }

    private static Map<Integer, Integer> getMapping(int value) {
        Map<Integer, Integer> map = new HashMap();
        Random random = new Random(value);

        List<Integer> charList = new ArrayList();
        for (byte i = -128; i < 127; i = (byte)(i + 1)) {
            if (i != -1) {
                charList.add(Integer.valueOf(i));
            }
        }
        charList.add(Integer.valueOf(127));
        while (charList.size() > 1)
        {
            int index = random.nextInt(charList.size() - 1) + 1;
            map.put(charList.get(0), charList.get(index));
            map.put(charList.get(index), charList.get(0));
            charList.remove(index);
            charList.remove(0);
        }
        if (charList.size() != 0) {
            map.put(charList.get(0), charList.get(0));
        }
        return map;
    }


    public static class SecurityOutputStream extends OutputStream {
        private OutputStream _out;
        private Map<Integer, Integer> _mapping;

        public SecurityOutputStream(OutputStream out, int value) throws IOException {
            this._out = out;
            DataOutputStream dataOut = new DataOutputStream(out);
            int v = (int)System.nanoTime();

            this._mapping = getMapping(v);
            v = (~ v) ^ value;
            dataOut.writeInt(v);
        }

        public void write(byte[] b, int off, int len) throws IOException {
            for (int i = off; i < len; i++) {
                b[i] = ((byte)mapping(this._mapping, b[i]));
            }
            this._out.write(b, off, len);
        }

        public void write(int b) throws IOException {
            this._out.write(mapping(this._mapping, b));
        }
    }

    public static class SMixOutputStream extends OutputStream
    {
        private OutputStream _out;
        private byte[] mask;
        private int _index = 0;

        public SMixOutputStream(OutputStream out, int maskLen) throws IOException {
            this._out = out;

            this.mask = new byte[maskLen];
            Random random = new Random();

            byte[] writeMask = new byte[this.mask.length];
            int randomSeed = maskLen < 256 ? 256 : maskLen;
            for (int i = 0; i < maskLen; i++)
            {
                this.mask[i] = ((byte)random.nextInt(randomSeed));
                writeMask[i] = ((byte)(this.mask[i] ^ (maskLen + (i > 0 ? writeMask[(i - 1)] : 0) * (int)Math.pow(-1, i + maskLen))));
            }
            this._out.write(writeMask, 0, maskLen);
        }

        public void write(byte[] b, int off, int len) throws IOException {
            for (int i = off; i < len; i++) {
                b[i] = ((byte)(this.mask[(this._index++ % this.mask.length)] ^ b[i] ^ mask.length));
            }
            this._out.write(b, off, len);
        }

        public void write(int b) throws IOException {
            throw new IOException("not support write");
        }
    }

    //第一位一个数字，表示加密用到的字节数；
    public static class KeySecurityOutputStream5 extends OutputStream {
        private OutputStream _out;
        private int _index = 0;
        private byte[] byteArr;
        private int _num;

        KeySecurityOutputStream5(OutputStream outputStream) throws IOException {
            _out = outputStream;
            Random random = new Random();
            _num = random.nextInt(4) + 4;
            byteArr = new byte[_num + 1];
            byteArr[0] = (byte) _num;
            for (int i = 1; i <= _num; i++) {
                byteArr[i] = (byte) random.nextInt();
            }
            _out.write(byteArr);
        }

        public void write(byte[] b, int off, int len) throws IOException {
            for (int i = off; i < len; i++) {
                b[i] = (byte) (((b[i] & 0xff) >>> 4) | ((b[i] & 0xff) << 4));
                b[i] = (byte) ((~b[i]) ^ byteArr[_index % _num + 1]);
                _index++;
            }
            this._out.write(b, off, len);
        }

        @Override
        public void write(int b) throws IOException {

        }
    }

    public static class KeySecurityInputStream5 extends InputStream {
        private InputStream _in;
        private int _index = 0;
        private int _num;
        private byte[] _code;

        KeySecurityInputStream5(InputStream in) throws IOException {
            _in = in;
            byte[] tempArr = new byte[1];
            _in.read(tempArr, 0, 1);
            _num = tempArr[0];
            _code = new byte[_num];
            _in.read(_code, 0, _num);
        }

        public int read(byte[] b, int off, int len) throws IOException
        {
            int length = _in.read(b, off, len);
            for (int i = off; i < length; i++) {
                b[i] = (byte) ~(b[i] ^ _code[_index % _num]);
                b[i] = (byte) (((b[i] & 0xff) >>> 4) | ((b[i] & 0xff) << 4));
                _index++;
            }
            return length;
        }

        @Override
        public int read() throws IOException {
            return 0;
        }
    }

    //异或,取反,4位换位
    public static class KeySecurityOutputStream4 extends OutputStream
    {
        private OutputStream _out;
        private byte _temp1;
        private byte _temp2;
        private int _index = 0;

        public KeySecurityOutputStream4(OutputStream out) throws IOException
        {
            this._out = out;
            byte[] tempArr = new byte[2];
            byte tempRan1 = (byte)new Random().nextInt();
            byte tempRan2 = (byte)new Random().nextInt();
            this._temp1 = tempRan1;
            tempArr[0] = tempRan1;
            this._temp2 = tempRan2;
            tempArr[1] = tempRan2;
            this._out.write(tempArr);
        }

        public void write(byte[] b, int off, int len) throws IOException
        {
            for (int i = off; i < len; i++) {
                if (_index % 2 == 0) {
                    b[i] = (byte)((~b[i]) ^ _temp1);
                } else {
                    b[i] = (byte)(b[i] ^ (~_temp2));
                }
                b[i] = (byte) (((b[i] & 0xff) >>> 4) | ((b[i] & 0xff) << 4));
                _index++;
            }
            this._out.write(b, off, len);
        }

        public void write(int b) throws IOException
        {
//            this._out.write(b ^ this._temp ^ this._key.charAt(this._index++ % this._key.length()));
        }
    }

    public static class KeySecurityInputStream4 extends InputStream
    {
        private InputStream _in;
        private byte _temp1;
        private byte _temp2;
        private int _index = 0;

        public KeySecurityInputStream4(InputStream in) throws IOException
        {
            this._in = in;
            byte[] tempArr = new byte[2];
            this._in.read(tempArr, 0, 2);
            _temp1 = tempArr[0];
            _temp2 = tempArr[1];
        }

        public int read(byte[] b, int off, int len) throws IOException
        {
            int length = _in.read(b, off, len);
            for (int i = off; i < length; i++) {
                b[i] = (byte) (((b[i] & 0xff) >>> 4) | ((b[i] & 0xff) << 4));
                if (_index % 2 == 0) {
                    b[i] = (byte)~(b[i] ^ _temp1);
                } else {
                    b[i] = (byte)(b[i] ^ ~_temp2);
                }
                _index++;
            }
            return length;
        }

        @Override
        public int read() throws IOException {
            return 0;
        }
    }

    //异或,取反
    public static class KeySecurityOutputStream3 extends OutputStream
    {
        private OutputStream _out;
        private byte _temp1;
        private byte _temp2;
        private int _index = 0;

        public KeySecurityOutputStream3(OutputStream out) throws IOException
        {
            this._out = out;
            byte[] tempArr = new byte[2];
            byte tempRan1 = (byte)new Random().nextInt();
            byte tempRan2 = (byte)new Random().nextInt();
            this._temp1 = tempRan1;
            tempArr[0] = tempRan1;
            this._temp2 = tempRan2;
            tempArr[1] = tempRan2;
            this._out.write(tempArr);
        }

        public void write(byte[] b, int off, int len) throws IOException
        {
            for (int i = off; i < len; i++) {
                if (_index % 2 == 0) {
                    b[i] = (byte)((~b[i]) ^ _temp1);
                } else {
                    b[i] = (byte)(b[i] ^ (~_temp2));
                }
                _index++;
            }
            this._out.write(b, off, len);
        }

        public void write(int b) throws IOException
        {
//            this._out.write(b ^ this._temp ^ this._key.charAt(this._index++ % this._key.length()));
        }
    }

    public static class KeySecurityInputStream3 extends InputStream
    {
        private InputStream _in;
        private byte _temp1;
        private byte _temp2;
        private int _index = 0;

        public KeySecurityInputStream3(InputStream in) throws IOException
        {
            this._in = in;
            byte[] tempArr = new byte[2];
            this._in.read(tempArr, 0, 2);
            _temp1 = tempArr[0];
            _temp2 = tempArr[1];
        }

        public int read(byte[] b, int off, int len) throws IOException
        {
            int length = _in.read(b, off, len);
            for (int i = off; i < length; i++) {
                if (_index % 2 == 0) {
                    b[i] = (byte)~(b[i] ^ _temp1);
                } else {
                    b[i] = (byte)(b[i] ^ ~_temp2);
                }
                _index++;
            }
            return length;
        }

        @Override
        public int read() throws IOException {
            return 0;
        }
    }

    public static class KeySecurityOutputStream2 extends OutputStream
    {
        private OutputStream _out;
        private byte _temp1;
        private byte _temp2;
        private int _index = 0;

        public KeySecurityOutputStream2(OutputStream out) throws IOException
        {
            this._out = out;
            byte[] tempArr = new byte[2];
            byte tempRan1 = (byte)new Random().nextInt();
            byte tempRan2 = (byte)new Random().nextInt();
            this._temp1 = tempRan1;
            tempArr[0] = tempRan1;
            this._temp2 = tempRan2;
            tempArr[1] = tempRan2;
            this._out.write(tempArr);
        }

        public void write(byte[] b, int off, int len) throws IOException
        {
            for (int i = off; i < len; i++) {
                if (_index % 2 == 0) {
                    b[i] = (byte)(b[i] ^ _temp1);
                } else {
                    b[i] = (byte)(b[i] ^ _temp2);
                }
                _index++;
            }
            this._out.write(b, off, len);
        }

        public void write(int b) throws IOException
        {
//            this._out.write(b ^ this._temp ^ this._key.charAt(this._index++ % this._key.length()));
        }
    }

    public static class KeySecurityInputStream2 extends InputStream
    {
        private InputStream _in;
        private byte _temp1;
        private byte _temp2;
        private int _index = 0;

        public KeySecurityInputStream2(InputStream in) throws IOException
        {
            this._in = in;
            byte[] tempArr = new byte[2];
            this._in.read(tempArr, 0, 2);
            _temp1 = tempArr[0];
            _temp2 = tempArr[1];
        }

        public int read(byte[] b, int off, int len) throws IOException
        {
            int length = _in.read(b, off, len);
            for (int i = off; i < length; i++) {
                if (_index % 2 == 0) {
                    b[i] = (byte)(b[i] ^ _temp1);
                } else {
                    b[i] = (byte)(b[i] ^ _temp2);
                }
                _index++;
            }
            return length;
        }

        @Override
        public int read() throws IOException {
            return 0;
        }
    }

    public static class KeySecurityOutputStream extends OutputStream
    {
        private OutputStream _out;
        private byte _temp;
        private int _index = 0;
        private String _key;

        public KeySecurityOutputStream(OutputStream out, String key) throws IOException
        {
            this._out = out;
            byte[] tmp21_19 = new byte[1];
            byte tmp35_34 = (byte)new Random().nextInt();
            this._temp = tmp35_34;
            tmp21_19[0] = tmp35_34;
            this._out.write(tmp21_19);
            this._key = key;
        }

        public void write(byte[] b, int off, int len) throws IOException
        {
            for (int i = off; i < len; i++) {
                b[i] = (byte)(b[i] ^ this._temp ^ this._key.charAt(this._index++ % this._key.length()));
            }
            this._out.write(b, off, len);
        }

        public void write(int b) throws IOException
        {
            this._out.write(b ^ this._temp ^ this._key.charAt(this._index++ % this._key.length()));
        }
    }
}
