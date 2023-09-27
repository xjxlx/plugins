package com.android.version;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

public abstract class VersionPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        TaskContainer tasks = project.getTasks();

        TaskProvider<VersionTask> version = tasks.register("version", VersionTask.class, new Action<VersionTask>() {
            @Override
            public void execute(VersionTask version) {

            }
        });

    }
}
