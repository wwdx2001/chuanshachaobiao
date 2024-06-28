package com.example.dataprovider3.entity;

import java.util.List;

public class DUBillServiceInfo {

    /**
     * id : 2
     * result : [{"iD":2,"i_RENWUBH":2,"i_ZHANGWUNY":201903,"d_PAIFASJ":1551974400000,"d_xiazaisj":1553133936000,"s_ZHUMA":"23D01011","s_LEIXING":"1","i_RENWUZT":1,"d_WANCHENGSJ":0,"s_BEIYONG1":"","s_BEIYONG2":"","s_BEIYONG3":""},{"iD":3,"i_RENWUBH":3,"i_ZHANGWUNY":201903,"d_PAIFASJ":1551974400000,"d_xiazaisj":1553133944000,"s_ZHUMA":"23A09007","s_LEIXING":"1","i_RENWUZT":1,"d_WANCHENGSJ":0,"s_BEIYONG1":"","s_BEIYONG2":"","s_BEIYONG3":""},{"iD":4,"i_RENWUBH":4,"i_ZHANGWUNY":201903,"d_PAIFASJ":1551974400000,"d_xiazaisj":1552875159000,"s_ZHUMA":"23A09005","s_LEIXING":"1","i_RENWUZT":1,"d_WANCHENGSJ":0,"s_BEIYONG1":"","s_BEIYONG2":"","s_BEIYONG3":""},{"iD":1,"i_RENWUBH":1,"i_ZHANGWUNY":201903,"d_PAIFASJ":1551974400000,"d_xiazaisj":1553145753000,"s_ZHUMA":"23A01001","s_LEIXING":"1","i_RENWUZT":1,"d_WANCHENGSJ":0,"s_BEIYONG1":"","s_BEIYONG2":"","s_BEIYONG3":""}]
     */

    private int id;
    private List<ResultBean> result;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * iD : 2
         * i_RENWUBH : 2
         * i_ZHANGWUNY : 201903
         * d_PAIFASJ : 1551974400000
         * d_xiazaisj : 1553133936000
         * s_ZHUMA : 23D01011
         * s_LEIXING : 1
         * i_RENWUZT : 1
         * d_WANCHENGSJ : 0
         * s_BEIYONG1 :
         * s_BEIYONG2 :
         * s_BEIYONG3 :
         */

        private int ID;
        private int I_RENWUBH;
        private int I_ZHANGWUNY;
        private long D_PAIFASJ;
        private long D_XIAZAISJ;
        private String S_ZHUMA;
        private String S_LEIXING;
        private int I_RENWUZT;
        private long D_WANCHENGSJ;
        private String S_BEIYONG1;
        private String S_BEIYONG2;
        private String S_BEIYONG3;

        public int getID() {
            return ID;
        }

        public void setID(int iD) {
            this.ID = iD;
        }

        public int getI_RENWUBH() {
            return I_RENWUBH;
        }

        public void setI_RENWUBH(int i_RENWUBH) {
            this.I_RENWUBH = i_RENWUBH;
        }

        public int getI_ZHANGWUNY() {
            return I_ZHANGWUNY;
        }

        public void setI_ZHANGWUNY(int i_ZHANGWUNY) {
            this.I_ZHANGWUNY = i_ZHANGWUNY;
        }

        public long getD_PAIFASJ() {
            return D_PAIFASJ;
        }

        public void setD_PAIFASJ(long d_PAIFASJ) {
            this.D_PAIFASJ = d_PAIFASJ;
        }

        public long getD_xiazaisj() {
            return D_XIAZAISJ;
        }

        public void setD_xiazaisj(long d_xiazaisj) {
            this.D_XIAZAISJ = d_xiazaisj;
        }

        public String getS_ZHUMA() {
            return S_ZHUMA;
        }

        public void setS_ZHUMA(String s_ZHUMA) {
            this.S_ZHUMA = s_ZHUMA;
        }

        public String getS_LEIXING() {
            return S_LEIXING;
        }

        public void setS_LEIXING(String s_LEIXING) {
            this.S_LEIXING = s_LEIXING;
        }

        public int getI_RENWUZT() {
            return I_RENWUZT;
        }

        public void setI_RENWUZT(int i_RENWUZT) {
            this.I_RENWUZT = i_RENWUZT;
        }

        public long getD_WANCHENGSJ() {
            return D_WANCHENGSJ;
        }

        public void setD_WANCHENGSJ(long d_WANCHENGSJ) {
            this.D_WANCHENGSJ = d_WANCHENGSJ;
        }

        public String getS_BEIYONG1() {
            return S_BEIYONG1;
        }

        public void setS_BEIYONG1(String s_BEIYONG1) {
            this.S_BEIYONG1 = s_BEIYONG1;
        }

        public String getS_BEIYONG2() {
            return S_BEIYONG2;
        }

        public void setS_BEIYONG2(String s_BEIYONG2) {
            this.S_BEIYONG2 = s_BEIYONG2;
        }

        public String getS_BEIYONG3() {
            return S_BEIYONG3;
        }

        public void setS_BEIYONG3(String s_BEIYONG3) {
            this.S_BEIYONG3 = s_BEIYONG3;
        }
    }
}
