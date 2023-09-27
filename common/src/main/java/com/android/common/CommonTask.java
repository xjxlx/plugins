package com.android.common;


import org.apache.groovy.util.SystemUtil;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.TaskAction;

public class CommonTask extends DefaultTask {

    @TaskAction
    public void taskAction() {
     }

    public int convertVersion(Provider<String> version) {
        String versionStr = version.get();
        return Integer.parseInt(versionStr);
    }

}
