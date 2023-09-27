package com.android.version;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

public abstract class VersionManagerPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        TaskContainer tasks = project.getTasks();

        TaskProvider<VersionManagerTask> versionManager = tasks.register("versionManager", VersionManagerTask.class, new Action<VersionManagerTask>() {
            @Override
            public void execute(VersionManagerTask version) {

            }
        });

    }
}
