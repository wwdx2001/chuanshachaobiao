package com.example.dataprovider3.entity;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xiaochao.dev@gamil.com
 * @date 2019/3/22 09:27
 */
public class DUBillServiceInfoResultBean extends LitePalSupport {

    private int myId;
    private int i_RENWUBH;
    private int i_ZHANGWUNY;
    private long d_PAIFASJ;
    private long d_xiazaisj;
    private String s_ZHUMA;
    private String s_LEIXING;
    private int i_RENWUZT;
    private int d_WANCHENGSJ;
    private String s_BEIYONG1;
    private String s_BEIYONG2;
    private String s_BEIYONG3;

    public int getID() {
        return myId;
    }

    public void setID(int iD) {
        this.myId = iD;
    }

    public int getI_RENWUBH() {
        return i_RENWUBH;
    }

    public void setI_RENWUBH(int i_RENWUBH) {
        this.i_RENWUBH = i_RENWUBH;
    }

    public int getI_ZHANGWUNY() {
        return i_ZHANGWUNY;
    }

    public void setI_ZHANGWUNY(int i_ZHANGWUNY) {
        this.i_ZHANGWUNY = i_ZHANGWUNY;
    }

    public long getD_PAIFASJ() {
        return d_PAIFASJ;
    }

    public void setD_PAIFASJ(long d_PAIFASJ) {
        this.d_PAIFASJ = d_PAIFASJ;
    }

    public long getD_xiazaisj() {
        return d_xiazaisj;
    }

    public void setD_xiazaisj(long d_xiazaisj) {
        this.d_xiazaisj = d_xiazaisj;
    }

    public String getS_ZHUMA() {
        return s_ZHUMA;
    }

    public void setS_ZHUMA(String s_ZHUMA) {
        this.s_ZHUMA = s_ZHUMA;
    }

    public String getS_LEIXING() {
        return s_LEIXING;
    }

    public void setS_LEIXING(String s_LEIXING) {
        this.s_LEIXING = s_LEIXING;
    }

    public int getI_RENWUZT() {
        return i_RENWUZT;
    }

    public void setI_RENWUZT(int i_RENWUZT) {
        this.i_RENWUZT = i_RENWUZT;
    }

    public int getD_WANCHENGSJ() {
        return d_WANCHENGSJ;
    }

    public void setD_WANCHENGSJ(int d_WANCHENGSJ) {
        this.d_WANCHENGSJ = d_WANCHENGSJ;
    }

    public String getS_BEIYONG1() {
        return s_BEIYONG1;
    }

    public void setS_BEIYONG1(String s_BEIYONG1) {
        this.s_BEIYONG1 = s_BEIYONG1;
    }

    public String getS_BEIYONG2() {
        return s_BEIYONG2;
    }

    public void setS_BEIYONG2(String s_BEIYONG2) {
        this.s_BEIYONG2 = s_BEIYONG2;
    }

    public String getS_BEIYONG3() {
        return s_BEIYONG3;
    }

    public void setS_BEIYONG3(String s_BEIYONG3) {
        this.s_BEIYONG3 = s_BEIYONG3;
    }

    public static DUBillServiceInfoResultBean toResult(DUBillServiceInfo.ResultBean resultBean) {
        if (resultBean == null) {
            return new DUBillServiceInfoResultBean();
        }
        DUBillServiceInfoResultBean duBillServiceInfoResultBean = new DUBillServiceInfoResultBean();
        duBillServiceInfoResultBean.setD_PAIFASJ(resultBean.getD_PAIFASJ());
        duBillServiceInfoResultBean.setD_WANCHENGSJ(resultBean.getD_WANCHENGSJ());
        duBillServiceInfoResultBean.setD_xiazaisj(resultBean.getD_xiazaisj());
        duBillServiceInfoResultBean.setI_RENWUBH(resultBean.getI_RENWUBH());
        duBillServiceInfoResultBean.setI_RENWUZT(resultBean.getI_RENWUZT());
        duBillServiceInfoResultBean.setI_ZHANGWUNY(resultBean.getI_ZHANGWUNY());
        duBillServiceInfoResultBean.setID(resultBean.getID());
        duBillServiceInfoResultBean.setS_BEIYONG1(resultBean.getS_BEIYONG1());
        duBillServiceInfoResultBean.setS_BEIYONG2(resultBean.getS_BEIYONG2());
        duBillServiceInfoResultBean.setS_BEIYONG3(resultBean.getS_BEIYONG3());
        duBillServiceInfoResultBean.setS_LEIXING(resultBean.getS_LEIXING());
        duBillServiceInfoResultBean.setS_ZHUMA(resultBean.getS_ZHUMA());
        return duBillServiceInfoResultBean;
    }

    public static List<DUBillServiceInfoResultBean> toResultList(List<DUBillServiceInfo.ResultBean> resultBeanList) {
        if (resultBeanList == null) {
            return new ArrayList();
        }
        List<DUBillServiceInfoResultBean> newList = new ArrayList<>();
        for (int i = 0; i < resultBeanList.size(); i++) {
            DUBillServiceInfoResultBean duBillServiceInfoResultBean = new DUBillServiceInfoResultBean();
            duBillServiceInfoResultBean.setD_PAIFASJ(resultBeanList.get(i).getD_PAIFASJ());
            duBillServiceInfoResultBean.setD_WANCHENGSJ(resultBeanList.get(i).getD_WANCHENGSJ());
            duBillServiceInfoResultBean.setD_xiazaisj(resultBeanList.get(i).getD_xiazaisj());
            duBillServiceInfoResultBean.setI_RENWUBH(resultBeanList.get(i).getI_RENWUBH());
            duBillServiceInfoResultBean.setI_RENWUZT(resultBeanList.get(i).getI_RENWUZT());
            duBillServiceInfoResultBean.setI_ZHANGWUNY(resultBeanList.get(i).getI_ZHANGWUNY());
            duBillServiceInfoResultBean.setID(resultBeanList.get(i).getID());
            duBillServiceInfoResultBean.setS_BEIYONG1(resultBeanList.get(i).getS_BEIYONG1());
            duBillServiceInfoResultBean.setS_BEIYONG2(resultBeanList.get(i).getS_BEIYONG2());
            duBillServiceInfoResultBean.setS_BEIYONG3(resultBeanList.get(i).getS_BEIYONG3());
            duBillServiceInfoResultBean.setS_LEIXING(resultBeanList.get(i).getS_LEIXING());
            duBillServiceInfoResultBean.setS_ZHUMA(resultBeanList.get(i).getS_ZHUMA());

//            List<DUBillServiceInfoResultBean> duBillServiceInfoResultBeans = LitePal.where("where s_ZHUMA = " + duBillServiceInfoResultBean.getS_ZHUMA())
//                    .find(DUBillServiceInfoResultBean.class);
//            if (duBillServiceInfoResultBeans != null && duBillServiceInfoResultBeans.size() > 0) {
//                if (duBillServiceInfoResultBeans.get(0).getI_RENWUZT() != duBillServiceInfoResultBean.getI_RENWUZT()) {
//                    newList.add(duBillServiceInfoResultBean);
//                }
//            }
            newList.add(duBillServiceInfoResultBean);
        }

        return newList;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DUBillServiceInfoResultBean that = (DUBillServiceInfoResultBean) o;
        return this.myId == that.myId &&
                i_RENWUBH == that.i_RENWUBH &&
                i_ZHANGWUNY == that.i_ZHANGWUNY &&
                d_PAIFASJ == that.d_PAIFASJ &&
//                i_RENWUZT == that.i_RENWUZT &&
                d_WANCHENGSJ == that.d_WANCHENGSJ &&
                Objects.equals(s_ZHUMA, that.s_ZHUMA) &&
                Objects.equals(s_LEIXING, that.s_LEIXING) &&
                Objects.equals(s_BEIYONG1, that.s_BEIYONG1) &&
                Objects.equals(s_BEIYONG2, that.s_BEIYONG2) &&
                Objects.equals(s_BEIYONG3, that.s_BEIYONG3);
    }

    @Override
    public int hashCode() {

        return Objects.hash(myId, i_RENWUBH, i_ZHANGWUNY, d_PAIFASJ, s_ZHUMA, s_LEIXING,/* i_RENWUZT, */d_WANCHENGSJ, s_BEIYONG1, s_BEIYONG2, s_BEIYONG3);
    }

    public DUBillServiceInfoResultBean() {
    }

    protected DUBillServiceInfoResultBean(Parcel in) {
        this.myId = in.readInt();
        this.i_RENWUBH = in.readInt();
        this.i_ZHANGWUNY = in.readInt();
        this.d_PAIFASJ = in.readLong();
        this.d_xiazaisj = in.readLong();
        this.s_ZHUMA = in.readString();
        this.s_LEIXING = in.readString();
        this.i_RENWUZT = in.readInt();
        this.d_WANCHENGSJ = in.readInt();
        this.s_BEIYONG1 = in.readString();
        this.s_BEIYONG2 = in.readString();
        this.s_BEIYONG3 = in.readString();
    }

    public static final Parcelable.Creator<DUBillServiceInfoResultBean> CREATOR = new Parcelable.Creator<DUBillServiceInfoResultBean>() {
        @Override
        public DUBillServiceInfoResultBean createFromParcel(Parcel source) {
            return new DUBillServiceInfoResultBean(source);
        }

        @Override
        public DUBillServiceInfoResultBean[] newArray(int size) {
            return new DUBillServiceInfoResultBean[size];
        }
    };
}
