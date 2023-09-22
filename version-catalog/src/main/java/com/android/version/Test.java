package com.android.version;

import static com.plugin.utils.SystemUtil.println;

import com.plugin.utils.FileUtil;
import com.plugin.utils.GradleUtil;

import java.io.File;
import java.util.List;

public class Test {

    private static FileUtil mFileUtil = new FileUtil();
    private static GradleUtil gradleUtil = new GradleUtil();

    public static void main(String[] args) {
        //        /Users/XJX/AndroidStudioProjects/plugins/app/build.gradle.kts
        String root = "/Users/XJX/AndroidStudioProjects/plugins";
        File file = new File(root, "app/build.gradle.kts");
        println("file :exists:" + file.exists());
        List<String> listGradleContent = mFileUtil.readFile(file);
//        for (int i = 0; i < listGradleContent.size(); i++) {
//            println("listGradleContent:" + listGradleContent.get(i));
//        }
        gradleUtil.changeGradleFile(file);

        // 获取依赖的内容
        List<String> dependenciesList = mFileUtil.filterStartAndEnd(listGradleContent, "dependencies", "}");
//        for (int i = 0; i < dependenciesList.size(); i++) {
//            String dependencies = dependenciesList.get(i);
//            // replace dependencies
//            println("dependencies:" + dependencies);

//            if (true) {
//                println("dependencies: " + dependencies);
//                String[] split = dependencies.split("\"");
//
//                if (split.length >= 1) {
//                    String originalDependencies = split[1];
//                    println("originalDependencies: " + originalDependencies);
//                    String[] splitChild = originalDependencies.split(":");
//                    String group = splitChild[0];
//                    String name = splitChild[1];
//                    println("[group]: " + group + " [name]: " + name);
//
//                    String newDependencies = "libs." + (name.replace("-", "."));
//                    println("newDependencies: " + newDependencies);
//                    String resultDependencies = split[0] + newDependencies + split[2];
//                    println("resultDependencies: " + resultDependencies);
//                }
//            }
//        }
    }
}
