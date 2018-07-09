package com.chen.security.json;

import java.util.Random;

/**
 * Created by PengChen on 2018/6/27.
 */

public class Test {
    public static void main(String[] args) {
//        System.out.println("start time = " + System.currentTimeMillis());
//        for (int i = 0; i < 10000; i++) {
//            IContext jsonContext = new JsonContext();
//            State state = new StartState(jsonContext);
//            state.handle("{\"name\":\"chen\",\"age\":23.56,\"sex\":\"男\"}");
//        }
//        System.out.println("start time = " + System.currentTimeMillis());

        System.out.println("start time = " + (new Random().nextInt(4) + 7));
//        System.out.println("start time = " + getRandomString2(8));
    }

    /**
     * 第二种方法
     */
    public static String getRandomString2(int length){
        //产生随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //循环length次
        for(int i=0; i<length; i++){
            //产生0-2个随机数，既与a-z，A-Z，0-9三种可能
            int number=random.nextInt(3);
            long result=0;
            switch(number){
                //如果number产生的是数字0；
                case 0:
                    //产生A-Z的ASCII码
                    result=Math.round(Math.random()*25+65);
                    //将ASCII码转换成字符
                    sb.append(String.valueOf((char)result));
                    break;
                case 1:
                    //产生a-z的ASCII码
                    result=Math.round(Math.random()*25+97);
                    sb.append(String.valueOf((char)result));
                    break;
                case 2:
                    //产生0-9的数字
                    sb.append(String.valueOf
                            (new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }
}
