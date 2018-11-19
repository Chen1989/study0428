package com.chen.tea;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by PengChen on 2018/11/13.
 */

public class ClassInfo {
    String className;
    ArrayList<FunctionInfo> functionInfos;

    HashSet<String> importSet = new HashSet<>();
    HashSet<String> methodSet = new HashSet<>();
    HashSet<String> stringSet = new HashSet<>();
    HashSet<String> classSet = new HashSet<>();

    public void writeSelf(String path) {
        try {
            readMethod();
            readClassSet();
            readString();
            File file = new File(path, className + ".java");
            FileOutputStream outputStream = new FileOutputStream(file);
            String packageName = "package com.dmy;\r\n\r\n";
            StringBuilder builder = new StringBuilder(packageName);
            builder.append(getImportStr());
            builder.append("public class " + className + "{\r\n\r\n");
            builder.append(getClassCode()).append("\r\n\t}");
            outputStream.write(builder.toString().getBytes(), 0, builder.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getClassCode() {
        StringBuilder builder = new StringBuilder();
        for (FunctionInfo fun : functionInfos) {
            builder.append(fun.codeData).append("\r\n\r\n");
        }
        return builder.toString();
    }

    private String getImportStr() {
        StringBuilder result = new StringBuilder();
        readImport();
        for (String str : importSet) {
            result.append("import ").append(str).append(";\r\n");
        }
        result.append("\r\n");
        return result.toString();
    }

    private void readImport() {
        if (importSet.isEmpty()) {
            for (FunctionInfo fun : functionInfos) {
                if (fun.importInfo != null && fun.importInfo.length > 0) {
                    importSet.addAll(Arrays.asList(fun.importInfo));
                }
            }
        }
    }

    private void readMethod() {
        if (methodSet.isEmpty()) {
            for (FunctionInfo fun : functionInfos) {
                if (fun.methodList != null && fun.methodList.size() > 0) {
                    methodSet.addAll(fun.methodList);
                }
                methodSet.add(fun.funName);
            }
        }
    }

    private void readString() {
        if (stringSet.isEmpty()) {
            for (FunctionInfo fun : functionInfos) {
                if (fun.strList != null && fun.strList.size() > 0) {
                    stringSet.addAll(fun.strList);
                }
            }
        }
    }

    private void readClassSet() {
        if (classSet.isEmpty()) {
            for (FunctionInfo fun : functionInfos) {
                if (fun.classList != null && fun.classList.size() > 0) {
                    classSet.addAll(fun.classList);
                }
            }
        }
    }
}
