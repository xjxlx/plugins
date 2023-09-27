package com.android.version;


import com.android.version.utils.GradleUtil;

import java.io.File;

public class TestVersion {
    private static final String URL_PATH = "https://github.com/xjxlx/plugins/blob/39a705f313bec743e2c0437ce0f61a64a63c60f2/gradle/libs.versions.toml";

    private static final GradleUtil mGradleUtil = new GradleUtil();

    public static void main(String[] args) {
        //        /Users/XJX/AndroidStudioProjects/plugins/app/build.gradle.kts
        String root = "/Users/XJX/AndroidStudioProjects/plugins";

        File rootFile = new File(root);
        mGradleUtil.initGradle(rootFile);
        mGradleUtil.writeGradleToLocal(URL_PATH, new File(rootFile, "gradle" + File.separator + "libs2.versions.toml"));
        mGradleUtil.changeModules();
    }
}
