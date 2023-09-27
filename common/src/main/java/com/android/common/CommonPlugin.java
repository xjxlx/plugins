package com.android.common;


import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;

public class CommonPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        // println("common plugin apply ---->");
        TaskProvider<CommonTask> versionTask = project.getTasks().register("commonPlugin", CommonTask.class, new Action<CommonTask>() {
            @Override
            public void execute(CommonTask version) {
            }
        });
    }


}
