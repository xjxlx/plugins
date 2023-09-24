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

        gradleUtil.readLibsVersions();
        gradleUtil.changeGradleFile(file);
    }
}
