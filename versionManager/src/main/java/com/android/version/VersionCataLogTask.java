package com.android.version;

import static com.plugin.utils.SystemUtil.println;

import com.plugin.utils.FileUtil;
import com.plugin.utils.GradleUtil;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.File;


public class VersionCataLogTask extends DefaultTask {

    private static String urlPath = "https://github.com/xjxlx/plugins/blob/39a705f313bec743e2c0437ce0f61a64a63c60f2/gradle/libs.versions.toml";

    FileUtil mFileUtil = new FileUtil();
    private final GradleUtil gradleUtil = new GradleUtil();

//    @get:InputFile
//    abstract val mergedManifest: RegularFileProperty
//
//    @get:OutputFile
//    abstract val updatedManifest: RegularFileProperty

    @TaskAction
    public void taskAction() {
        println("taskAction: ------> ");

        Project project = getProject();
        File rootDir = project.getRootDir();
        println("rootPath:" + rootDir.getAbsolutePath());


        gradleUtil.initGradle(project);
        gradleUtil.writeGradleToLocal(urlPath, new File(rootDir, "gradle" + File.separator + "libs2.versions.toml"));
        gradleUtil.changeModules();
        String gradleVersion = project.getGradle().getGradleVersion();
        println("gradleVersion:" + gradleVersion);
    }

}
