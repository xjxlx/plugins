package com.android.version;

import static com.plugin.utils.SystemUtil.println;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        //        /Users/XJX/AndroidStudioProjects/plugins/app/build.gradle.kts
        String root = "/Users/XJX/AndroidStudioProjects/plugins";
        File file = new File(root, "app/build.gradle.kts");
        println("file :exists:" + file.exists());

//        List<String> modelGradleContent = mFileUtil.readFile(modelGradle);
//        // 获取依赖的内容
//        List<String> dependenciesList = mFileUtil.filterStartAndEnd(modelGradleContent, "dependencies", "}");
//        for (int i = 0; i < dependenciesList.size(); i++) {
//            String dependencies = dependenciesList.get(i);
//            // replace dependencies
//            if (isKts) {
//                SystemUtil.println("dependencies: " + dependencies);
//                String[] split = dependencies.split("\"");
//
//                if (split.length >= 1) {
//                    String originalDependencies = split[1];
//                    SystemUtil.println("originalDependencies: " + originalDependencies);
//                    String[] splitChild = originalDependencies.split(":");
//                    String group = splitChild[0];
//                    String name = splitChild[1];
//                    SystemUtil.println("[group]: " + group + " [name]: " + name);
//
//                    String newDependencies = "libs." + (name.replace("-", "."));
//                    SystemUtil.println("newDependencies: " + newDependencies);
//                    String resultDependencies = split[0] + newDependencies + split[2];
//                    SystemUtil.println("resultDependencies: " + resultDependencies);
//                }
//            }
//        }
    }
}
