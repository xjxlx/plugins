import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class VersionCataLogTask extends DefaultTask {

    private static String urlPath = "https://github.com/xjxlx/plugins/blob/39a705f313bec743e2c0437ce0f61a64a63c60f2/gradle/libs.versions.toml";

    FileUtil mFileUtil = new FileUtil();

//    @get:InputFile
//    abstract val mergedManifest: RegularFileProperty
//
//    @get:OutputFile
//    abstract val updatedManifest: RegularFileProperty

    @TaskAction
    public void taskAction() {
        SystemUtil.println("taskAction: ------> ");

        Project project = getProject();
        File rootDir = project.getRootDir();

        // 写入云端文件到gradle
        String configuration = mFileUtil.writeGradleFile(urlPath, new File(rootDir, "gradle" + File.separator + "libs2.versions.toml"));

        // 查找model的name
        File[] rootFiles = rootDir.listFiles();
        File settingsFile = mFileUtil.filterStart(rootFiles, "settings.gradle");
        List<String> settingContent = mFileUtil.readFile(settingsFile);
        List<String> listInclude = mFileUtil.filterStart(settingContent, "include");
        List<String> modelList = filterModel(listInclude);

        for (int i = 0; i < modelList.size(); i++) {
            String model = modelList.get(i);
            modelTask(new File(rootDir, model));
        }
    }

    private List<String> filterModel(List<String> includeList) {
        ArrayList<String> modelName = new ArrayList<>();
        if (includeList != null) {
            for (int i = 0; i < includeList.size(); i++) {
                String include = includeList.get(i);

                // split
                String[] split = include.split(":");
                if (split != null) {
                    for (int j = 0; j < split.length; j++) {
                        String splitContent = split[j];
                        if (splitContent.contains("(")) {
                            continue;
                        }
                        if (splitContent.contains(")")) {
                            splitContent = splitContent.replace("\")", "");
                        }
                        modelName.add(splitContent.trim());
                    }
                }
            }
        }
        return modelName;
    }

    private void modelTask(File model) {
        SystemUtil.println("model: " + model.getName());
        File filter = mFileUtil.filterStart(model.listFiles(), "build.gradle");
        List<String> modelGradleContent = mFileUtil.readFile(filter);
        // 获取依赖的内容
        List<String> dependenciesList = mFileUtil.filterStartAndEnd(modelGradleContent, "dependencies", "}");
        for (int i = 0; i < dependenciesList.size(); i++) {
            String dependencies = dependenciesList.get(i);
            SystemUtil.println("dependencies: " + dependencies);
        }
        SystemUtil.println("model find success !  \r\n");

        // 更改配置信息
        changeModelDependencies(dependenciesList);
    }

    private void changeModelDependencies(List<String> dependenciesList) {

    }

}
