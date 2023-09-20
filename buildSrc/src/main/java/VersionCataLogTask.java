import com.android.tools.r8.S;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import utils.FileUtil;
import utils.SystemUtil;

abstract public class VersionCataLogTask extends DefaultTask {

    public FileUtil mFileUtil = new FileUtil();

//    @get:InputFile
//    abstract val mergedManifest: RegularFileProperty
//
//    @get:OutputFile
//    abstract val updatedManifest: RegularFileProperty

    @TaskAction
    public void taskAction() {
        Project project = getProject();
        File root = project.getRootDir();
        String rootPath = root.getAbsolutePath();

        // 查找gradle文件，读取内容
        File versionFile = new File(rootPath, "gradle" + File.separator + "libs.versions.toml");
        String versionPath = versionFile.getAbsolutePath();

        // 查找model的name
        File[] rootFiles = root.listFiles();
        File settingsFile = mFileUtil.filterStart(rootFiles, "settings.gradle");
        List<String> settingContent = mFileUtil.readFile(settingsFile);
        List<String> listInclude = mFileUtil.filterStart(settingContent, "include");
        List<String> modelList = filterModel(listInclude);

        for (int i = 0; i < modelList.size(); i++) {
            String model = modelList.get(i);
            modelTask(new File(rootPath, model));
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
