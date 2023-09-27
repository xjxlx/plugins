package com.android.common.utils;

import org.gradle.api.provider.Provider;

public class PrintlnUtil {
    public static void println(Provider<String> content) {
        System.out.println(content.get());
    }
}
