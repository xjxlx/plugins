package com.android.version;


import static com.android.version.utils.PrintlnUtil.println;

import com.android.version.utils.GradleUtil;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.File;


public class VersionTask extends DefaultTask {

    private static final String URL_PATH = "https://github.com/xjxlx/plugins/blob/39a705f313bec743e2c0437ce0f61a64a63c60f2/gradle/libs.versions.toml";

    private final GradleUtil mGradleUtil = new GradleUtil();

    @TaskAction
    public void taskAction() {
        println("version manager taskAction: ------> ");
        Project project = getProject();

        mGradleUtil.initGradle(project);
        mGradleUtil.writeGradleToLocal(URL_PATH, new File(project.getRootDir(), "gradle" + File.separator + "libs2.versions.toml"));
        mGradleUtil.changeModules();
        mGradleUtil.changeRootGradle();
        println("versionTask success!");
    }

}
