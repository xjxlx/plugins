package com.android.common.utils;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CommonPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        SystemUtil.println("common plugin apply ---->");
    }
}
