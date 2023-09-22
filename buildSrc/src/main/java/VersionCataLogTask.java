import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
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
        SystemUtil.println("rootPath:" + rootDir.getAbsolutePath());

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
        boolean isKts = false;
        SystemUtil.println("model: " + model.getName());
        File modelGradle = mFileUtil.filterStart(model.listFiles(), "build.gradle");
        String modelGradleName = modelGradle.getName();
        SystemUtil.println("modelGradleName : " + modelGradleName);
        if (modelGradleName.endsWith(".kts")) {
            isKts = true;
        } else if (modelGradleName.endsWith(".gradle")) {
            isKts = false;
        }

        try {
            RandomAccessFile raf = new RandomAccessFile(modelGradle, "rw"); // 读写模式进行输出
            StringBuffer stringBuffer = new StringBuffer();
            byte[] buffer = new byte[1024];
            int offset = 0;

            while ((offset = raf.read(buffer, 0, 1024)) != -1) {
                String string = new String(buffer, "utf-8");
                stringBuffer.append(string);
            }
            SystemUtil.println("buffer: " + buffer.toString());


        } catch (IOException e) {
            SystemUtil.println("找不到gradle文件");
        }

        List<String> modelGradleContent = mFileUtil.readFile(modelGradle);
        // 获取依赖的内容
        List<String> dependenciesList = mFileUtil.filterStartAndEnd(modelGradleContent, "dependencies", "}");
        for (int i = 0; i < dependenciesList.size(); i++) {
            String dependencies = dependenciesList.get(i);
            // replace dependencies
            if (isKts) {
                SystemUtil.println("dependencies: " + dependencies);
                String[] split = dependencies.split("\"");

                if (split.length >= 1) {
                    String originalDependencies = split[1];
                    SystemUtil.println("originalDependencies: " + originalDependencies);
                    String[] splitChild = originalDependencies.split(":");
                    String group = splitChild[0];
                    String name = splitChild[1];
                    SystemUtil.println("[group]: " + group + " [name]: " + name);

                    String newDependencies = "libs." + (name.replace("-", "."));
                    SystemUtil.println("newDependencies: " + newDependencies);
                    String resultDependencies = split[0] + newDependencies + split[2];
                    SystemUtil.println("resultDependencies: " + resultDependencies);
                }
            }
        }
        SystemUtil.println("\r\n");

        // 更改配置信息
        changeModelDependencies(dependenciesList);
    }

    private void changeModelDependencies(List<String> dependenciesList) {

    }

}
