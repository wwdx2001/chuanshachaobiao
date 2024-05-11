package com.example.dataprovider3.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class CallForPaymentBackFillDataBean implements Parcelable {

  @Id
  private Long id;
  private String V_CAOZUOR;
  private String V_CAOZUOSJ = "";
  private String V_RENWUM = "";
  private String V_RENWUID = "";
  private String V_QIANFEIYY1;
  private int V_QIANFEIYY1_POSITION;
  private int V_QIANFEIYY1_SELECT_POSITION;
  private String V_QIANFEIYY2;
  private int V_QIANFEIYY2_POSITION;
  private int V_QIANFEIYY2_SELECT_POSITION;
  private String V_ISYONGSHUI;
  private int V_ISYONGSHUI_POSITION;
  private String V_ISLIANXIYH;
  private int V_ISLIANXIYH_POSITION;
  private String V_ISZHUTICZ;
  private int V_ISZHUTICZ_POSITION;
  private String V_ISYYFF;
  private int V_ISYYFF_POSITION;
  private String V_ISXINXIBG;
  private int V_ISXINXIBG_POSITION;
  private String V_LIANXIR = "";
  private String V_LIANXIDH = "";
  private String V_BEIZHU = "";
  private String callForPayImages;
  private String OtherImages;
  private String voices;
  private String videos;
  private String videoUrl;
  private Boolean isSave;
  @Generated(hash = 1955007518)
public CallForPaymentBackFillDataBean(Long id, String V_CAOZUOR, String V_CAOZUOSJ, String V_RENWUM, String V_RENWUID,
                                      String V_QIANFEIYY1, int V_QIANFEIYY1_POSITION, int V_QIANFEIYY1_SELECT_POSITION, String V_QIANFEIYY2,
                                      int V_QIANFEIYY2_POSITION, int V_QIANFEIYY2_SELECT_POSITION, String V_ISYONGSHUI, int V_ISYONGSHUI_POSITION,
                                      String V_ISLIANXIYH, int V_ISLIANXIYH_POSITION, String V_ISZHUTICZ, int V_ISZHUTICZ_POSITION, String V_ISYYFF,
                                      int V_ISYYFF_POSITION, String V_ISXINXIBG, int V_ISXINXIBG_POSITION, String V_LIANXIR, String V_LIANXIDH,
                                      String V_BEIZHU, String callForPayImages, String OtherImages, String voices, String videos, String videoUrl,
                                      Boolean isSave) {
    this.id = id;
    this.V_CAOZUOR = V_CAOZUOR;
    this.V_CAOZUOSJ = V_CAOZUOSJ;
    this.V_RENWUM = V_RENWUM;
    this.V_RENWUID = V_RENWUID;
    this.V_QIANFEIYY1 = V_QIANFEIYY1;
    this.V_QIANFEIYY1_POSITION = V_QIANFEIYY1_POSITION;
    this.V_QIANFEIYY1_SELECT_POSITION = V_QIANFEIYY1_SELECT_POSITION;
    this.V_QIANFEIYY2 = V_QIANFEIYY2;
    this.V_QIANFEIYY2_POSITION = V_QIANFEIYY2_POSITION;
    this.V_QIANFEIYY2_SELECT_POSITION = V_QIANFEIYY2_SELECT_POSITION;
    this.V_ISYONGSHUI = V_ISYONGSHUI;
    this.V_ISYONGSHUI_POSITION = V_ISYONGSHUI_POSITION;
    this.V_ISLIANXIYH = V_ISLIANXIYH;
    this.V_ISLIANXIYH_POSITION = V_ISLIANXIYH_POSITION;
    this.V_ISZHUTICZ = V_ISZHUTICZ;
    this.V_ISZHUTICZ_POSITION = V_ISZHUTICZ_POSITION;
    this.V_ISYYFF = V_ISYYFF;
    this.V_ISYYFF_POSITION = V_ISYYFF_POSITION;
    this.V_ISXINXIBG = V_ISXINXIBG;
    this.V_ISXINXIBG_POSITION = V_ISXINXIBG_POSITION;
    this.V_LIANXIR = V_LIANXIR;
    this.V_LIANXIDH = V_LIANXIDH;
    this.V_BEIZHU = V_BEIZHU;
    this.callForPayImages = callForPayImages;
    this.OtherImages = OtherImages;
    this.voices = voices;
    this.videos = videos;
    this.videoUrl = videoUrl;
    this.isSave = isSave;
}
@Generated(hash = 830887086)
  public CallForPaymentBackFillDataBean() {
  }

  protected CallForPaymentBackFillDataBean(Parcel in) {
    if (in.readByte() == 0) {
      id = null;
    } else {
      id = in.readLong();
    }
    V_CAOZUOR = in.readString();
    V_CAOZUOSJ = in.readString();
    V_RENWUM = in.readString();
    V_RENWUID = in.readString();
    V_QIANFEIYY1 = in.readString();
    V_QIANFEIYY1_POSITION = in.readInt();
    V_QIANFEIYY2 = in.readString();
    V_QIANFEIYY2_POSITION = in.readInt();
    V_ISYONGSHUI = in.readString();
    V_ISYONGSHUI_POSITION = in.readInt();
    V_ISLIANXIYH = in.readString();
    V_ISLIANXIYH_POSITION = in.readInt();
    V_ISZHUTICZ = in.readString();
    V_ISZHUTICZ_POSITION = in.readInt();
    V_ISYYFF = in.readString();
    V_ISYYFF_POSITION = in.readInt();
    V_ISXINXIBG = in.readString();
    V_ISXINXIBG_POSITION = in.readInt();
    V_LIANXIR = in.readString();
    V_LIANXIDH = in.readString();
    V_BEIZHU = in.readString();
    callForPayImages = in.readString();
    OtherImages = in.readString();
    voices = in.readString();
    videos = in.readString();
  }

  public static final Creator<CallForPaymentBackFillDataBean> CREATOR = new Creator<CallForPaymentBackFillDataBean>() {
    @Override
    public CallForPaymentBackFillDataBean createFromParcel(Parcel in) {
      return new CallForPaymentBackFillDataBean(in);
    }

    @Override
    public CallForPaymentBackFillDataBean[] newArray(int size) {
      return new CallForPaymentBackFillDataBean[size];
    }
  };

  public Long getId() {
      return this.id;
  }
  public String getV_CAOZUOR() {
      return this.V_CAOZUOR;
  }
  public void setV_CAOZUOR(String V_CAOZUOR) {
      this.V_CAOZUOR = V_CAOZUOR;
  }
  public String getV_CAOZUOSJ() {
      return this.V_CAOZUOSJ;
  }
  public void setV_CAOZUOSJ(String V_CAOZUOSJ) {
      this.V_CAOZUOSJ = V_CAOZUOSJ;
  }
  public String getV_RENWUM() {
      return this.V_RENWUM;
  }
  public void setV_RENWUM(String V_RENWUM) {
      this.V_RENWUM = V_RENWUM;
  }
  public String getV_RENWUID() {
      return this.V_RENWUID;
  }
  public void setV_RENWUID(String V_RENWUID) {
      this.V_RENWUID = V_RENWUID;
  }
  public String getV_QIANFEIYY1() {
      return this.V_QIANFEIYY1;
  }
  public void setV_QIANFEIYY1(String V_QIANFEIYY1) {
      this.V_QIANFEIYY1 = V_QIANFEIYY1;
  }
  public String getV_QIANFEIYY2() {
      return this.V_QIANFEIYY2;
  }
  public void setV_QIANFEIYY2(String V_QIANFEIYY2) {
      this.V_QIANFEIYY2 = V_QIANFEIYY2;
  }
  public String getV_ISYONGSHUI() {
      return this.V_ISYONGSHUI;
  }
  public void setV_ISYONGSHUI(String V_ISYONGSHUI) {
      this.V_ISYONGSHUI = V_ISYONGSHUI;
  }
  public String getV_ISLIANXIYH() {
      return this.V_ISLIANXIYH;
  }
  public void setV_ISLIANXIYH(String V_ISLIANXIYH) {
      this.V_ISLIANXIYH = V_ISLIANXIYH;
  }
  public String getV_ISZHUTICZ() {
      return this.V_ISZHUTICZ;
  }
  public void setV_ISZHUTICZ(String V_ISZHUTICZ) {
      this.V_ISZHUTICZ = V_ISZHUTICZ;
  }
  public String getV_ISYYFF() {
      return this.V_ISYYFF;
  }
  public void setV_ISYYFF(String V_ISYYFF) {
      this.V_ISYYFF = V_ISYYFF;
  }
  public String getV_ISXINXIBG() {
      return this.V_ISXINXIBG;
  }
  public void setV_ISXINXIBG(String V_ISXINXIBG) {
      this.V_ISXINXIBG = V_ISXINXIBG;
  }
  public String getV_LIANXIR() {
      return this.V_LIANXIR;
  }
  public void setV_LIANXIR(String V_LIANXIR) {
      this.V_LIANXIR = V_LIANXIR;
  }
  public String getV_LIANXIDH() {
      return this.V_LIANXIDH;
  }
  public void setV_LIANXIDH(String V_LIANXIDH) {
      this.V_LIANXIDH = V_LIANXIDH;
  }
  public String getV_BEIZHU() {
      return this.V_BEIZHU;
  }
  public void setV_BEIZHU(String V_BEIZHU) {
      this.V_BEIZHU = V_BEIZHU;
  }
public int getV_QIANFEIYY1_POSITION() {
    return this.V_QIANFEIYY1_POSITION;
}
public void setV_QIANFEIYY1_POSITION(int V_QIANFEIYY1_POSITION) {
    this.V_QIANFEIYY1_POSITION = V_QIANFEIYY1_POSITION;
}
public int getV_QIANFEIYY2_POSITION() {
    return this.V_QIANFEIYY2_POSITION;
}
public void setV_QIANFEIYY2_POSITION(int V_QIANFEIYY2_POSITION) {
    this.V_QIANFEIYY2_POSITION = V_QIANFEIYY2_POSITION;
}
public int getV_ISYONGSHUI_POSITION() {
    return this.V_ISYONGSHUI_POSITION;
}
public void setV_ISYONGSHUI_POSITION(int V_ISYONGSHUI_POSITION) {
    this.V_ISYONGSHUI_POSITION = V_ISYONGSHUI_POSITION;
}
public int getV_ISLIANXIYH_POSITION() {
    return this.V_ISLIANXIYH_POSITION;
}
public void setV_ISLIANXIYH_POSITION(int V_ISLIANXIYH_POSITION) {
    this.V_ISLIANXIYH_POSITION = V_ISLIANXIYH_POSITION;
}
public int getV_ISZHUTICZ_POSITION() {
    return this.V_ISZHUTICZ_POSITION;
}
public void setV_ISZHUTICZ_POSITION(int V_ISZHUTICZ_POSITION) {
    this.V_ISZHUTICZ_POSITION = V_ISZHUTICZ_POSITION;
}
public int getV_ISYYFF_POSITION() {
    return this.V_ISYYFF_POSITION;
}
public void setV_ISYYFF_POSITION(int V_ISYYFF_POSITION) {
    this.V_ISYYFF_POSITION = V_ISYYFF_POSITION;
}
public int getV_ISXINXIBG_POSITION() {
    return this.V_ISXINXIBG_POSITION;
}
public void setV_ISXINXIBG_POSITION(int V_ISXINXIBG_POSITION) {
    this.V_ISXINXIBG_POSITION = V_ISXINXIBG_POSITION;
}
public String getCallForPayImages() {
    return this.callForPayImages;
}
public void setCallForPayImages(String callForPayImages) {
    this.callForPayImages = callForPayImages;
}
public String getOtherImages() {
    return this.OtherImages;
}
public void setOtherImages(String OtherImages) {
    this.OtherImages = OtherImages;
}
public String getVoices() {
    return this.voices;
}
public void setVoices(String voices) {
    this.voices = voices;
}
public String getVideos() {
    return this.videos;
}
public void setVideos(String videos) {
    this.videos = videos;
}
public void setId(Long id) {
    this.id = id;
}

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    if (id == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeLong(id);
    }
    dest.writeString(V_CAOZUOR);
    dest.writeString(V_CAOZUOSJ);
    dest.writeString(V_RENWUM);
    dest.writeString(V_RENWUID);
    dest.writeString(V_QIANFEIYY1);
    dest.writeInt(V_QIANFEIYY1_POSITION);
    dest.writeString(V_QIANFEIYY2);
    dest.writeInt(V_QIANFEIYY2_POSITION);
    dest.writeString(V_ISYONGSHUI);
    dest.writeInt(V_ISYONGSHUI_POSITION);
    dest.writeString(V_ISLIANXIYH);
    dest.writeInt(V_ISLIANXIYH_POSITION);
    dest.writeString(V_ISZHUTICZ);
    dest.writeInt(V_ISZHUTICZ_POSITION);
    dest.writeString(V_ISYYFF);
    dest.writeInt(V_ISYYFF_POSITION);
    dest.writeString(V_ISXINXIBG);
    dest.writeInt(V_ISXINXIBG_POSITION);
    dest.writeString(V_LIANXIR);
    dest.writeString(V_LIANXIDH);
    dest.writeString(V_BEIZHU);
    dest.writeString(callForPayImages);
    dest.writeString(OtherImages);
    dest.writeString(voices);
    dest.writeString(videos);
  }
public Boolean getIsSave() {
    return this.isSave;
}
public void setIsSave(Boolean isSave) {
    this.isSave = isSave;
}
public String getVideoUrl() {
    return this.videoUrl;
}
public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
}
public int getV_QIANFEIYY2_SELECT_POSITION() {
    return this.V_QIANFEIYY2_SELECT_POSITION;
}
public void setV_QIANFEIYY2_SELECT_POSITION(int V_QIANFEIYY2_SELECT_POSITION) {
    this.V_QIANFEIYY2_SELECT_POSITION = V_QIANFEIYY2_SELECT_POSITION;
}
public int getV_QIANFEIYY1_SELECT_POSITION() {
    return this.V_QIANFEIYY1_SELECT_POSITION;
}
public void setV_QIANFEIYY1_SELECT_POSITION(int V_QIANFEIYY1_SELECT_POSITION) {
    this.V_QIANFEIYY1_SELECT_POSITION = V_QIANFEIYY1_SELECT_POSITION;
}
}
