package com.android.common.utils;

import org.gradle.api.provider.Provider;

public class ConvertUtil {

    public static int convertVersion(Provider<String> version) {
        return Integer.parseInt(version.get());
    }

}
