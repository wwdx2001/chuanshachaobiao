package com.example.dataprovider3.entity;

import java.util.List;

public class CallForPaymentBackFillBean {

  private String key;
  private List<List<String>> strings;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public List<List<String>> getStrings() {
    return strings;
  }

  public void setStrings(List<List<String>> strings) {
    this.strings = strings;
  }
}
