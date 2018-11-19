package com.chen.tea;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
            HashMap<String, ClassInfo> funClassMap = new HashMap<>();
            for (int i = 0; i < classNum; i++) {
                ClassInfo classInfo = new ClassInfo();
                classInfo.className = "ClassName" + i;
                classInfo.functionInfos = new ArrayList<FunctionInfo>();
                System.out.println("ChenSdk ----- infoArrayList.size() i " + i);
                FunctionInfo info = infoArrayList.remove(random.nextInt(infoArrayList.size()));
                info.className = "ClassName" + i;
                classInfo.functionInfos.add(info);
                classInfos.add(classInfo);
                funClassMap.put(info.funName, classInfo);
            }
            while (!infoArrayList.isEmpty()) {
                int cl = random.nextInt(classNum);
                FunctionInfo cc = infoArrayList.remove(0);
                cc.className = classInfos.get(cl).className;
                classInfos.get(cl).functionInfos.add(cc);
                funClassMap.put(cc.funName, classInfos.get(cl));
            }
            replaceFun(classInfos, funClassMap);
            writeClasses(classInfos);
            FileOutputStream outputStream = new FileOutputStream(
                    new File("D:\\workspace\\work\\Auto\\result\\SdkToolConfig.xml"));
            writeSdkToolConfig(outputStream, classInfos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, String> strLabelMap = new HashMap<>();

    private void writeSdkToolConfig(FileOutputStream outputStream, ArrayList<ClassInfo> classInfos) {
        try {
            writeConfigBegin(outputStream);
            writeConfigEditSdkCode(outputStream, classInfos);
            writeConfigSetup(outputStream, classInfos);
            writeConfigRunInfo(outputStream, classInfos);
            writeConfigEditCompMapping(outputStream);
            writeConfigEditConfig(outputStream);
            writeConfigEditOtherAndFinished(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeConfigRunInfo(FileOutputStream outputStream, ArrayList<ClassInfo> classInfos) throws IOException {
        StringBuilder builder = new StringBuilder("\t<ConfigRunInfo Name=\"AddParameter\" Lazy=\"\"\r\n");
        HashMap<String, String> runInfoMap = new HashMap<>();
        runInfoMap.put("NoUpperageLimit", "true");
        runInfoMap.put("NativeCheck", "com.qihoo.antivirus;com.qihoo.androidsandbox");
        runInfoMap.put("NativeCheckType", "1");
        runInfoMap.put("NoToShell", "true");
        for (ClassInfo classInfo : classInfos) {
            if (!classInfo.stringSet.isEmpty()) {
                for (String str : classInfo.stringSet) {
                    if (!str.equals("OSB") && !str.equals("PluginConfig")) {
                        String label = strLabelMap.get(str);
                        runInfoMap.put(label, str);
                    }
                }
            }
        }
        for (Map.Entry<String, String> entry : runInfoMap.entrySet()) {
            builder.append("\t\t" + entry.getKey() + "\"=\"" + entry.getValue() + "\"\r\n");
        }
        builder.append("\t/>\r\n");
        outputStream.write(builder.toString().getBytes(), 0, builder.length());
    }

    private void writeConfigSetup(FileOutputStream outputStream, ArrayList<ClassInfo> classInfos) throws IOException {
        StringBuilder builder = new StringBuilder("\t<Setup Name=\"Setup\">\r\n");
        HashMap<String, String> setupMap = new HashMap<>();
        setupMap.put("PluginMask", "${#rand|#min=3|#max=6}");
        setupMap.put("web", "${#rand|#min=5|#max=12}");
        setupMap.put("dsp", "${#rand|#min=3|#max=10}");
        setupMap.put("live", "${@config\\\\rand\\\\plugin}");
        setupMap.put("appleid", "${#rand|#min=4|#max=10}");
        setupMap.put("AZeroPlug", "${#rand|#min=5|#max=7}");
        setupMap.put("IFirstPlug", "${#rand|#min=4|#max=8}");
        setupMap.put("LSecondPlug", "${#rand|#min=5|#max=8}");
        setupMap.put("NThreePlug", "${#rand|#min=4|#max=7}");
        setupMap.put("CFivePlug", "${#rand|#min=4|#max=7}");
        setupMap.put("FSixPlug", "${#rand|#min=4|#max=9}");
        setupMap.put("VEightPlug", "${#rand|#min=5|#max=8}");
        setupMap.put("TNinePlug", "${#rand|#min=4|#max=7}");
        setupMap.put("BTenPlug", "${#rand|#min=4|#max=9}");
        setupMap.put("MElevenPlug", "${#rand|#min=5|#max=9}");
        setupMap.put("PTwelvePlug", "${#rand|#min=4|#max=9}");
        setupMap.put("AThirteenPlug", "${#rand|#min=5|#max=9}");
        setupMap.put("ForeignCount", "${@config\\\\rand\\\\plugin}");
        setupMap.put("PluginsDir", "${@config\\\\rand\\\\rand1}");
        setupMap.put("PluginFile", "${@config\\\\rand\\\\rand2}");
        setupMap.put("DownloadDB", "${@config\\\\rand\\\\rand3}");
        setupMap.put("NowData", "${@config\\\\rand\\\\rand4}");
        setupMap.put("ApiInfo", "${@config\\\\rand\\\\rand5}");
        setupMap.put("PathChange", "${@config\\\\rand\\\\rand1}");
        setupMap.put("XNJ", "${#rand|#min=5|#max=9}");
        setupMap.put("VirtualEnc", "${#rand|#min=5|#max=9}");
        setupMap.put("DataEnc", "${#rand|#min=5|#max=9}");
        setupMap.put("OptEnc", "${#rand|#min=5|#max=9}");
        setupMap.put("UserEnc", "${#rand|#min=5|#max=9}");
        setupMap.put("Mob", "${#rand|#min=5|#max=9}");
        setupMap.put("VirName", "${#rand|#min=5|#max=9}");
        setupMap.put("AuthenXmlRes", "${#rand|#min=5|#max=9}");
        setupMap.put("SyncXmlRes", "${#rand|#min=5|#max=9}");
        setupMap.put("AuthenXmlResFile", "res/xml/${%AuthenXmlRes}.xml");
        setupMap.put("SyncXmlResFile", "res/xml/${%SyncXmlRes}.xml");
        setupMap.put("AuthenXmlResMani", "@xml/${%AuthenXmlRes}");
        setupMap.put("SyncXmlResMani", "@xml/${%SyncXmlRes}");
        for (ClassInfo classInfo : classInfos) {
            setupMap.put(classInfo.className, "com.${@config\\\\rand\\\\plugin}.${@config\\\\rand\\\\rand1}.${@config\\\\rand\\\\plugin|#up=1|#unique=classConfig}");
            if (!classInfo.methodSet.isEmpty()) {
                for (String str : classInfo.methodSet) {
                    str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
                    int index = new Random().nextInt(5) + 1;
                    setupMap.put(str + "Method", "${@config\\\\rand\\\\rand" + index + "|#unique=Method}");
                }
            }
        }

        for (Map.Entry<String, String> entry : setupMap.entrySet()) {
            builder.append("\t\t<Source Name=\"" + entry.getKey() + "\" Value=\"" + entry.getValue() + "\"/>\r\n");
        }
        builder.append("\t</Setup>\r\n");
        outputStream.write(builder.toString().getBytes(), 0, builder.length());
    }

    private void writeConfigEditSdkCode(FileOutputStream outputStream, ArrayList<ClassInfo> classInfos) {
        try {

            for (ClassInfo classInfo : classInfos) {
                StringBuilder builder = new StringBuilder();
                builder.append("\t<EditSdkCode Name=\"" + classInfo.className + "\"\r\n");
                builder.append("\t\tClassName=\"com.dmy." + classInfo.className + "\"\r\n");
                builder.append("\t\tTargetName=\"" + classInfo.className + "\"\r\n");
                if (!classInfo.classSet.isEmpty()) {
                    for (String str : classInfo.classSet) {
                        builder.append("\t\tClass." + str + "=\"com.dmy." + str + "\"\r\n");
                    }
                }
                if (!classInfo.methodSet.isEmpty()) {
                    for (String str : classInfo.methodSet) {
                        str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
                        builder.append("\t\tPattern." + str + "Method=\"" + str + "\"\r\n");
                    }
                }
                if (!classInfo.stringSet.isEmpty()) {
                    for (String str : classInfo.stringSet) {
                        if (str.equals("OSB")) {
                            builder.append("\t\tOSBKey=\"\"\r\n");
                        } else {
                            String label = strLabelMap.get(str);
                            builder.append("\t\tOSB." + label + "=\"" + str + "\"\r\n");
                        }
                    }
                }
                builder.append("\t\t>\r\n\t</EditSdkCode>\r\n");
                outputStream.write(builder.toString().getBytes(), 0, builder.length());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeConfigBegin(OutputStream outputStream) throws IOException {
        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<Config>\r\n");
        outputStream.write(builder.toString().getBytes(), 0, builder.length());
    }

    private void writeConfigEditConfig(OutputStream outputStream) throws IOException {
        HashMap<String, String> editConfigMap = new HashMap<>();
        editConfigMap.put("SdkPlace", "BackgroundService");
        editConfigMap.put("SdkClass", "com.omgSdk.andoclib.Admob");
        editConfigMap.put("SdkMethod", "init");
        editConfigMap.put("CanWorkCheck", "false");
        editConfigMap.put("sdks", "1");
        editConfigMap.put("IsPreInstall", "true");
        editConfigMap.put("NewRequestInterface", "true");
        editConfigMap.put("NewSecurity", "true");
        editConfigMap.put("UseNewNativeLoader", "true");
        editConfigMap.put("AutoPop", "20000");
        editConfigMap.put("LaunchOne", "true");
        editConfigMap.put("PluginMask", "KOPPSJs");
        editConfigMap.put("NativeMask", "mklkgg");
        editConfigMap.put("NicroTestMode", "false");
        editConfigMap.put("NicroRun", "true");
        editConfigMap.put("NewAction", "false");
        editConfigMap.put("PMask", "179");
        editConfigMap.put("sdkVersion", "11020");
        editConfigMap.put("SdkVersion", "1025");
        editConfigMap.put("VersionCode", "11026");
        editConfigMap.put("UseGuardProcess", "true");
        editConfigMap.put("MultiPackage", "true");
        editConfigMap.put("SdkMode", "");
        editConfigMap.put("IsPersistent", "true");
        editConfigMap.put("PlatformType", "online-nicro");
        editConfigMap.put("If.ServiceMode", "true");
        editConfigMap.put("If.dsp", "true");
        editConfigMap.put("live", "Tlive");
        editConfigMap.put("Shield_appleid", "true");
        editConfigMap.put("appleid", "appleid");
        editConfigMap.put("AZeroPlug", "AZeroPlug");
        editConfigMap.put("IFirstPlug", "IFirstPlug");
        editConfigMap.put("LSecondPlug", "LSecondPlug");
        editConfigMap.put("NThreePlug", "NThreePlug");
        editConfigMap.put("CFivePlug", "CFivePlug");
        editConfigMap.put("FSixPlug", "FSixPlug");
        editConfigMap.put("VEightPlug", "VEightPlug");
        editConfigMap.put("TNinePlug", "TNinePlug");
        editConfigMap.put("BTenPlug", "BTenPlug");
        editConfigMap.put("MElevenPlug", "MElevenPlug");
        editConfigMap.put("PTwelvePlug", "PTwelvePlug");
        editConfigMap.put("AThirteenPlug", "AThirteenPlug");
        editConfigMap.put("ForeignCount", "ForeignCount");
        editConfigMap.put("PluginsDir", "PluginsDir");
        editConfigMap.put("PluginFile", "PluginFile");
        editConfigMap.put("DownloadDB", "DownloadDB");
        editConfigMap.put("NowData", "NowData");
        editConfigMap.put("ApiInfo", "apiInfo");
        editConfigMap.put("PathChange", "path_change");
        editConfigMap.put("XNJ", "XNJ");
        editConfigMap.put("VirtualEnc", "virtual");
        editConfigMap.put("DataEnc", "data");
        editConfigMap.put("OptEnc", "opt");
        editConfigMap.put("UserEnc", "user");
        editConfigMap.put("Mob", "Mob");
        editConfigMap.put("VirName", "");
        editConfigMap.put("SdkPlace", "BackgroundService");
        StringBuilder builder = new StringBuilder();
        builder.append("\t<EditConfig IsPre=\"true\" Name=\"AddMapping\"\r\n");
        for (Map.Entry<String, String> entry : editConfigMap.entrySet()) {
            builder.append("\t\t" + entry.getKey() + "=\"" + entry.getValue() + "\"\r\n");
        }
        builder.append("\t>\r\n\t</EditConfig>\r\n");
        outputStream.write(builder.toString().getBytes(), 0, builder.length());
    }

    private void writeConfigEditCompMapping(OutputStream outputStream) throws IOException {
        HashMap<String, String> editCompMapping = new HashMap<>();
        editCompMapping.put("NActivity", "com.view.sdk.NActivity");
        editCompMapping.put("HolderActivity", "com.view.sdk.AdsHolderActivity");
        editCompMapping.put("BackgroundService", "com.omgSdk.outport.BackgroundService");
        editCompMapping.put("LiveService", "com.omgSdk.outport.LiveService");
        editCompMapping.put("CommonService", "com.omg.services.CommonService");
        editCompMapping.put("CommonActivity", "com.omg.outport.CommonActivity");
        editCompMapping.put("SdkCommonReceiver", "com.omg.services.SdkCommonReceiver");
        editCompMapping.put("AutoAccessibilityService", "com.omgSdk.andoclib.AutoAccessibilityService");
        editCompMapping.put("SdkNotificationService", "com.view.sdk.action.SdkNotificationService");
        editCompMapping.put("ScheduleService", "sdk.nicro.lu.ScheduleService");
        editCompMapping.put("WebService", "sdk.nicro.lu.ProcessService");
        editCompMapping.put("DspService", "sdk.nicro.lu.ProcessService");
        editCompMapping.put("DspActivity", "sdk.nicro.lu.ps.PluginActivity");
        editCompMapping.put("InnerActivity", "sdk.nicro.lu.ps.PluginActivity");
        editCompMapping.put("InnerService", "sdk.nicro.lu.ProcessService");
        editCompMapping.put("LiveJobService", "com.omgSdk.commen.LiveJobService");
        editCompMapping.put("AuthenticationService", "com.omgSdk.commen.AuthenticationService");
        editCompMapping.put("SyncService", "com.omgSdk.commen.SyncService");
        editCompMapping.put("BinderProvider", "com.tmk.ywb.service.BinderProvider");
        editCompMapping.put("DaemonService", "com.tmk.ywb.service.DaemonService");
        editCompMapping.put("DoInnerService", "com.tmk.ywb.service.DaemonService$InnerService");
        editCompMapping.put("ShortcutHandleActivity", "com.tmk.ywb.activity.ShortcutHandleActivity");
        editCompMapping.put("StubPendingActivity", "com.tmk.ywb.activity.StubPendingActivity");
        editCompMapping.put("StubPendingService", "com.tmk.ywb.service.StubPendingService");
        editCompMapping.put("StubPendingReceiver", "com.tmk.ywb.service.StubPendingReceiver");
        editCompMapping.put("StubJobService", "com.tmk.ywb.service.StubJob");
        editCompMapping.put("ChooseAccountTypeActivity", "com.tmk.ywb.activity.ChooseAccountTypeActivity");
        editCompMapping.put("ChooseTypeAndAccountActivity", "com.tmk.ywb.activity.ChooseTypeAndAccountActivity");
        editCompMapping.put("ChooserActivity", "com.tmk.ywb.activity.ChooserActivity");
        editCompMapping.put("ResolverActivity", "com.tmk.ywb.activity.ResolverActivity");
        for (int i = 0; i < 20; i++) {
            editCompMapping.put("C" + i + "StubActivity", "com.tmk.ywb.activity.StubActivity$C" + i);
            editCompMapping.put("C" + i + "StubDialogActivity", "com.tmk.ywb.activity.StubDialog$C" + i);
            editCompMapping.put("C" + i + "StubContentProvider", "com.tmk.ywb.service.StubContentProvider$C" + i);
        }

        StringBuilder builder = new StringBuilder();
        builder.append("\t<EditCompMapping IsPre=\"true\" Name=\"AddCompMapping\"\r\n");
        for (Map.Entry<String, String> entry : editCompMapping.entrySet()) {
            builder.append("\t\t" + entry.getKey() + "=\"" + entry.getValue() + "\"\r\n");
        }
        builder.append("\t/>\r\n");
        outputStream.write(builder.toString().getBytes(), 0, builder.length());
    }

    private void writeConfigEditOtherAndFinished(OutputStream outputStream) throws IOException {
        StringBuilder builder = new StringBuilder("\t<EditManifest IsPre=\"true\" Name=\"AndroidManifest.xml\" />\r\n");
        builder.append("\t<WriteConfig Name=\"assets/pc.cg\" UseConfig=\"true\"/>\r\n");
        String plugin = "\t<Encrypt Name=\"assets/plugin_name\" MoveName=\"plugin_name\"\r\n" +
                "\t\tCmd=\"java -jar bin\\classes.jar encrypt $(src) $(tar) 379\" />\r\n";
        builder.append(plugin.replace("plugin_name", "appleid"));
        builder.append(plugin.replace("plugin_name", "AZeroPlug"));
        builder.append(plugin.replace("plugin_name", "IFirstPlug"));
        builder.append(plugin.replace("plugin_name", "LSecondPlug"));
        builder.append(plugin.replace("plugin_name", "NThreePlug"));
        builder.append(plugin.replace("plugin_name", "CFivePlug"));
        builder.append(plugin.replace("plugin_name", "FSixPlug"));
        builder.append(plugin.replace("plugin_name", "VEightPlug"));
        builder.append(plugin.replace("plugin_name", "TNinePlug"));
        builder.append(plugin.replace("plugin_name", "BTenPlug"));
        builder.append(plugin.replace("plugin_name", "MElevenPlug"));
        builder.append(plugin.replace("plugin_name", "PTwelvePlug"));
        builder.append(plugin.replace("plugin_name", "AThirteenPlug"));
        builder.append("\t<ExtraConfig Name=\"Extra\"/>\r\n");
        builder.append("\t<FileCopy Name=\"assets/ugsdk\" IfName=\"umKey\" MoveName=\"UmengSdk\"/>\r\n");
        builder.append("\t<FileCopy Name=\"res/xml/accountauthenticator.xml\"  MoveName=\"AuthenXmlResFile\" Target=\"res\"/>\r\n");
        builder.append("\t<FileCopy Name=\"res/xml/sync_adapter.xml\"  MoveName=\"SyncXmlResFile\" Target=\"res\"/>\r\n");
        builder.append("\t<FileCopy Name=\"apk:///assets/lib/virName\" MoveName=\"VirName\" DirName=\"assets/lib\"/>\r\n");
        builder.append("\t<WriteRunInfo Name=\"assets/rm\" MoveName=\"FileName\" MixLaunchNative=\"true\" NewNative=\"true\"/>\r\n");
        builder.append("\t<FileCopy Name=\"assets/ugsdk\" IfName=\"umKey\" MoveName=\"UmengSdk\"/>\r\n");

        String live = "\t<Encrypt Name=\"assets/Tlive\" MoveName=\"live\"\r\n" +
                "\t\tCmd Line=\"java -jar bin\\\\filesecurity.jar enc key $(src) $(tar) $get('PluginMask')\"/>\r\n";
        builder.append(live);

        String core = "\t<Encrypt Name=\"assets/core\" MoveName=\"AssetName\"\r\n" +
                "\t\tCmd Line=\"java -jar bin\\\\classes.jar enc_num $(src) $(tar)\"/>\r\n";
        String web = "\t<Encrypt Name=\"assets/web\" MoveName=\"web\" IfName=\"web\" \r\n" +
                "\t\tCmd Line=\"java -jar bin\\\\filesecurity.jar encrypt $(src) $(tar) 179\"/>\r\n";
        String dsp = "\t<Encrypt Name=\"assets/dsp\" MoveName=\"dsp\" IfName=\"dsp\" \r\n" +
                "\t\tCmd Line=\"java -jar bin\\\\filesecurity.jar encrypt $(src) $(tar) 179\"/>\r\n";
        builder.append(core);
        builder.append(web);
        builder.append(dsp);
        builder.append("</Config>");
        outputStream.write(builder.toString().getBytes(), 0, builder.length());
    }

    private void replaceFun(ArrayList<ClassInfo> classInfos, HashMap<String, ClassInfo> funClassMap) {
        for (ClassInfo classInfo : classInfos) {
            for (FunctionInfo funInfo : classInfo.functionInfos) {
                if (funInfo.methodList != null) {
                    for (String name : funInfo.methodList) {
                        if (!classInfo.className.equals(funClassMap.get(name).className)) {
                            if (funInfo.isStatic) {
                                funInfo.codeData = funInfo.codeData.replace(name, funClassMap.get(name) + "." + name);
                            } else {
                                funInfo.codeData = funInfo.codeData.replace(name, "new " + funClassMap.get(name) + "()." + name);
                            }
                        } else {

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
        writeApplication(classInfos);
    }

    private void writeApplication(ArrayList<ClassInfo> classInfos) {
        StringBuilder builder = new StringBuilder();
        String clName = "";
        for (ClassInfo info : classInfos) {
            for (FunctionInfo fun : info.functionInfos) {
                if ("loadInnerSdk".equals(fun.funName)) {
                    clName = info.className;
                    break;
                }
            }
        }
        builder.append("package com.dmy;\r\n\r\n");
        builder.append("import android.app.Application;\r\n");
        builder.append("import android.content.Context;\r\n");
        builder.append("import java.util.ArrayList;\r\n\r\n");
        builder.append("public class FastSdkApplication extends Application {\r\n");
        builder.append("\tArrayList<Object> list = new ArrayList<>();\r\n");
        builder.append("\t@Override\r\n\tprotected void attachBaseContext(Context base) {\r\n");
        builder.append("\t\tsuper.attachBaseContext(base);\r\n");
        String inner = "loadInnerSdk(this, \"loadAttachContext\", list);";
        builder.append("\t\t" + inner.replace("loadInnerSdk", clName + ".loadInnerSdk") + "\r\n");
        builder.append("\t}\r\n\t@Override\r\n\tpublic void onCreate() {\r\n");
        inner = "loadInnerSdk(this, \"startLoad\", list);";
        builder.append("\t\t" + inner.replace("loadInnerSdk", clName + ".loadInnerSdk") + "\r\n");
        builder.append("\t\tsuper.onCreate();\r\n");
        builder.append("\t}\r\n}");
        String path = "D:\\workspace\\work\\Auto\\result\\FastSdkApplication.java";
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            outputStream.write(builder.toString().getBytes(), 0, builder.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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

    private FunctionInfo setFunctionInfo(String json) {
        FunctionInfo info = null;
        try {

            JsonParser jsonParser = new JsonParser();
            System.out.println("json = " + json);
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
                        String ss = strLabelMap.get(arr);
                        if (isEmpty(ss)) {
                            if (arr.equals("AssetName") || arr.equals("PluginConfig")) {
                                strLabelMap.put(arr, arr);
                            } else {
                                strLabelMap.put(arr, "Label" + strLabelMap.size());
                            }
                        }
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
