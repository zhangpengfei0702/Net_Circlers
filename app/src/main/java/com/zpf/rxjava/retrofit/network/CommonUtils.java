package com.zpf.rxjava.retrofit.network;

import java.util.Locale;

public class CommonUtils {
    public static boolean getLocale() {
        return Locale.getDefault().toString().contains("zh_") ? true : false;
    }
}
