package com.android.plugins;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import kotlin.text.Charsets;

public class Test {
    public void getTag() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("git", "describe", "--tags", "--abbrev=0");
            Process process = processBuilder.start();

            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder builder = new StringBuilder();


            while (bufferedReader.read() != -1) {
                builder.append(bufferedReader.readLine());
            }
            System.out.println("tag:" + builder.toString());
        } catch (IOException e) {
            System.out.println("git not find tag!");
        }

    }
}
//    private fun latestGitTag(): String {
//        val process = ProcessBuilder("git", "describe", "--tags", "--abbrev=0").start()
//        return process.inputStream.bufferedReader()
//        .use { bufferedReader ->
//        bufferedReader.readText()
//        .trim()
//        }
//        }

//    fun getContentForFile(file: File?): String {
//        var result = ""
//        if (file != null && file.exists()) {
//        var mInputStream: FileInputStream? = null
//        var mReader: BufferedReader? = null
//        try {
//        mInputStream = FileInputStream(file)
//        mReader = BufferedReader(InputStreamReader(mInputStream))
//        val builder = StringBuilder()
//        var timeLine: String?
//        while (mReader.readLine()
//        .also { timeLine = it } != null) {
//        builder.append(timeLine)
//        }
//        result = builder.toString()
//        } catch (e: java.lang.Exception) {
//        e.printStackTrace()
//        } finally {
//        try {
//        mInputStream?.close()
//        mReader?.close()
//        } catch (ignored: java.lang.Exception) {
//        }
//        }
//        }
//        return result
//        }