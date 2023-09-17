package com.android.helper.plugins;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class PublishPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        System.out.println("我是三方自定义的插件");
    }
}
