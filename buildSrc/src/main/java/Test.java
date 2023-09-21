import org.gradle.internal.impldep.org.jsoup.Jsoup;
import org.gradle.internal.impldep.org.jsoup.nodes.Document;
import org.gradle.internal.impldep.org.jsoup.nodes.Element;
import org.json.JSONArray;
import org.json.JSONObject;

public class Test {
    private static String urlPath = "https://github.com/xjxlx/plugins/blob/39a705f313bec743e2c0437ce0f61a64a63c60f2/gradle/libs.versions.toml";

    public static void main(String[] args) {
//        readTxtFileBodyDoc();

        VersionCataLogTask versionCataLogTask = new VersionCataLogTask();
        versionCataLogTask.taskAction();
    }

    public static void readTxtFileBodyDoc() {
        String encoding = "UTF-8";
        try {
            //读取线上的html文件地址
            try {
                Document doc = Jsoup.connect(urlPath).get();
                Element body = doc.body();

                for (Element allElement : body.getAllElements()) {
                    String data = allElement.data();
                    if (!data.isEmpty()) {
                        if (data.startsWith("{") && data.endsWith("}")) {
                            JSONObject jsonObject = new JSONObject(data);
                            JSONObject jsonPayload = jsonObject.getJSONObject("payload");

                            JSONObject jsonBlob = jsonPayload.getJSONObject("blob");
                            JSONArray rawLines = jsonBlob.getJSONArray("rawLines");
                            println("rawLines: " + rawLines.toString());
                        }
                    }
                }
            } catch (Exception e) {
                println("body: error: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void println(String content) {
        System.out.println(content);
    }
}
