package com.android.helper.utils

object TextUtils {

    fun isEmpty(content: String?): Boolean {
        return content?.isEmpty() ?: true
    }
}