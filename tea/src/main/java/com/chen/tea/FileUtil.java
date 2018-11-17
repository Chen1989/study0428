package com.chen.tea;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by PengChen on 2018/11/12.
 */

public class FileUtil {
    public void readFile() {
        try {
            String path = "D:\\workspace\\work\\Auto\\fun";
            File file = new File(path);
            File[] files = file.listFiles();
            ArrayList<FunctionInfo> infoArrayList = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                FileInputStream inputStream = new FileInputStream(new File(files[i], "1"));
                int len = 0;
                StringBuffer buffer = new StringBuffer();
                byte[] var = new byte[1024*4];
                while ((len = inputStream.read(var)) > 0) {
                    new String(var, 0, len, "UTF-8");
                    buffer.append(new String(var, 0, len));
                }
                String result = buffer.toString();
                FunctionInfo functionInfo = setFunctionInfo(result);
                infoArrayList.add(functionInfo);
            }
            mergeFunctions(infoArrayList);
            System.out.println("ChenSdk ----- infoArrayList.size() = " + infoArrayList.size());
            Random random = new Random();
            int classNum = random.nextInt(2) + 2;
            ArrayList<ClassInfo> classInfos = new ArrayList<>(classNum);
            HashMap<String, String> funClassMap = new HashMap<>();
            for (int i = 0; i < classNum; i++) {
                ClassInfo classInfo = new ClassInfo();
                classInfo.className = "ClassName" + i;
                classInfo.functionInfos = new ArrayList<FunctionInfo>();
                System.out.println("ChenSdk ----- infoArrayList.size() i " + i);
                FunctionInfo info = infoArrayList.remove(random.nextInt(infoArrayList.size()));
                info.className = "ClassName" + i;
                classInfo.functionInfos.add(info);
                classInfos.add(classInfo);
                funClassMap.put(info.funName, classInfo.className);
            }
            while (!infoArrayList.isEmpty()) {
                int cl = random.nextInt(classNum);
                FunctionInfo cc = infoArrayList.remove(0);
                cc.className = classInfos.get(cl).className;
                classInfos.get(cl).functionInfos.add(cc);
                funClassMap.put(cc.funName, classInfos.get(cl).className);
            }
            replaceFun(classInfos, funClassMap);
            writeClasses(classInfos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void replaceFun(ArrayList<ClassInfo> classInfos, HashMap<String, String> funClassMap) {
        for (ClassInfo classInfo : classInfos) {
            for (FunctionInfo funInfo : classInfo.functionInfos) {
                if (funInfo.methodList != null) {
                    for (String name : funInfo.methodList) {
                        if (funInfo.isStatic) {
                            funInfo.codeData = funInfo.codeData.replaceAll(name, funClassMap.get(name) + "." + name);
                        } else {
                            funInfo.codeData = funInfo.codeData.replaceAll(name, "new " + funClassMap.get(name) + "()." + name);
                        }
                    }
                }
            }
        }
    }

    private void writeClasses(ArrayList<ClassInfo> classInfos) {
        String path = "D:\\workspace\\work\\Auto\\result";
        deleteDirectory(new File(path));
        for (int i = 0; i < classInfos.size(); i++) {
            ClassInfo info = classInfos.get(i);
            info.writeSelf(path);
        }
    }

    public void deleteDirectory(File file) {

        File[] childFiles = file.listFiles();
        if (childFiles != null) {
            for (File childFile : childFiles) {
                childFile.delete();
            }
        }

    }

    public void deleteDirectory1(File file) {
        if (file.isFile()) {
            file.delete();
        } else {
            File[] childFiles = file.listFiles();
            if (childFiles != null) {
                for (File childFile : childFiles) {
                    deleteDirectory1(childFile);
                }
            }
            file.delete();
        }
    }

    private FunctionInfo setFunctionInfo(String json) {
        FunctionInfo info = null;
        try {

            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(json);
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                info = new FunctionInfo();
                info.funName = jsonObject.get("funName").getAsString();
                info.codeData = jsonObject.get("codeData").getAsString();
                String importInfoStr = jsonObject.get("importInfo").getAsString();
                if (!isEmpty(importInfoStr)) {
                    info.importInfo = importInfoStr.split(",");
                }
                String parameterStr = jsonObject.get("parameter").getAsString();
                if (!isEmpty(parameterStr)) {
                    info.parameter = parameterStr.split(",");
                }
                String parameterTypeStr = jsonObject.get("parameterType").getAsString();
                if (!isEmpty(parameterTypeStr)) {
                    info.parameterType = parameterTypeStr.split(",");
                }
                String methodListStr = jsonObject.get("methodList").getAsString();
                if (!isEmpty(methodListStr)) {
                    String[] arrs = methodListStr.split(",");
                    info.methodList = new ArrayList<>();
                    for (String arr : arrs) {
                        info.methodList.add(arr);
                    }
                }
                String classListStr = jsonObject.get("classList").getAsString();
                if (!isEmpty(classListStr)) {
                    String[] arrs = classListStr.split(",");
                    info.classList = new ArrayList<>();
                    for (String arr : arrs) {
                        info.classList.add(arr);
                    }
                }
                String strListStr = jsonObject.get("strList").getAsString();
                if (!isEmpty(strListStr)) {
                    String[] arrs = strListStr.split(",");
                    info.strList = new ArrayList<>();
                    for (String arr : arrs) {
                        info.strList.add(arr);
                    }
                }
                info.isStatic = jsonObject.get("isStatic").getAsBoolean();
                info.merge = jsonObject.get("merge").getAsBoolean();
                info.merged = jsonObject.get("merged").getAsBoolean();
//            Random random = new Random();
//            int ra = random.nextInt(100);
//            if (ra < 50) {
//                info.merged = true;
//            }
                info.result = jsonObject.get("result").getAsString();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    private synchronized void mergeFunctions(ArrayList<FunctionInfo> functionInfos) {
        for (int i = functionInfos.size() - 1; i >= 0; i--) {
            System.out.println("ChenSdk ----- merged = " + functionInfos.get(i).merged);
            System.out.println("ChenSdk ----- merge = " + functionInfos.get(i).merge);
            if (functionInfos.get(i).merged && functionInfos.get(i).merge) {
                for (FunctionInfo info : functionInfos) {
                    //执行合并操作,不考虑自调用
                    if (info.methodList != null && info.methodList.contains(functionInfos.get(i).funName)) {
                        //\r\n分行，分为赋值语句替换，是否有返回值
                        String[] codeDataArr = info.codeData.split("\r\n");
                        for (int j = 0; j < codeDataArr.length; j++) {
                            if (codeDataArr[j].contains(functionInfos.get(i).funName)) {
                                //有参数，无返回值
                                if (functionInfos.get(i).parameter != null
                                        && isEmpty(functionInfos.get(i).result)) {
                                    String[] parameters = findParameter(codeDataArr[j]);
                                    String code = getReplacedCode(functionInfos.get(i), parameters);
                                    info.codeData = info.codeData.replace(codeDataArr[j], code);
                                }
                                //无参数有返回值
                                else if (functionInfos.get(i).parameter == null
                                        && !isEmpty(functionInfos.get(i).result)) {
                                    String code = functionInfos.get(i).codeData;
                                    code = code.substring(code.indexOf("{") + 1, code.lastIndexOf("}"));
                                    String[] state = codeDataArr[j].split("=");
                                    if (state != null) {
                                        code = code.replace("return", state[0].trim() + " = ");
                                    } else {
                                        code = code.replace("return", "");
                                    }
                                    info.codeData = info.codeData.replace(codeDataArr[j], code);
                                }
                                //有参数，有返回值
                                else if (functionInfos.get(i).parameter != null
                                        && !isEmpty(functionInfos.get(i).result)) {
                                    String[] parameters = findParameter(codeDataArr[j]);
                                    String code = getReplacedCode(functionInfos.get(i), parameters);
                                    String[] state = codeDataArr[j].split("=");
                                    if (state != null) {
                                        code = code.replace("return", state[0].trim() + " = ");
                                    } else {
                                        code = code.replace("return", "");
                                    }
                                    System.out.println("ChenSdk ----- code = " + code);
                                    System.out.println("ChenSdk ----- codeDataArr[j] = " + codeDataArr[j]);
                                    info.codeData = info.codeData.replace(codeDataArr[j], code);
                                }
                                //无参数，无返回值
                                else {
                                    String code = functionInfos.get(i).codeData;
                                    code = code.substring(code.indexOf("{") + 1, code.lastIndexOf("}"));
                                    info.codeData = info.codeData.replace(codeDataArr[j], code);
                                }
                            }
                        }
                        if (functionInfos.get(i).methodList != null) {
                            info.methodList.addAll(functionInfos.get(i).methodList);
                        }
                        if (functionInfos.get(i).classList != null) {
                            info.classList.addAll(functionInfos.get(i).classList);
                        }
//
                        System.out.println("ChenSdk ----- code = " + info.codeData);
                    }

                }

                functionInfos.remove(i);
            }
        }
    }

    //确保statement是有参数的
    private String[] findParameter(String statement) {
        String tt = statement.substring(statement.indexOf("(") + 1, statement.indexOf(")"));
        tt = tt.replaceAll(" ", "");
        String[] parameters = tt.split(",");
        return parameters;
    }

    private String getReplacedCode(FunctionInfo info, String[] parameters) {
        String code = info.codeData;
        for (int i = 0; i < parameters.length; i++) {
            code = code.replaceAll(info.parameter[i], parameters[i]);
        }
        code = code.substring(code.indexOf("{") + 1, code.lastIndexOf("}"));
        return code;
    }

    public boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
