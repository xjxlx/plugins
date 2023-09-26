package com.android.version;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

public abstract class VersionCataLogPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        TaskContainer tasks = project.getTasks();

        TaskProvider<VersionCataLogTask> versionTask = tasks.register("versionCatalog", VersionCataLogTask.class, new Action<VersionCataLogTask>() {
            @Override
            public void execute(VersionCataLogTask version) {

            }
        });

    }
}
