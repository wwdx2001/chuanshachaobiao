package com.sh3h.mobileutil.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;

public class GsonUtils {

  public static Gson getGson(ToNumberPolicy policy) {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setObjectToNumberStrategy(policy);
    return gsonBuilder.create();
  }

}
