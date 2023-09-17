package com.android.helper.interfaces;

import org.gradle.api.provider.Property;

public class PublishExtension {
    /**
     * 组的名字
     */
    private Property<String> groupId = null;
    /**
     * 插件名称，必传参数
     */
    private Property<String> artifactId = null;
    /**
     * 版本号，必传参数
     */
    private Property<String> version = null;

    public Property<String> getGroupId() {
        return groupId;
    }

    public void setGroupId(Property<String> groupId) {
        this.groupId = groupId;
    }

    public Property<String> getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(Property<String> artifactId) {
        this.artifactId = artifactId;
    }

    public Property<String> getVersion() {
        return version;
    }

    public void setVersion(Property<String> version) {
        this.version = version;
    }
}
