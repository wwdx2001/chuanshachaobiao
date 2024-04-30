package com.example.dataprovider3.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class CallForPaymentTaskBean {

  /**
   * S_CEBENH : 554411
   * HUSHU : 6
   * WEIWANC : 6
   * YIWANC : 0
   * D_PAIFARQ : 2024/3/18 15:11:39
   */

  @Id
  private Long Id;
  private String S_CEBENH;
  private String HUSHU;
  private String WEIWANC;
  private String YIWANC;
  private String D_PAIFARQ;

  public CallForPaymentTaskBean(String s_CEBENH, String HUSHU, String WEIWANC, String YIWANC, String d_PAIFARQ) {
    S_CEBENH = s_CEBENH;
    this.HUSHU = HUSHU;
    this.WEIWANC = WEIWANC;
    this.YIWANC = YIWANC;
    D_PAIFARQ = d_PAIFARQ;
  }

  @Generated(hash = 719102617)
  public CallForPaymentTaskBean(Long Id, String S_CEBENH, String HUSHU, String WEIWANC, String YIWANC,
                                String D_PAIFARQ) {
      this.Id = Id;
      this.S_CEBENH = S_CEBENH;
      this.HUSHU = HUSHU;
      this.WEIWANC = WEIWANC;
      this.YIWANC = YIWANC;
      this.D_PAIFARQ = D_PAIFARQ;
  }

  @Generated(hash = 1728247350)
  public CallForPaymentTaskBean() {
  }

  public String getS_CEBENH() {
    return S_CEBENH;
  }

  public void setS_CEBENH(String S_CEBENH) {
    this.S_CEBENH = S_CEBENH;
  }

  public String getHUSHU() {
    return HUSHU;
  }

  public void setHUSHU(String HUSHU) {
    this.HUSHU = HUSHU;
  }

  public String getWEIWANC() {
    return WEIWANC;
  }

  public void setWEIWANC(String WEIWANC) {
    this.WEIWANC = WEIWANC;
  }

  public String getYIWANC() {
    return YIWANC;
  }

  public void setYIWANC(String YIWANC) {
    this.YIWANC = YIWANC;
  }

  public String getD_PAIFARQ() {
    return D_PAIFARQ;
  }

  public void setD_PAIFARQ(String D_PAIFARQ) {
    this.D_PAIFARQ = D_PAIFARQ;
  }

  public Long getId() {
      return this.Id;
  }

  public void setId(Long Id) {
      this.Id = Id;
  }
}
