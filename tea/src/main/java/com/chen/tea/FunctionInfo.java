package com.chen.tea;

import java.util.List;

/**
 * Created by PengChen on 2018/11/13.
 */

public class FunctionInfo {
    List<String> methodList;    //使用到的方法
    List<String> classList;     //使用到的类，完整类名
    List<String> strList;       //使用到的字符串
    String codeData;            //函数体,包含需要替换的部分(即使用到的函数)
    String className;           //所在类名，完整类名
    String funName;             //函数名
    boolean isStatic;           //是否静态方法,调用时会用到
    String[] parameter;         //函数参数
    String[] parameterType;     //函数参数类型
    String[] importInfo;        //导入信息
    boolean merge;              //是否能合并
    boolean merged;             //是否合并
    String result;              //返回值

}
