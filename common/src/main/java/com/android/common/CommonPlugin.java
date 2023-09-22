package com.android.common;

import com.android.common.utils.SystemUtil;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CommonPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        SystemUtil.println("common plugin apply ---->");
    }
}
