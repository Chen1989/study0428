package com.cp.chengradle.just;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by PengChen on 2018/11/13.
 */

public class ReadFile {

    public synchronized void readFile(Context context) {
        try {
            AssetManager manager = context.getAssets();
            String[] funs = manager.list("fun");
            ArrayList<FunctionInfo> infoArrayList = new ArrayList<>();
            for (int i = 0; i < funs.length; i++) {

                InputStream inputStream = context.getAssets().open("fun/" + funs[i] + "/1");
                int len = 0;
                StringBuffer buffer = new StringBuffer();
                byte[] var = new byte[1024*4];
                while ((len = inputStream.read(var)) > 0) {
                    buffer.append(new String(var, 0, len));
                }
                String result = buffer.toString();
                FunctionInfo functionInfo = setFunctionInfo(result);
                infoArrayList.add(functionInfo);
            }
            Log.i("ChenSdk", "infoArrayList.size() = " + infoArrayList.size());
            //合并操作
            mergeFunctions(infoArrayList);
            Log.i("ChenSdk", "infoArrayList.size() = " + infoArrayList.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FunctionInfo setFunctionInfo(String json) {
        FunctionInfo info = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            info = new FunctionInfo();
            info.funName = jsonObject.optString("funName");
            info.codeData = jsonObject.optString("codeData");
            String importInfoStr = jsonObject.optString("importInfo");
            if (!TextUtils.isEmpty(importInfoStr)) {
                info.importInfo = importInfoStr.split(",");
            }
            String parameterStr = jsonObject.optString("parameter");
            if (!TextUtils.isEmpty(parameterStr)) {
                info.parameter = parameterStr.split(",");
            }
            String parameterTypeStr = jsonObject.optString("parameterType");
            if (!TextUtils.isEmpty(parameterTypeStr)) {
                info.parameterType = parameterTypeStr.split(",");
            }
            String methodListStr = jsonObject.optString("methodList");
            if (!TextUtils.isEmpty(methodListStr)) {
                String[] arrs = methodListStr.split(",");
                info.methodList = new ArrayList<>();
                for (String arr : arrs) {
                    info.methodList.add(arr);
                }
            }
            String classListStr = jsonObject.optString("classList");
            if (!TextUtils.isEmpty(classListStr)) {
                String[] arrs = classListStr.split(",");
                info.classList = new ArrayList<>();
                for (String arr : arrs) {
                    info.classList.add(arr);
                }
            }
            String strListStr = jsonObject.optString("strList");
            if (!TextUtils.isEmpty(strListStr)) {
                String[] arrs = strListStr.split(",");
                info.strList = new ArrayList<>();
                for (String arr : arrs) {
                    info.strList.add(arr);
                }
            }
            info.isStatic = jsonObject.optBoolean("isStatic");
            info.merge = jsonObject.optBoolean("merge");
            info.merged = jsonObject.optBoolean("merged");
//            Random random = new Random();
//            int ra = random.nextInt(100);
//            if (ra < 50) {
//                info.merged = true;
//            }
            info.result = jsonObject.optString("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

    private synchronized void mergeFunctions(ArrayList<FunctionInfo> functionInfos) {
        for (int i = functionInfos.size() - 1; i >= 0; i--) {
            Log.i("ChenSdk", "merged = " + functionInfos.get(i).merged);
            Log.i("ChenSdk", "merge = " + functionInfos.get(i).merge);
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
                                        && TextUtils.isEmpty(functionInfos.get(i).result)) {
                                    String[] parameters = findParameter(codeDataArr[j]);
                                    String code = getReplacedCode(functionInfos.get(i), parameters);
                                    info.codeData = info.codeData.replace(codeDataArr[j], code);
                                }
                                //无参数有返回值
                                else if (functionInfos.get(i).parameter == null
                                        && !TextUtils.isEmpty(functionInfos.get(i).result)) {
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
                                        && !TextUtils.isEmpty(functionInfos.get(i).result)) {
                                    String[] parameters = findParameter(codeDataArr[j]);
                                    String code = getReplacedCode(functionInfos.get(i), parameters);
                                    String[] state = codeDataArr[j].split("=");
                                    if (state != null) {
                                        code = code.replace("return", state[0].trim() + " = ");
                                    } else {
                                        code = code.replace("return", "");
                                    }
                                    Log.i("ChenSdk", "code = " + code);
                                    Log.i("ChenSdk", "codeDataArr[j] = " + codeDataArr[j]);
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
                        Log.i("ChenSdk", "code = " + info.codeData);
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
}
