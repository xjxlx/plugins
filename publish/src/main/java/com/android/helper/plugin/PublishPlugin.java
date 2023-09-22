package com.android.helper.plugin;

import static com.plugin.utils.SystemUtil.println;

import com.android.build.api.dsl.LibraryExtension;
import com.android.build.api.dsl.LibrarySingleVariant;
import com.android.helper.interfaces.PublishPluginExtension;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class PublishPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        registerPublishType(project);
    }

    /**
     * 注册一个release的发布类型
     */
    private void registerPublishType(Project project) {
        LibraryExtension libraryExtension = project.getExtensions().getByType(LibraryExtension.class);
        libraryExtension.getPublishing().singleVariant("release", new Function1<LibrarySingleVariant, Unit>() {
            @Override
            public Unit invoke(LibrarySingleVariant library) {
                library.withSourcesJar();
                library.withJavadocJar();

                // 2：发布插件
                publishTask(project);
                return null;
            }
        });
    }

    /**
     * 如果要使用：PublishingExtension 扩展属性的话，必须要依赖于这个插件
     * plugins {
     * id "maven-publish"
     * }
     */
    private void publishTask(Project project) {
        // 在所有的配置都完成之后执行
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                // 1：获取插件版本信息
                PublishPluginExtension extension = project.getExtensions().create("publishExtension", PublishPluginExtension.class);
                Property<String> groupId = extension.getGroupId();
                Property<String> artifactId = extension.getArtifactId();
                Property<String> version = extension.getVersion();

                // 1：获取插件版本信息
                println("groupId:" + groupId.get() + " artifactId:" + artifactId.get() + " version:" + version.get());

                PublishingExtension publish = project.getExtensions().getByType(PublishingExtension.class);

                publish.getPublications().create("release", MavenPublication.class, maven -> {
                    // 设置属性
                    maven.setGroupId("com.android.helper");
                    maven.setArtifactId("publish");
                    maven.setVersion("1.0.0");

                    // 发布
                    maven.from(project.getComponents().getByName("release"));
                });
            }
        });
    }
}
