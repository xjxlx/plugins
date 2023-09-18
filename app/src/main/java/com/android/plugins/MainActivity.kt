package com.android.plugins

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val test = Test()
        test.getTag()
    }
}

/**
 * 获取 git 仓库中最新的 tag作为版本号
 */
private fun latestGitTag(): String {
    val process = ProcessBuilder("git", "describe", "--tags", "--abbrev=0").start()
    return process.inputStream.bufferedReader()
        .use { bufferedReader ->
            bufferedReader.readText()
                .trim()
        }
}
