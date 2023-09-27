package com.android.helper.plugin;

import static com.android.helper.utils.PrintlnUtil.println;

import com.android.build.api.dsl.LibraryExtension;
import com.android.helper.interfaces.PublishPluginExtension;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.provider.Property;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;

public class PublishPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        boolean hasPlugin = project.getPluginManager().hasPlugin("maven-publish");
        println("hasPlugin:" + hasPlugin);

        // 1：注册一个发布release的buildType
        registerPublishType(project);

        // 2：注册一个片段，用来传输数据使用
        PublishPluginExtension publishExtension = project.getExtensions().create("publishExtension", PublishPluginExtension.class);
        Property<String> groupId = publishExtension.getGroupId().convention("com.android.helper");
        Property<String> artifactId = publishExtension.getArtifactId().convention("publish");
        Property<String> version = publishExtension.getVersion().convention("1.0");

        // 3：注册一个发布的task
        project.task("publishTask", task -> {
            // 发布插件
            publishTask(project, task, groupId.get(), artifactId.get(), version.get());
        });
    }

    /**
     * 注册一个release的发布类型
     */
    private void registerPublishType(Project project) {
        LibraryExtension libraryExtension = project.getExtensions().getByType(LibraryExtension.class);
        libraryExtension.getPublishing().singleVariant("release", library -> {
            library.withSourcesJar();
            library.withJavadocJar();
            return null;
        });
    }

    /**
     * 如果要使用：PublishingExtension 扩展属性的话，必须要依赖于这个插件
     * plugins {
     * id "maven-publish"
     * }
     */
    private void publishTask(Project project, Task task, String groupId, String artifactId, String version) {
        // 获取插件版本信息
        println("groupId:" + groupId + " artifactId:" + artifactId + " version:" + version);

        // 在所有的配置都完成之后执行
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                PublishingExtension publish = project.getExtensions().getByType(PublishingExtension.class);

                publish.getPublications().create("release", MavenPublication.class, maven -> {
                    // 设置属性
                    maven.setGroupId("com.android.helper");
                    maven.setArtifactId("publish");
                    maven.setVersion("1.0.0");

                    // 发布
                    task.doLast(task1 -> maven.from(project.getComponents().getByName("release")));
                });
            }
        });
    }
}
